package connectornew;

import connectornew.messages.CTI;
import connectornew.messages.agent_events.*;
import connectornew.messages.miscellaneous.*;
import connectornew.messages.session_management.OpenConf;
import connectornew.messages.session_management.OpenReq;
import org.apache.commons.codec.binary.Hex;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;

/**
 * Created by srg on 14.07.16.
 */
public class TransportStack extends Thread {
    static int readCount = 0;
    static int writeCount = 0;
    private static Logger logger = new connectornew.Logger("TRANSPORT");
    private final byte[] HEART_BEAT_REQUEST = ClientDescriptor.hexStringToByteArray("000000040000000500000001");
    private final byte[] HEART_BEAT_RESPONSE = ClientDescriptor.hexStringToByteArray("000000040000000600000001");
    private Queue<byte[]> inputMessages = new ConcurrentLinkedQueue<byte[]>();
    private Queue<byte[]> outputMessages = new ConcurrentLinkedQueue<byte[]>();
    private Socket clientSocket;


    //Constructors
    public TransportStack(Socket s) {
        this.clientSocket = s;
        logger.setLevel(Level.INFO);
    }

    //Methods
    public static byte[] read(Socket s, boolean shouldWaiting) throws IOException {
        InputStream fromClient = s.getInputStream();
//        System.out.println("available " + fromClient.available());
        if (!shouldWaiting && !(fromClient.available() > 0)) {
//            logger.log(Level.INFO, "nothing to read");
            return null;
        }

        long messageLength = 0L;
        long messageType = 0L;
        int b;
        byte[] messageLengthInByte = new byte[4];
        byte[] messageTypeInByte = new byte[4];
        // определение длинны сообщения

        int counter = 0;
        while (counter < messageLengthInByte.length) {
            b = fromClient.read();
            messageLengthInByte[counter] = (byte) b;
//            System.out.print(String.format("%02x", b & 0xFF));
            counter++;
        }
        messageLength = convertByteArraySize4ToLong(messageLengthInByte);
        //надо ли проверять размер сообщения?
        if (messageLength <= 4329) {
//            logger.log(Level.INFO, String.format("message lengths %s - correct", messageLength));
        } else logger.log(Level.INFO, String.format("message lengths %s - incorrect", messageLength));
        // определение типа сообщения
        counter = 0;
        while (counter < messageTypeInByte.length) {
            b = fromClient.read();
            messageTypeInByte[counter] = (byte) b;
//            System.out.print(String.format("%02x", b & 0xFF));
            counter++;
        }
        messageType = convertByteArraySize4ToLong(messageTypeInByte);
//        logger.log(Level.INFO, String.format("message type %s", messageType));
        //формирование сообщения
        int offset = messageLengthInByte.length + messageTypeInByte.length;
        byte[] resultMessage = new byte[(int) messageLength + offset];
        System.arraycopy(messageLengthInByte, 0, resultMessage, 0, messageLengthInByte.length);
        System.arraycopy(messageTypeInByte, 0, resultMessage, messageLengthInByte.length, messageTypeInByte.length);
        //сдвиг, учитывающие начальные сообщения
        counter = 0;
        while (counter < messageLength) {
            b = fromClient.read();
            resultMessage[counter + offset] = (byte) b;
//            System.out.print(String.format("%02x", b & 0xFF));
            counter++;
        }
        s.setSoLinger(true, 0);


        return resultMessage;
    }

    public static void write(Socket s, byte[] message) throws IOException {
        OutputStream toClient = s.getOutputStream();
        toClient.write(message);
        toClient.flush();
        s.setSoLinger(true, 0);
    }

    private static long convertByteArraySize4ToLong(byte[] variable) {
        long value = 0;
        for (int i = 0; i < variable.length; i++) {
            value = (value << 4) + (variable[i] & 0xff);
        }
        return value;
    }

    //Getters and setters
    public Queue<byte[]> getInputMessages() {
        return inputMessages;
    }

    public void setInputMessages(Queue<byte[]> inputMessages) {
        this.inputMessages = inputMessages;
    }

    public Queue<byte[]> getOutputMessages() {
        return outputMessages;
    }

    public void setOutputMessages(Queue<byte[]> outputMessages) {
        this.outputMessages = outputMessages;
    }

    public int getReadCount() {
        return readCount;
    }

    public int getWriteCount() {
        return writeCount;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        logger.log(Level.INFO, "Start transport stack thread");
        super.run();
        byte[] inputMessage;
        byte[] outputMessage;
        String direction;
        while (!isInterrupted()) {
            try {
                inputMessage = read(clientSocket, false);

                if (inputMessage != null && Arrays.equals(inputMessage, HEART_BEAT_REQUEST)) {
                    logger.log(Level.SEVERE, String.format("GOT HEART_BEAT_REQUEST"));
                    try {
                        write(clientSocket, HEART_BEAT_RESPONSE);
                        inputMessage = null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    logger.log(Level.SEVERE, String.format("SENT HEART_BEAT_RESPONSE"));
                }

                if (inputMessage != null) {
                    inputMessages.add(inputMessage);
                    readCount++;
                    logger.log(Level.FINER, String.format("READ MESSAGE FROM NET TYPE #" + ByteBuffer.wrap(inputMessage, 4, 4).getInt() + " : " + Hex.encodeHexString(inputMessage)));
                    direction = " CTI-OS -> CTI ";
                    int code = ByteBuffer.wrap(inputMessage, 4, 4).getInt();
                    switch (code) {
                        case CTI.MSG_OPEN_REQ: {
                            OpenReq openReq = OpenReq.deserializeMessage(inputMessage);
                            logger.log(Level.INFO, openReq.toString());
                            break;
                        }
                        case CTI.MSG_CLIENT_EVENT_REPORT_REQ: {
                            ClientEventReportReq clientEventReportReq = ClientEventReportReq.deserializeMessage(inputMessage);
                            logger.log(Level.INFO, direction + clientEventReportReq.toString());
                            break;
                        }
                        case CTI.MSG_QUERY_AGENT_STATE_REQ: {
                            QueryAgentStateReq queryAgentStateReq = QueryAgentStateReq.deserializeMessage(inputMessage);
                            logger.log(Level.INFO, direction + queryAgentStateReq.toString());
                            break;
                        }
                        case CTI.MSG_SET_AGENT_STATE_REQ: {
                            SetAgentStateReq setAgentStateReq = SetAgentStateReq.deserializeMessage(inputMessage);
                            logger.log(Level.INFO, direction + setAgentStateReq.toString());
                            break;
                        }
                        case CTI.MSG_CONFIG_REQUEST_KEY_EVENT: {
                            ConfigRequestKeyEvent configRequestKeyEvent = ConfigRequestKeyEvent.deserializeMessage(inputMessage);
                            logger.log(Level.INFO, direction + configRequestKeyEvent.toString());
                            break;
                        }
                        case CTI.MSG_CONFIG_REQUEST_EVENT: {
                            ConfigRequestEvent configRequestEvent = ConfigRequestEvent.deserializeMessage(inputMessage);
                            logger.log(Level.INFO, direction + configRequestEvent.toString());
                            break;
                        }

                        default: {
                            logger.log(Level.INFO, "unknown input message #" + code + " MESSAGE: " + Hex.encodeHexString(inputMessage));
                        }
                    }
                }

                outputMessage = outputMessages.poll();
                if (outputMessage != null) {
                    int code = ByteBuffer.wrap(outputMessage, 4, 4).getInt();
                    direction = " CTI-OS <- CTI ";
                    switch (code) {
                        case CTI.MSG_OPEN_CONF: {
                            OpenConf openReq = OpenConf.deserializeMessage(outputMessage);
                            logger.log(Level.INFO, direction + openReq.toString());
                            break;
                        }
                        case CTI.MSG_SYSTEM_EVENT: {
                            SystemEvent systemEvent = SystemEvent.deserializeMessage(outputMessage);
                            logger.log(Level.INFO, direction + systemEvent.toString());
                            break;
                        }
                        case CTI.MSG_CLIENT_EVENT_REPORT_CONF: {
                            ClientEventReportConf clientEventReportConf = ClientEventReportConf.deserializeMessage(outputMessage);
                            logger.log(Level.INFO, direction + clientEventReportConf.toString());
                            break;
                        }
                        case CTI.MSG_QUERY_AGENT_STATE_CONF: {
                            QueryAgentStateConf queryAgentStateConf = QueryAgentStateConf.deserializeMessage(outputMessage);
                            logger.log(Level.INFO, direction + queryAgentStateConf.toString());
                            break;
                        }
                        case CTI.MSG_AGENT_TEAM_CONFIG_EVENT: {
                            AgentTeamConfigEvent agentTeamConfigEvent = AgentTeamConfigEvent.deserializeMessage(outputMessage);
                            logger.log(Level.INFO, direction + agentTeamConfigEvent.toString());
                            break;
                        }
                        case CTI.MSG_CONFIG_KEY_EVENT: {
                            ConfigKeyEvent configKeyEvent = ConfigKeyEvent.deserializeMessage(outputMessage);
                            logger.log(Level.INFO, direction + configKeyEvent.toString());
                            break;
                        }
                        case CTI.MSG_CONFIG_BEGIN_EVENT: {
                            ConfigBeginEvent configBeginEvent = ConfigBeginEvent.deserializeMessage(outputMessage);
                            logger.log(Level.INFO, direction + configBeginEvent.toString());
                            break;
                        }
                        case CTI.MSG_CONFIG_END_EVENT: {
                            ConfigEndEvent configEndEvent = ConfigEndEvent.deserializeMessage(outputMessage);
                            logger.log(Level.INFO, direction + configEndEvent.toString());
                            break;
                        }
                        case CTI.MSG_CONFIG_SKILL_GROUP_EVENT: {
                            ConfigSkillGroupEvent configSkillGroupEvent = ConfigSkillGroupEvent.deserializeMessage(outputMessage);
                            logger.log(Level.INFO, direction + configSkillGroupEvent.toString());
                            break;
                        }
                        case CTI.MSG_CONFIG_AGENT_EVENT: {
                            ConfigAgentEvent configAgentEvent = ConfigAgentEvent.deserializeMessage(outputMessage);
                            logger.log(Level.INFO, direction + configAgentEvent.toString());
                            break;
                        }
                        default: {
                            logger.log(Level.INFO, "unknown output message #" + code + " MESSAGE: " + Hex.encodeHexString(outputMessage));
                        }
                    }

                    write(clientSocket, outputMessage);
                    logger.log(Level.FINER, String.format("WROTE MESSAGE TO NET TYPE #" + ByteBuffer.wrap(outputMessage, 4, 4).getInt() + " : " + Hex.encodeHexString(outputMessage)));
                    writeCount++;
                }
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    clientSocket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                interrupt();
                break;
            }
        }

    }
}

package connectornew.connector;

import connectornew.ClientDescriptor;
import connectornew.ScenarioPairContainer;
import connectornew.TransportStack;
import connectornew.messages.*;
import connectornew.messages.session_management.CloseConf;
import connectornew.messages.session_management.CloseReq;
import org.apache.commons.codec.binary.Hex;

import javax.management.Query;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Map;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by srg on 06.09.16.
 */
public class ClientsExecutor implements Runnable {

    TransportStack stack;
    Map<String, Object> agentsScenario;
    Map<String, ClientDescriptor> agentList;
    Logger logger = Logger.getLogger("CLIENT EXECUTOR");


    //Constructors
    public ClientsExecutor() {
    }

    public ClientsExecutor(TransportStack stack, Map<String, Object> agentsScenario, Map<String, ClientDescriptor> agentList) {
        this.stack = stack;
        this.agentsScenario = agentsScenario;
        this.agentList = agentList;
    }

    @Override
    public void run() {
        Queue<byte[]> inputMessages = stack.getInputMessages();
        Queue<byte[]> outputMessages = stack.getOutputMessages();
        System.out.println("IN CLIENT");
        a:
        while (!Thread.currentThread().isInterrupted()) {
            byte[] message = inputMessages.peek();
            if (message == null) continue;
            System.out.println("CLIENT POOL SIZE " + agentList.size());
            System.out.println("INPUT CLIENT MESSAGE #" + ByteBuffer.wrap(message, 4, 4).getInt() + " " + Hex.encodeHexString(message));
            switch (Header.parseMessageType(message)) {
                case CTI.MSG_QUERY_AGENT_STATE_REQ: {
                    //десериализация сообщения
                    QueryAgentStateReq queryAgentStateReq = QueryAgentStateReq.deserializeMessage(message);
                    //получение идентификатора пользователя
                    String agentId = queryAgentStateReq.getFloatingFields().get(0).getData();
                    // создание объекта, описывающего состояние объекта
                    agentList.put(agentId, new ClientDescriptor());
                    logger.log(Level.INFO, queryAgentStateReq.toString());
                    executeCommandOfScenario(inputMessages, outputMessages, agentId);
                    break;
                }
                case CTI.MSG_SET_AGENT_STATE_REQ: {
                    SetAgentStateReq agentStateReq = SetAgentStateReq.deserializeMessage(message);
                    System.out.println(agentStateReq);
                    logger.log(Level.INFO, agentStateReq.toString());

                    if (agentStateReq.getFloatingFields().isEmpty()) {
                        inputMessages.remove();
                        try {
                            SetAgentStateConf setAgentStateConf = new SetAgentStateConf();
                            setAgentStateConf.setInvokeID(agentStateReq.getInvokeID());
                            outputMessages.add(setAgentStateConf.serializeMessage());
                            logger.log(Level.INFO, setAgentStateConf.toString());
                        } catch (Exception e) {
                            logger.log(Level.SEVERE, e.getMessage());
                        }
                    } else {
                        inputMessages.remove();
                        try {
                            for (FloatingField f : agentStateReq.getFloatingFields()) {
                                //получение agentId
                                if (f.getTag() == 5) {
                                    agentList.get(f.getData()).setAgentState(AgentStates.AGENT_STATE_LOGIN);
                                    System.out.println(agentList.get(f.getData()));
                                }
                            }
                            SetAgentStateConf setAgentStateConf = new SetAgentStateConf();
                            setAgentStateConf.setInvokeID(agentStateReq.getInvokeID());
                            outputMessages.add(setAgentStateConf.serializeMessage());
                            logger.log(Level.INFO, setAgentStateConf.toString());
                        } catch (Exception e) {
                            logger.log(Level.SEVERE, e.getMessage());
                        }
                    }
                    break;
                }
                case CTI.MSG_AGENT_DESK_SETTINGS_REQ: {
                    AgentDeskSettingsReq agentDeskSettingsReq = AgentDeskSettingsReq.deserializeMessage(message);
                    String agentId = agentDeskSettingsReq.getFloatingFields().get(0).getData();
                    logger.log(Level.INFO, agentDeskSettingsReq.toString());
                    executeCommandOfScenario(inputMessages, outputMessages, agentId);
                    break;
                }
                case CTI.MSG_CLIENT_EVENT_REPORT_REQ: {
                    ClientEventReportReq clientEventReportReq = ClientEventReportReq.deserializeMessage(message);
                    logger.log(Level.INFO, clientEventReportReq.toString());
                    inputMessages.remove();
                    try {
                        ClientEventReportConf clientEventReportConf = new ClientEventReportConf();
                        clientEventReportConf.setInvokeID(clientEventReportReq.getInvokeID());
                        outputMessages.add(clientEventReportConf.serializeMessage());
                        logger.log(Level.INFO, clientEventReportConf.toString());
                    } catch (Exception e) {
                        logger.log(Level.SEVERE, e.getMessage());
                    }
                    break;
                }
                case CTI.MSG_CLOSE_REQ: {
                    CloseReq closeReq = CloseReq.deserializeMessage(message);
                    logger.log(Level.INFO, closeReq.toString());
                    inputMessages.remove();
                    try {
                        CloseConf closeConf = new CloseConf(closeReq.getInvokeId());
                        outputMessages.add(closeConf.serializeMessage());
                        logger.log(Level.INFO, closeConf.toString());
                        break a;
                    } catch (Exception e) {
                        logger.log(Level.SEVERE, e.getMessage());
                    }
                    break;
                }
                default: {
                    System.out.println("REMOVE " + Hex.encodeHexString(inputMessages.remove()));
                    break;
                }
            }
        }
    }

    private void executeCommandOfScenario(Queue<byte[]> inputMessages, Queue<byte[]> outputMessages, String agentId) {
        ScenarioPairContainer spc = ScenarioPairContainer
                .getCommand(agentsScenario, agentList.get(agentId).getClientState(), 0);
        System.out.println("agentId = " + agentId + " PREGET: ");
        //обработка метода GET
        ScriptExecutorHolder.execute(spc,
                inputMessages,
                outputMessages,
                agentList.get(agentId),
                logger);
        //отправка POST сообщений, до появления GET
        spc = ScenarioPairContainer
                .getCommand(agentsScenario, agentList.get(agentId).getClientState(), 0);
        System.out.println("agentId = " + agentId + " PREPOST: ");
        ScriptExecutorHolder.execute(spc,
                inputMessages,
                outputMessages,
                agentList.get(agentId),
                logger);
    }
}

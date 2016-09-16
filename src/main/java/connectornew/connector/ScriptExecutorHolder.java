package connectornew.connector;

import connectornew.ClientDescriptor;
import connectornew.Logger;
import connectornew.ScenarioPairContainer;
import connectornew.VariablesDescriptor;
import org.apache.commons.codec.binary.Hex;

import java.nio.ByteBuffer;
import java.util.*;
import java.util.logging.Level;

/**
 * Created by srg on 19.08.16.
 */
public class ScriptExecutorHolder {
    public static void execute(ScenarioPairContainer spc, Queue<byte[]> inputMessages, Queue<byte[]> outputMessages, ClientDescriptor clientDescriptor, Logger logger) {
        byte[] inputMessage;

        switch (spc.getMethod()) {
            //метод GET
            case 0: {
                logger.log(Level.FINE, String.format("GET COMMAND"));
                inputMessage = null;
                long startRead = System.nanoTime();
                //ожидание получения
                int tmp = 0;
                while (inputMessage == null) {
//                    try {
//                        Thread.currentThread().sleep(500);
//                        tmp++;
//                    } catch (Exception e) {
//                    }
                    inputMessage = inputMessages.poll();
//                    if (tmp > 7) return;
                }
                logger.log(Level.FINEST, "INPUT MESSAGE: " + Hex.encodeHexString(inputMessage));
                logger.log(Level.FINEST, String.format("Reading time from buffer: %f ms", (double) ((System.nanoTime() - startRead) * 0.000001)));
                logger.log(Level.FINEST, String.format("GET: Input message in hex: %s", Hex.encodeHexString(inputMessage)));
                if (spc.getCommand() instanceof String) {
                    //извлекаются переменные из "компилированного" сценария
                    for (VariablesDescriptor varDesc : (List<VariablesDescriptor>) spc.getVariables()) {
                        switch (varDesc.getType()) {
                            // вычленить переменную
                            case 1: {
                                //запись переменной, извлеченной из полученного от клиента сообщения в ClientDescriptor
                                clientDescriptor.getVariableContainer()
                                        .put(varDesc.getName(), Arrays.copyOfRange(inputMessage, varDesc.getBeginPosition(), varDesc.getBeginPosition() + varDesc.getLength()));
                                break;
                            }
                            default: {
                                logger.log(Level.WARNING, "Unknown command GET");
                                break;
                            }
                        }
                    }
                    byte[] resultMessage = assemblyMessageInByte(spc, clientDescriptor);
                    logger.log(Level.INFO, String.format("GET: Expected message #%s in hex: %s", ByteBuffer.wrap(resultMessage, 4, 4).getInt(), Hex.encodeHexString(resultMessage)));
                    logger.log(Level.INFO, String.format("GET: Is received message equals to processed message: %s", Arrays.equals(inputMessage, resultMessage)));
                    break;
                } else if (spc.getCommand() instanceof byte[]) {
                    byte[] resultMessage = (byte[]) spc.getCommand();
                    logger.log(Level.FINEST, String.format("GET: Loaded message in hex from scenario: %s", Hex.encodeHexString(resultMessage)));
                    logger.log(Level.INFO, String.format("GET: Expected message #%s in hex: %s", ByteBuffer.wrap(resultMessage, 4, 4).getInt(), Hex.encodeHexString(resultMessage)));
                    logger.log(Level.INFO, String.format("GET: Is received message equals to processed message: %s", Arrays.equals(inputMessage, resultMessage)));
                    break;
                }
            }
            //метод PUT
            case 1: {
                logger.log(Level.FINE, String.format("PUT COMMAND"));
                for (VariablesDescriptor varDesc : (List<VariablesDescriptor>) spc.getVariables()) {
                    if (varDesc.getType() == 3) {
                        //сгенерировать переменную по имени
                        byte[] var = null;
                        if (varDesc.getName().equals("ICMCentralControllerTimer"))
                            var = ByteBuffer.allocate(varDesc.getLength()).putInt((int) (System.currentTimeMillis() / 1000)).array();
                        clientDescriptor.getVariableContainer().put(varDesc.getName(), var);
                        logger.log(Level.FINEST, String.format("TIME IN HEX: " + Hex.encodeHexString(var)));
                    }
                }
                    /*проверяет, если команда представлена в сценарии в byte[], она извлекаетя из сценария.
                    в ином случае команда собирается изх переменных и блоков, представленый в byte[]
                    */
                byte[] resultMessage;
                if (spc.getCommand() instanceof byte[]) {
                    resultMessage = (byte[]) spc.getCommand();
                    logger.log(Level.FINE, String.format("PUT: Processed message in hex: %s", Hex.encodeHexString(resultMessage)));
                } else {
                    resultMessage = assemblyMessageInByte(spc, clientDescriptor);
                    logger.log(Level.FINE, String.format("PUT: Processed message in hex: %s", Hex.encodeHexString(resultMessage)));
                }

                long startWrite = System.nanoTime();
                outputMessages.add(resultMessage);
                logger.log(Level.FINEST, String.format("Writing time to buffer: %f ms", (double) ((System.nanoTime() - startWrite) * 0.000001)));
                logger.log(Level.FINEST, String.format("PUT: Sent message"));
                break;
            }
            default: {
                logger.log(Level.WARNING, String.valueOf("Unknown command in scenario"));
                break;
            }
        }
    }

    public static byte[] assemblyMessageInByte(ScenarioPairContainer spc, ClientDescriptor clientDescriptor) {
        //определение длины и сборка полученненного сообщения
        int messageLength = 0;
        int iterator = 0;
        for (Object arr : spc.getInBytes()) {
            byte[] array = ((byte[]) arr);
            //получение значения переменной из ClientDescriptor
            if (array.length == 0) {
                a:
                for (VariablesDescriptor vd : (List<VariablesDescriptor>) spc.getVariables()) {
                    if (vd.getPositionInArray() == iterator) {
                        messageLength += vd.getLength();
                        break a;
                    }
                }
            } else {
                messageLength += ((byte[]) arr).length;
            }

            iterator++;
        }
        byte[] resultMessage = new byte[messageLength];
        int offset = 0;
        iterator = 0;
        for (Object arr : spc.getInBytes()) {
            byte[] array = ((byte[]) arr);
            //получение значения переменной из ClientDescriptor
            if (array.length == 0) {
                a:
                for (VariablesDescriptor vd : (List<VariablesDescriptor>) spc.getVariables()) {
                    if (vd.getPositionInArray() == iterator) {
                        array = clientDescriptor.getVariableContainer().get(vd.getName());
                        break a;
                    }
                }
            }
            //сборка сообщения
            System.arraycopy(array, 0, resultMessage, offset, array.length);
            offset += array.length;
            iterator++;
        }
        return resultMessage;
    }
}

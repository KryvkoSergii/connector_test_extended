package connectornew.connector;

import connectornew.ClientDescriptor;
import connectornew.ScenarioPairContainer;
import connectornew.TransportStack;

import java.net.Socket;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by srg on 05.07.16.
 */
public class ExecutorThread implements Runnable {
    private Logger logger;
    private boolean isConnectionEstablished = false;
    private TransportStack stack;
    private Map<String, Object> connectionInitiatingScenario;
    private ClientDescriptor scriptExecutingStateHolder;

    //Constructors
    public ExecutorThread(TransportStack stack, Map<String, Object> connectionInitiatingScenario) {
        this.stack = stack;
        this.connectionInitiatingScenario = connectionInitiatingScenario;
        scriptExecutingStateHolder = new ClientDescriptor();
        logger = Logger.getLogger("EXECUTOR THREAD-" + stack.getClientSocket().getPort());
        logger.setLevel(Level.INFO);
        logger.log(Level.SEVERE, stack.getClientSocket().getRemoteSocketAddress() + " accepted");
    }


    //getters and setters
    public boolean isConnectionEstablished() {
        return isConnectionEstablished;
    }

    public void setConnectionEstablished(boolean connectionEstablished) {
        isConnectionEstablished = connectionEstablished;
    }


    @Override
    public void run() {
        logger.log(Level.SEVERE, String.format("Execution started..."));
        long initTime = System.currentTimeMillis();
        //Создание транспортного стека
        Queue<byte[]> inputMessages = stack.getInputMessages();
        Queue<byte[]> outputMessages = stack.getOutputMessages();
        stack.start();
        logger.log(Level.INFO, "Creating TransportStack ".concat(Long.toString(System.currentTimeMillis() - initTime)).concat(" ms"));
        while (!isConnectionEstablished | !Thread.currentThread().isInterrupted()) {
            //разделитель сообщений
            if (logger.getLevel().intValue() <= Level.INFO.intValue()) System.out.println("");

            long initTimeLoadCommand = System.nanoTime();
            ScenarioPairContainer spc = ScenarioPairContainer
                    .getCommand(connectionInitiatingScenario, scriptExecutingStateHolder.getClientState(), 0);
            logger.log(Level.INFO, String.format("Scenario accessing time:".concat(Double.toString(((System.nanoTime() - initTimeLoadCommand) * 0.000001))).concat(" ms")));

            //проверка окончания скрипта
            if (spc == null) {
                logger.log(Level.INFO, "ConnectionInitiateScenario executed");
                logger.log(Level.INFO, String.format("Executing time: %s ms", System.currentTimeMillis() - initTime));
                logger.log(Level.SEVERE, String.format("Done read cycles %s, write cycles %s", stack.getReadCount(), stack.getWriteCount()));
                isConnectionEstablished = true;
                break;
            }

            //
            logger.log(Level.INFO, String.format("State: %s , command type: %s", Arrays.toString(scriptExecutingStateHolder.getClientState()), spc.getMethod()));
            ScriptExecutorHolder.execute(spc, inputMessages, outputMessages, scriptExecutingStateHolder, logger);
        }
        //запуск клиента
        logger.log(Level.SEVERE, "CONNECTION ESTABLISHED");
    }
}

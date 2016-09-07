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
        logger = Logger.getLogger("ExecutorThread-" + stack.getClientSocket().getPort());
        logger.setLevel(Level.INFO);
        logger.log(Level.INFO, stack.getClientSocket().getRemoteSocketAddress() + " accepted");
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
        logger.log(Level.INFO, String.format("Execution started..."));
        long initTime = System.currentTimeMillis();
        //Создание транспортного стека
        Queue<byte[]> inputMessages = stack.getInputMessages();
        Queue<byte[]> outputMessages = stack.getOutputMessages();
        stack.start();
        logger.log(Level.INFO, "Creating TransportStack ".concat(Long.toString(System.currentTimeMillis() - initTime)).concat(" ms"));
        while (!isConnectionEstablished | !Thread.currentThread().isInterrupted()) {
            //разделитель сообщений
            if (logger.getLevel().intValue() >= Level.INFO.intValue()) System.out.println("");

            long initTimeLoadCommand = System.nanoTime();
            ScenarioPairContainer spc = getCommand(connectionInitiatingScenario, scriptExecutingStateHolder.getClientState(), 0);
            logger.log(Level.INFO, String.format("Scenario accessing time:".concat(Double.toString(((System.nanoTime() - initTimeLoadCommand) * 0.000001))).concat(" ms")));

            //проверка окончания скрипта
            if (spc == null) {
                logger.log(Level.INFO, "ConnectionInitiateScenario executed");
                logger.log(Level.INFO, String.format("Executing time: %s ms", System.currentTimeMillis() - initTime));
                logger.log(Level.INFO, String.format("Done read cycles %s, write cycles %s", stack.getReadCount(), stack.getWriteCount()));
                isConnectionEstablished = true;
                break;
            }

            //
            logger.log(Level.INFO, String.format("State: %s , command type: %s", Arrays.toString(scriptExecutingStateHolder.getClientState()), spc.getMethod()));
            ScriptExecutorHolder.execute(spc, inputMessages, outputMessages, scriptExecutingStateHolder, logger);
        }
        //запуск клиента
        logger.log(Level.INFO, "CONNECTION ESTABLISHED");
    }

    private ScenarioPairContainer getCommand(Map<String, Object> scenario, String[] state, int level) {
        Iterator iterator = scenario.entrySet().iterator();
        Map.Entry<String, Object> object = null;
        boolean onNextStep = false;
        ScenarioPairContainer spc = null;

        while (iterator.hasNext()) {
            if (!onNextStep) object = (Map.Entry<String, Object>) iterator.next();
            else onNextStep = false;

            if (state[level] == null) state[level] = object.getKey();

            //ссылка на указанный в String[] state ключ элемента
            if (object.getKey().equals(state[level])) {
                //если элемент содержит мап(внутренняя вложеность)
                if (object.getValue() instanceof Map) {
                    spc = getCommand((Map<String, Object>) object.getValue(), state, level + 1);
                    // если spc==null работа с вложенными элементами закончена и необходимо перейти на следующий элемент.
                    if (spc == null) {
                        //если iterator.hasNext()==false более элементов нет, нужно переходить на уровень выше
                        if (!iterator.hasNext()) return null;
                        //переход на следующий элемент. что не выполнять повторного iterator.next() выше введена переменная onNextStep
                        object = (Map.Entry<String, Object>) iterator.next();
                        state[level] = object.getKey();
                        onNextStep = true;
                    }
                    return spc;
                } else {
                    List tmp = (List) object.getValue();
                    //обработка первого прохода по методу
                    if (state[level + 1] == null) state[level + 1] = String.valueOf(0);

                    int positionInList = Integer.valueOf(state[level + 1]);
                    if (tmp.size() < positionInList + 1) return null;
                    spc = (ScenarioPairContainer) tmp.get(positionInList);
                    state[level + 1] = String.valueOf(positionInList + 1);
                    return spc;
                }
            }
        }
        return spc;
    }

}

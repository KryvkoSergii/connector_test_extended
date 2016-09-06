package connectornew;

import connectornew.connector.ClientsExecutor;
import connectornew.connector.ExecutorThread;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by srg on 04.07.16.
 */
public class StartServer {
    private Logger logger = Logger.getLogger(StartServer.class.getClass().getName());
    private Map<String, Object> initiateConnectionScenario;
    private Map<String, Object> agentsScenario;
    private Map<String, ClientDescriptor> agentList = new ConcurrentHashMap<String, ClientDescriptor>();
    private static Map<String,String> initParam = new HashMap<String, String>();
    private boolean isStopped = false;

    //methods
    public static void main(String[] args) {
        StartServer ss = new StartServer();
        ss.doLabel();

        if (args.length==0)
        {
            System.out.println("Укажите параметры запуска в формате: <путь к скрипту установки> ");
        }
        // загрузка сценария установки подключения
        if (args.length>0 && args[0] != null && !args[0].isEmpty()) ss.loadInitiateConnectionScenario(args[0].toString());
        else ss.loadInitiateConnectionScenario("/home/user/tmp/scenarios_short1.xml");
        // загрузка сценария агентов
        if (args.length>1 && args[1] != null && !args[1].isEmpty()) ss.loadAgentsScenario(args[1].toString());
        else ss.loadAgentsScenario("/home/user/tmp/scenarios_short1.xml");


        //установка количества исполнительных потоков
//        ss.createExecutorsPool(1);
        ss.getAgentList().put("client", new ClientDescriptor());
        ss.startListening();
//        ss.test();
    }

    //getter and setters
    public Map<String, ClientDescriptor> getAgentList() {
        return agentList;
    }

    public void setAgentList(Map<String, ClientDescriptor> agentList) {
        this.agentList = agentList;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    private void startListening() {
        logger.log(Level.INFO, String.format("Init server acceptor..."));
        try {
            ServerSocket ss = new ServerSocket(42027);
            ExecutorService executorService = Executors.newCachedThreadPool();
            while (!isStopped) {
                logger.log(Level.INFO, "Waiting...");
                Socket s = ss.accept();
                s.setSendBufferSize(4096);

                ExecutorThread connectionInitiator = new ExecutorThread(s, initiateConnectionScenario);
                executorService.execute(connectionInitiator);
//
//                ClientsExecutor clientsExecutor = new ClientsExecutor(s, agentsScenario, agentList);
//                executorService.execute(clientsExecutor);

            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    private void loadInitiateConnectionScenario(String scenarioFilePath) {
        long initTime = System.currentTimeMillis();
        logger.log(Level.INFO, String.format("Loading loadInitiateConnectionScenario from file: %s", scenarioFilePath));
        this.initiateConnectionScenario = loadScenario(scenarioFilePath);
        logger.log(Level.INFO, String.format("Script preparing time: %s ms", System.currentTimeMillis() - initTime));
    }

    private void loadAgentsScenario(String scenarioFilePath) {
        long initTime = System.currentTimeMillis();
        logger.log(Level.INFO, String.format("Loading agentScenario from file: %s", scenarioFilePath));
        this.agentsScenario = loadScenario(scenarioFilePath);
        logger.log(Level.INFO, String.format("Script preparing time: %s ms", System.currentTimeMillis() - initTime));
    }

    private Map<String, Object> loadScenario(String scenarioFilePath) {
        Map<String, Object> tmp = null;
        try {
            tmp = ClientDescriptor.parseScenarioContainer(scenarioFilePath);
            tmp = ClientDescriptor.preCompile(tmp);
        } catch (ParserConfigurationException e) {
            logger.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
        } catch (SAXException e) {
            logger.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
        }
        return tmp;
    }

    private void doLabel() {
        System.out.println("*****   *******     **   **     **   **     *****   *****   ******  *******     *******");
        System.out.println("*****   *******     **   **     **   **     *****   *****   ******  *******     *******");
        System.out.println("**      **   **     ***  **     ***  **     **      **        **    **   **     **   **");
        System.out.println("**      **   **     ** * **     ** * **     *****   **        **    **   **     *******");
        System.out.println("**      **   **     **  ***     **  ***     **      **        **    **   **     **  ** ");
        System.out.println("*****   *******     **   **     **   **     *****   *****     **    *******     **   **");
        System.out.println("*****   *******     **   **     **   **     *****   *****     **    *******     **   **");

    }

    private static void menu(String[] args){
        System.out.println();
    }

}
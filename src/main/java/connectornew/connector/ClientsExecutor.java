package connectornew.connector;

import connectornew.ClientDescriptor;
import connectornew.TransportStack;

import java.net.Socket;
import java.util.Map;
import java.util.Queue;

/**
 * Created by srg on 06.09.16.
 */
public class ClientsExecutor implements Runnable {

    Socket clientSocket;
    Map<String, Object> agentsScenario;
    Map<String, ClientDescriptor> agentList;


    //Constructors
    public ClientsExecutor() {
    }

    public ClientsExecutor(Socket clientSocket, Map<String, Object> agentsScenario, Map<String, ClientDescriptor> agentList) {
        this.clientSocket = clientSocket;
        this.agentsScenario = agentsScenario;
        this.agentList = agentList;
    }

    @Override
    public void run() {

        TransportStack stack = new TransportStack(clientSocket);
        Queue<byte[]> inputMessages = stack.getInputMessages();
        Queue<byte[]> outputMessages = stack.getOutputMessages();
        stack.start();


    }
}

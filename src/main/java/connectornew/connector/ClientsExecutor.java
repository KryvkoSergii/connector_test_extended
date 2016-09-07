package connectornew.connector;

import connectornew.ClientDescriptor;
import connectornew.TransportStack;
import connectornew.messages.CTI;
import connectornew.messages.Header;
import connectornew.messages.SetAgentStateReq;
import org.apache.commons.codec.binary.Hex;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Queue;

/**
 * Created by srg on 06.09.16.
 */
public class ClientsExecutor implements Runnable {

    TransportStack stack;
    Map<String, Object> agentsScenario;
    Map<String, ClientDescriptor> agentList;


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
        while (!Thread.currentThread().isInterrupted()) {
            byte[] message = inputMessages.peek();
            if (message==null) continue;
            switch (Header.parseMessageType(message)) {
                case CTI.MSG_SET_AGENT_STATE_REQ: {
                    SetAgentStateReq agentStateReq = SetAgentStateReq.deserializeMessage(message);
                    System.out.println(agentStateReq);
                }
                default: {
                    System.out.println("REMOVE " + Hex.encodeHexString(inputMessages.remove()));
                }
            }

        }

    }
}

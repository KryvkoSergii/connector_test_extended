package connectornew.messages.agent_events;

import connectornew.messages.FloatingField;
import connectornew.messages.Header;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by srg on 08.09.16.
 */
public class AgentDeskSettingsReq extends Header {
    private final static int FIXED_PART = 8;
    private final static int MAX_LENGTH = 30;
    List<FloatingField> floatingFields = new ArrayList<>();
    private int invokeId;
    private int peripheralId;


    //Constructors
    public AgentDeskSettingsReq() {
    }


    //Static methods
    public static AgentDeskSettingsReq deserializeMessage(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        AgentDeskSettingsReq message = new AgentDeskSettingsReq();
        message.setMessageLength(buffer.getInt());
        message.setMessageType(buffer.getInt());
        message.setInvokeId(buffer.getInt());
        message.setPeripheralId(buffer.getInt());
        while (true) {
            try {
                message.getFloatingFields().add(FloatingField.deserializeField(buffer));
            } catch (BufferUnderflowException e) {
                break;
            }
        }
        return message;
    }


    //Getters and Setters
    public int getInvokeId() {
        return invokeId;
    }

    public void setInvokeId(int invokeId) {
        this.invokeId = invokeId;
    }

    public int getPeripheralId() {
        return peripheralId;
    }

    public void setPeripheralId(int peripheralId) {
        this.peripheralId = peripheralId;
    }

    public List<FloatingField> getFloatingFields() {
        return floatingFields;
    }

    public void setFloatingFields(List<FloatingField> floatingFields) {
        this.floatingFields = floatingFields;
    }


    //Methods
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AgentDeskSettingsReq{");
        sb.append(super.toString()).append(",");
        sb.append("invokeId=").append(invokeId);
        sb.append(", peripheralId=").append(peripheralId);
        sb.append(", floatingFields=").append(floatingFields);
        sb.append('}');
        return sb.toString();
    }
}

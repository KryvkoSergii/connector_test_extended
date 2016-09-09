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
public class ClientEventReportReq extends Header {
//    private final static int FIXED_PART = 4;
//    private final static int MAX_LENGTH = 4;
    private int invokeID;
    private short state;
    List<FloatingField> floatingFields = new ArrayList<>(2);

    public int getInvokeID() {
        return invokeID;
    }

    public void setInvokeID(int invokeID) {
        this.invokeID = invokeID;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public List<FloatingField> getFloatingFields() {
        return floatingFields;
    }

    public void setFloatingFields(List<FloatingField> floatingFields) {
        this.floatingFields = floatingFields;
    }

    //static methods
    public static ClientEventReportReq deserializeMessage(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        ClientEventReportReq message = new ClientEventReportReq();
        message.setMessageLength(buffer.getInt());
        message.setMessageType(buffer.getInt());
        message.setInvokeID(buffer.getInt());
        message.setState(buffer.getShort());
        while (true) {
            try {
                message.getFloatingFields().add(FloatingField.deserializeField(buffer));
            } catch (BufferUnderflowException e) {
                break;
            }
        }
        return message;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ClientEventReportReq{");
        sb.append(super.toString()).append(",");
        sb.append("invokeID=").append(invokeID);
        sb.append(", state=").append(state);
        sb.append(", floatingFields=").append(floatingFields);
        sb.append('}');
        return sb.toString();
    }
}

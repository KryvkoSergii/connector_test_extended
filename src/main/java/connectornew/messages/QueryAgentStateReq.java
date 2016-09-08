package connectornew.messages;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueryAgentStateReq extends Header {
    private final static int FIXED_PART = 16;
    private final static int MAX_LENGTH = 122;
    public final static int NULL_PERIPHERAL_ID = 0xFFFFFFFF;

    private int invokeId;
    private int peripheralId;
    private int mrdid;              //Media Routing Domain ID
    private int agentIdICMA;
    List<FloatingField> floatingFields = new ArrayList<>(1);


    //Constructors
    public QueryAgentStateReq() {
    }


    //Getters & setters
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

    public int getMrdid() {
        return mrdid;
    }

    public void setMrdid(int mrdid) {
        this.mrdid = mrdid;
    }

    public int getAgentIdICMA() {
        return agentIdICMA;
    }

    public void setAgentIdICMA(int agentIdICMA) {
        this.agentIdICMA = agentIdICMA;
    }

    public List<FloatingField> getFloatingFields() {
        return floatingFields;
    }

    public void setFloatingFields(List<FloatingField> floatingFields) {
        this.floatingFields = floatingFields;
    }


    //Methods
    public byte[] serializeMessage() throws Exception {
        try {
            this.setMessageLength(FIXED_PART + FloatingField.calculateFloatingPart(floatingFields));
            if (getMessageLength() > MAX_LENGTH)
                throw new Exception("MSG_QUERY_AGENT_STATE_REQ is longer than (bytes) " + MAX_LENGTH);
            ByteBuffer buffer = ByteBuffer.allocate(Header.MHDR + this.getMessageLength()).putInt(getMessageLength()).putInt(getMessageType())
                    .putInt(invokeId).putInt(peripheralId).putInt(mrdid).putInt(agentIdICMA);
            for (FloatingField field : floatingFields) {
                field.serializeField(buffer);
            }
            return buffer.array();
        } catch (BufferOverflowException e) {
            throw new Exception("Buffer overflowed during MSG_QUERY_AGENT_STATE_REQ serialization!");
        }
    }

    //static methods
    public static QueryAgentStateReq deserializeMessage(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        QueryAgentStateReq message = new QueryAgentStateReq();
        message.setMessageLength(buffer.getInt());
        message.setMessageType(buffer.getInt());
        message.setInvokeId(buffer.getInt());
        message.setPeripheralId(buffer.getInt());
        message.setMrdid(buffer.getInt());
        message.setAgentIdICMA(buffer.getInt());
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
        final StringBuilder sb = new StringBuilder("QueryAgentStateReq{");
        sb.append(super.toString()).append(",");
        sb.append("invokeId=").append(invokeId);
        sb.append(", peripheralId=").append(peripheralId);
        sb.append(", mrdid=").append(mrdid);
        sb.append(", agentIdICMA=").append(agentIdICMA);
        sb.append(", floatingFields=").append(Arrays.deepToString(floatingFields.toArray()));
        sb.append('}');
        return sb.toString();
    }
}

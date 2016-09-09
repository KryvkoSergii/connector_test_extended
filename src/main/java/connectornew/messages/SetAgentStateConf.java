package connectornew.messages;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

/**
 * Created by srg on 08.09.16.
 */
public class SetAgentStateConf extends Header {
    private final static int FIXED_PART = 4;
    private final static int MAX_LENGTH = 4;
    private int invokeID;

    public int getInvokeID() {
        return invokeID;
    }

    public void setInvokeID(int invokeID) {
        this.invokeID = invokeID;
    }

    //Methods
    public byte[] serializeMessage() throws Exception {
        try {
            this.setMessageLength(MHDR + FIXED_PART);
            this.setMessageType(CTI.MSG_SET_AGENT_STATE_CONF);
            ByteBuffer buffer = ByteBuffer.allocate(getMessageLength()).putInt(getMessageLength())
                    .putInt(getMessageType())
                    .putInt(getInvokeID());
            return buffer.array();
        } catch (BufferOverflowException e) {
            throw new Exception("Buffer overflowed during MSG_QUERY_AGENT_STATE_REQ serialization!");
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SetAgentStateConf{");
        sb.append(super.toString()).append(",");
        sb.append("invokeID=").append(invokeID);
        sb.append('}');
        return sb.toString();
    }
}

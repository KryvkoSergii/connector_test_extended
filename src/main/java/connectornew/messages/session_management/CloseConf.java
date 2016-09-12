package connectornew.messages.session_management;

import connectornew.messages.common.Header;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

/**
 * Added by A.Osadchuk on 26.08.2016 at 18:12.
 * Project: SmiddleRecording
 */
public class CloseConf extends Header {
    private final static int FIXED_PART = 4;

    private int invokeId;


    //Constructors
    public CloseConf(int invokeId) {
        this.invokeId = invokeId;
    }

    //Methods
    public static CloseConf deserializeMessage(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        try {
            return new CloseConf(buffer.getInt());
        } catch (BufferUnderflowException e) {
            return new CloseConf(-1);
        }
    }

    //Getters & setters
    public int getInvokeId() {
        return invokeId;
    }

    public void setInvokeId(int invokeId) {
        this.invokeId = invokeId;
    }

    //Methods
    public byte[] serializeMessage() throws Exception {
        try {
            return ByteBuffer.allocate(Header.MHDR + FIXED_PART).putInt(getMessageLength()).putInt(getMessageType()).putInt(invokeId).array();
        } catch (BufferOverflowException e) {
            throw new Exception("Buffer overflowed during MSG_CLOSE_REQ serialization!");
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CloseConf{");
        sb.append(super.toString()).append(",");
        sb.append("invokeId=").append(invokeId);
        sb.append('}');
        return sb.toString();
    }
}

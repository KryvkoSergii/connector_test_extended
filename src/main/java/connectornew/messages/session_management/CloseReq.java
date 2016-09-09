package connectornew.messages.session_management;

import connectornew.messages.CTI;
import connectornew.messages.FloatingField;
import connectornew.messages.Header;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

/**
 * Added by A.Osadchuk on 26.08.2016 at 18:11.
 * Project: SmiddleRecording
 */
public class CloseReq extends Header {
    private final static int FIXED_PART = 16;

    private int invokeId;
    private int status;


    //Constructors
    public CloseReq() {
    }

    //static methods
    public static CloseReq deserializeMessage(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        CloseReq message = new CloseReq();
        message.setMessageLength(buffer.getInt());
        message.setMessageType(buffer.getInt());
        message.setInvokeId(buffer.getInt());
        message.setStatus(buffer.getShort());
        return message;
    }

    //Getters & setters
    public int getInvokeId() {
        return invokeId;
    }

    public void setInvokeId(int invokeId) {
        this.invokeId = invokeId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    //Methods
    public byte[] serializeMessage() throws Exception {
        try {
            return ByteBuffer.allocate(Header.MHDR + FIXED_PART).putInt(getMessageLength()).putInt(getMessageType()).putInt(invokeId).putInt(status).array();
        } catch (BufferOverflowException e) {
            throw new Exception("Buffer overflowed during MSG_CLOSE_REQ serialization!");
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CloseReq{");
        sb.append(super.toString()).append(",");
        sb.append("invokeId=").append(invokeId);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}

package connectornew.messages.common;

import connectornew.messages.CTI;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

/**
 * Added by A.Osadchuk on 23.08.2016 at 10:36.
 * Project: SmiddleRecording
 */
public class FailureConf extends Header {
    private final static int FIXED_PART = 8;

    private int invokeId;
    private String status;


    //Constructors
    private FailureConf() {
        super(FIXED_PART, CTI.MSG_FAILURE_CONF);
    }


    //Getters & setters
    public int getInvokeId() {
        return invokeId;
    }

    public void setInvokeId(int invokeId) {
        this.invokeId = invokeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    //Methods
    public static FailureConf deserializeMessage(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        FailureConf message = new FailureConf();
        try {
            message.setInvokeId(buffer.getInt());
            message.setStatus(ConfCodes.getMessage(buffer.getInt()));
            return message;
        } catch (BufferUnderflowException e) {
            return message;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Header{messageLength=").append(getMessageLength());
        sb.append(", messageType=").append(getMessageType());
        sb.append('}');
        sb.append(" FailureConf{invokeId=").append(invokeId);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}

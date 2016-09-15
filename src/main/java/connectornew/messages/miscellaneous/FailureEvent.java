package connectornew.messages.miscellaneous;

import connectornew.messages.CTI;
import connectornew.messages.common.ConfCodes;
import connectornew.messages.common.Header;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

/**
 * Added by A.Osadchuk on 23.08.2016 at 10:36.
 * Project: SmiddleRecording
 */
public class FailureEvent extends Header {
    private final static int FIXED_PART = 4;

    private String status;


    //Constructors
    private FailureEvent() {
        super(FIXED_PART, CTI.MSG_FAILURE_EVENT);
    }


    //Getters & setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    //Methods
    public static FailureEvent deserializeMessage(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        FailureEvent event = new FailureEvent();
        try {
            event.setStatus(ConfCodes.getMessage(buffer.getInt()));
            return event;
        } catch (BufferUnderflowException e) {
            return event;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Header{messageLength=").append(getMessageLength());
        sb.append(", messageType=").append(getMessageType());
        sb.append('}');
        sb.append(" FailureEvent{status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}

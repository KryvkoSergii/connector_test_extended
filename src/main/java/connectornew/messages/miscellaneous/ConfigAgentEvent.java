package connectornew.messages.miscellaneous;

import connectornew.messages.CTI;
import connectornew.messages.common.FloatingField;
import connectornew.messages.common.Header;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by srg on 15.09.16.
 */
public class ConfigAgentEvent extends Header {
    private short numRecords;
    private List<FloatingField> floatingFields = new ArrayList<>(1);

    public ConfigAgentEvent() {
        super(CTI.MSG_CONFIG_AGENT_EVENT);
    }

    public static ConfigAgentEvent deserializeMessage(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        ConfigAgentEvent message = new ConfigAgentEvent();
        try {
            message.setMessageLength(buffer.getInt());
            message.setMessageType(buffer.getInt());
            message.setNumRecords(buffer.getShort());
            while (true) {
                try {
                    message.getFloatingFields().add(FloatingField.deserializeField(buffer));
                } catch (BufferUnderflowException e) {
                    break;
                }
            }
            return message;
        } catch (BufferUnderflowException e) {
            return message;
        }
    }

    public short getNumRecords() {
        return numRecords;
    }

    public void setNumRecords(short numRecords) {
        this.numRecords = numRecords;
    }

    public List<FloatingField> getFloatingFields() {
        return floatingFields;
    }

    public void setFloatingFields(List<FloatingField> floatingFields) {
        this.floatingFields = floatingFields;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ConfigAgentEvent{");
        sb.append(super.toString());
        sb.append("numRecords=").append(numRecords);
        sb.append(", floatingFields=").append(floatingFields);
        sb.append('}');
        return sb.toString();
    }
}

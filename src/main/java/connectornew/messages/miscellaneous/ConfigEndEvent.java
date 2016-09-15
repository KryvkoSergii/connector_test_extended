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
public class ConfigEndEvent extends Header {
    private int configEndStatus;
    private List<FloatingField> floatingFields = new ArrayList<>(1);

    public ConfigEndEvent() {
        super(CTI.MSG_CONFIG_END_EVENT);
    }

    public static ConfigEndEvent deserializeMessage(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        ConfigEndEvent message = new ConfigEndEvent();
        try {
            message.setMessageLength(buffer.getInt());
            message.setMessageType(buffer.getInt());
            message.setConfigEndStatus(buffer.getInt());

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

    public int getConfigEndStatus() {
        return configEndStatus;
    }

    public void setConfigEndStatus(int configEndStatus) {
        this.configEndStatus = configEndStatus;
    }

    public List<FloatingField> getFloatingFields() {
        return floatingFields;
    }

    public void setFloatingFields(List<FloatingField> floatingFields) {
        this.floatingFields = floatingFields;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ConfigEndEvent{");
        sb.append(super.toString());
        sb.append("configEndStatus=").append(configEndStatus);
        sb.append(", floatingFields=").append(floatingFields);
        sb.append('}');
        return sb.toString();
    }
}

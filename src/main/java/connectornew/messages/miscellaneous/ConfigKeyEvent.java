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
public class ConfigKeyEvent extends Header {
    private int configKeyStatus;
    private List<FloatingField> floatingFields = new ArrayList<>(5);

    public ConfigKeyEvent() {
        this.setMessageType(CTI.MSG_CONFIG_KEY_EVENT);
    }


    public static ConfigKeyEvent deserializeMessage(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        ConfigKeyEvent message = new ConfigKeyEvent();
        try {
            message.setMessageLength(buffer.getInt());
            message.setMessageType(buffer.getInt());
            message.setConfigKeyStatus(buffer.getInt());
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

    public int getConfigKeyStatus() {
        return configKeyStatus;
    }

    public void setConfigKeyStatus(int configKeyStatus) {
        this.configKeyStatus = configKeyStatus;
    }

    public List<FloatingField> getFloatingFields() {
        return floatingFields;
    }

    public void setFloatingFields(List<FloatingField> floatingFields) {
        this.floatingFields = floatingFields;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ConfigKeyEvent{");
        sb.append(super.toString());
        sb.append("configKeyStatus=").append(configKeyStatus);
        sb.append(", floatingFields=").append(floatingFields);
        sb.append('}');
        return sb.toString();
    }
}

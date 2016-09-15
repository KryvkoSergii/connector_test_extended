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
public class ConfigBeginEvent extends Header {
    private short configType;
    private int configInformation;
    private List<FloatingField> floatingFields = new ArrayList<>(1);

    public ConfigBeginEvent() {
        this.setMessageType(CTI.MSG_CONFIG_BEGIN_EVENT);
    }

    public static ConfigBeginEvent deserializeMessage(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        ConfigBeginEvent message = new ConfigBeginEvent();
        try {
            message.setMessageLength(buffer.getInt());
            message.setMessageType(buffer.getInt());
            message.setConfigType(buffer.getShort());
            message.setConfigInformation(buffer.getInt());
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

    public short getConfigType() {
        return configType;
    }

    public void setConfigType(short configType) {
        this.configType = configType;
    }

    public int getConfigInformation() {
        return configInformation;
    }

    public void setConfigInformation(int configInformation) {
        this.configInformation = configInformation;
    }

    public List<FloatingField> getFloatingFields() {
        return floatingFields;
    }

    public void setFloatingFields(List<FloatingField> floatingFields) {
        this.floatingFields = floatingFields;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ConfigBeginEvent{");
        sb.append(super.toString());
        sb.append("configType=").append(configType);
        sb.append(", configInformation=").append(configInformation);
        sb.append(", floatingFields=").append(floatingFields);
        sb.append('}');
        return sb.toString();
    }
}

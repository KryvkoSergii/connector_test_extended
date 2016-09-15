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
public class ConfigRequestEvent extends Header {
    private int configInformation;
    private int peripheralID;
    private List<FloatingField> floatingFields = new ArrayList<>(1);

    public ConfigRequestEvent() {
        this.setMessageType(CTI.MSG_CONFIG_REQUEST_EVENT);
    }

    public static ConfigRequestEvent deserializeMessage(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        ConfigRequestEvent message = new ConfigRequestEvent();
        try {
            message.setMessageLength(buffer.getInt());
            message.setMessageType(buffer.getInt());
            message.setConfigInformation(buffer.getInt());
            message.setPeripheralID(buffer.getInt());
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

    public int getConfigInformation() {
        return configInformation;
    }

    public void setConfigInformation(int configInformation) {
        this.configInformation = configInformation;
    }

    public int getPeripheralID() {
        return peripheralID;
    }

    public void setPeripheralID(int peripheralID) {
        this.peripheralID = peripheralID;
    }

    public List<FloatingField> getFloatingFields() {
        return floatingFields;
    }

    public void setFloatingFields(List<FloatingField> floatingFields) {
        this.floatingFields = floatingFields;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ConfigRequestEvent{");
        sb.append(super.toString());
        sb.append("configInformation=").append(configInformation);
        sb.append(", peripheralID=").append(peripheralID);
        sb.append(", floatingFields=").append(floatingFields);
        sb.append('}');
        return sb.toString();
    }
}

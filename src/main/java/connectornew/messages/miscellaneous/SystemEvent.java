package connectornew.messages.miscellaneous;

import ua.com.smiddle.SmiddleRecording.core.model.cti.CTI;
import ua.com.smiddle.SmiddleRecording.core.model.cti.common.FloatingField;
import ua.com.smiddle.SmiddleRecording.core.model.cti.common.Header;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Added by A.Osadchuk on 28.08.2016 at 17:16.
 * Project: SmiddleRecording
 */
public class SystemEvent extends Header {
    private String pgStatus;
    private Date ICMCentralControllerTime;
    private String systemEventId;
    private String systemEventArg1;
    private String systemEventArg2;
    private String systemEventArg3;
    private String eventDeviceType;
    List<FloatingField> floatingFields = new ArrayList<>(2);


    //Constructors
    public SystemEvent() {
        super(CTI.MSG_SYSTEM_EVENT);
    }


    //Getters & setters
    public String getPgStatus() {
        return pgStatus;
    }

    public void setPgStatus(String pgStatus) {
        this.pgStatus = pgStatus;
    }

    public Date getICMCentralControllerTime() {
        return ICMCentralControllerTime;
    }

    public void setICMCentralControllerTime(Date ICMCentralControllerTime) {
        this.ICMCentralControllerTime = ICMCentralControllerTime;
    }

    public String getSystemEventId() {
        return systemEventId;
    }

    public void setSystemEventId(String systemEventId) {
        this.systemEventId = systemEventId;
    }

    public String getSystemEventArg1() {
        return systemEventArg1;
    }

    public void setSystemEventArg1(String systemEventArg1) {
        this.systemEventArg1 = systemEventArg1;
    }

    public String getSystemEventArg2() {
        return systemEventArg2;
    }

    public void setSystemEventArg2(String systemEventArg2) {
        this.systemEventArg2 = systemEventArg2;
    }

    public String getSystemEventArg3() {
        return systemEventArg3;
    }

    public void setSystemEventArg3(String systemEventArg3) {
        this.systemEventArg3 = systemEventArg3;
    }

    public String getEventDeviceType() {
        return eventDeviceType;
    }

    public void setEventDeviceType(String eventDeviceType) {
        this.eventDeviceType = eventDeviceType;
    }

    public List<FloatingField> getFloatingFields() {
        return floatingFields;
    }

    public void setFloatingFields(List<FloatingField> floatingFields) {
        this.floatingFields = floatingFields;
    }


    //Methods
    public static SystemEvent deserializeMessage(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        SystemEvent message = new SystemEvent();
        message.setMessageLength(bytes.length);
        try {
            message.setPgStatus(PGStatusCodes.getMessage(buffer.getInt()));
            message.setICMCentralControllerTime(new Date(Integer.toUnsignedLong(buffer.getInt()) * 1000));
            message.setSystemEventId(SystemEvents.getMessage(buffer.getInt()));
            message.setSystemEventArg1(SystemEvents.getMessage(buffer.getInt()));
            message.setSystemEventArg2(SystemEvents.getMessage(buffer.getInt()));
            message.setSystemEventArg3(SystemEvents.getMessage(buffer.getInt()));
            message.setEventDeviceType(EventDeviceTypes.getMessage(Short.toUnsignedInt(buffer.getShort())));
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Header{messageLength=").append(getMessageLength());
        sb.append(", messageType=").append(getMessageType());
        sb.append('}');
        sb.append(" SystemEvent{pgStatus=").append(pgStatus);
        sb.append(", ICMCentralControllerTime=").append(ICMCentralControllerTime);
        sb.append(", systemEventId=").append(systemEventId);
        sb.append(", systemEventArg1=").append(systemEventArg1);
        sb.append(", systemEventArg2=").append(systemEventArg2);
        sb.append(", systemEventArg3=").append(systemEventArg3);
        sb.append(", eventDeviceType=").append(eventDeviceType);
        sb.append(", floatingFields=").append(Arrays.deepToString(floatingFields.toArray()));
        sb.append('}');
        return sb.toString();
    }
}

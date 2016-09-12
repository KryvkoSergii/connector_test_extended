package connectornew.messages.session_management;

import connectornew.messages.CTI;
import connectornew.messages.common.FloatingField;
import connectornew.messages.common.Header;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Added by A.Osadchuk on 23.08.2016 at 10:36.
 * Project: SmiddleRecording
 */
public class OpenReq extends Header {
    public final static int MAX_IDLE_TIMEOUT = 0xFFFFFFFF;
    public final static int NULL_PERIPHERAL_ID = 0xFFFFFFFF;
    private final static int FIXED_PART = 44;
    private final static int MAX_LENGTH = 318;
    List<FloatingField> floatingFields = new ArrayList<>(7);
    private int invokeId;
    private int versionNumber = CTI.PROTOCOL_VERSION;
    private int idleTimeout;
    private int peripheralId;   //Идентификатор сервиса
    private int servicesMask;
    private int callMsgMask;
    private int agentStateMask;
    private int configMsgMask;
    private int reserved1;
    private int reserved2;
    private int reserved3;


    //Constructors
    public OpenReq() {
        super(CTI.MSG_OPEN_REQ);
    }

    //static methods
    public static OpenReq deserializeMessage(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        OpenReq message = new OpenReq();
        message.setMessageLength(buffer.getInt());
        message.setMessageType(buffer.getInt());
        message.setInvokeId(buffer.getInt());
        message.setVersionNumber(buffer.getInt());
        message.setIdleTimeout(buffer.getInt());
        message.setPeripheralId(buffer.getInt());
        message.setServicesMask(buffer.getInt());
        message.setCallMsgMask(buffer.getInt());
        message.setAgentStateMask(buffer.getInt());
        message.setConfigMsgMask(buffer.getInt());
        message.setReserved1(buffer.getInt());
        message.setReserved2(buffer.getInt());
        message.setReserved3(buffer.getInt());
        while (true) {
            try {
                message.getFloatingFields().add(FloatingField.deserializeField(buffer));
            } catch (BufferUnderflowException e) {
                break;
            }
        }
        return message;
    }

    //Getters & setters
    public int getInvokeId() {
        return invokeId;
    }

    public void setInvokeId(int invokeId) {
        this.invokeId = invokeId;
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    public int getIdleTimeout() {
        return idleTimeout;
    }

    public void setIdleTimeout(int idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public int getPeripheralId() {
        return peripheralId;
    }

    public void setPeripheralId(int peripheralId) {
        this.peripheralId = peripheralId;
    }

    public int getServicesMask() {
        return servicesMask;
    }

    public void setServicesMask(int servicesMask) {
        this.servicesMask = servicesMask;
    }

    public int getCallMsgMask() {
        return callMsgMask;
    }

    public void setCallMsgMask(int callMsgMask) {
        this.callMsgMask = callMsgMask;
    }

    public int getAgentStateMask() {
        return agentStateMask;
    }

    public void setAgentStateMask(int agentStateMask) {
        this.agentStateMask = agentStateMask;
    }

    public int getConfigMsgMask() {
        return configMsgMask;
    }

    public void setConfigMsgMask(int configMsgMask) {
        this.configMsgMask = configMsgMask;
    }

    public List<FloatingField> getFloatingFields() {
        return floatingFields;
    }

    public void setFloatingFields(List<FloatingField> floatingFields) {
        this.floatingFields = floatingFields;
    }

    public int getReserved1() {
        return reserved1;
    }

    public void setReserved1(int reserved1) {
        this.reserved1 = reserved1;
    }

    public int getReserved2() {
        return reserved2;
    }

    public void setReserved2(int reserved2) {
        this.reserved2 = reserved2;
    }

    public int getReserved3() {
        return reserved3;
    }

    public void setReserved3(int reserved3) {
        this.reserved3 = reserved3;
    }

    //Methods
    public byte[] serializeMessage() throws Exception {
        try {
            this.setMessageLength(FIXED_PART + FloatingField.calculateFloatingPart(floatingFields));
            if (getMessageLength() > MAX_LENGTH)
                throw new Exception("MSG_OPEN_REQ is longer than (bytes) " + MAX_LENGTH);
            ByteBuffer buffer = ByteBuffer.allocate(Header.MHDR + this.getMessageLength()).putInt(getMessageLength()).putInt(getMessageType()).putInt(invokeId)
                    .putInt(versionNumber).putInt(idleTimeout).putInt(peripheralId).putInt(servicesMask).putInt(callMsgMask)
                    .putInt(agentStateMask).putInt(configMsgMask).putInt(reserved1).putInt(reserved2).putInt(reserved3);
            for (FloatingField field : floatingFields) {
                field.serializeField(buffer);
            }
            return buffer.array();
        } catch (BufferOverflowException e) {
            throw new Exception("Buffer overflowed during MSG_OPEN_REQ serialization!");
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Header{messageLength=").append(getMessageLength());
        sb.append(", messageType=").append(getMessageType());
        sb.append('}');
        sb.append(" OpenReq{invokeId=").append(invokeId);
        sb.append(", versionNumber=").append(versionNumber);
        sb.append(", idleTimeout=").append(idleTimeout);
        sb.append(", peripheralId=").append(peripheralId);
        sb.append(", servicesMask=").append(servicesMask);
        sb.append(", callMsgMask=").append(callMsgMask);
        sb.append(", agentStateMask=").append(agentStateMask);
        sb.append(", configMsgMask=").append(configMsgMask);
        sb.append(", reserved1=").append(reserved1);
        sb.append(", reserved2=").append(reserved2);
        sb.append(", reserved3=").append(reserved3);
        sb.append(", floatingFields=").append(Arrays.deepToString(floatingFields.toArray()));
        sb.append('}');
        return sb.toString();
    }
}
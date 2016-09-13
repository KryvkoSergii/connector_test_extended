package connectornew.messages.session_management;

import connectornew.messages.CTI;
import connectornew.messages.agent_events.AgentStates;
import connectornew.messages.common.FloatingField;
import connectornew.messages.common.Header;
import connectornew.messages.common.PeripheralTypes;
import connectornew.messages.miscellaneous.PGStatusCodes;

import java.io.UnsupportedEncodingException;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Added by A.Osadchuk on 25.08.2016 at 12:46.
 * Project: SmiddleRecording
 */
public class OpenConf extends Header {
    //private int departmentId;       //Department ID of the Agent in CTI Protocol(v19)
    List<FloatingField> floatingFields = new ArrayList<>(0);
    private int invokeId;           //Set to the value of the InvokeID from the corresponding OPEN_REQ message.
    private int servicesGranted;    //A bitwise combination of the CTI Services listed in that the CTI client has been granted. Services granted may be less than those requested.
    private int monitorId;          //The identifier of the event monitor created by the OPEN_REQ, or zero if no monitor was created.
    private PGStatusCodes PGStatus;           //The current operational status of the Peripheral Gateway. Any non-zero indicates a component failure or communication outage that prevents normal CTI operations.
    private Date ICMCentralControllerTime; //The current Central Controller date and time.
    private short peripheralOnline; //The current UCCE on-line status of the agent’s peripheral, when Client Events service has been granted. Otherwise, set this value to TRUE only when all peripherals monitored by the PG are on-line.
    private PeripheralTypes peripheralType;   //The type of the peripheral when Client Events Service has been granted.
    private AgentStates agentState;       //The current state of the associated agent phone (Client Events Service only).
    private int MAX_LENGTH = 132;
    private int FIXED_PART = 26;

    //Constructors
    public OpenConf() {
        super(CTI.MSG_OPEN_CONF);
    }

    //Methods
    public static OpenConf deserializeMessage(byte[] bytes) throws UnsupportedEncodingException {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        OpenConf message = new OpenConf();
        message.setMessageLength(bytes.length);
        message.setInvokeId(buffer.getInt());
        message.setServicesGranted(buffer.getInt());
        message.setMonitorId(buffer.getInt());
        message.setPGStatus(PGStatusCodes.getPGStatusCode(buffer.getInt()));
        message.setICMCentralControllerTime(new Date(Integer.toUnsignedLong(buffer.getInt()) * 1000));
        message.setPeripheralOnline(buffer.getShort());
        message.setPeripheralType(PeripheralTypes.getType(Short.toUnsignedInt(buffer.getShort())));
        message.setAgentState(AgentStates.getState(Short.toUnsignedInt(buffer.getShort())));
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

    public int getServicesGranted() {
        return servicesGranted;
    }

    public void setServicesGranted(int servicesGranted) {
        this.servicesGranted = servicesGranted;
    }

    public int getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(int monitorId) {
        this.monitorId = monitorId;
    }

    public PGStatusCodes getPGStatus() {
        return PGStatus;
    }

    public void setPGStatus(PGStatusCodes PGStatus) {
        this.PGStatus = PGStatus;
    }

    public Date getICMCentralControllerTime() {
        return ICMCentralControllerTime;
    }

    public void setICMCentralControllerTime(Date ICMCentralControllerTime) {
        this.ICMCentralControllerTime = ICMCentralControllerTime;
    }

    public short getPeripheralOnline() {
        return peripheralOnline;
    }

    public void setPeripheralOnline(short peripheralOnline) {
        this.peripheralOnline = peripheralOnline;
    }

    public PeripheralTypes getPeripheralType() {
        return peripheralType;
    }

    public void setPeripheralType(PeripheralTypes peripheralType) {
        this.peripheralType = peripheralType;
    }

    public AgentStates getAgentState() {
        return agentState;
    }

    public void setAgentState(AgentStates agentState) {
        this.agentState = agentState;
    }

    public List<FloatingField> getFloatingFields() {
        return floatingFields;
    }

    public void setFloatingFields(List<FloatingField> floatingFields) {
        this.floatingFields = floatingFields;
    }

    //Methods
    public byte[] serializeMessage() throws Exception {
        try {
            this.setMessageLength(MHDR + FIXED_PART + FloatingField.calculateFloatingPart(floatingFields));
            if (getMessageLength() > MAX_LENGTH)
                throw new Exception("MSG_OPEN_REQ is longer than (bytes) " + MAX_LENGTH);
            ByteBuffer buffer = ByteBuffer.allocate(this.getMessageLength())
                    .putInt(getMessageLength())
                    .putInt(getMessageType())
                    .putInt(invokeId)
                    .putInt(servicesGranted)
                    .putInt(monitorId)
                    .putInt(PGStatusCodes.setIntState(PGStatus))
                    .putInt(new Long(ICMCentralControllerTime.getTime() / 1000).intValue())
                    .putShort(peripheralOnline)
                    .putShort((short) PeripheralTypes.getInt(peripheralType))
                    .putShort((short) AgentStates.setIntState(agentState));
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
        sb.append(" OpenConf{invokeId=").append(invokeId);
        sb.append(", servicesGranted=").append(servicesGranted);
        sb.append(", monitorId=").append(monitorId);
        sb.append(", PGStatus=").append(PGStatus);
        sb.append(", ICMCentralControllerTime=").append(ICMCentralControllerTime);
        sb.append(", peripheralOnline=").append(peripheralOnline);
        sb.append(", peripheralType=").append(peripheralType);
        sb.append(", agentState=").append(agentState);
        sb.append(", floatingFields=").append(Arrays.deepToString(floatingFields.toArray()));
        sb.append('}');
        return sb.toString();
    }
}

package connectornew.messages.agent_events;

import connectornew.messages.CTI;
import connectornew.messages.common.FloatingField;
import connectornew.messages.common.Header;

import java.io.UnsupportedEncodingException;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Added by A.Osadchuk on 01.09.2016 at 14:08.
 * Project: SmiddleRecording
 */
public class QueryAgentStateConf extends Header {
    private final static int FIXED_PART = 30;
    private final static int MAX_LENGTH = 528;
    private int invokeId;
    private AgentStates agentState;       //One of the values from Table 6-5 representing the current overall state of the associated agent.
    private short numSkillGroups;
    private int mrdid;              //Media Routing Domain ID
    private int numTasks;
    private short agentMode;        //ROUTABLE = 0, NOT ROUTABLE = 1
    private int maxTaskLimit;
    private int agentIdICMA;
    private int agentAvailabilityStatus;    //NOT AVAILABLE = 0, ICM AVAILABLE = 1, APPLICATION AVAILABLE=2
    private List<FloatingField> floatingFields = new ArrayList<>(7);

    //Constructors
    public QueryAgentStateConf() {
    }

    //Methods
    public static QueryAgentStateConf deserializeMessage(byte[] bytes) throws UnsupportedEncodingException {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        QueryAgentStateConf message = new QueryAgentStateConf();
        try {
            message.setMessageLength(bytes.length);
            message.setInvokeId(buffer.getInt());
            message.setAgentState(AgentStates.getState(Short.toUnsignedInt(buffer.getShort())));
            message.setNumSkillGroups(buffer.getShort());
            message.setMrdid(buffer.getInt());
            message.setNumTasks(buffer.getInt());
            message.setAgentMode(buffer.getShort());
            message.setMaxTaskLimit(buffer.getInt());
            message.setAgentIdICMA(buffer.getInt());
            message.setAgentAvailabilityStatus(buffer.getInt());
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

    public byte[] serializeMessage() throws Exception {
        try {
            this.setMessageLength(MHDR + FIXED_PART + FloatingField.calculateFloatingPart(floatingFields));
            this.setMessageType(CTI.MSG_CLIENT_EVENT_REPORT_CONF);
            ByteBuffer buffer = ByteBuffer.allocate(MHDR + FIXED_PART + FloatingField.calculateFloatingPart(floatingFields))
                    .putInt(MHDR + FIXED_PART + FloatingField.calculateFloatingPart(floatingFields))
                    .putInt(CTI.MSG_QUERY_AGENT_STATE_CONF)
                    .putInt(invokeId)
                    .putShort((short) AgentStates.setIntState(agentState))
                    .putShort(numSkillGroups)
                    .putInt(mrdid)
                    .putInt(numTasks)
                    .putShort(agentMode)
                    .putInt(maxTaskLimit)
                    .putInt(agentIdICMA)
                    .putInt(agentAvailabilityStatus);
            for (FloatingField field : floatingFields) {
                field.serializeField(buffer);
            }
            return buffer.array();
        } catch (BufferOverflowException e) {
            throw new Exception("Buffer overflowed during MSG_QUERY_AGENT_STATE_CONF serialization!");
        }
    }

    //Getters & setters
    public int getInvokeId() {
        return invokeId;
    }

    public void setInvokeId(int invokeId) {
        this.invokeId = invokeId;
    }

    public AgentStates getAgentState() {
        return agentState;
    }

    public void setAgentState(AgentStates agentState) {
        this.agentState = agentState;
    }

    public short getNumSkillGroups() {
        return numSkillGroups;
    }

    public void setNumSkillGroups(short numSkillGroups) {
        this.numSkillGroups = numSkillGroups;
    }

    public int getMrdid() {
        return mrdid;
    }

    public void setMrdid(int mrdid) {
        this.mrdid = mrdid;
    }

    public int getNumTasks() {
        return numTasks;
    }

    public void setNumTasks(int numTasks) {
        this.numTasks = numTasks;
    }

    public short getAgentMode() {
        return agentMode;
    }

    public void setAgentMode(short agentMode) {
        this.agentMode = agentMode;
    }

    public int getMaxTaskLimit() {
        return maxTaskLimit;
    }

    public void setMaxTaskLimit(int maxTaskLimit) {
        this.maxTaskLimit = maxTaskLimit;
    }

    public int getAgentIdICMA() {
        return agentIdICMA;
    }

    public void setAgentIdICMA(int agentIdICMA) {
        this.agentIdICMA = agentIdICMA;
    }

    public int getAgentAvailabilityStatus() {
        return agentAvailabilityStatus;
    }

    public void setAgentAvailabilityStatus(int agentAvailabilityStatus) {
        this.agentAvailabilityStatus = agentAvailabilityStatus;
    }

    public List<FloatingField> getFloatingFields() {
        return floatingFields;
    }

    public void setFloatingFields(List<FloatingField> floatingFields) {
        this.floatingFields = floatingFields;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(" QueryAgentStateConf{");
        sb.append(super.toString());
        sb.append(", invokeId=").append(invokeId);
        sb.append(", agentState='").append(agentState).append('\'');
        sb.append(", numSkillGroups=").append(numSkillGroups);
        sb.append(", mrdid=").append(mrdid);
        sb.append(", numTasks=").append(numTasks);
        sb.append(", agentMode=").append(agentMode);
        sb.append(", maxTaskLimit=").append(maxTaskLimit);
        sb.append(", agentIdICMA=").append(agentIdICMA);
        sb.append(", agentAvailabilityStatus=").append(agentAvailabilityStatus);
        sb.append(", floatingFields=").append(Arrays.deepToString(floatingFields.toArray()));
        sb.append('}');
        return sb.toString();
    }
}

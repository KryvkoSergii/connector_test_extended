package connectornew.messages;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * Created by srg on 07.09.16.
 */
public class FloatingPart {
    private String agentInstrument;
    private String agentID;
    private String agentPassword;
    private String positionId;
    private String supervisorID;
    private int skillGroupNumber;
    private short skillGroupPriority;


    //constructors
    public FloatingPart() {
    }


    //static methods
    public static FloatingPart deserializeMessage(ByteBuffer buffer) throws Exception {
        FloatingPart message = new FloatingPart();
        message.setAgentInstrument(retriveString(buffer, Fields.STRING_64));//??
        message.setAgentID(retriveString(buffer, Fields.STRING_12));
        message.setAgentPassword(retriveString(buffer, Fields.STRING_64));
        message.setPositionId(retriveString(buffer, Fields.STRING_12));
        message.setSupervisorID(retriveString(buffer, Fields.STRING_12));
        message.setSkillGroupNumber(buffer.getInt());
        message.setSkillGroupPriority(buffer.getShort());
        return message;
    }

    private static String retriveString(ByteBuffer buffer, int length) throws Exception {
        byte[] array = new byte[length];
        buffer.get(array, buffer.position(), array.length);
        return new String(array, "US-ASCII").replace("\u0000", "");
    }


    //getters and setters

    public String getAgentInstrument() {
        return agentInstrument;
    }

    public void setAgentInstrument(String agentInstrument) {
        this.agentInstrument = agentInstrument;
    }

    public String getAgentID() {
        return agentID;
    }

    public void setAgentID(String agentID) {
        this.agentID = agentID;
    }

    public String getAgentPassword() {
        return agentPassword;
    }

    public void setAgentPassword(String agentPassword) {
        this.agentPassword = agentPassword;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getSupervisorID() {
        return supervisorID;
    }

    public void setSupervisorID(String supervisorID) {
        this.supervisorID = supervisorID;
    }

    public int getSkillGroupNumber() {
        return skillGroupNumber;
    }

    public void setSkillGroupNumber(int skillGroupNumber) {
        this.skillGroupNumber = skillGroupNumber;
    }

    public short getSkillGroupPriority() {
        return skillGroupPriority;
    }

    public void setSkillGroupPriority(short skillGroupPriority) {
        this.skillGroupPriority = skillGroupPriority;
    }


    //Methods
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FloatingPart{");
        sb.append("agentInstrument='").append(agentInstrument).append('\'');
        sb.append(", agentID='").append(agentID).append('\'');
        sb.append(", agentPassword='").append(agentPassword).append('\'');
        sb.append(", positionId='").append(positionId).append('\'');
        sb.append(", supervisorID='").append(supervisorID).append('\'');
        sb.append(", skillGroupNumber=").append(skillGroupNumber);
        sb.append(", skillGroupPriority=").append(skillGroupPriority);
        sb.append('}');
        return sb.toString();
    }
}

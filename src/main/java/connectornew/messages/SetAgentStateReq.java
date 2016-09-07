package connectornew.messages;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by srg on 07.09.16.
 */
public class SetAgentStateReq extends Header {
    List<FloatingField> floatingFields = new ArrayList();
    private int invokeID;
    private int pheriperalID;
    private String agentState;
    private short agentWorkMode;
    private short numSkillsGroups;
    private short eventReasonCode;
    private char forsedFlag;
    private int agentServiceReq;


    //Constructors
    private SetAgentStateReq() {

    }

    public SetAgentStateReq(byte[] message) {
        super(message);

    }


    //static methods
    public static SetAgentStateReq deserializeMessage(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        SetAgentStateReq message = new SetAgentStateReq();
        message.setMessageLength(buffer.getInt());
        message.setMessageType(buffer.getInt());
        message.setInvokeID(buffer.getInt());
        message.setAgentState(AgentStates.getState(Short.toUnsignedInt(buffer.getShort())).name());
        message.setAgentWorkMode(buffer.getShort());
        message.setNumSkillsGroups(buffer.getShort());
        message.setEventReasonCode(buffer.getShort());
        message.setForsedFlag((char) buffer.get());
        message.setAgentServiceReq(buffer.getInt());
        while (true) {
            try {
                message.getFloatingFields().add(FloatingField.deserializeField(buffer));
            } catch (BufferUnderflowException e) {
                break;
            }
        }
        return message;
    }


    //Getters and setters
    public int getInvokeID() {
        return invokeID;
    }

    public void setInvokeID(int invokeID) {
        this.invokeID = invokeID;
    }

    public int getPheriperalID() {
        return pheriperalID;
    }

    public void setPheriperalID(int pheriperalID) {
        this.pheriperalID = pheriperalID;
    }

    public String getAgentState() {
        return agentState;
    }

    public void setAgentState(String agentState) {
        this.agentState = agentState;
    }

    public int getAgentWorkMode() {
        return agentWorkMode;
    }

    public void setAgentWorkMode(short agentWorkMode) {
        this.agentWorkMode = agentWorkMode;
    }

    public int getNumSkillsGroups() {
        return numSkillsGroups;
    }

    public void setNumSkillsGroups(short numSkillsGroups) {
        this.numSkillsGroups = numSkillsGroups;
    }

    public int getEventReasonCode() {
        return eventReasonCode;
    }

    public void setEventReasonCode(short eventReasonCode) {
        this.eventReasonCode = eventReasonCode;
    }

    public char getForsedFlag() {
        return forsedFlag;
    }

    public void setForsedFlag(char forsedFlag) {
        this.forsedFlag = forsedFlag;
    }

    public int getAgentServiceReq() {
        return agentServiceReq;
    }

    public void setAgentServiceReq(int agentServiceReq) {
        this.agentServiceReq = agentServiceReq;
    }

    public List<FloatingField> getFloatingFields() {
        return floatingFields;
    }

    public void setFloatingFields(List<FloatingField> floatingFields) {
        this.floatingFields = floatingFields;
    }

    //Methods
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SetAgentStateReq{");
        sb.append("invokeID=").append(invokeID);
        sb.append(", pheriperalID=").append(pheriperalID);
        sb.append(", agentState='").append(agentState).append('\'');
        sb.append(", agentWorkMode=").append(agentWorkMode);
        sb.append(", numSkillsGroups=").append(numSkillsGroups);
        sb.append(", eventReasonCode=").append(eventReasonCode);
        sb.append(", forsedFlag=").append(forsedFlag);
        sb.append(", agentServiceReq=").append(agentServiceReq);
        sb.append(", floatingFields=").append(floatingFields);
        sb.append('}');
        return sb.toString();
    }
}

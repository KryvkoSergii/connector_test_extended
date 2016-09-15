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
public class AgentTeamConfigEvent extends Header {
    private int peripheralId;
    private int teamId;
    private short numberOfAgents;
    private short configOperation;
    private List<FloatingField> floatingFields = new ArrayList<>(1);

    public AgentTeamConfigEvent() {
        super(CTI.MSG_AGENT_TEAM_CONFIG_EVENT);
    }

    public static AgentTeamConfigEvent deserializeMessage(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        AgentTeamConfigEvent message = new AgentTeamConfigEvent();
        message.setMessageLength(buffer.getInt());
        message.setMessageType(buffer.getInt());
        message.setPeripheralId(buffer.getInt());
        message.setTeamId(buffer.getInt());
        message.setNumberOfAgents(buffer.getShort());
        message.setConfigOperation(buffer.getShort());
        while (true) {
            try {
                message.getFloatingFields().add(FloatingField.deserializeField(buffer));
            } catch (BufferUnderflowException e) {
                break;
            }
        }
        return message;
    }

    public int getPeripheralId() {
        return peripheralId;
    }

    public void setPeripheralId(int peripheralId) {
        this.peripheralId = peripheralId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public short getNumberOfAgents() {
        return numberOfAgents;
    }

    public void setNumberOfAgents(short numberOfAgents) {
        this.numberOfAgents = numberOfAgents;
    }

    public short getConfigOperation() {
        return configOperation;
    }

    public void setConfigOperation(short configOperation) {
        this.configOperation = configOperation;
    }

    public List<FloatingField> getFloatingFields() {
        return floatingFields;
    }

    public void setFloatingFields(List<FloatingField> floatingFields) {
        this.floatingFields = floatingFields;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AgentTeamConfigEvent{");
        sb.append(super.toString()).append(",");
        sb.append(", peripheralId=").append(peripheralId);
        sb.append(", teamId=").append(teamId);
        sb.append(", numberOfAgents=").append(numberOfAgents);
        sb.append(", configOperation=").append(configOperation);
        sb.append("floatingFields=").append(floatingFields);
        sb.append('}');
        return sb.toString();
    }
}

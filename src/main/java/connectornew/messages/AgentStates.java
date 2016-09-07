package connectornew.messages;

/**
 * Added by A.Osadchuk on 31.08.2016 at 13:42.
 * Project: SmiddleRecording
 */
public enum AgentStates {
    AGENT_STATE_LOGIN, AGENT_STATE_LOGOUT, AGENT_STATE_NOT_READY, AGENT_STATE_AVAILABLE, AGENT_STATE_TALKING,
    AGENT_STATE_WORK_NOT_READY, AGENT_STATE_WORK_READY, AGENT_STATE_BUSY_OTHER, AGENT_STATE_RESERVED, AGENT_STATE_UNKNOWN,
    AGENT_STATE_HOLD, AGENT_STATE_ACTIVE, AGENT_STATE_PAUSED, AGENT_STATE_INTERRUPTED, AGENT_STATE_NOT_ACTIVE;


    //Methods
    public static AgentStates getState(int mask) {
        for (AgentStates code : AgentStates.values()) {
            if (code.ordinal() == mask) return code;
        }
        return AGENT_STATE_UNKNOWN;
    }
}

package connectornew.messages.common;

/**
 * Added by A.Osadchuk on 23.08.2016 at 12:24.
 * Project: SmiddleRecording
 */
public enum ConfCodes {
    NO_ERROR {private final static String MSG = "No error occurred.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_VERSION {private final static String MSG = "The CTI Server does not support the protocol version number requested by the CTI client.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_MESSAGE_TYPE {private final static String MSG = "A message with an invalid message type field was received.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_FIELD_TAG {private final static String MSG = "A message with an invalid floating field tag was received.";

        public String getMessage() {
            return MSG;
        }
    },
    SESSION_NOT_OPEN {private final static String MSG = "No session is currently open on the connection.";

        public String getMessage() {
            return MSG;
        }
    },
    SESSION_ALREADY_OPEN {private final static String MSG = "A session is already open on the connection.";

        public String getMessage() {
            return MSG;
        }
    },
    REQUIRED_DATA_MISSING {private final static String MSG = "The request did not include one or more floating items that are required.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_PERIPHERAL_ID {private final static String MSG = "A message with an invalid PeripheralID value was received.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_AGENT_DATA {private final static String MSG = "The provided agent data item(s) are invalid.";

        public String getMessage() {
            return MSG;
        }
    },
    AGENT_NOT_LOGGED_ON {private final static String MSG = "The indicated agent is not currently logged on.";

        public String getMessage() {
            return MSG;
        }
    },
    DEVICE_IN_USE {private final static String MSG = "The indicated agent teleset is already associated with a different CTI client";

        public String getMessage() {
            return MSG;
        }
    },
    NEW_SESSION_OPENED {private final static String MSG = "This session is being terminated due to a new session open request from the client.";

        public String getMessage() {
            return MSG;
        }
    },
    FUNCTION_NOT_AVAILABLE {private final static String MSG = "A request message was received for a function or service that was not granted to the client.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_CALLID {private final static String MSG = "A request message was received with an invalid CallID value.";

        public String getMessage() {
            return MSG;
        }
    },
    PROTECTED_VARIABLE {private final static String MSG = "The CTI client may not update the requested variable.";

        public String getMessage() {
            return MSG;
        }
    },
    CTI_SERVER_OFFLINE {private final static String MSG = "The CTI Server is not able to function normally. The CTI client should close the session upon receipt of this error.";

        public String getMessage() {
            return MSG;
        }
    },
    TIMEOUT {private final static String MSG = "The CTI Server failed to respond to a request message within the time-out period, or no messages have been received from the CTI client within the IdleTimeout period.";

        public String getMessage() {
            return MSG;
        }
    },
    UNSPECIFIED_FAILURE {private final static String MSG = "An unspecified error occurred.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_TIMEOUT {private final static String MSG = "The IdleTimeout field contains a value that is less than 20 seconds (4 times the minimum heartbeat interval of 5 seconds).";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_SERVICE_MASK {private final static String MSG = "The ServicesRequested field has unused bits set. All unused bit positions must be zero.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_CALL_MSG_MASK {private final static String MSG = "The CallMsgMask field has unused bits set. All unused bit positions must be zero.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_AGENT_STATE_MASK {private final static String MSG = "The AgentStateMask field has unused bits set. All unused bit positions must be zero.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_RESERVED_FIELD {private final static String MSG = "A Reserved field has a non-zero value.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_FIELD_LENGTH {private final static String MSG = "A floating field exceeds the allowable length for that field type.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_DIGITS {private final static String MSG = "A STRING field contains characters that are not digits (0 - 9).";

        public String getMessage() {
            return MSG;
        }
    },
    BAD_MESSAGE_FORMAT {private final static String MSG = "The message is improperly constructed. This may be caused by omitted or incorrectly sized fixed message fields.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_TAG_FOR_MSG_TYPE {private final static String MSG = "A floating field tag is present that specifies a field that does not belong in this message type.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_DEVICE_ID_TYPE {private final static String MSG = "A DeviceIDType field contains a value that is not in Table 6-11 (CTIServer message reference guide).";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_LCL_CONN_STATE {private final static String MSG = "A LocalConnectionState field contains a value that is not in Table 6-8 (CTIServer message reference guide).";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_EVENT_CAUSE {private final static String MSG = "An EventCause field contains a value that is not in Table 6-9 (CTIServer message reference guide).";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_NUM_PARTIES {private final static String MSG = "The NumParties field contains a value that exceeds the maximum (16).";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_SYS_EVENT_ID {private final static String MSG = "The SystemEventID field contains a value that is not in Table 6-2 (CTIServer message reference guide).";

        public String getMessage() {
            return MSG;
        }
    },
    INCONSISTENT_AGENT_DATA {private final static String MSG = "The provided agent extension, agent id, and/or agent instrument values are inconsistent with each other.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_CONNECTION_ID_TYPE {private final static String MSG = "A ConnectionDeviceIDType field contains a value that is not in Table 6-13 (CTIServer message reference guide).";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_CALL_TYPE {private final static String MSG = "The CallType field contains a value that is not in Table 6-12.";

        public String getMessage() {
            return MSG;
        }
    },
    NOT_CALL_PARTY {private final static String MSG = "A CallDataUpdate or Release Call request specified a call that the client is not a party to.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_PASSWORD {private final static String MSG = "The ClientID or Client Password provided in an OPEN_REQ message is incorrect.";

        public String getMessage() {
            return MSG;
        }
    },
    CLIENT_DISCONNECTED {private final static String MSG = "The client TCP/IP connection was disconnected without a CLOSE_REQ.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_OBJECT_STATE {private final static String MSG = "An invalid object state value was provided.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_NUM_SKILL_GROUPS {private final static String MSG = "An invalid NumSkillGroups value was provided.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_NUM_LINES {private final static String MSG = "An invalid NumLines value was provided.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_LINE_TYPE {private final static String MSG = "An invalid LineType value was provided.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_ALLOCATION_STATE {private final static String MSG = "An invalid AllocationState value was provided.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_ANSWERING_MACHINE {private final static String MSG = "An invalid AnsweringMachine value was provided.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_CALL_MANNER_TYPE {private final static String MSG = "An invalid CallMannerType value was provided.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_CALL_PLACEMENT_TYPE {private final static String MSG = "An invalid CallPlacementType value was provided.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_CONSULT_TYPE {private final static String MSG = "An invalid ConsultType value was provided.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_FACILITY_TYPE {private final static String MSG = "An invalid FacilityType value was provided.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_MSG_TYPE_FOR_VERSION {private final static String MSG = "The provided MessageType is invalid for the opened protocol version.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_TAG_FOR_VERSION {private final static String MSG = "A floating field tag value is invalid for the opened protocol version.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_AGENT_WORK_MODE {private final static String MSG = "An invalid AgentWorkMode value was provided.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_CALL_OPTION {private final static String MSG = "An invalid call option value was provided.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_DESTINATION_COUNTRY {private final static String MSG = "An invalid destination country value was provided.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_ANSWER_DETECT_MODE {private final static String MSG = "An invalid answer detect mode value was provided.";

        public String getMessage() {
            return MSG;
        }
    },
    MUTUALLY_EXCLUS_DEVICEID_TYPES {private final static String MSG = "A peripheral monitor request may not specify both a call and a device.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_MONITORID {private final static String MSG = "An invalid monitorID value was provided.";

        public String getMessage() {
            return MSG;
        }
    },
    SESSION_MONITOR_ALREADY_EXISTS {private final static String MSG = "A requested session monitor was already created.";

        public String getMessage() {
            return MSG;
        }
    },
    SESSION_MONITOR_IS_CLIENTS {private final static String MSG = "A client may not monitor its own session.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_CALL_CONTROL_MASK {private final static String MSG = "An invalid call control mask value was provided.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_FEATURE_MASK {private final static String MSG = "An invalid feature mask value was provided.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_TRANSFER_CONFERENCE_SETUP_MASK {private final static String MSG = "An invalid transfer conference setup mask value was provided.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_ARRAY_INDEX {private final static String MSG = "An invalid named array index value was provided.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_CHARACTER {private final static String MSG = "An invalid character value was provided.";

        public String getMessage() {
            return MSG;
        }
    },
    CLIENT_NOT_FOUND {private final static String MSG = "There is no open session with a matching ClientID.";

        public String getMessage() {
            return MSG;
        }
    },
    SUPERVISOR_NOT_FOUND {private final static String MSG = "The agent’s supervisor is unknown or does not have an open CTI session.";

        public String getMessage() {
            return MSG;
        }
    },
    TEAM_NOT_FOUND {private final static String MSG = "The agent is not a member of an agent team.";

        public String getMessage() {
            return MSG;
        }
    },
    NO_CALL_ACTIVE {private final static String MSG = "The specified agent does not have an active call.";

        public String getMessage() {
            return MSG;
        }
    },
    NAMED_VARIABLE_NOT_CONFIGURED {private final static String MSG = "The specified named variable is not configured in the UCCE.";

        public String getMessage() {
            return MSG;
        }
    },
    NAMED_ARRAY_NOT_CONFIGURED {private final static String MSG = "The specified named array is not configured in the UCCE.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_CALL_VARIABLE_MASK {private final static String MSG = "The specified call variable mask in not valid.";

        public String getMessage() {
            return MSG;
        }
    },
    ELEMENT_NOT_FOUND {private final static String MSG = "An internal error occurred manipulating a named variable or named array element.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_DISTRIBUTION_TYPE {private final static String MSG = "The specified distribution type is invalid.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_SKILL_GROUP {private final static String MSG = "The specified skill group is invalid.";

        public String getMessage() {
            return MSG;
        }
    },
    TOO_MUCH_DATA {private final static String MSG = "The total combined size of named variables and named arrays may not exceed the limit of 2000 bytes.";

        public String getMessage() {
            return MSG;
        }
    },
    VALUE_TOO_LONG {private final static String MSG = "The value of the specified named variable or named array element exceeds the maximum permissible length.";

        public String getMessage() {
            return MSG;
        }
    },
    SCALAR_FUNCTION_ON_ARRAY {private final static String MSG = "A NamedArray was specified with a NamedVariable tag.";

        public String getMessage() {
            return MSG;
        }
    },
    ARRAY_FUNCTION_ON_SCALAR {private final static String MSG = "A NamedVariable was specified with a NamedArray tag.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_NUM_NAMED_VARIABLES {private final static String MSG = "The value in the NumNamedVariables field is different than the number of NamedVariable floating fields in the message.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_NUM_NAMED_ARRAYS {private final static String MSG = "The value in the NumNamedArrays field is different than the number of NamedArray floating fields in the message.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_RTP_DIRECTION {private final static String MSG = "The RTP direction value is invalid.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_RTP_TYPE {private final static String MSG = "The RTP type value is invalid.";

        public String getMessage() {
            return MSG;
        }
    },
    CALLED_PARTY_DISPOSITION {private final static String MSG = "The called party disposition is invalid.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_SUPERVISORY_ACTION {private final static String MSG = "The supervisory action is invalid.";

        public String getMessage() {
            return MSG;
        }
    },
    AGENT_TEAM_MONITOR_ALREADY_EXISTS {private final static String MSG = "The agent team monitor already exists.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_SERVICE {private final static String MSG = "The ServiceNumber or ServiceID value is invalid.";

        public String getMessage() {
            return MSG;
        }
    },
    SERVICE_CONFLICT {private final static String MSG = "The ServiceNumber and ServiceID values given represent different services.";

        public String getMessage() {
            return MSG;
        }
    },
    SKILL_GROUP_CONFLICT {private final static String MSG = "The SkillGroupNumber/SkillGroupPriority and SkillGroupID values given represent different skill groups.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_DEVICE {private final static String MSG = "The specified device is invalid.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_MR_DOMAIN {private final static String MSG = "Media Routing Domain is invalid.";

        public String getMessage() {
            return MSG;
        }
    },
    MONITOR_ALREADY_EXISTS {private final static String MSG = "Monitor already exists.";

        public String getMessage() {
            return MSG;
        }
    },
    MONITOR_TERMINATED {private final static String MSG = "Monitor has terminated.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_TASK_MSG_MASK {private final static String MSG = "The task msg mask is invalid.";

        public String getMessage() {
            return MSG;
        }
    },
    SERVER_NOT_MASTER {private final static String MSG = "The server is a standby server.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_CSD {private final static String MSG = "The CSD Specified is invalid (UCCX Only).";

        public String getMessage() {
            return MSG;
        }
    },
    JTAPI_CCM_PROBLEM {private final static String MSG = "Indicates a JTAPI or Unified CM problem.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_CONFIG_MSG_MASK {private final static String MSG = "Indicates a bad config mask in OPEN_REQ.";

        public String getMessage() {
            return MSG;
        }
    },
    AUTO_CONFIG_RESET {private final static String MSG = "Indicates a configuration change (UCCX only).";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_MONITOR_STATUS {private final static String MSG = "Indicates an invalid monitor.";

        public String getMessage() {
            return MSG;
        }
    },
    INVALID_REQUEST_TYPE {private final static String MSG = "Indicates an invalid request ID type.";

        public String getMessage() {
            return MSG;
        }
    };

    protected abstract String getMessage();

    public static String getMessage(int ordinal) {
        for (ConfCodes o : ConfCodes.values()) {
            if (o.ordinal() == ordinal) {
                return o.getMessage();
            }
        }
        return "Confirmation code=" + ordinal + " not found!";
    }
}

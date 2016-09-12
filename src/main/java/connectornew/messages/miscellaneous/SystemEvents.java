package connectornew.messages.miscellaneous;

/**
 * Added by A.Osadchuk on 25.08.2016 at 17:24.
 * Project: SmiddleRecording
 */
public enum SystemEvents {
    SYS_NO_ERROR {private final static String MSG = "No error";

        public String getMessage() {
            return MSG;
        }
    },
    SYS_CENTRAL_CONTROLLER_ONLINE {private final static String MSG = "The PG has resumed communication with the UCCE Central Controller.";

        public String getMessage() {
            return MSG;
        }
    },
    SYS_CENTRAL_CONTROLLER_OFFLINE {private final static String MSG = "The PG is unable to communicate with the UCCE Central Controller.";

        public String getMessage() {
            return MSG;
        }
    },
    SYS_PERIPHERAL_ONLINE {private final static String MSG = "A peripheral monitored by the PG has gone online. SystemEventArg1 contains the PeripheralID of the peripheral.";

        public String getMessage() {
            return MSG;
        }
    },
    SYS_PERIPHERAL_OFFLINE {private final static String MSG = "A peripheral monitored by the PG has gone offline. SystemEventArg1 contains the PeripheralID of the peripheral.";

        public String getMessage() {
            return MSG;
        }
    },
    SYS_TEXT_FYI {private final static String MSG = "Broadcast of informational \"text\" floating field.";

        public String getMessage() {
            return MSG;
        }
    },
    SYS_PERIPHERAL_GATEWAY_OFFLINE {private final static String MSG = "The CTI Server is unable to communicate with the UCCE Peripheral Gateway.";

        public String getMessage() {
            return MSG;
        }
    },
    SYS_CTI_SERVER_OFFLINE {private final static String MSG = "The local software component is unable to communicate with the CTI Server.";

        public String getMessage() {
            return MSG;
        }
    },
    SYS_CTI_SERVER_ONLINE {private final static String MSG = "The local software component has resumed communication with the CTI Server.";

        public String getMessage() {
            return MSG;
        }
    },
    SYS_HALF_HOUR_CHANGE {private final static String MSG = "The UCCE Central Controller time has changed to a new half hour.";

        public String getMessage() {
            return MSG;
        }
    },
    SYS_INSTRUMENT_OUT_OF_SERVICE {private final static String MSG = "An Enterprise Agent device target has been removed from service. SystemEventArg1 contains the PeripheralID of the peripheral, and SystemEventText contains the AgentInstrument that was removed from service.";

        public String getMessage() {
            return MSG;
        }
    },
    SYS_INSTRUMENT_BACK_IN_SERVICE {private final static String MSG = "An Enterprise Agent device target has been returned to service. SystemEventArg1 contains the PeripheralID of the peripheral, and SystemEventText contains the AgentInstrument that was returned to service.";

        public String getMessage() {
            return MSG;
        }
    };

    protected abstract String getMessage();

    public static String getMessage(int ordinal) {
        for (SystemEvents o : values()) {
            if (o.ordinal() == ordinal) {
                return o.getMessage();
            }
        }
        return "SysEvent code=" + ordinal + " not found!";
    }
}

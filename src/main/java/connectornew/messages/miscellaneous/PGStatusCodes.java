package connectornew.messages.miscellaneous;

/**
 * Added by A.Osadchuk on 30.08.2016 at 15:24.
 * Project: SmiddleRecording
 */
public enum PGStatusCodes {
    PGS_OPC_DOWN {
        private final int mask = 0x00000001;
        private final String msg = "Communication lost between the CTI Server and the PG’s Open Peripheral Controller (OPC) process. No call or agent state event messages can be sent due to this condition.";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    },
    PGS_CC_DOWN {
        private final int mask = 0x00000002;
        private final String msg = "Communication lost between the PG and the UCCE Central Controller. Primarily affects translation routing and post-routing, other call and agent event messages can still be sent.";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    },
    PGS_PERIPHERAL_OFFLINE {
        private final int mask = 0x00000004;
        private final String msg = "One or more of the peripherals monitored by the PG are offline.";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    },
    PGS_CTI_SERVER_OFFLINE {
        private final int mask = 0x00000008;
        private final String msg = "Loss of communication between the CTI Server and the CTI Client. This status code is not reported by a software layer between the CTI Server and the client application.";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    },
    PGS_LIMITED_FUNCTION {
        private final int mask = 0x00000010;
        private final String msg = "This status code may be reported by a software layer between the CTI Server and the client application when PGS_CTI_SERVER_OFFLINE is true to indicate that limited local call control is possible.";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    };

    protected abstract int getMask();

    protected abstract String getMessage();

    public static String getMessage(int mask) {
        for (PGStatusCodes code : PGStatusCodes.values()) {
            if (code.getMask() == mask) return code.getMessage();
        }
        return "PGStatusCodes not found for Mask=" + mask;
    }
}

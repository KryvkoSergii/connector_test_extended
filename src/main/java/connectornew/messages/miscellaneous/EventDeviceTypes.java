package connectornew.messages.miscellaneous;

/**
 * Added by A.Osadchuk on 30.08.2016 at 15:51.
 * Project: SmiddleRecording
 */
public enum EventDeviceTypes {
    NONE {
        private final int mask = 0xffff;
        private final String msg = "No device ID is provided.";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    },
    DEVICE_IDENTIFIER {
        private final int mask = 0;
        private final String msg = "The provided device ID identifies a peripheral teleset (extension).";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    },
    TRUNK_IDENTIFIER {
        private final int mask = 70;
        private final String msg = "The provided device ID identifies a peripheral Trunk.";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    },
    TRUNK_GROUP_IDENTIFIER {
        private final int mask = 71;
        private final String msg = "The provided device ID identifies a peripheral Trunk Group.";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    },
    IP_PHONE_MAC_IDENTIFIER {
        private final int mask = 72;
        private final String msg = "The provided device ID identifiers the MAC address of an IP phone (UCCX ONLY).";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    },
    CTI_PORT {
        private final int mask = 73;
        private final String msg = "The provided device ID identifiers a CTI PORT (UCCX ONLY).";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    },
    ROUTE_POINT {
        private final int mask = 74;
        private final String msg = "The provided device ID identifies a ROUTE POINT.";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    },
    EXTERNAL {
        private final int mask = 75;
        private final String msg = "The provided device ID is an ANI number or some other external identifier.";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    },
    AGENT_DEVICE {
        private final int mask = 76;
        private final String msg = "The provided device ID is the ID of an AGENT Device (phone).";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    },
    QUEUE {
        private final int mask = 77;
        private final String msg = "The provided device ID is the ID of a QUEUE.";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    },
    NON_ACD_DEVICE_IDENTIFIER {
        private final int mask = 78;
        private final String msg = "The provided device ID identifies a peripheral telset (extension) that is classified as being a non-ACD extension.";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    },
    SHARED_DEVICE_IDENTIFIER {
        private final int mask = 79;
        private final String msg = "The provided device ID identifies a peripheral telset (extension) that is classified as being a shared line (0 or more telsets share this extension).";

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
        for (EventDeviceTypes type : EventDeviceTypes.values()) {
            if (type.getMask() == mask) return type.getMessage();
        }
        return "EventDeviceTypes not found for Mask=" + mask;
    }
}

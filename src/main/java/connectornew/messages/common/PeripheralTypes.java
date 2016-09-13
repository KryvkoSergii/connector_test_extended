package connectornew.messages.common;

/**
 * Added by A.Osadchuk on 30.08.2016 at 16:50.
 * Project: SmiddleRecording
 */
public enum PeripheralTypes {
    PT_NONE {
        private final int mask = 0xffff;
        private final String msg = "Not Applicable.";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    },
    PT_ASPECT {
        private final int mask = 1;
        private final String msg = "Aspect Call Center ACD.";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    },
    PT_MERIDIAN {
        private final int mask = 2;
        private final String msg = "Northern Telecom Meridian ACD.";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    },
    PT_G2 {
        private final int mask = 3;
        private final String msg = "Lucent G2.";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    },
    PT_DEFINITY_ECS_NON_EAS {
        private final int mask = 4;
        private final String msg = "Lucent DEFINITY ECS (without Expert Agent Selection).";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    },
    PT_DEFINITY_ECS_EAS {
        private final int mask = 5;
        private final String msg = "Lucent DEFINITY ECS (with Expert Agent Selection).";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    },
    PT_GALAXY {
        private final int mask = 6;
        private final String msg = "Rockwell Galaxy ACD.";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    },
    PT_SPECTRUM {
        private final int mask = 7;
        private final String msg = "Rockwell Spectrum ACD.";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    },
    PT_VRU {
        private final int mask = 8;
        private final String msg = "VRU (event type interface).";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    },
    PT_VRU_POLLED {
        private final int mask = 9;
        private final String msg = "VRU (polled type interface).";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    },
    PT_DMS100 {
        private final int mask = 10;
        private final String msg = "Northern Telecom DMS-100, DMS-500, or SL-10010.";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    },
    PT_SIEMENS_9006 {
        private final int mask = 11;
        private final String msg = "Siemens Hicom ACD (9006).";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    },
    PT_SIEMENS_9005 {
        private final int mask = 12;
        private final String msg = "Siemens 9751 CBX Release 9005 (Rolm 9005).";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    },
    PT_ALCATEL {
        private final int mask = 13;
        private final String msg = "VAlcatel 4400 ACD.";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    },
    PT_NEC_NEAX_2x00 {
        private final int mask = 14;
        private final String msg = "NEC NEAX 2400, NEAX 7400, or APEX 7600 ACD.";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    },
    PT_ACP_1000 {
        private final int mask = 15;
        private final String msg = "Ericsson ACP1000.";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    },
    PT_SYMPOSIUM {
        private final int mask = 16;
        private final String msg = "Northern Telecom Symposium.";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    },
    PT_ENTERPRISE_AGENT {
        private final int mask = 17;
        private final String msg = "Enterprise Agent Manager.";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    },
    PT_MD110 {
        private final int mask = 18;
        private final String msg = "Ericsson MD-110.";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    },
    PT_MEDIA_ROUTING {
        private final int mask = 19;
        private final String msg = "Media Routing.";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    },
    PT_GENERIC {
        private final int mask = 20;
        private final String msg = "Generic.";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    },
    PT_ACMI_CRS {
        private final int mask = 21;
        private final String msg = "A Gateway PG over UCCX.";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    },
    PT_ACMI_IPCC {
        private final int mask = 22;
        private final String msg = "A Gateway PG over UCCE or UCCX.";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    },
    PT_SIMPLIFIED_IPCC {
        private final int mask = 23;
        private final String msg = "A system using the System PG.";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    },
    PT_ARS {
        private final int mask = 24;
        private final String msg = "A system using the ARS PG.";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    },
    PT_ACMI_ERS {
        private final int mask = 25;
        private final String msg = "A system using the ERS PG.";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    },
    PT_ACMI_EXPERT_ADVISOR {
        private final int mask = 26;
        private final String msg = "A Gateway PG over Expert Advisor.";

        protected int getMask() {
            return mask;
        }

        protected String getMessage() {
            return msg;
        }
    };


    public static String getMessage(int mask) {
        for (PeripheralTypes code : PeripheralTypes.values()) {
            if (code.getMask() == mask) return code.getMessage();
        }
        return "PeripheralType not found for Mask=" + mask;
    }

    public static int getInt(PeripheralTypes type) {
        for (PeripheralTypes code : PeripheralTypes.values()) {
            if (code.getMask() == type.getMask()) return type.getMask();
        }
        return 0xffff;
    }

    public static PeripheralTypes getType(int mask) {
        for (PeripheralTypes code : PeripheralTypes.values()) {
            if (code.getMask() == mask) return code;
        }
        return PT_NONE;
    }

    //Methods
    protected abstract int getMask();

    protected abstract String getMessage();
}

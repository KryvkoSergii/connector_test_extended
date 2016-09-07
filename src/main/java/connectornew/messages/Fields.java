package connectornew.messages;

/**
 * Added by A.Osadchuk on 25.08.2016 at 18:10.
 * Project: SmiddleRecording
 */
public enum Fields {
    TAG_FIELD_NOTFOUND {
        private final static int TAG_ID = 0;

        public int getTagId() {
            return TAG_ID;
        }

        public int getMaxLength() {
            return STRING_64;
        }

        public String getDataType() {
            return DATA_TYPE_STRING;
        }
    },
    TAG_CLIENT_ID {
        private final static int TAG_ID = 1;

        public int getTagId() {
            return TAG_ID;
        }

        public int getMaxLength() {
            return STRING_64;
        }

        public String getDataType() {
            return DATA_TYPE_STRING;
        }
    },
    TAG_CLIENT_PASSWORD {
        private final static int TAG_ID = 2;

        public int getTagId() {
            return TAG_ID;
        }

        public int getMaxLength() {
            return STRING_64;
        }

        public String getDataType() {
            return DATA_TYPE_STRING;
        }
    },
    TAG_CLIENT_SIGNATURE {
        private final static int TAG_ID = 3;

        public int getTagId() {
            return TAG_ID;
        }

        public int getMaxLength() {
            return STRING_64;
        }

        public String getDataType() {
            return DATA_TYPE_STRING;
        }
    },
    TAG_AGENT_EXTENSION {
        private final static int TAG_ID = 4;

        public int getTagId() {
            return TAG_ID;
        }

        public int getMaxLength() {
            return STRING_16;
        }

        public String getDataType() {
            return DATA_TYPE_STRING;
        }
    },
    TAG_AGENT_ID {
        private final static int TAG_ID = 5;

        public int getTagId() {
            return TAG_ID;
        }

        public int getMaxLength() {
            return STRING_12;
        }

        public String getDataType() {
            return DATA_TYPE_STRING;
        }
    },
    TAG_AGENT_INSTRUMENT {
        private final static int TAG_ID = 6;

        public int getTagId() {
            return TAG_ID;
        }

        public int getMaxLength() {
            return STRING_64;
        }

        public String getDataType() {
            return DATA_TYPE_STRING;
        }
    },
    TAG_TEXT {
        private final static int TAG_ID = 7;

        public int getTagId() {
            return TAG_ID;
        }

        public int getMaxLength() {
            return STRING_255;
        }

        public String getDataType() {
            return DATA_TYPE_STRING;
        }
    },
    TAG_SKILL_GROUP_NUMBER {
        private final static int TAG_ID = 62;

        public int getTagId() {
            return TAG_ID;
        }

        public int getMaxLength() {
            return Integer.BYTES;
        }

        public String getDataType() {
            return DATA_TYPE_INTEGER;
        }
    },
    TAG_SKILL_GROUP_ID {
        private final static int TAG_ID = 63;

        public int getTagId() {
            return TAG_ID;
        }

        public int getMaxLength() {
            return Integer.BYTES;
        }

        public String getDataType() {
            return DATA_TYPE_INTEGER;
        }
    },
    TAG_SKILL_GROUP_PRIORITY {
        private final static int TAG_ID = 64;

        public int getTagId() {
            return TAG_ID;
        }

        public int getMaxLength() {
            return Short.BYTES;
        }

        public String getDataType() {
            return DATA_TYPE_SHORT;
        }
    },
    TAG_SKILL_GROUP_STATE {
        private final static int TAG_ID = 65;

        public int getTagId() {
            return TAG_ID;
        }

        public int getMaxLength() {
            return Short.BYTES;
        }

        public String getDataType() {
            return DATA_TYPE_SHORT;
        }
    },
    TAG_APP_PATH_ID {
        private final static int TAG_ID = 97;

        public int getTagId() {
            return TAG_ID;
        }

        public int getMaxLength() {
            return Integer.BYTES;
        }

        public String getDataType() {
            return DATA_TYPE_INTEGER;
        }
    },
    TAG_NEXT_AGENT_STATE {
        private final static int TAG_ID = 123;

        public int getTagId() {
            return TAG_ID;
        }

        public int getMaxLength() {
            return Short.BYTES;
        }

        public String getDataType() {
            return DATA_TYPE_SHORT;
        }
    },
    TAG_DURATION {
        private final static int TAG_ID = 150;

        public int getTagId() {
            return TAG_ID;
        }

        public int getMaxLength() {
            return Integer.BYTES;
        }

        public String getDataType() {
            return DATA_TYPE_INTEGER;
        }
    },
    TAG_EVENT_DEVICE_ID {
        private final static int TAG_ID = 206;

        public int getTagId() {
            return TAG_ID;
        }

        public int getMaxLength() {
            return STRING_64;
        }

        public String getDataType() {
            return DATA_TYPE_STRING;
        }
    },
    TAG_PERIPHERAL_ID {
        private final static int TAG_ID = 208;

        public int getTagId() {
            return TAG_ID;
        }

        public int getMaxLength() {
            return Integer.BYTES;
        }

        public String getDataType() {
            return DATA_TYPE_INTEGER;
        }
    },
    TAG_MULTI_LINE_AGENT_CONTROL {
        private final static int TAG_ID = 224;

        public int getTagId() {
            return TAG_ID;
        }

        public int getMaxLength() {
            return Short.BYTES;
        }

        public String getDataType() {
            return DATA_TYPE_SHORT;
        }
    },
    TAG_NUM_PERIPHERALS {
        private final static int TAG_ID = 228;

        public int getTagId() {
            return TAG_ID;
        }

        public int getMaxLength() {
            return Short.BYTES;
        }

        public String getDataType() {
            return DATA_TYPE_SHORT;
        }
    };

    //Protocol data types
    public final static String DATA_TYPE_BYTE = "Byte";
    public final static String DATA_TYPE_SHORT = "Short";
    public final static String DATA_TYPE_INTEGER = "Integer";
    public final static String DATA_TYPE_STRING = "String";
    //Field length
    public final static int STRING_12 = 12;
    public final static int STRING_16 = 16;
    public final static int STRING_64 = 64;
    public final static int STRING_255 = 255;

    //Methods
    public abstract int getTagId();

    public abstract int getMaxLength();

    public abstract String getDataType();

    public static Fields getField(int tagId) {
        for (Fields field : Fields.values()) {
            if (field.getTagId() == tagId) return field;
        }
        return TAG_FIELD_NOTFOUND;
    }

    /*public static String getDataType(int tagId) {
        for (Fields o : Fields.values()) {
            if (o.getTagId() == tagId) return o.getDataType();
        }
        return DATA_TYPE_STRING;
    }*/
}

package connectornew.messages;

import connectornew.exception.CTIFieldException;

import java.io.UnsupportedEncodingException;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.List;

/**
 * Added by A.Osadchuk on 24.08.2016 at 10:36.
 * Project: SmiddleRecording
 */
public class FloatingField {
    private int tag;
    private int fieldLength;
    private String data;


    //Constructors
    private FloatingField() {
    }

    public FloatingField(int tag, String data) {
        this.tag = tag;
        this.data = data;
    }


    //Getters & setters
    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getFieldLength() {
        return fieldLength;
    }

    public void setFieldLength(int fieldLength) {
        this.fieldLength = fieldLength;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    //Methods
    public void serializeField(ByteBuffer buffer) throws BufferOverflowException, CTIFieldException {
        //Fields.getDataType(this.tag);
        switch (Fields.getField(this.tag).getDataType()) {
            case Fields.DATA_TYPE_BYTE: {
                fieldLength = Byte.BYTES;
                buffer.put((byte) tag).put((byte) fieldLength).put(Byte.valueOf(data));
                break;
            }
            case Fields.DATA_TYPE_SHORT: {
                fieldLength = Short.BYTES;
                buffer.put((byte) tag).put((byte) fieldLength).putShort(Short.valueOf(data));
                break;
            }
            case Fields.DATA_TYPE_INTEGER: {
                fieldLength = Integer.BYTES;
                buffer.put((byte) tag).put((byte) fieldLength).putInt(Integer.valueOf(data));
                break;
            }
            default: {  //String
                try {
                    fieldLength = data.getBytes("US-ASCII").length;
                    if (fieldLength > Fields.getField(tag).getMaxLength())
                        throw new CTIFieldException("Field " + Fields.getField(tag).name() + " has length more than (bytes) " + Fields.getField(tag).getMaxLength());
                    buffer.put((byte) tag).put((byte) fieldLength).put(data.getBytes("US-ASCII"));
                } catch (UnsupportedEncodingException e) {
                    //
                }
            }
        }
    }

    public static FloatingField deserializeField(ByteBuffer buffer) throws BufferUnderflowException {
        FloatingField field = new FloatingField();
        field.setTag(Byte.toUnsignedInt(buffer.get()));
        field.setFieldLength(Byte.toUnsignedInt(buffer.get()));
        //Fields.getDataType(field.getTag())
        switch (Fields.getField(field.getTag()).getDataType()) {
            case Fields.DATA_TYPE_BYTE: {
                field.setData(String.valueOf(Byte.toUnsignedInt(buffer.get())));
                break;
            }
            case Fields.DATA_TYPE_SHORT: {
                field.setData(String.valueOf(Short.toUnsignedInt(buffer.getShort())));
                break;
            }
            case Fields.DATA_TYPE_INTEGER: {
                field.setData(String.valueOf(Integer.toUnsignedLong(buffer.getInt())));
                break;
            }
            default: {  //String
                byte[] data = new byte[field.getFieldLength()];
                buffer.get(data, 0, field.getFieldLength());
                try {
                    field.setData(new String(data, "US-ASCII").replace("\u0000", ""));
                } catch (UnsupportedEncodingException e) {
                    //
                }
            }
        }
        return field;
    }

    public static int calculateFloatingPart(List<FloatingField> floatingFields) {
        int floatingPart = 0;
        for (FloatingField f : floatingFields) {
            switch (Fields.getField(f.getTag()).getDataType()) {
                case Fields.DATA_TYPE_SHORT: {
                    f.setFieldLength(Short.BYTES);
                    break;
                }
                case Fields.DATA_TYPE_INTEGER: {
                    f.setFieldLength(Integer.BYTES);
                    break;
                }
                default: {  //String
                    try {
                        f.setFieldLength(f.getData().getBytes("US-ASCII").length);
                    } catch (UnsupportedEncodingException e) {
                        //
                    }
                }
            }
            floatingPart += 2 + f.getFieldLength();
        }
        return floatingPart;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FloatingField{");
        sb.append("tag=").append(tag);
        sb.append(", fieldLength=").append(fieldLength);
        sb.append(", data='").append(data).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

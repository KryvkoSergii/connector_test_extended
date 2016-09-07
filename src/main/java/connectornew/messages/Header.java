package connectornew.messages;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by srg on 07.09.16.
 */
public class Header {
    public final static int MHDR = 8;
    private int messageLength;
    private int messageType;

    public Header() {

    }

    public Header(byte[] message) {
        int messageLength = ByteBuffer.wrap(message, 0, 3).getInt();
        int messageType = ByteBuffer.wrap(message, 4, 7).getInt();
    }

    //static methods
    public static int parseMessageLength(byte[] message) {
        return ByteBuffer.wrap(message, 0, 3).getInt();
    }

    public static int parseMessageType(byte[] message) {
        return ByteBuffer.wrap(message, 4, 7).getInt();
    }

    //getters and setters
    public int getMessageLength() {
        return messageLength;
    }

    public void setMessageLength(int messageLength) {
        this.messageLength = messageLength;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }
}

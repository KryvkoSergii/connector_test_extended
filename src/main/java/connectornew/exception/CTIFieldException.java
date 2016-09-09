package connectornew.exception;

/**
 * Created by A.Osadchuk on 01.09.2016.
 */
public class CTIFieldException extends Exception {

    public CTIFieldException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "CTIFieldException: " + super.getMessage();
    }
}

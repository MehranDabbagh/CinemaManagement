package CustomExceptions;

public class DateFormatException extends RuntimeException{
    public DateFormatException() {
    }

    public DateFormatException(String message) {
        super(message);
    }

    public DateFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}

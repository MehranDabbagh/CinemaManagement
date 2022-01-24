package CustomExceptions;

public class OutOfRangeInputException extends  RuntimeException{
    public OutOfRangeInputException() {
    }

    public OutOfRangeInputException(String message) {
        super(message);
    }

    public OutOfRangeInputException(String message, Throwable cause) {
        super(message, cause);
    }
}

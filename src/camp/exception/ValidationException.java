package camp.exception;

import java.util.LinkedList;
import java.util.List;

public class ValidationException extends Exception{
    public ValidationException() {
        super("발생한 에러 : ");
    }
}

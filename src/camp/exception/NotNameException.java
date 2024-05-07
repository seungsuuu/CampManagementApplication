package camp.exception;

// 이름이 한 글자거나 이름에 숫자가 입력된 경우 예외 처리
public class NotNameException extends Exception{
    public NotNameException() {
        super("이름을 확인해주세요!");        
    }
}

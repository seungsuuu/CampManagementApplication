package camp.exception;

public class ValidationException extends Exception {
    /**
     * 이름, 상태 예외처리
     * @param errorType is string. 이름과 상태 중 예외가 발생한 곳
     */
    public ValidationException(String errorType) {
        if (errorType.equals("notName")) {
            System.out.println("이름을 확인해주세요!");
        } else if (errorType.equals("notStatus")) {
            System.out.println("상태를 입력해주세요!");
        }
    }

    /**
     * 과목 예외처리
     * @param isNotEnoughSubject is boolean. 필수과목 3개 이상, 선택과목 2개 이상 입력 여부
     * @param isNotSubject is boolean. 과목이 아닌 값의 입력 여부
     */
    public ValidationException(boolean isNotEnoughSubject, boolean isNotSubject) {
        if (isNotEnoughSubject) {
            System.out.println("필수과목 3개 이상, 선택과목 2개 이상 입력해야 합니다!");
        }
        if (isNotSubject) {
            System.out.println("없는 과목입니다!");
        }
    }
}

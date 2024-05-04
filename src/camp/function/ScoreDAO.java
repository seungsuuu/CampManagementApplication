package camp.function;

import camp.model.Score;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class ScoreDAO {
    StudentDAO studentDAO = new StudentDAO();
    SubjectDAO subjectDAO = new SubjectDAO();

    // 데이터 저장할 리스트
    private List<Score> scoreStore = new LinkedList<>();

    public List<Score> getScoreStore() {
        return scoreStore;
    }

    public ScoreDAO() {
        this.scoreStore = Stream.of(
                new Score(
                        studentDAO.getStudentStore().get(0).getStudentId(),
                        subjectDAO.getSubjectStore().get(0).getSubjectId(),
                        1,
                        10,
                        'N'
                ),
                new Score(
                        studentDAO.getStudentStore().get(1).getStudentId(),
                        subjectDAO.getSubjectStore().get(1).getSubjectId(),
                        1,
                        20,
                        'N'
                ),
                new Score(
                        studentDAO.getStudentStore().get(2).getStudentId(),
                        subjectDAO.getSubjectStore().get(2).getSubjectId(),
                        1,
                        30,
                        'N'
                )
        ).toList();
    }

    // 수강생 고유번호 입력받음
    public String enterStudentId() {
        System.out.print("\n관리할 수강생의 번호를 입력하시오...");
        Scanner sc = new Scanner(System.in);
        return sc.next();
    }

    // 수강생의 과목별 시험 회차 및 점수 등록
    public void createScore() {
        String studentId = enterStudentId(); // 관리할 수강생 고유 번호
        System.out.println("시험 점수를 등록합니다...");
        // 기능 구현
        System.out.println("\n점수 등록 성공!");
    }

    // 수강생의 과목별 회차 점수 수정
    public void updateRoundScoreBySubject() {
        String studentId = enterStudentId(); // 관리할 수강생 고유 번호
        // 기능 구현 (수정할 과목 및 회차, 점수)
        System.out.println("시험 점수를 수정합니다...");
        // 기능 구현
        System.out.println("\n점수 수정 성공!");
    }

    // 수강생의 특정 과목 회차별 등급 조회
    public void inquireRoundGradeBySubject() {
        String studentId = enterStudentId(); // 관리할 수강생 고유 번호
        // 기능 구현 (조회할 특정 과목)
        System.out.println("회차별 등급을 조회합니다...");
        // 기능 구현
        System.out.println("\n등급 조회 성공!");
    }
}

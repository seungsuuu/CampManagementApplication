package camp.function;

import camp.model.Score;
import camp.model.Student;
import camp.model.Subject;

import javax.swing.plaf.synth.SynthColorChooserUI;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
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
        this.scoreStore = new LinkedList<>(Stream.of(
                new Score(
                        studentDAO.getStudentStore().get(0).getStudentId(),
                        subjectDAO.getSubjectStore().get(0).getSubjectId(),
                        1,
                        10,
                        'N'
                ),
                new Score(
                        studentDAO.getStudentStore().get(0).getStudentId(),
                        subjectDAO.getSubjectStore().get(0).getSubjectId(),
                        2,
                        20,
                        'F'
                ),
                new Score(
                        studentDAO.getStudentStore().get(2).getStudentId(),
                        subjectDAO.getSubjectStore().get(2).getSubjectId(),
                        1,
                        30,
                        'N'
                )
        ).toList());
    }

    // 수강생 고유번호 입력받음
    public String enterStudentId() {
        System.out.print("\n관리할 수강생의 번호를 입력하시오...");
        Scanner sc = new Scanner(System.in);
        return sc.next();
    }

    // 과목 이름 입력받기
    private String enterSubjectName() {
        System.out.print("\n관리할 과목의 이름을 입력하시오...");
        Scanner sc = new Scanner(System.in);
        // 공백이 포함된 문장도 있으므로 nextLine을 사용한다
        return sc.nextLine();
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
    public void inquireRoundRankBySubject() {
        LinkedList<String> studentSubjects = new LinkedList<>();

        boolean studentIdCheck = false;
        boolean subjectCheck = false;

        // 기능 구현 (조회할 수강생의 조회할 특정 과목)
        String studentId = enterStudentId(); // 관리할 수강생 고유 번호
        for(Student st : studentDAO.getStudentStore()) {
            if (Objects.equals(st.getStudentId(), studentId)){
                System.out.println("수강생 번호 일치");
                studentIdCheck = true;
                studentSubjects = st.getStudentSubjects();
                break;
            } else {
                System.out.println("입력된 수강생 번호와 일치하는 수강생이 없습니다.");
                return;
            }
        }

        String subjectName = enterSubjectName(); // 관리할 수강 과목
        for (String sj : studentSubjects) {
            if(Objects.equals(sj, subjectName)) {
                System.out.println("수강생 과목 일치");
                subjectCheck = true;
                break;
            } else {
                System.out.println("입력된 수강생 과목과 일치하는 수강생의 수강 과목이 없습니다.");
                return;
            }
        }

        String subjectID;
        for (Subject sjs : subjectDAO.getSubjectStore()){
            if(Objects.equals(subjectName, sjs.getSubjectName())) {
                subjectID = sjs.getSubjectId();
                break;
            } else {
                System.out.println("수강 과목 이름에서 ID로 변경 실패!");
                return;
            }
        }

        System.out.println("회차별 등급을 조회합니다...");
        // 기능 구현
        if (studentIdCheck && subjectCheck ) {

        }



        System.out.println("\n등급 조회 성공!");
    }
}

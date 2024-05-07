package camp.function;

import camp.model.Score;
import camp.model.Student;
import camp.model.Subject;

import java.util.*;
import java.util.stream.Stream;

public class ScoreDAO {

    StudentDAO studentDAO = new StudentDAO();
    SubjectDAO subjectDAO = new SubjectDAO();

    // 데이터 저장할 리스트
    private List<Score> scoreStore = new LinkedList<>();

    public List<Score> getScoreStore() {
        return scoreStore;
    }

    // 성적을 추가하기 위한 setter
    public void AddScoreStore(Score score) {
        scoreStore.add(score);
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
        ).toList());
    }

    // 수강생 고유번호 입력받음
    public String enterStudentId() {
        System.out.print("\n관리할 수강생의 번호를 입력하시오...");
        Scanner sc = new Scanner(System.in);
        return sc.next();
    }

    // 해당 수강생이 등록한 과목 출력하기
    private void inquirySubject(Student student) {
        System.out.println(student.getStudentName() + "님의 수강 내역");
        for (String subject : student.getStudentSubjects()) {
            System.out.println(subject);
        }
    }

    // 과목 이름 입력받기
    private String enterSubjectName() {
        System.out.print("\n관리할 과목의 이름을 입력하시오...");
        Scanner sc = new Scanner(System.in);
        // 공백이 포함된 문장도 있으므로 nextLine을 사용한다
        return sc.nextLine();
    }

    // 랭크 산정 함수
    private Character enterRank(Subject subject, int score) {
        // 과목 타입
        String SUBJECT_TYPE_MANDATORY = "MANDATORY";
        String SUBJECT_TYPE_CHOICE = "CHOICE";

        Character scorerank = '-';
        // 필수 과목일 경우
        if (Objects.equals(subject.getSubjectType(), SUBJECT_TYPE_MANDATORY)) {
            if (score >= 95) {
                scorerank = 'A';
            } else if (score >= 90) {
                scorerank = 'B';
            } else if (score >= 80) {
                scorerank = 'C';
            } else if (score >= 70) {
                scorerank = 'D';
            } else if (score >= 60) {
                scorerank = 'F';
            } else {
                scorerank = 'N';
            } // 선택 과목일 경우
        } else if (Objects.equals(subject.getSubjectType(), SUBJECT_TYPE_CHOICE)) {
            switch (score / 10) {
                case 10, 9 -> scorerank = 'A';
                case 8 -> scorerank = 'B';
                case 7 -> scorerank = 'C';
                case 6 -> scorerank = 'D';
                case 5 -> scorerank = 'F';
                default -> scorerank = 'N';
            }
        }
        return scorerank;
    }

    // 과목 회차 입력받고, data가 없을 경우 점수 입력 받기
    private void enterScore(ScoreDAO scoreDAO, SubjectDAO subjectDAO
            , Student student, String subject) {
        List<Subject> subjectList = subjectDAO.getSubjectStore();
        List<Score> scoreList = scoreDAO.getScoreStore();

        Scanner sc = new Scanner(System.in);
        char scorerank;
        System.out.print("\n점수를 입력할 회차를 입력해주세요 : ");
        int round = sc.nextInt();

        //해당 수강생의 해당 과목의 해당 회차의 성적이 존재하는지 조회
        Optional<Subject> findsubject = subjectList.stream().filter(s -> s.getSubjectName().equals(subject))
                .findFirst();
        String subjectname = findsubject.get().getSubjectId();

        Optional<Score> filteredscore = scoreList.stream().filter(s -> s.getSubjectId().equals(subjectname))
                .filter(s -> s.getStudentId().equals(student.getStudentId()))
                .filter(s -> s.getScoreRound() == round)
                .findFirst();

        if (filteredscore.isPresent()) { // 해당 학생의 해당 과목의 해당 회차 점수가 이미 존재할 경우
            System.out.println("이미 존재하는 점수입니다.");
            return;
        } else { // 점수가 존재하지 않을 경우
            System.out.print("점수를 입력해주세요 : ");
            int score = sc.nextInt();

            // 과목명으로 과목 ID, 과목 타입 알아내기
            Optional<Subject> acesssubject = subjectList.stream()
                    .filter(sub -> sub.getSubjectName().equals(subject))
                    .findFirst();
            // 과목 객체로 변환
            Subject realsubject = acesssubject.get();
            // 랭크 산정
            scorerank = enterRank(realsubject, score);
            // scorestore에 추가할 score 객체 생성
            Score newscore = new Score(student.getStudentId(), realsubject.getSubjectId(), round, score, scorerank);
            // add
            scoreDAO.AddScoreStore(newscore);
            System.out.println(scoreDAO.getScoreStore());
        }
    }

    // studentDAO,scoreDAO,subjectDAO
    // 수강생의 과목별 시험 회차 및 점수 등록
    public void createScore(StudentDAO studentDAO, ScoreDAO scoreDAO, SubjectDAO subjectDAO) {
        String studentId = enterStudentId(); // 관리할 수강생 고유 번호
        String subject; // 관리자에게 입력받은 과목명

        List<Student> students = studentDAO.getStudentStore();

        // 입력받은 수강생이 실제 수강생 목록에 존재하는지 확인
        Optional<Student> optionalstudent = students.stream().filter(s -> s.getStudentId().equals(studentId)).findFirst();
        Student student;

        if (optionalstudent.isPresent()) { // 수강생의 이름이 일치할 경우
            student = optionalstudent.get();
            inquirySubject(student); // 해당 학생의 수강 과목 조회
            subject = enterSubjectName();

            // 해당 수강생의 수강 과목에 해당 과목이 있는지 조회
            boolean existsubject = false;
            for (String name : student.getStudentSubjects()) {
                if (name.equals(subject)) {
                    existsubject = true;
                    break;
                }
            }
            if (existsubject) { // 과목이 존재한다면 점수 입력 함수로 이동
                enterScore(scoreDAO, subjectDAO, student, subject);

            } else {
                System.out.println("해당 과목은 없습니다.");
            }
        } else {
            System.out.println("해당 학생을 찾을 수 없습니다.");
        }
    }

    // 수강생의 과목별 회차 점수 수정
    public void updateRoundScoreBySubject(ScoreDAO scoreDAO, SubjectDAO subjectDAO) {
        String studentId = enterStudentId(); // 관리할 수강생 고유 번호
        String subjectName = enterSubjectName(); // 수정할 과목명
        int round;

        // 학생 찾기
        Optional<Student> optionalStudent = studentDAO.getStudentStore()
                .stream()
                .filter(student -> student.getStudentId().equals(studentId))
                .findFirst();

        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            // 해당 학생이 해당 과목을 수강했는지 확인
            if (student.getStudentSubjects().contains(subjectName)) {
                // 수정할 회차 입력
                Scanner sc = new Scanner(System.in);
                System.out.print("수정할 회차를 입력하세요: ");
                round = sc.nextInt();

                // 점수 엔트리 찾기
                Optional<Score> optionalScore = scoreDAO.getScoreStore()
                        .stream()
                        .filter(score -> score.getStudentId().equals(studentId) &&
                                score.getScoreRound() == round)
                        .findFirst();

                // 등급 재계산 부분
                if (optionalScore.isPresent()) {
                    Score score = optionalScore.get();
                    // 새로운 점수 입력
                    System.out.print("새로운 점수를 입력하세요: ");
                    int newScore = sc.nextInt();
                    // 점수 업데이트
                    score.setScorePoint(newScore);

                    // 과목명으로부터 해당 과목 객체를 가져옴
                    Optional<Subject> optionalSubject = subjectDAO.getSubjectStore().stream()
                            .filter(subject -> subject.getSubjectName().equals(subjectName))
                            .findFirst();

                    if (optionalSubject.isPresent()) {
                        Subject subject = optionalSubject.get();
                        // 등급 재계산을 위해 과목 객체를 인자로 전달하여 enterRank 메서드 호출
                        score.setScoreRank(enterRank(subject, newScore));
                        System.out.println("점수가 수정되었습니다: " + score);
                    } else {
                        System.out.println("해당 과목을 찾을 수 없습니다.");
                    }
                } else {
                    System.out.println("해당 회차의 점수가 존재하지 않습니다.");
                }
            } else {
                System.out.println("해당 학생을 찾을 수 없습니다.");
            }
        }

        // 수강생의 특정 과목 회차별 등급 조회
        public void inquireRoundGradeBySubject () {
            String studentId = enterStudentId(); // 관리할 수강생 고유 번호
            // 기능 구현 (조회할 특정 과목)
            System.out.println("회차별 등급을 조회합니다...");
            // 기능 구현
            System.out.println("\n등급 조회 성공!");
        }
    }
}
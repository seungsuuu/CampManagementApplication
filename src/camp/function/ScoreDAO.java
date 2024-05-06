package camp.function;

import camp.model.Score;
import camp.model.Subject;

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
                        studentDAO.getStudentStore().get(0).getStudentId(),
                        subjectDAO.getSubjectStore().get(2).getSubjectId(),
                        1,
                        30,
                        'N'
                )
        ).toList());
    }

    // 수강생 고유번호 입력받음
    public String enterStudentId() {
        System.out.print("\n수강생의 번호를 입력하시오...");
        Scanner sc = new Scanner(System.in);
        return sc.next();
    }

    // 과목 이름 입력받기
    private String enterSubjectName() {
        System.out.print("\n과목의 이름을 입력하시오...");
        Scanner sc = new Scanner(System.in);
        // 공백이 포함된 문장도 있으므로 nextLine을 사용한다
        return sc.nextLine();
    }

    // 과목 이름 입력받기
    private int enterRound() {
        System.out.print("\n과목의 시험 회차를 입력하시오...");
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
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
        List<Score> inquireScore = new LinkedList<>();
        Score resultScore = new Score();

        String studentId = enterStudentId(); // 조회할 수강생 고유 번호
        String subjectName = enterSubjectName(); // 조회할 수강 과목 이름
        int round = enterRound(); // 죄회할 성적 회차

        String subjectId = "";
        for (Subject subject : subjectDAO.getSubjectStore()){
            if(Objects.equals(subjectName, subject.getSubjectName())) {
                subjectId = subject.getSubjectId();
                break;
            } else {
                System.out.println("수강 과목 이름을 잘못 입력하였습니다.");
                return;
            }
        }

        int countStudentId = 0;
        for(Score score : scoreStore) {
            if (Objects.equals(score.getStudentId(), studentId)){
                inquireScore.add(score);
                countStudentId++;
            }
        }
        if (countStudentId == 0) {
            System.out.println("입력된 수강생 번호를 잘못 입력하였거나, 입력된 수강생 번호와 일치하는 수강생의 점수가 없습니다.");
            return;
        }

        int countSubjectId = 0;
        for(Score score: inquireScore) {
            if (!Objects.equals(score.getSubjectId(), subjectId)){
                inquireScore.remove(score);
            } else {
                countSubjectId++;
            }
        }
        if (countSubjectId == 0) {
            System.out.println("입력된 수강 과목과 일치하는 과목의 점수가 없습니다.");
            return;
        }

        int countRound = 0;
        for (Score score : inquireScore) {
            if(round == score.getScoreRound()) {
                resultScore = score;
                countRound++;
            }
        }
        if (countRound != 1) {
            System.out.println("입력된 시험 회차와 일치하는 과목의 시험 회차가 없습니다.");
            return;
        }

        System.out.println("\n회차별 등급을 조회합니다...");

        System.out.println("\n수강생 : "+studentId+" / 수강 과목 : "+subjectName);
        System.out.println("시험 회차 : "+round+" / 시험 등급 : "+resultScore.getScoreRank());

        System.out.println("\n등급 조회 성공!");
    }
}

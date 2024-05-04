package camp.function;

import camp.exception.NotEnoughSubjectsException;
import camp.exception.NotStatusException;
import camp.exception.SubjectOutOfBoundException;
import camp.model.Student;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class StudentDAO {
    private static Scanner sc = new Scanner(System.in);
    SubjectDAO subjectDAO = new SubjectDAO();

    // 고유 번호
    private int studentIndex;
    private static final String INDEX_TYPE_STUDENT = "ST";

    // 데이터 저장 리스트
    private List<Student> studentStore = new LinkedList<>();

    public List<Student> getStudentStore() {
        return studentStore;
    }

    // 생성자
    public StudentDAO() {
        this.studentStore = new LinkedList<>(Stream.of(
                new Student(
                        sequence(),
                        "여단",
                        "Green",
                        new LinkedList<>(List.of("Java", "객체지향", "Spring", "디자인 패턴", "Spring Security"))
                ),
                new Student(
                        sequence(),
                        "이단",
                        "Red",
                        new LinkedList<>(List.of("Java", "객체지향", "Spring", "JPA", "디자인 패턴", "Spring Security", "Redis"))
                ),
                new Student(
                        sequence(),
                        "삼단",
                        "Yellow",
                        new LinkedList<>(List.of("Java", "객체지향", "Spring", "JPA", "MySQL", "디자인 패턴", "Spring Security", "Redis", "MongoDB"))
                )
        ).toList());
    }

      // 수강생 등록
    public void createStudent() {
        String studentID = initializeData.sequence(InitializeData.INDEX_TYPE_STUDENT);
        String studentName = " ";
        String studentStatus = " ";
        LinkedList<String> studentSubjects = new LinkedList<>();
        String[] mandatorySubjects = new String[]{"Java", "객체지향", "Spring", "JPA", "MySQL"}; // 최소 3개 이상
        String[] choiceSubjects = new String[]{"디자인 패턴", "Spring Security", "Redis", "MongoDB"}; // 최소 2개 이상
        int count = 0;

        // 기능 구현 (필수 과목, 선택 과목)

        Student student = new Student(studentID, studentName, studentStatus, studentSubjects); // 수강생 인스턴스 생성 예시 코드
        // 기능 구현
        System.out.println("수강생 등록 성공!\n");
    }

    // 수강생 목록 조회
    public void inquireStudent() {
        System.out.println("\n수강생 목록을 조회합니다...");
        // 기능 구현
        System.out.println("\n수강생 목록 조회 성공!");
    }

    // 고유 번호 증가
    public String sequence() {
        studentIndex++;
        return INDEX_TYPE_STUDENT + studentIndex;
    }
}




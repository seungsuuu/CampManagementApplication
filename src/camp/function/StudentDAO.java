package camp.function;

import camp.model.Student;

import java.util.*;
import java.util.stream.Stream;


public class StudentDAO {
    private static Scanner sc = new Scanner(System.in);
    SubjectDAO subjectDAO = new SubjectDAO();
    InitializeData initializeData = new InitializeData();

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


        Student student = new Student(studentID, studentName, studentStatus, studentSubjects); // 수강생 인스턴스 생성 예시 코드
        // 기능 구현
        System.out.println("수강생 등록 성공!\n");
    }


    // 수강생 목록 조회
    public void inquireStudent() {
        List<Student> students = getStudentStore();

        System.out.println("\n수강생 목록을 조회합니다...");
        System.out.print("조회할 학생의 이름을 입력하세요 : ");
        String name = sc.next();
        System.out.print("조회할 학생의 고유번호을 입력하세요 : ");
        String index = sc.next();

        Optional<Student> optionalStudentName = students.stream().filter(s-> s.getStudentName().equals(name)).findFirst();
        Optional<Student> optionalStudentIndex = students.stream().filter(s -> s.getStudentId().equals(index)).findFirst();

        Student student;

        if(optionalStudentIndex.isPresent() && optionalStudentName.isPresent()){
            student = optionalStudentIndex.get();
            System.out.println("=== 조회내용 ===");
            System.out.println("이름: "+student.getStudentName());
            System.out.println("고유번호 : "+student.getStudentId());
            System.out.println("상태 : "+student.getStudentStatus());
            System.out.println("과목 : "+student.getStudentSubjects());
            System.out.println("\n수강생 목록 조회 성공!");
        }else{
            System.out.println("다시한번 확인해주세요.");
        }

    }

    // 고유 번호 증가
    public String sequence() {
        studentIndex++;
        return INDEX_TYPE_STUDENT + studentIndex;
    }
}




package camp.function;

import camp.exception.ValidationException;
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
    // 리스트 중복 제거: list -> set -> list
    //                 List<String> newList = list.stream().distinct().collect(Collectors.toList());
    public void createStudent() {
        String studentID = sequence();

        boolean isPrintName = true;
        String studentName = " ";

        boolean isPrintStatus = true;
        String studentStatus = " ";
        LinkedList<String> statusTypes = new LinkedList<>(List.of("green", "yellow", "red"));

        boolean isPrintSubject = true;
        LinkedList<String> studentSubjects = new LinkedList<>();
        int countMandatory = 0;
        int countChoice = 0;
        LinkedList<String> mandatorySubjects = new LinkedList<>(); // 최소 3개 이상
        LinkedList<String> choiceSubjects = new LinkedList<>(); // 최소 2개 이상
        boolean isNotSubject = false;
        boolean isNotEnoughSubject = false;

        sc = new Scanner(System.in);
        String input = " ";
        int index = 0;

        for (int i = 0; i < subjectDAO.getSubjectStore().size(); i++) {
            if (subjectDAO.getSubjectStore().get(i).getSubjectType().equals("MANDATORY")) {
                mandatorySubjects.add(subjectDAO.getSubjectStore().get(i).getSubjectName());
            } else {
                choiceSubjects.add(subjectDAO.getSubjectStore().get(i).getSubjectName());
            }
        }

        while (true) {
            try {
                if (isPrintName) {
                    System.out.println("\n수강생을 등록합니다...");
                    System.out.print("수강생 이름 입력: ");
                    studentName = sc.nextLine();
                    if (studentName.length() <= 1 || studentName.chars().anyMatch(Character::isDigit)) {
                        throw new ValidationException("notName");
                    }
                    isPrintName = false;
                }

                if (isPrintStatus) {
                    System.out.print("\n수강생 상태: ");
                    for (int i = 0; i < statusTypes.size(); i++) {
                        System.out.print((i + 1) + "." + statusTypes.get(i) + " ");
                    }
                    System.out.print("\n수강생 상태를 번호로 입력하세요: ");
                    input = sc.nextLine();
                    index = Integer.parseInt(input);
                    if (index <= 0 || index > statusTypes.size()) {
                        throw new ValidationException("notStatus");
                    } else {
                        studentStatus = statusTypes.get(index - 1);
                        isPrintStatus = false;
                    }
                }

                if (isPrintSubject) {
                    countMandatory = 0;
                    countChoice = 0;
                    isNotSubject = false;
                    isNotEnoughSubject = false;

                    System.out.print("\n필수과목: ");
                    for (int i = 0; i < mandatorySubjects.size(); i++) {
                        System.out.print((i + 1) + "." + mandatorySubjects.get(i) + " ");
                    }
                    System.out.print("\n선택과목: ");
                    for (int i = 0; i < choiceSubjects.size(); i++) {
                        System.out.print((i + mandatorySubjects.size() + 1) + "." + choiceSubjects.get(i) + " ");
                    }
                    System.out.println("\n필수과목 3개 이상, 선택과목 2개 이상, 입력이 끝나면 end 를 입력하세요.");
                    System.out.print("수강생이 선택한 과목 번호를 입력하세요: ");
                }

                input = sc.next();
                if (input.equals("end")) {
                    if (countMandatory >= 3 && countChoice >= 2 && !isNotSubject) {
                        break;
                    }
                    if (countMandatory >= 3 && countChoice >= 2) {
                        isNotEnoughSubject = false;
                    } else {
                        isNotEnoughSubject = true;
                    }
                    isPrintSubject = true;
                    throw new ValidationException(isNotEnoughSubject, isNotSubject);
                }

                index = Integer.parseInt(input);
                if (index <= 0 || index > (mandatorySubjects.size() + choiceSubjects.size())) {
                    isNotSubject = true;
                } else if (index <= mandatorySubjects.size()) {
                    studentSubjects.add(mandatorySubjects.get(index - 1));
                    countMandatory++;
                } else {
                    studentSubjects.add(choiceSubjects.get(index - mandatorySubjects.size() - 1));
                    countChoice++;
                }
                isPrintSubject = false;
            } catch (NumberFormatException e) {
                System.out.println("번호를 입력하세요!");
                sc = new Scanner(System.in);
                studentSubjects.clear();
            } catch (ValidationException e) {
                sc = new Scanner(System.in);
                studentSubjects.clear();
            }
        }

        Set<String> set = new LinkedHashSet<>(studentSubjects);
        LinkedList<String> distinctStudentSubjects = new LinkedList<>(set);

        Student student = new Student(studentID, studentName, studentStatus, distinctStudentSubjects);
        studentStore.add(student);

        System.out.println("\n수강생 등록 성공!\n");
//        System.out.println(studentStore);
    }

    // 수강생 목록 조회
    public void inquireStudent() {
        List<Student> studentList = getStudentStore();

        System.out.println("\n수강생 목록을 조회합니다...");
        for (Student student : studentStore) {
            System.out.println("이름 : " + student.getStudentName() + " / 고유번호 : " + student.getStudentId());
        }
        System.out.print("\n상세정보를 확인하시겠습니까?(네 / 아니오) ");
        String check = sc.next();

        if (check.equals("네")) {
            System.out.print("\n조회할 학생의 고유번호를 입력하세요 : ");
            String index = sc.next();
            Optional<Student> optionalStudentIndex = studentList.stream().filter(student -> student.getStudentId().equals(index)).findFirst();

            if (optionalStudentIndex.isPresent()) {
                Student student = optionalStudentIndex.get();
                System.out.println("이름: " + student.getStudentName());
                System.out.println("고유번호 : " + student.getStudentId());
                System.out.println("상태 : " + student.getStudentStatus());
                System.out.println("과목 : " + student.getStudentSubjects());
                System.out.println("\n수강생 목록 조회 성공!");
            } else {
                System.out.println("고유번호를 확인해주세요.");
            }
        }
    }

    // 고유 번호 증가
    public String sequence() {
        studentIndex++;
        return INDEX_TYPE_STUDENT + studentIndex;
    }
}
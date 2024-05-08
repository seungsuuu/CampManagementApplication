package camp.function;

import camp.exception.ValidationException;
import camp.model.Student;
import camp.model.Subject;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
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

    /**
     * 수강생 등록 <br>
     * 이름, 상태, 선택한 과목들을 입력 받아 수강생 정보를 저장하는 리스트에 삽입 <br>
     * <p>
     * 변수 정보 <br>
     * studentID, studentName, studentStatus, studentSubjects 새로 등록한 수강생의 고유번호, 이름, 상태, 선택한 과목들 <br>
     * isPrintName, isPrintStatus, isPrintSubject 이름, 상태, 선택한 과목들 입력 시 안내문 출력 여부 <br>
     * statusTypes, mandatorySubjects, choiceSubjects 선택할 수 있는 수강생 상태, 필수과목, 선택과목 종류를 저장하는 리스트 <br>
     * countMandatory, countChoice 수강생이 선택한 필수과목 수, 선택과목 수 <br>
     * isNotSubject, isNotEnoughSubject 선택한 과목 입력 시 예외처리 여부 <br>
     *
     * @throws NumberFormatException
     *         유효한 이름, 상태, 과목이 입력되지 않았을 때 예외 처리
     */
    public void createStudent() {
        String studentID = sequence();
        String studentName = " ";
        String studentStatus = " ";
        List<String> studentSubjects = new LinkedList<>();

        boolean isPrintName = true;
        boolean isPrintStatus = true;
        boolean isPrintSubject = true;

        LinkedList<String> statusTypes = new LinkedList<>(List.of("green", "yellow", "red"));
        List<String> mandatorySubjects = subjectDAO.getSubjectStore().stream()
                .filter(s -> s.getSubjectType().equals("MANDATORY"))
                .map(Subject::getSubjectName)
                .toList(); // 최소 3개 이상
        List<String> choiceSubjects = subjectDAO.getSubjectStore().stream()
                .filter(s -> s.getSubjectType().equals("CHOICE"))
                .map(Subject::getSubjectName)
                .toList(); // 최소 2개 이상

        int countMandatory = 0;
        int countChoice = 0;
        boolean isNotSubject = false;
        boolean isNotEnoughSubject = false;

        sc = new Scanner(System.in);
        String input = " ";
        int inputToInt = 0;

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

                    inputToInt = Integer.parseInt(input);
                    if (inputToInt <= 0 || inputToInt > statusTypes.size()) {
                        throw new ValidationException("notStatus");
                    } else {
                        studentStatus = statusTypes.get(inputToInt - 1);
                        isPrintStatus = false;
                    }
                }

                if (isPrintSubject) {
                    countMandatory = 0;
                    countChoice = 0;
                    isNotSubject = false;
                    isNotEnoughSubject = false;

                    AtomicInteger index = new AtomicInteger(1); // 시작 인덱스
                    System.out.print("\n필수과목: ");
                    mandatorySubjects.forEach(name -> {
                                int currentIndex = index.getAndIncrement();
                                System.out.print(currentIndex + "." + name + " ");
                            }
                    );
                    System.out.print("\n선택과목: ");
                    choiceSubjects.forEach(name -> {
                                int currentIndex = index.getAndIncrement();
                                System.out.print(currentIndex + "." + name + " ");
                            }
                    );
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

                inputToInt = Integer.parseInt(input);
                if (inputToInt <= 0 || inputToInt > (mandatorySubjects.size() + choiceSubjects.size())) {
                    isNotSubject = true;
                } else if (inputToInt <= mandatorySubjects.size()) {
                    studentSubjects.add(mandatorySubjects.get(inputToInt - 1));
                    countMandatory++;
                } else {
                    studentSubjects.add(choiceSubjects.get(inputToInt - mandatorySubjects.size() - 1));
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

        LinkedList<String> distinctStudentSubjects = studentSubjects.stream().distinct().collect(Collectors.toCollection(LinkedList::new));
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
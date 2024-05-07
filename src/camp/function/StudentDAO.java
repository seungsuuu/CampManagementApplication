package camp.function;

import camp.exception.NotEnoughSubjectsException;
import camp.exception.NotStatusException;
import camp.exception.SubjectOutOfBoundException;
import camp.exception.ValidationException;
import camp.model.Student;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import camp.model.Student;



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
        String studentName = " ";
        String studentStatus = " ";
        LinkedList<String> statusTypes = new LinkedList<>(List.of("green", "yellow", "red"));
        LinkedList<String> studentSubjects = new LinkedList<>();
        LinkedList<String> mandatorySubjects = new LinkedList<>(); // 최소 3개 이상
        LinkedList<String> choiceSubjects = new LinkedList<>(); // 최소 2개 이상
        LinkedList<String> errors = new LinkedList<>();
        int countMandatory = 0;
        int countChoice = 0;
        boolean printStatus = true;
        boolean printSubject = true;
        String input = " ";
        int index = 0;
        sc = new Scanner(System.in);

        System.out.println("\n수강생을 등록합니다...");
        System.out.print("수강생 이름 입력: ");
        studentName = sc.nextLine();

        while (true) {
            try {
                if (printStatus) {
                    System.out.print("\n수강생 상태: ");
                    for (int i = 0; i < statusTypes.size(); i++) {
                        System.out.print((i + 1) + "." + statusTypes.get(i) + " ");
                    }
                    System.out.println();
                    System.out.print("수강생 상태를 번호로 입력하세요: ");
                    input = sc.nextLine();
                    index = Integer.parseInt(input);
                    if (index <= 0 || index > statusTypes.size()) {
                        throw new NotStatusException();
                    } else {
                        studentStatus = statusTypes.get(index - 1);
                        printStatus = false;
                    }
                }

                if (printSubject) {
                    countMandatory = 0;
                    countChoice = 0;
                    mandatorySubjects.clear();
                    choiceSubjects.clear();

                    for (int i = 0; i < subjectDAO.getSubjectStore().size(); i++) {
                        if (subjectDAO.getSubjectStore().get(i).getSubjectType().equals("MANDATORY")) {
                            mandatorySubjects.add(subjectDAO.getSubjectStore().get(i).getSubjectName());
                        } else {
                            choiceSubjects.add(subjectDAO.getSubjectStore().get(i).getSubjectName());
                        }
                    }
                    System.out.print("\n필수과목: ");
                    for (int i = 0; i < mandatorySubjects.size(); i++) {
                        System.out.print((i + 1) + "." + mandatorySubjects.get(i) + " ");
                    }
                    System.out.println();
                    System.out.print("선택과목: ");
                    for (int i = 0; i < choiceSubjects.size(); i++) {
                        System.out.print((i + mandatorySubjects.size() + 1) + "." + choiceSubjects.get(i) + " ");
                    }
                    System.out.println();
                    System.out.println("입력이 끝나면 end 를 입력하세요!");
                    System.out.print("수강생이 선택한 과목 번호를 입력하세요: ");
                }

                if (!errors.isEmpty() && input.equals("end")) {
                    throw new ValidationException(errors);
                }

                input = sc.next();

                if (input.equals("end")) {
                    if (countMandatory >= 3 && countChoice >= 2) {
                        if (!errors.isEmpty()) {
                            throw new ValidationException(errors);
                        }
                        break;
                    } else {
                        throw new NotEnoughSubjectsException();
                    }
                }

                index = Integer.parseInt(input);
                if (index <= 0 || index > (mandatorySubjects.size() + choiceSubjects.size())) {
                    throw new SubjectOutOfBoundException();
                } else if (index <= mandatorySubjects.size()) {
                    studentSubjects.add(mandatorySubjects.get(index - 1));
                    countMandatory++;
                    printSubject = false;
                } else {
                    studentSubjects.add(choiceSubjects.get(index - mandatorySubjects.size() - 1));
                    countChoice++;
                    printSubject = false;
                }
            } catch (NotStatusException e) {
                System.out.println(e.getMessage());
                printStatus = true;
            } catch (NumberFormatException e) {
                System.out.println("번호를 입력하세요!");
                sc = new Scanner(System.in);
                studentSubjects.clear();
            } catch (ValidationException e) {
                errors.clear();
                studentSubjects.clear();
                sc = new Scanner(System.in);
                printSubject = true;
            } catch (SubjectOutOfBoundException e) {
                errors.add(new SubjectOutOfBoundException().getMessage());
                studentSubjects.clear();
                printSubject = false;
            } catch (NotEnoughSubjectsException e) {
                errors.add(new NotEnoughSubjectsException().getMessage());
                sc = new Scanner(System.in);
                studentSubjects.clear();
                printSubject = false;
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
        // 학생 목록 가져오기
        List<Student> studentsList = getStudentStore();

        System.out.println("\n수강생 목록을 조회합니다...");
        System.out.print("조회할 학생의 이름을 입력하세요 : ");
        String name = sc.next();
        System.out.print("조회할 학생의 고유번호을 입력하세요 : ");
        String index = sc.next();

        // 이름과 고유번호 확인
        Optional<Student> optionalStudentName = studentsList.stream().filter(s-> s.getStudentName().equals(name)).findFirst();
        Optional<Student> optionalStudentIndex = studentsList.stream().filter(s -> s.getStudentId().equals(index)).findFirst();


        // 조회하기
        if(optionalStudentIndex.isPresent() && optionalStudentName.isPresent()){
            Student student = optionalStudentIndex.get();
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
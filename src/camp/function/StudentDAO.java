package camp.function;

import camp.Exception.NotEnoughSubjectsException;
import camp.Exception.SubjectOutOfBoundException;
import camp.model.Student;

import java.util.*;

public class StudentDAO {
    // 스캐너
    private static Scanner sc = new Scanner(System.in);

    // 고유번호 부여
    InitializeData initializeData = new InitializeData();

    // 수강생 등록
    public void createStudent() {
        String studentID = initializeData.sequence(InitializeData.INDEX_TYPE_STUDENT);
        String studentName = " ";
        String studentStatus = " ";
        LinkedList<String> studentSubjects = new LinkedList<>();
        LinkedList<String> mandatorySubjects = new LinkedList<>(List.of("Java", "객체지향", "Spring", "JPA", "MySQL")); // 최소 3개 이상
        LinkedList<String> choiceSubjects = new LinkedList<>(List.of("디자인 패턴", "Spring Security", "Redis", "MongoDB")); // 최소 3개 이상
        int countMandatory = 0;
        int countChoice = 0;
        boolean printData = true;
        String input = " ";
        sc = new Scanner(System.in);

        System.out.println("\n수강생을 등록합니다...");
        System.out.print("수강생 이름 입력: ");
        studentName = sc.nextLine();

        System.out.print("수강생 상태 입력: ");
        studentStatus = sc.nextLine();

        while (true) {
            try {
                if (printData) {
                    System.out.print("필수과목: ");
                    for (int i = 0; i < mandatorySubjects.size(); i++) {
                        System.out.print((i + 1) + ". " + mandatorySubjects.get(i) + " ");
                    }
                    System.out.println();
                    System.out.print("선택과목: ");
                    for (int i = 0; i < choiceSubjects.size(); i++) {
                        System.out.print((i + mandatorySubjects.size() + 1) + ". " + choiceSubjects.get(i) + " ");
                    }
                    System.out.println();
                    System.out.println("입력이 끝나면 end 를 입력하세요!");
                    System.out.print("수강생이 선택한 과목을 입력하세요: ");
                }

                input = sc.next();
                if (input.equals("end")) {
                    if (countMandatory >= 3 && countChoice >= 2) {
                        break;
                    } else {
                        throw new NotEnoughSubjectsException();
                    }
                }

                int index = Integer.parseInt(input);

                if (index > (mandatorySubjects.size() + choiceSubjects.size())) {
                    throw new SubjectOutOfBoundException();
                } else if (index <= mandatorySubjects.size()) {
                    studentSubjects.add(mandatorySubjects.get(index - 1));
                    countMandatory++;
                    printData = false;
                } else {
                    studentSubjects.add(choiceSubjects.get(index - mandatorySubjects.size() - 1));
                    countChoice++;
                    printData = false;
                }

            } catch (SubjectOutOfBoundException e) {
                System.out.print(e.getMessage() + "\n\n");
                printData = true;
                studentSubjects.clear();
            } catch (NotEnoughSubjectsException e) {
                System.out.print(e.getMessage() + "\n\n");
                sc.nextLine();
                printData = true;
                studentSubjects.clear();
            } catch (NumberFormatException e) {
                System.out.println("과목번호를 입력하세요!\n");
                sc.nextLine();
                printData = true;
                studentSubjects.clear();
            }
        }

        Student student = new Student(studentID, studentName, studentStatus, studentSubjects);
        initializeData.setStudentStore(student);

        System.out.println("수강생 등록 성공!\n");
        System.out.println(initializeData.getStudentStore());
    }

    // 수강생 목록 조회
    public void inquireStudent() {
        System.out.println("\n수강생 목록을 조회합니다...");
        // 기능 구현
        System.out.println("\n수강생 목록 조회 성공!");
    }
}

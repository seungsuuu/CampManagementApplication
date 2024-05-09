package camp.model;

import java.util.LinkedList;

public class Student {
    private String studentId; // 고유번호
    private String studentName; // 이름
    private String studentStatus; // 상태
    private LinkedList<String> studentSubjects = new LinkedList<>(); // 선택 과목 목록

    // 생성자
    public Student() {
    }

    public Student(String studentId, String studentName, String studentStatus, LinkedList<String> studentSubjects) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentStatus = studentStatus;
        this.studentSubjects = studentSubjects;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", studentName='" + studentName + '\'' +
                ", studentStatus='" + studentStatus + '\'' +
                ", studentSubjects=" + studentSubjects +
                "}\n";
    }

    // getter
    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentStatus() {
        return studentStatus;
    }

    public LinkedList<String> getStudentSubjects() {
        return studentSubjects;
    }

}

package camp.function;

import camp.model.Subject;

import java.util.LinkedList;
import java.util.List;

public class SubjectDAO {
    // 과목 타입
    private String SUBJECT_TYPE_MANDATORY = "MANDATORY";
    private String SUBJECT_TYPE_CHOICE = "CHOICE";

    // index 관리 필드
    private int subjectIndex;
    protected static final String INDEX_TYPE_SUBJECT = "SU";

    // 데이터 저장 리스트
    private List<Subject> subjectStore = new LinkedList<>();

    public List<Subject> getSubjectStore() {
        return subjectStore;
    }

    public SubjectDAO() {
        this.subjectStore = List.of(
                new Subject(
                        sequence(),
                        "Java",
                        SUBJECT_TYPE_MANDATORY
                ),
                new Subject(
                        sequence(),
                        "객체지향",
                        SUBJECT_TYPE_MANDATORY
                ),
                new Subject(
                        sequence(),
                        "Spring",
                        SUBJECT_TYPE_MANDATORY
                ),
                new Subject(
                        sequence(),
                        "JPA",
                        SUBJECT_TYPE_MANDATORY
                ),
                new Subject(
                        sequence(),
                        "MySQL",
                        SUBJECT_TYPE_MANDATORY
                ),
                new Subject(
                        sequence(),
                        "디자인 패턴",
                        SUBJECT_TYPE_CHOICE
                ),
                new Subject(
                        sequence(),
                        "Spring Security",
                        SUBJECT_TYPE_CHOICE
                ),
                new Subject(
                        sequence(),
                        "Redis",
                        SUBJECT_TYPE_CHOICE
                ),
                new Subject(
                        sequence(),
                        "MongoDB",
                        SUBJECT_TYPE_CHOICE
                )
        );
    }

    // 고유번호
    public String sequence() {
        subjectIndex++;
        return INDEX_TYPE_SUBJECT + subjectIndex;
    }
}

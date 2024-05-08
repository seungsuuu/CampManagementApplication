package camp.model;

public class Score {
    private String studentId; // 수강생 고유번호
    private String subjectId; // 과목 고유번호
    private int scoreRound;// 회차 (1 ~ 10)
    private int scorePoint;// 점수 ( 0 ~ 100 (음수 안 됨) )
    private char scoreRank; // 등급 (A, B, C, D, E, F, N)

    public Score() {
    }

    public Score(String studentId, String subjectId, int scoreRound, int scorePoint, char scoreRank  ) {
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.scoreRound = scoreRound;
        this.scorePoint = scorePoint;
        this.scoreRank = scoreRank;
    }

    // 출력
    @Override
    public String toString() {
        return "Score{" +
                "studentId='" + studentId + '\'' +
                ", subjectId='" + subjectId + '\'' +
                ", scoreRound=" + scoreRound +
                ", scorePoint=" + scorePoint +
                ", scoreRank=" + scoreRank +
                "}\n";
    }

    // getter
    public int getScorePoint() {
        return scorePoint;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public int getScoreRound() {
        return scoreRound;
    }

    public char getScoreRank() {
        return scoreRank;
    }

    // setter
//    public void setStudentId(String studentId) {
//        this.studentId = studentId;
//    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

//    public void setScoreRound(int scoreRound) {
//        this.scoreRound = scoreRound;
//    }

    public void setScorePoint(int scorePoint) {
        this.scorePoint = scorePoint;
    }

    public void setScoreRank(char scoreRank) {
        this.scoreRank = scoreRank;
    }

    /* scoreID
    public Score(String seq) {
        this.scoreId = seq;
    }
    // Getter
    public String getScoreId() {
        return scoreId;
    }*/

}

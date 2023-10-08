public class Grade implements Comparable<Grade>, Cloneable {
    private Double partialScore, examScore;
    private Student student;
    private String course;

    public Grade(Double partialScore, Double examScore, Student student, String course) {
        this.partialScore = partialScore;
        this.examScore = examScore;
        this.student = student;
        this.course = course;
    }

    public Grade(Double partialScore, Double examScore, Student student) {
        this(partialScore, examScore, student, null);
    }

    public Grade(Double partialScore, Double examScore) {
        this(partialScore, examScore, null);
    }

    public Grade() {
        this(0d,0d);
    }

    @Override
    public boolean equals(Object obj) {
        Grade gr = (Grade) obj;
        return gr.getStudent().getLastName().equals(student.getLastName()) &&
                gr.getStudent().getFirstName().equals(student.getFirstName()) &&
                gr.getCourse().equals(course);
    }

    @Override
    public int compareTo(Grade o) {
        double dif = o.getTotal() - getTotal();
        return  ((int)Math.signum(o.getExamScore() - getExamScore()))*100;
    }

    public Double getPartialScore() {
        return partialScore;
    }

    public void setPartialScore(Double partialScore) {
        this.partialScore = partialScore;
    }

    public Double getExamScore() {
        return examScore;
    }

    public void setExamScore(Double examScore) {
        this.examScore = examScore;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Double getTotal() {
        return this.examScore + this.partialScore;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "partialScore=" + partialScore +
                ", examScore=" + examScore +
                ", student=" + student +
                ", course='" + course + '\'' +
                '}'+'\n';
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}

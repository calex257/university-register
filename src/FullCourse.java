import java.util.ArrayList;

public class FullCourse extends Course {
    @Override
    public ArrayList<Student> getGraduatedStudents() {
        ArrayList<Student> arr = new ArrayList<Student>(100);
        for(Grade gr:getNote()) {
            if(gr.getExamScore() >=2 && gr.getPartialScore() >=3) {
                arr.add(gr.getStudent());
            }
        }
        return arr;
    }

    public FullCourse(FullCourseBuilder fcb) {
        super(fcb);
    }
    public static class FullCourseBuilder extends CourseBuilder {
        @Override
        public Course build() {
            return new FullCourse(this);
        }
    }
}

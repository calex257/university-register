import java.util.ArrayList;

public class PartialCourse extends Course{
    @Override
    public ArrayList<Student> getGraduatedStudents() {
        ArrayList<Student> arr = new ArrayList<Student>(100);
        for(Grade gr:getNote()) {
            if(gr.getTotal()>=5) {
                arr.add(gr.getStudent());
            }
        }
        return arr;
    }

    public PartialCourse(PartialCourseBuilder bld) {
        super(bld);
    }

    public static class PartialCourseBuilder extends CourseBuilder {
        @Override
        public Course build() {
            return new PartialCourse(this);
        }
    }
}

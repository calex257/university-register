import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class ScoreVisitor implements Visitor {

    private Hashtable<Teacher, ArrayList<Tuple<Student, String, Double>>> teacher_htable;
    private Hashtable<Assistant, ArrayList<Tuple<Student, String, Double>>> assistant_htable;


    public ArrayList<Grade> getUnvalidatedGrades(Teacher teacher, Course c) {
        ArrayList<Grade> arrlist = new ArrayList<>();
        if (c.getTitular().equals(teacher)) {
            ArrayList<Tuple<Student, String, Double>> arr = teacher_htable.get(teacher);
            System.out.println(arr);
            System.out.println(c.getNote());
            for (Tuple<Student, String, Double> tp : arr) {
                int exista = 0;
                for (Grade gr : c.getNote()) {
                    if (tp.getStudent().equals(gr.getStudent()) &&
                            tp.getNota().equals(gr.getExamScore()) &&
                            tp.getNume().equals(c.getNume())) {
                        exista = 1;
                    }
                }
                if (exista == 0) {
                    Grade gr = new Grade(0d, tp.getNota(), tp.student, tp.nume);
                    arrlist.add(gr);
                }
            }
        }

        return arrlist;
    }

    public ArrayList<Grade> getUnvalidatedGrades(Assistant assistant, Course c) {
        ArrayList<Grade> arrlist = new ArrayList<>();
        if (c.getAsistenti().contains(assistant)) {
            ArrayList<Tuple<Student, String, Double>> arr = assistant_htable.get(assistant);
            for (Tuple<Student, String, Double> tp : arr) {
                int exista = 0;
                for (Grade gr : c.getNote()) {
                    if (tp.getStudent().equals(gr.getStudent()) &&
                            tp.getNota().equals(gr.getPartialScore()) &&
                            tp.getNume().equals(c.getNume())) {
                    exista = 1;
                    }
                }
                if(exista == 0) {
                    Grade gr = new Grade(tp.getNota(), 0d, tp.student, tp.nume);
                    arrlist.add(gr);
                }
            }
        }

        return arrlist;
    }

    @Override
    public void visit(Assistant assistant) {
        if (assistant_htable.get(assistant) != null) {
            ArrayList<Tuple<Student, String, Double>> arr = assistant_htable.get(assistant);
            for (Tuple<Student, String, Double> tp : arr) {
                Grade gr = new Grade(tp.nota, 0d, tp.student, tp.nume);
                validateGrade(gr, tp.getNume());
//                Catalog.getCatalogInstance().getCourseByName(tp.getNume())
//                        .addGrade(gr);
//                Catalog.getCatalogInstance().notifyObservers(gr);
            }
        }
    }

    public void validateGrade(Grade grade, String course_name) {
        Catalog.getCatalogInstance().getCourseByName(course_name)
                .addGrade(grade);
        Catalog.getCatalogInstance().notifyObservers(grade);
    }

    public Hashtable<Teacher, ArrayList<Tuple<Student, String, Double>>> getTeacher_htable() {
        return teacher_htable;
    }

    public void setTeacher_htable(Hashtable<Teacher, ArrayList<Tuple<Student, String, Double>>> teacher_htable) {
        this.teacher_htable = teacher_htable;
    }

    public Hashtable<Assistant, ArrayList<Tuple<Student, String, Double>>> getAssistant_htable() {
        return assistant_htable;
    }

    public void setAssistant_htable(Hashtable<Assistant, ArrayList<Tuple<Student, String, Double>>> assistant_htable) {
        this.assistant_htable = assistant_htable;
    }

    public ScoreVisitor() {
        teacher_htable = new Hashtable<Teacher, ArrayList<Tuple<Student, String, Double>>>();
        assistant_htable = new Hashtable<Assistant, ArrayList<Tuple<Student, String, Double>>>();
    }

    @Override
    public void visit(Teacher teacher) {

        if (teacher_htable.get(teacher) != null) {
            ArrayList<Tuple<Student, String, Double>> arr = teacher_htable.get(teacher);
            for (Tuple<Student, String, Double> tp : arr) {
                Grade gr = new Grade(0d, tp.getNota(), tp.student, tp.nume);
                Catalog.getCatalogInstance().getCourseByName(tp.getNume())
                        .addGrade(gr);
                Catalog.getCatalogInstance().notifyObservers(gr);
            }
        }
    }

    public void addTeacherGrade(Teacher t, Student s, String n, Double d) {
        if (teacher_htable.get(t) != null) {
            teacher_htable.get(t).add(new Tuple<Student, String, Double>(s, n, d));
        } else {
            ArrayList<Tuple<Student, String, Double>> arr = new ArrayList<Tuple<Student, String, Double>>(100);
            arr.add(new Tuple<Student, String, Double>(s, n, d));
            teacher_htable.put(t, arr);
        }
    }

    public void addAssistantGrade(Assistant a, Student s, String n, Double d) {
        if (assistant_htable.get(a) != null) {
            assistant_htable.get(a).add(new Tuple<Student, String, Double>(s, n, d));
        } else {
            ArrayList<Tuple<Student, String, Double>> arr = new ArrayList<Tuple<Student, String, Double>>(100);
            arr.add(new Tuple<Student, String, Double>(s, n, d));
            assistant_htable.put(a, arr);
        }
    }

    public ScoreVisitor(Hashtable<Teacher, ArrayList<Tuple<Student, String, Double>>> teacher_htable,
                        Hashtable<Assistant, ArrayList<Tuple<Student, String, Double>>> assistant_htable) {
        this.teacher_htable = teacher_htable;
        this.assistant_htable = assistant_htable;
    }

    private static class Tuple<U, V, W> {
        U student;
        V nume;
        W nota;

        public Tuple(U student, V nume, W nota) {
            this.student = student;
            this.nume = nume;
            this.nota = nota;
        }

        public U getStudent() {
            return student;
        }

        public V getNume() {
            return nume;
        }

        public W getNota() {
            return nota;
        }

        @Override
        public String toString() {
            return "Tuple{" +
                    "student=" + student +
                    ", nume=" + nume +
                    ", nota=" + nota +
                    '}';
        }

    }
}

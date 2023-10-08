import java.util.*;

public abstract class Course {

    private String nume;
    private Teacher titular;
    private HashSet<Assistant> asistenti;
    private TreeSet<Grade> note;
    private Hashtable<String, Group> grupe;
    private int pct_credit;
    private Snapshot memo;

    public Assistant getAssistantByName(String firstname, String lastname) {
        for(Assistant as : asistenti) {
            if(as.getLastName().equals(lastname) && as.getFirstName().equals(firstname)) {
                return as;
            }
        }
        return null;
    }
    public void makeBackup() {
        memo = new Snapshot(note);
    }

    public void undo() {
        note = memo.getBackup_note();
    }

    private BestScore best_score;

    public Course (CourseBuilder bld) {
        this.nume = bld.nume;
        this.titular = bld.titular;
        this.asistenti = bld.asistenti;
        this.note = bld.note;
        this.grupe = bld.grupe;
        this.pct_credit = bld.pct_credit;
        this.best_score = bld.best_score;
    }

    public BestScore getBest_score() {
        return best_score;
    }

    public void setBest_score(BestScore best_score) {
        this.best_score = best_score;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Teacher getTitular() {
        return titular;
    }

    public void setTitular(Teacher titular) {
        this.titular = titular;
    }

    public HashSet<Assistant> getAsistenti() {
        return asistenti;
    }

    public void setAsistenti(HashSet<Assistant> asistenti) {
        this.asistenti = asistenti;
    }

    public TreeSet<Grade> getNote() {
        return note;
    }

    public void setNote(TreeSet<Grade> note) {
        this.note = note;
    }

    public Hashtable<String, Group> getGrupe() {
        return grupe;
    }

    public void setGrupe(Hashtable<String, Group> grupe) {
        this.grupe = grupe;
    }

    public int getPct_credit() {
        return pct_credit;
    }

    public void setPct_credit(int pct_credit) {
        this.pct_credit = pct_credit;
    }

    public void addAssistant(String ID, Assistant assistant) {
        Group gr = grupe.get(ID);
        if (gr != null) {
            gr.setAssistant(assistant);
        }
        asistenti.add(assistant);
    }

    public void addStudent(String ID, Student student) {
        Group gr = grupe.get(ID);
        if (gr != null) {
            gr.add(student);
        }
    }

    public void addGroup(Group group) {
        grupe.put(group.getID(), group);
    }

    public void addGroup(String ID, Assistant assistant) {
        grupe.put(ID, new Group(ID, assistant));
    }

    public void addGroup(String ID, Assistant assistant, Comparator<Student> comp) {
        grupe.put(ID, new Group(ID, assistant, comp));
    }

    public Grade getGrade(Student student) {
        for(Grade gr:note){
            if(gr.getStudent().equals(student)){
                return gr;
            }
        }
        return null;
    }

    public void addGrade(Grade grade) {
        for(Grade nota:note) {
            if(nota.equals(grade)) {
                if(grade.getExamScore() == 0d) {
                    nota.setPartialScore(grade.getPartialScore());
                } else if(grade.getPartialScore() == 0d){
                    nota.setExamScore(grade.getExamScore());
                }
                return;
            }
        }
        note.add(grade);
    }

    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> list = new ArrayList<Student>(100);
        for(Map.Entry<String, Group> e :grupe.entrySet()) {
            list.addAll(e.getValue());
        }
        return list;
    }

    public HashMap<Student, Grade> getAllStudentGrades() {
        HashMap<Student, Grade> hmap = new HashMap<Student, Grade>();
        for (Grade gr: note) {
            hmap.put(gr.getStudent(), gr);
        }
        return hmap;
    }

    public abstract ArrayList<Student> getGraduatedStudents();

    public Student getBestStudent() {
        return best_score.getBestStudent(note);
    }

    @Override
    public String toString() {
        return "Course{" +
                "nume='" + nume + '\'' +
                ", titular=" + titular +
                ", asistenti=" + asistenti +
                ", note=" + note +
                ", grupe=" + grupe +
                ", pct_credit=" + pct_credit +
                ", best_score=" + best_score +
                '}';
    }

    public static abstract class CourseBuilder {
        private String nume;
        private Teacher titular;
        private HashSet<Assistant> asistenti;
        private TreeSet<Grade> note;
        private Hashtable<String, Group> grupe;
        private int pct_credit;
        private BestScore best_score;

        public CourseBuilder (String nume, Teacher titular, int pct_credit) {
            this.nume = nume;
            this.titular = titular;
            this.pct_credit = pct_credit;
            asistenti = new HashSet<Assistant>();
            note = new TreeSet<Grade>();
            grupe = new Hashtable<String, Group>();
        }

        public CourseBuilder (String nume, Teacher titular) {
            this(nume, titular, 0);
        }

        public CourseBuilder (String nume) {
            this(nume, null);
        }

        public CourseBuilder () {
            this("");
        }

        public CourseBuilder nume(String nm) {
            this.nume = nm;
            return this;
        }

        public CourseBuilder titular(Teacher tt) {
            this.titular = tt;
            return this;
        }
        public CourseBuilder asistent(Assistant ast){
            asistenti.add(ast);
            return this;
        }
        public CourseBuilder nota(Grade nota) {
            note.add(nota);
            return this;
        }
        public CourseBuilder grupa(String ID,Group grp) {
            grupe.put(ID, grp);
            return this;
        }

        public CourseBuilder strategie(BestScore bst) {
            best_score = bst;
            return this;
        }

        public CourseBuilder pct_credit(int pct) {
            this.pct_credit = pct;
            return this;
        }

        public CourseBuilder setAsistenti(HashSet<Assistant> asistenti) {
            this.asistenti = asistenti;
            return this;
        }

        public CourseBuilder setNote(TreeSet<Grade> note) {
            this.note = note;
            return this;
        }

        public CourseBuilder setGrupe(Hashtable<String, Group> grupe) {
            this.grupe = grupe;
            return this;
        }

        public abstract Course build();
    }

    private class Snapshot {
        private TreeSet<Grade> backup_note;

        public Snapshot(TreeSet<Grade> backup_note) {
            this.backup_note = new TreeSet<>();
            for(Grade gr : backup_note) {
                try{
                    Grade aux = (Grade) gr.clone();
                    this.backup_note.add(aux);
                } catch(CloneNotSupportedException e){
                    System.out.println("oof");
                }
            }
        }

        public TreeSet<Grade> getBackup_note() {
            return backup_note;
        }

        public void setBackup_note(TreeSet<Grade> backup_note) {
            this.backup_note = (TreeSet<Grade>) backup_note.clone();
        }
    }
}

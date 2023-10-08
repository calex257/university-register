import java.util.TreeSet;

public class BestExamScore implements BestScore{
    @Override
    public Student getBestStudent(TreeSet<Grade> tr) {
        Double max = -1d;
        Student st = null;
        for (Grade gr : tr) {
            if(gr.getExamScore() > max) {
                max = gr.getExamScore();
                st = gr.getStudent();
            }
        }
        return st;
    }
}

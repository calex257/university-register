import java.util.TreeSet;

public class BestPartialScore implements BestScore{
    @Override
    public Student getBestStudent(TreeSet<Grade> tr) {
        Double max = -1d;
        Student st = null;
        for (Grade gr : tr) {
            if(gr.getPartialScore() > max) {
                max = gr.getPartialScore();
                st = gr.getStudent();
            }
        }
        return st;
    }
}

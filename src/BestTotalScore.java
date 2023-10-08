import java.util.TreeSet;

public class BestTotalScore implements BestScore {
    @Override
    public Student getBestStudent(TreeSet<Grade> tr) {
        Double max = -1d;
        Student st = null;
        for (Grade gr : tr) {
            if(gr.getTotal() > max) {
                max = gr.getTotal();
                st = gr.getStudent();
            }
        }
        return st;
    }
}

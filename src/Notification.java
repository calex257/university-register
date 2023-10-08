public class Notification {
    private Grade gr;

    public Notification(Grade gr) {
        this.gr = gr;
    }

    public Grade getGr() {
        return gr;
    }

    public void setGr(Grade gr) {
        this.gr = gr;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "gr=" + gr +
                '}';
    }
}

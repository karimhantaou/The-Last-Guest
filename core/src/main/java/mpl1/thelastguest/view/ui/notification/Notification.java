package mpl1.thelastguest.view.ui.notification;

public class Notification {
    private String text;
    private float duration = 3f;

    public Notification(String text) {
        this.text = text;
    }

    public Notification(String text, float duration) {
        this.text = text;
        this.duration = duration;
    }

    public String getText() {
        return text;
    }

    public float getDuration() {
        return duration;
    }
}

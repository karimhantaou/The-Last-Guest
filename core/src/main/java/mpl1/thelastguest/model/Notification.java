package mpl1.thelastguest.model;

/**
 * Represents a temporary on-screen notification displayed to the player.
 */
public class Notification {
    private String text;
    private float duration = 3f;

    /**
     * Creates a notification with default display duration.
     * @param text the text message to display
     */
    public Notification(String text) {
        this.text = text;
    }

    /**
     * Creates a notification with set display duration.
     * @param text the text message to display
     * @param duration the time of display duration
     */
    public Notification(String text, float duration) {
        this.text = text;
        this.duration = duration;
    }

    /**
     * Returns the message displayed by this notification.
     * @return the notification text
     */
    public String getText() {
        return text;
    }

    /**
     * Returns the duration of this notification.
     * @return the duration text
     */
    public float getDuration() {
        return duration;
    }
}

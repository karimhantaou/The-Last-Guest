package mpl1.thelastguest.model;

/**
 * Represents a dialogue entry used in conversations between characters.
 */
public class Dialogue {
    private String type;
    private String message;

    /**
     * Returns the dialogue type, which identifies who or what this message belongs to.
     * @return the dialogue type
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the dialogue message text.
     * @return the message content
     */
    public String getMessage() {
        return message;
    }
}

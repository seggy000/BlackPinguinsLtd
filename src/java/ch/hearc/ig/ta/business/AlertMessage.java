package ch.hearc.ig.ta.business;

public class AlertMessage {
    
    private boolean failed;
    private String message;

    public AlertMessage(final boolean failed, final String message) {
        this.failed = failed;
        this.message = message;
    }

    public boolean isFailed() {
        return failed;
    }

    public String getMessage() {
        return message;
    }
    
}

package task4.variant1.subtask5;

public class Call {
    private final String callerName;
    private final int callNumber;
    private boolean isActive;

    public Call(String callerName, int callNumber) {

        this.callerName = callerName;
        this.callNumber = callNumber;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return String.format("[Call number %d from %s]", callNumber, callerName);
    }
}

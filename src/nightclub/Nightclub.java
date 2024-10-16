package nightclub;

public interface Nightclub {
    void enterClub() throws InterruptedException;
    void leaveClub() throws InterruptedException;
    int getCurrentCapacity();
    int getMaxCapacity();
}
package nightclub;

public class NightclubMonitor implements Nightclub {
    private static final int MAX_CAPACITY = 10; // Reduced for better visualization
    private int patronsInside = 0;

    @Override
    public synchronized void enterClub() throws InterruptedException {
        while (patronsInside >= MAX_CAPACITY) {
            wait();
        }
        patronsInside++;
    }

    @Override
    public synchronized void leaveClub() throws InterruptedException {
        patronsInside--;
        notifyAll();
    }

    @Override
    public int getCurrentCapacity() {
        return patronsInside;
    }

    @Override
    public int getMaxCapacity() {
        return MAX_CAPACITY;
    }
}
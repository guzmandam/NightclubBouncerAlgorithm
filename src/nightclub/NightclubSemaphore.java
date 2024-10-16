package nightclub;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class NightclubSemaphore implements Nightclub {
    private static final int MAX_CAPACITY = 10; // Reduced for better visualization
    private final Semaphore capacity = new Semaphore(MAX_CAPACITY, true);
    private final AtomicInteger currentPatrons = new AtomicInteger(0);

    @Override
    public void enterClub() throws InterruptedException {
        capacity.acquire();
        currentPatrons.incrementAndGet();
    }

    @Override
    public void leaveClub() throws InterruptedException {
        capacity.release();
        currentPatrons.decrementAndGet();
    }

    @Override
    public int getCurrentCapacity() {
        return currentPatrons.get();
    }

    @Override
    public int getMaxCapacity() {
        return MAX_CAPACITY;
    }
}
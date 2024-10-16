package nightclub;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NightclubMutex implements Nightclub {
    private static final int MAX_CAPACITY = 10; // Reduced for better visualization
    private int patronsInside = 0;
    private final Lock lock = new ReentrantLock();

    @Override
    public void enterClub() throws InterruptedException {
        lock.lock();
        try {
            while (patronsInside >= MAX_CAPACITY) {
                lock.unlock();
                Thread.sleep(100); // Wait before trying again
                lock.lock();
            }
            patronsInside++;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void leaveClub() throws InterruptedException {
        lock.lock();
        try {
            patronsInside--;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int getCurrentCapacity() {
        lock.lock();
        try {
            return patronsInside;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int getMaxCapacity() {
        return MAX_CAPACITY;
    }
}
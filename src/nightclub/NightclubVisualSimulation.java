package nightclub;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class NightclubVisualSimulation {
    private static final int TOTAL_PATRONS = 15;
    private static final int UPDATE_INTERVAL_MS = 500;
    private static final Map<Thread, Patron> threadToPatronMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        Nightclub nightclub = new NightclubSemaphore();
        ExecutorService executorService = Executors.newFixedThreadPool(TOTAL_PATRONS);

        for (int i = 0; i < TOTAL_PATRONS; i++) {
            Patron patron = new Patron(nightclub);
            executorService.execute(() -> {
                threadToPatronMap.put(Thread.currentThread(), patron);
                patron.run();
                threadToPatronMap.remove(Thread.currentThread());
            });
        }

        // Start the visualization thread
        new Thread(() -> {
            try {
                while (!executorService.isTerminated()) {
                    printNightclubState(nightclub);
                    Thread.sleep(UPDATE_INTERVAL_MS);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        executorService.shutdown();
    }

    private static void printNightclubState(Nightclub nightclub) {
        int currentCapacity = nightclub.getCurrentCapacity();
        int maxCapacity = nightclub.getMaxCapacity();

        StringBuilder sb = new StringBuilder();
        sb.append("\rNightclub: [");

        int patronCount = 0;
        for (Map.Entry<Thread, Patron> entry : threadToPatronMap.entrySet()) {
            if (patronCount < currentCapacity) {
                sb.append(entry.getValue().getIdentifier());
                patronCount++;
            } else {
                break;
            }
        }

        // Fill the remaining space with empty slots
        for (int i = patronCount; i < maxCapacity; i++) {
            sb.append("  ");
        }

        sb.append("] ").append(currentCapacity).append("/").append(maxCapacity);

        // Add waiting patrons
        sb.append(" | Waiting: ");
        threadToPatronMap.entrySet().stream()
                .skip(currentCapacity)
                .limit(5) // Show up to 5 waiting patrons
                .forEach(entry -> sb.append(entry.getValue().getIdentifier()));

        System.out.print(sb.toString());
        System.out.println();
    }
}
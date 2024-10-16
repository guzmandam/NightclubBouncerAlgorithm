package nightclub;

import java.util.Random;

public class Patron implements Runnable {
    private static final String[] EMOJIS = {"😀", "😎", "🤠", "🥳", "🤓", "😍", "🤩", "😜", "🧐", "🤪"};
    private static final String[] NAMES = {"Andres", "Kevin", "Anthony", "Juan", "Juanito", "Axel", "Lael", "Oscar", "Elizabeth", "Paula"};

    private final Nightclub nightclub;
    private final String emoji;
    private final String name;
    private final Random random = new Random();

    public Patron(Nightclub nightclub) {
        this.nightclub = nightclub;
        this.emoji = EMOJIS[random.nextInt(EMOJIS.length)];
        this.name = NAMES[random.nextInt(NAMES.length)];
    }

    public String getIdentifier() {
        return emoji + "-" + name + " ";
    }

    @Override
    public void run() {
        try {
            // Wait before trying to enter
            Thread.sleep(random.nextInt(5000));

            System.out.println(getIdentifier() + " is trying to enter the club.");
            nightclub.enterClub();
            System.out.println(getIdentifier() + " has entered the club.");

            // Spend time in the club (3-8 seconds)
            Thread.sleep(3000 + random.nextInt(5000));

            System.out.println(getIdentifier() + " is leaving the club.");
            nightclub.leaveClub();
            System.out.println(getIdentifier() + " has left the club.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
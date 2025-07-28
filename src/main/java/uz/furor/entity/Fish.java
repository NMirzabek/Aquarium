package uz.furor.entity;

public class Fish implements Runnable {
    private final Aquarium aquarium;
    private final Gender gender;
    private final int lifeSpan;
    private final int id;
    private static int idCounter = 0;
    private Thread thread;


    public Fish(Aquarium aquarium, Gender gender, int lifeSpan) {
        this.aquarium = aquarium;
        this.gender = gender;
        this.lifeSpan = lifeSpan;
        synchronized (Fish.class) {
            this.id = ++idCounter;
        }
    }

    public int getId() {
        return id;
    }

    public Gender getGender() {
        return gender;
    }

    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    public void join() throws InterruptedException {
        if (thread != null) {
            thread.join();
        }
    }

    @Override
    public void run() {
        System.out.printf(
                "[TUG'ILISH] Baliq #%d (%s) yaratildi, umr davomiyligi: %d ms%n",
                id, gender, lifeSpan
        );
        aquarium.addFish(this);
        try {
            Thread.sleep(lifeSpan / 2);
            aquarium.reproduce(this);
            Thread.sleep(lifeSpan - lifeSpan / 2);
        } catch (InterruptedException ignored) {
        } finally {
            aquarium.removeFish(this);
            System.out.printf(
                    "[O'LIM] Baliq #%d (%s) vafot etdi.%n",
                    id, gender
            );
        }
    }
}

package uz.furor.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Aquarium {
    private final List<Fish> fishList = new ArrayList<>();

    public synchronized void addFish(Fish fish) {
        fishList.add(fish);
    }

    public synchronized void removeFish(Fish fish) {
        fishList.remove(fish);
    }

    public synchronized List<Fish> getFishList() {
        return new ArrayList<>(fishList);
    }

    public void reproduce(Fish fish) {
        Fish partner = null;
        synchronized (this) {
            for (Fish other : fishList) {
                if (other != fish && other.getGender() != fish.getGender()) {
                    partner = other;
                    break;
                }
            }
        }
        if (partner == null) {
            System.out.printf(
                    "[KO'PAYISH] Baliq #%d (%s) juft topa olmadi.%n",
                    fish.getId(), fish.getGender()
            );
            return;
        }
        Random rand = new Random();
        Gender newGender;
        if (rand.nextBoolean()) {
            newGender = Gender.MALE;
        } else {
            newGender = Gender.FEMALE;
        }
        int life = 3000 + rand.nextInt(7000);
        Fish baby = new Fish(this, newGender, life);
        baby.start();
        System.out.printf(
                "[KO'PAYISH] Baliq #%d (%s) va baliq #%d (%s) ko'paydi: yangi baliq #%d (%s) yaratildi, umr = %d ms.%n",
                fish.getId(), fish.getGender(),
                partner.getId(), partner.getGender(),
                baby.getId(), newGender, life
        );
    }
}

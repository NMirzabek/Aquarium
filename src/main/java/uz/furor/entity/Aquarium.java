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
            System.out.println(
                    "[KO'PAYISH] Baliq #" + fish.getId() + " (" + fish.getGender() + ") juft topa olmadi."
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
        System.out.println(
                "[KO'PAYISH] Baliq #" + fish.getId() + " (" + fish.getGender() + ")" +
                " va baliq #" + partner.getId() + " (" + partner.getGender() + ")" +
                " ko'paydi: yangi baliq #" + baby.getId() + " (" + newGender + ")" +
                " yaratildi, umr = " + life + " ms."
        );
    }
}

package uz.furor;


import uz.furor.entity.Aquarium;
import uz.furor.entity.Fish;
import uz.furor.entity.Gender;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random rand = new Random();
        Aquarium aquarium = new Aquarium();

        int maleCount = 1 + rand.nextInt(3);
        int femaleCount = 1 + rand.nextInt(3);

        System.out.printf("Boshlang'ich baliqlar: %d erkak, %d urg'ochi%n",
                maleCount, femaleCount);

        List<Fish> initialFish = new ArrayList<>();

        for (int i = 0; i < maleCount; i++) {
            int life = 3000 + rand.nextInt(7000);
            initialFish.add(new Fish(aquarium, Gender.MALE, life));
        }

        for (int i = 0; i < femaleCount; i++) {
            int life = 3000 + rand.nextInt(7000);
            initialFish.add(new Fish(aquarium, Gender.FEMALE, life));
        }

        for (Fish fish : initialFish) {
            fish.start();
        }

        for (Fish fish : initialFish) {
            try {
                fish.join();
            } catch (InterruptedException ignored) {
            }
        }

        while (true) {
            List<Fish> remaining = aquarium.getFishList();
            if (remaining.isEmpty()) {
                break;
            }
            for (Fish f : remaining) {
                try {
                    f.join();
                } catch (InterruptedException ignored) {
                }
            }
        }

        System.out.println("[TUGATILDI] Hamma baliq o'ldi. Simulyatsiya tugadi.");
    }
}

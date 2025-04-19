package ru.netology;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();
    public static final int numberRoutes = 1000;

    public static void main(String[] args) {
        for (int i = 0; i < numberRoutes; i++) {
            new Thread(() -> {
                synchronized (sizeToFreq) {
                    String route = generateRoute("RLRFR", 100);
                    int numberR = (int) route.chars().filter(x -> (char) x == 'R')
                            .count();
                    if (sizeToFreq.get(numberR) == null) {
                        sizeToFreq.put(numberR, 1);
                    } else {
                        sizeToFreq.put(numberR, sizeToFreq.get(numberR) + 1);
                    }
                    System.out.println(numberR);
                }
            }).start();
        }

        new Thread(() -> {
            synchronized (sizeToFreq) {
                Map.Entry<Integer, Integer> maxEntry = sizeToFreq.entrySet().stream()
                        .max(Map.Entry.<Integer, Integer>comparingByValue())
                        .get();
                System.out.printf("Самое частое количество повторений %d (встретилось %d раз)\n",
                        maxEntry.getKey(), maxEntry.getValue());
                sizeToFreq.remove(maxEntry.getKey());
                System.out.println("Другие размеры:");
                for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
                    System.out.printf("- %d (%d раз)\n", entry.getKey(), entry.getValue());
                }
            }
        }).start();
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}
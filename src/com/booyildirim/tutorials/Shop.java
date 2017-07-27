package com.booyildirim.tutorials;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class Shop {

    private static final Random RANDOM = new Random();

    private String name;

    public Shop(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * synchronous version
     * @param product
     * @return
     */
    public double getPrice(String product) {
        return calculatePrice(product);
    }

    public double getPriceRandomDelay(String product) {
        return calculatePriceRandomDelay(product);
    }

    /**
     * asynchronous version - manual future calculation
     * @param product
     * @return
     */
    public CompletableFuture<Double> getPriceAsync(String product) {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread(() -> {
            double price = calculatePrice(product);
            futurePrice.complete(price);
        }).start();
        return futurePrice;
    }

    /**
     * asynchronous version - good way !
     * @param product
     * @return
     */
    public CompletableFuture<Double> getPriceAsyncWithFactoryMethods(String product) {
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
    }

    private double calculatePrice(String product) {
        delay();
        return RANDOM.nextDouble() * product.charAt(0) + product.charAt(1); // some randomized price
    }

    private double calculatePriceRandomDelay(String product) {
        randomDelay();
        return RANDOM.nextDouble() * product.charAt(0) + product.charAt(1); // some randomized price
    }

    private static void randomDelay() {
        int delay = 500+ RANDOM.nextInt(10000);
        System.out.println(delay);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException("Sorry pal, I couldn't delay, I failed !");
        }
    }

    private static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException("sorry I can not sleep right now :/");
        }
    }
}

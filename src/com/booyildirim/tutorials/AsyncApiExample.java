package com.booyildirim.tutorials;

import java.util.concurrent.CompletableFuture;

public class AsyncApiExample {

    public static void main(String[] args) {
        Shop shop = new Shop("AShop");

        long start = System.nanoTime();
        CompletableFuture<Double> futurePrice = shop.getPriceAsync("product1");
        long invocationTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("Invocation returned after " + invocationTime + " msecs");

        // do sth else if you have
        System.out.println("I could do sth else if I have without blocking this main thread you know ;)");

        try {
            double price = futurePrice.get();
            System.out.printf("Price is %.2f%n", price);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        long retrievalTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("Price returned after " + retrievalTime + " msecs");

    }
}

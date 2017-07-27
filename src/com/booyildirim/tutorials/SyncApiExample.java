package com.booyildirim.tutorials;

public class SyncApiExample {

    public static void main(String[] args) {
        Shop shop = new Shop("AShop");

        long start = System.nanoTime();
        double result = shop.getPrice("product1");
        long endLap = ((System.nanoTime() - start) / 1_000_000);

        System.out.println("took " + endLap);
        System.out.printf("Price is %.2f%n", result);
    }
}

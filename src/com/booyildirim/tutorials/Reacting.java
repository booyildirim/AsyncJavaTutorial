package com.booyildirim.tutorials;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class Reacting {

    public static Stream<CompletableFuture<Double>> findPricesStream(List<Shop> shops, String proudct) {
        return shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPriceRandomDelay(proudct)));
    }

    public static void findAllPricesAsync() {
        Shop shop1 = new Shop("shop1");
        Shop shop2 = new Shop("shop2");
        Shop shop3 = new Shop("shop3");
        Shop shop4 = new Shop("shop4");

        List<Shop> shops = new ArrayList<>();
        shops.add(shop1);
        shops.add(shop2);
        shops.add(shop3);
        shops.add(shop4);

        long start = System.nanoTime();
        CompletableFuture[] futures = findPricesStream(shops, "myPhone7")
                .map(f -> f.thenAccept(aDouble -> System.out.println("price is : " + aDouble + " done in " +
                        ((System.nanoTime() - start) / 1_000_000) + " msecs)")))
                .toArray(CompletableFuture[]::new);

        CompletableFuture.allOf(futures).join();
        System.out.println("All shops have now responded in " + ((System.nanoTime() - start) / 1_000_000) + " msecs");
    }

    public static void main(String[] args) throws InterruptedException {
        VisualVmAttacher.attach();
        Thread.sleep(10000); // connect to visual vm !!!
        findAllPricesAsync();
        Thread.sleep(10000); // connect to visual vm !!!
    }


}

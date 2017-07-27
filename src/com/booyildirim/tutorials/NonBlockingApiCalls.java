package com.booyildirim.tutorials;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class NonBlockingApiCalls {

    private static final List<Shop> shops = Arrays.asList(new Shop("BestPrice"), new Shop("LetsSaveBig"), new Shop("MyFavoriteShop"), new Shop("BuyItAll"));

    public static void main(String[] args) {
        synchronousExample();
        parallelExample();
        asyncExample();
    }

    /**
     * not very efficient version, all calls are made one by one
     */
    private static void synchronousExample() {

        long start = System.nanoTime();
        shops.stream()
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice("iphone7")))
                .collect(Collectors.toList());
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
    }

    /**
     * parallel threads on multiple cores example
     */
    private static void parallelExample() {
        long start = System.nanoTime();
        shops.parallelStream()
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice("iphone7")))
                .collect(Collectors.toList());
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
    }

    /**
     * asynchronous example
     */
    private static void asyncExample() {
        long start = System.nanoTime();

        List<CompletableFuture<String>> priceFutures = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getName() + " price is " +
                        shop.getPrice("iphone7")))
                .collect(Collectors.toList());

        priceFutures.stream()
                .map(CompletableFuture::join) // wait untill the compleation of all async operations
                .collect(Collectors.toList());

        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
    }
}

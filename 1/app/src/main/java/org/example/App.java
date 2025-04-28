package org.example;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.*;

import javax.management.RuntimeErrorException;



public class App {

    public static Map<String, Double> getAveragePerCategory(List<Product> products) {
        return products.stream().collect(
            Collectors.groupingBy(x->x.getCategory(),
            Collectors.averagingDouble(x->x.getPrice())));
    }

    public static String processPayment(Payment p, Currency target) {
        if (p.currency() == "USD" || p.currency() == "GBP" || p.currency() == "EUR") {
            return switch(target) {
                case USD usd-> String.format("Converting %.2f %s to %s", p.amount(), p.currency(), target.toString());
                case EUR eur-> String.format("Converting %.2f %s to %s", p.amount(), p.currency(), target.toString());
                case GBP gbp-> String.format("Converting %.2f %s to %s", p.amount(), p.currency(), target.toString());
                default -> String.format("Currency Conversion of %s is unsupported", p.currency());
            };
        } else {
            return String.format("Payment currency is not supported");
        }
    }
    public static CompletableFuture<String> fetchAsync(long uuid, int delay) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                long i = (long)(Math.random() * delay);
                System.out.printf("Delay of %d ms\n", i);
                TimeUnit.MILLISECONDS.sleep((long)Math.random() * i);
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
                return "";
            }

            if (uuid % 2 == 0) {
                return "{Data: " + uuid + "}";
            } else if (uuid % 3 == 0) {
                throw new IllegalStateException("Invalid User ID" + uuid);
            } else {
                throw new RuntimeException("Data not found for the ID");
            }
        });
    }

    public static CompletableFuture<String> fetchUserIds(long uuid1, long uuid2) {
        CompletableFuture<String> future1 = fetchAsync(uuid1, 1000);
        CompletableFuture<String> future2 = fetchAsync(uuid2, 200);
        return future1.thenCombine(future2, (r1, r2) -> {
                return String.format("{Data1: %s, Data2: %s}", r1, r2);
            }
        ).handle((combined, throwable) -> {
            if (throwable != null) {
                System.err.println("Exception after combination: " + throwable.getMessage());
                return "";
            }
            return combined;
        });
    }



    public static void main(String[] args) {
        List<Product> l1 = new ArrayList<>();
        for (int i = 0; i< 10; i++) {
            l1.add(new Product(i * 2, "Product" + i, "book"));
            l1.add(new Product(i * 2, "Product" + i, "grinch"));
        }
        l1.stream().forEach(x -> System.out.println(x.toString()));
        System.out.println(getAveragePerCategory(l1));
        Payment usdPayment = new Payment(100.0, "USD");
        Payment gbpPayment = new Payment(10.0, "GBP");
        Payment unknown = new Payment(100.0, "SGD");
        Currency targetEUR = new EUR();
        Currency targetUSD = new USD();
        Currency targetGBP = new GBP();
        System.out.println(processPayment(usdPayment, targetUSD));
        System.out.println(processPayment(gbpPayment, targetEUR));
        System.out.println(processPayment(usdPayment, targetGBP));
        System.out.println(processPayment(unknown, targetGBP));
        long user1Id = 10L;
        long user2Id = 50L;

        System.out.println("--- Successful Data Retrieval ---");
        CompletableFuture<String> combinedData1 = fetchUserIds(user1Id, user2Id);
        System.out.println("Fetching data for users " + user1Id + " and " + user2Id + "...");
        try {
            System.out.println("Combined Data: " + combinedData1.get(2, TimeUnit.SECONDS));
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long user3Id = 3L;
        System.out.println("\n--- One Service Returns Error ---");
        CompletableFuture<String> combinedData2 = fetchUserIds(user1Id, user3Id);
        System.out.println("Fetching data for users " + user1Id + " and " + user3Id + "...");
        try {
            System.out.println("Combined Data: " + combinedData2.get(2, TimeUnit.SECONDS));
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}
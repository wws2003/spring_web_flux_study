package com.learnreactiveprogramming.service;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Flow;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MyFluxAndMonoSample {

    public Flux<String> myFirstFlux() {
        // Flux is a publisher
        return Flux.fromIterable(List.of("1", "2", "3"));
    }

    public Flux<String> myFirstNamesFlux() {
        // Flux is a publisher
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .log();
    }

    public Flux<String> myFirstFlattenNamesFlux() {
        // Flux is a publisher
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(s -> s.split(""))
                .flatMap(Flux::fromArray)
                .log()
                .map(String::valueOf)
                .log();
    }

    public Flux<String> myFirstFlattenNamesFlux2() {
        // Flux is a publisher
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(s -> s.chars().mapToObj(c -> (char) c).toArray(Character[]::new))
                .flatMap(Flux::fromArray)
                .log()
                .map(String::valueOf)
                .log();
    }

    public Flux<String> myFirstFlattenNamesFluxAsync() {
        // Flux is a publisher
        var delayDuration = new Random().nextInt(1000);
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(s -> s.chars().mapToObj(c -> (char) c).toArray(Character[]::new))
                .flatMap(arr -> Flux.fromArray(arr).delayElements(Duration.ofMillis(delayDuration)))
                .log()
                .map(String::valueOf)
                .log();
    }

    public Flux<String> myFirstConcatMapNamesFlux() {
        // Flux is a publisher
        var delayDuration = new Random().nextInt(1000);
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(s -> s.chars().mapToObj(c -> (char) c).toArray(Character[]::new))
                .concatMap(arr -> Flux.fromArray(arr).delayElements(Duration.ofMillis(delayDuration)))
                .log()
                .map(String::valueOf)
                .log();
    }

    public Mono<List<String>> myFirstMonoFlatMap() {
        Mono<String> myMono = Mono.just("alex");
        // Return some things like Mono<List<String>> ([a, l, e, x])
        return myMono.map(s -> s.chars().mapToObj(c -> (char)c).map(String::valueOf).toArray(String[]::new))
                .flatMap(arr -> Mono.just(Arrays.asList(arr)))
                .log();
    }


    public Flux<String> myFirstTransformedFlux() {
        // Try to use flatMapMany as well
        String str = "AbCdEf";
        // Filter upper case character then turn to lower case
        Function<Flux<String>, Flux<String>> transformer = f -> f.filter(s -> s.equals(s.toUpperCase()))
                .map(String::toLowerCase);

        return Mono.just(str)
                .flatMapMany(s -> Flux.fromStream(s.chars().mapToObj(c -> (char)c).map(String::valueOf)))
                .transform(transformer);
    }

    public Flux<Integer> myFirstConcatFlux() {
        Flux<Integer> oddFlux = Flux.just(1, 3, 5, 7).delayElements(Duration.ofMillis(120)).log();
        Flux<Integer> evenFlux = Flux.just(2, 4, 6, 8).delayElements(Duration.ofMillis(120)).log();
        return oddFlux.concatWith(evenFlux).log();
    }

    public Flux<Integer> myFirstMergeFlux() {
        Flux<Integer> oddFlux = Flux.just(1, 3, 5, 7).delayElements(Duration.ofMillis(120)).log();
        Flux<Integer> evenFlux = Flux.just(2, 4, 6, 8).delayElements(Duration.ofMillis(120)).log();
        return oddFlux.mergeWith(evenFlux).log();
    }

    public Flux<Integer> myFirstMergeSequentialFlux() {
        Flux<Integer> oddFlux = Flux.just(1, 3, 5, 7).delayElements(Duration.ofMillis(120)).log();
        Flux<Integer> evenFlux = Flux.just(2, 4, 6, 8).delayElements(Duration.ofMillis(120)).log();
        return Flux.mergeSequential(oddFlux, evenFlux).log();
    }

    public Flux<Integer> myFirstFlux1() {
        return Flux.fromIterable(List.of(1, 2, 3))
                .map(i -> i + 2)
                .log();
    }

    public Flux<Integer> myIntFlux2() {
        var flux = Flux.fromIterable(List.of(1, 2, 3));
        // No return
        flux.map(i -> i + 2);
        return flux;
    }

    public Mono<String> myFirstMono() {
        // Mono is also a publisher
        return Mono.just("Me").log();
    }

    public static void main(String[] args) {
        MyFluxAndMonoSample myFluxAndMonoSample = new MyFluxAndMonoSample();
        // Just print the text for now
        myFluxAndMonoSample.myFirstFlux()
                .log()
                .delayElements(Duration.ofMillis(100)) // Each element is delayed 100ms
                .subscribe(s -> {
                    long currentTime = System.currentTimeMillis();
                    System.out.println("Get value: " + s + " at " + currentTime);
                });

        // Must wait here to complete emitting elements
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
//        myFluxAndMonoSample.myFirstMono().subscribe(System.out::println);
    }
}

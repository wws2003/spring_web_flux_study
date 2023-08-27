package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.List;

class FluxAndMonoGeneratorServiceTest {
    FluxAndMonoGeneratorService service = new FluxAndMonoGeneratorService();
    MyFluxAndMonoSample sample = new MyFluxAndMonoSample();

    @Test
    public void testFlux1() {
        // Given

        // When: Get the flux publisher
        var nameFlux1 = service.namesFlux();

        // Then
        StepVerifier.create(nameFlux1)
                .expectNext("alex", "ben", "chloe")
                .verifyComplete();

        // When
        var nameFlux2 = service.namesFlux();

        // Then: Verify some count ?
        StepVerifier.create(nameFlux2).expectNext("alex").expectNextCount(2).verifyComplete();
    }

    @Test
    public void testFlux_immutability() {
        var flux = sample.myIntFlux2();

        StepVerifier.create(flux).expectNext(1, 2, 3).verifyComplete();
    }

    @Test
    public void testFlux_flatten() {
        var flux = sample.myFirstFlattenNamesFlux2();

        // Expect
        StepVerifier.create(flux)
                .expectNext("a", "l")
                .expectNextCount(10)
                .verifyComplete();
    }

    @Test
    public void testFlux_flatten_async() {
        var flux = sample.myFirstFlattenNamesFluxAsync();

        // Free-test
//        flux.subscribe(System.out::println);

        // Expect
        StepVerifier.create(flux)
                .expectNext("a", "l")
                .expectNextCount(10)
                .verifyComplete();
    }

    @Test
    public void testFlux_concat_map() {
        var flux = sample.myFirstConcatMapNamesFlux();

        // Free-test
//        flux.subscribe(System.out::println);

        // Expect
        StepVerifier.create(flux)
                .expectNext("a", "l")
                .expectNextCount(10)
                .verifyComplete();
    }

    @Test
    public void testMono_flatMap() {
        var mono = sample.myFirstMonoFlatMap();

        // Free-test
//        flux.subscribe(System.out::println);

        // Expect
        StepVerifier.create(mono)
                .expectNext(List.of("a", "l", "e", "x"))
                .verifyComplete();
    }

    @Test
    public void testMono_flatMapMany() {
        var flux = sample.myFirstTransformedFlux();

        // Free-test
//        flux.subscribe(System.out::println);

        // Expect
        StepVerifier.create(flux)
                .expectNext("a", "c", "e")
                .verifyComplete();
    }

    @Test
    public void testFlux_concatenatedFlux() {
        var flux = sample.myFirstConcatFlux();

        // Free-test
//        flux.subscribe(System.out::println);

        // Expect
        StepVerifier.create(flux)
                .expectNext(1, 3, 5, 7, 2, 4, 6, 8)
                .verifyComplete();
    }

    @Test
    public void testFlux_mergedFlux() {
        var flux = sample.myFirstMergeFlux();

        // Free-test
        flux.subscribe(System.out::println);

        // Must wait here to complete emitting elements
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

//        // Expect
//        StepVerifier.create(flux)
//                .expectNext(1, 3, 5, 7, 2, 4, 6, 8)
//                .verifyComplete();
    }

    @Test
    public void testFlux_mergedSequentiallyFlux() {
        var flux = sample.myFirstMergeSequentialFlux();

        // Free-test
        flux.subscribe(System.out::println);

        // Must wait here to complete emitting elements
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

//        // Expect
//        StepVerifier.create(flux)
//                .expectNext(1, 3, 5, 7, 2, 4, 6, 8)
//                .verifyComplete();
    }
}
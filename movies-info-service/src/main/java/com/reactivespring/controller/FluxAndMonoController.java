package com.reactivespring.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
public class FluxAndMonoController {

    // Get mapping return a flux
    @GetMapping("/flux")
    public Flux<Integer> getFlux() {
        // But this method still returns all data at once ?
        return Flux.just(1, 2, 3, 4, 5).delayElements(Duration.ofMillis(1000)).log();
    }

    @GetMapping("/mono")
    public Mono<String> getMono() {
        return Mono.just("I am me").log();
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getStream() {
        return Flux.interval(Duration.ofMillis(1000))
                .map(itv -> "Interval " + itv)
                .log();
    }
}

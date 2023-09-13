package com.reactivespring.controller;

import com.reactivespring.domain.MovieInfo;
import com.reactivespring.service.MovieInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
public class MovieInfoController {

    //    @Autowired
    private MovieInfoService movieInfoService;

    public MovieInfoController(MovieInfoService movieInfoService) {
        // Use the constructor pattern as described in the course
        this.movieInfoService = movieInfoService;
    }

    @PostMapping("/movieinfos/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MovieInfo> addMovieInfo(@RequestBody MovieInfo movieInfo) {
        return movieInfoService.addMovieInfo(movieInfo).log();
    }

    @GetMapping("/movieinfos/getall")
    public Flux<MovieInfo> getAllMovies() {
        return movieInfoService.getAllMovies().log();
    }

    @GetMapping("/movieinfos/get/{id}")
    public Mono<MovieInfo> getMovieById(@PathVariable String movieId) {
        return movieInfoService.getMovieById(movieId).log();
    }

    @PutMapping("/movieinfos/update/{id}")
    public Mono<MovieInfo> updateMovieInfo(@RequestBody MovieInfo movieInfo,
                                           @PathVariable String movieId) {
        return movieInfoService.updateMovieById(movieId, movieInfo).log();
    }

    @DeleteMapping("/movieinfos/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteMovieById(@PathVariable String movieId) {
        return movieInfoService.deleteMovieById(movieId).log();
    }
}

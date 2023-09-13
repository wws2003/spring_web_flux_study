package com.reactivespring.repository;

import com.reactivespring.domain.MovieInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ActiveProfiles("test")
class MovieInfoRepositoryIntegrationTest {
    @Autowired
    MovieInfoRepository movieInfoRepository;

    @BeforeEach
    void setup() {
        var movieInfos = List.of(new MovieInfo(null, "Batman Begins",
                        2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15")),
                new MovieInfo(null, "The Dark Knight",
                        2008, List.of("Christian Bale", "HeathLedger"), LocalDate.parse("2008-07-18")),
                new MovieInfo("abc", "Dark Knight Rises",
                        2012, List.of("Christian Bale", "Tom Hardy"), LocalDate.parse("2012-07-20")));

        // saveAll also returns flux (of saved items)
        movieInfoRepository.saveAll(movieInfos)
                .log()
                .blockLast();
    }

    @AfterEach
    void tearDown() {
        // deleteAll returns a Mono signaling finish
        movieInfoRepository.deleteAll().block();
    }

    @Test
    void testFindAll() {
        // request(n / unbounded) -> onNext(1) -> onNext(2)... -> onComplete()
        var moviesFlux = movieInfoRepository.findAll().log();

        // Free-test for now
        moviesFlux.map(MovieInfo::getName).subscribe(System.out::println);
    }
}
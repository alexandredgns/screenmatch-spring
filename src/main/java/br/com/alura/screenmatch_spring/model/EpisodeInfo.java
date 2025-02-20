package br.com.alura.screenmatch_spring.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EpisodeInfo(@JsonAlias("Title") String title,
                          @JsonAlias("Episode") Integer number,
                          @JsonAlias("imdbRating") String imdbRating,
                          @JsonAlias("Released") String released) {
}


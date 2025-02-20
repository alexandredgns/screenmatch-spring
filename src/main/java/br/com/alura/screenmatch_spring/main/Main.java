package br.com.alura.screenmatch_spring.main;

import br.com.alura.screenmatch_spring.model.Episode;
import br.com.alura.screenmatch_spring.model.EpisodeInfo;
import br.com.alura.screenmatch_spring.model.SeasonInfo;
import br.com.alura.screenmatch_spring.model.SeriesInfo;
import br.com.alura.screenmatch_spring.service.ApiConsumption;
import br.com.alura.screenmatch_spring.service.DataConversion;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static final String QUERY = "https://www.omdbapi.com/?";
    private static final String API_KEY = "apikey=dbb76fcb&t=";

    private final ApiConsumption apiConsumption = new ApiConsumption();
    private final DataConversion dataConversion = new DataConversion();
    private final Scanner scanner = new Scanner(System.in);

    public void displayMenu() {
        System.out.println("Search a TV Show: ");
        var search = scanner.nextLine();

        var query = QUERY + API_KEY + search.replace(" ", "+");
        var json = apiConsumption.getData(query);

        var seriesInfo = dataConversion.getData(json, SeriesInfo.class);

        List<SeasonInfo> seasons = new ArrayList<>();

        for (int i = 1; i <= seriesInfo.totalSeasons(); i++) {
            json = apiConsumption.getData(query + "&Season=" + i);
            var seasonInfo = dataConversion.getData(json, SeasonInfo.class);
            seasons.add(seasonInfo);
        }

        seasons.forEach(System.out::println);

//        for (int i = 0; i < seriesInfo.totalSeasons(); i++) {
//            System.out.println("---------");
//            System.out.println(" Season " +  (i+1));
//            System.out.println("---------");
//            List<EpisodeInfo> episodesOfSeason = seasons.get(i).episodes();
//            for (int j = 0; j < episodesOfSeason.size(); j++) {
//                System.out.println(episodesOfSeason.get(j).title());
//            }
//        }

        seasons.forEach(s -> s.episodes().forEach(e -> System.out.println(e.title())));

        List<EpisodeInfo> allEpisodesInfo = seasons.stream()
                .flatMap(s -> s.episodes().stream())
                .collect(Collectors.toList());

        System.out.println("Top 5 Episodes");
        allEpisodesInfo.stream()
                .filter(e -> !e.imdbRating().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(EpisodeInfo::imdbRating).reversed())
                .limit(5)
                .forEach(System.out::println);

        List<Episode> allEpisodes = seasons.stream()
                .flatMap(s -> s.episodes().stream()
                        .map(e -> new Episode(s.number(), e))
                ).collect(Collectors.toList());

        allEpisodes.forEach(System.out::println);

        System.out.println("From wich year do you want to search?");
        var year = scanner.nextInt();
        scanner.nextLine();

        LocalDate searchYear = LocalDate.of(year, 1, 1);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        allEpisodes.stream()
                .filter(e -> e.getReleased().isAfter(searchYear) && e.getReleased() != null)
                .forEach(e -> System.out.println(
                        "Season: " + e.getSeason() +
                        " | Episode:" + e.getNumber() +
                        " | Released: " + e.getReleased().format(dtf)
                ));

    }
}

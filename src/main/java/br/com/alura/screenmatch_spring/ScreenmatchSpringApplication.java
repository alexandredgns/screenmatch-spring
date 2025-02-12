package br.com.alura.screenmatch_spring;

import br.com.alura.screenmatch_spring.model.SeriesInfo;
import br.com.alura.screenmatch_spring.service.ApiConsumption;
import br.com.alura.screenmatch_spring.service.DataConversion;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchSpringApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchSpringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var apiConsumption = new ApiConsumption();

		var query = "https://www.omdbapi.com/?apikey=dbb76fcb&t=gilmore+girls";
		var json = apiConsumption.getData(query);
		System.out.println(json);

//		var query2 = "https://coffee.alexflipnote.dev/random.json";
//		var json2 = apiConsumption.getData(query2);
//		System.out.println(json2);

		var dataConversion = new DataConversion();
		var seriesInfo = dataConversion.getData(json, SeriesInfo.class);
		System.out.println(seriesInfo);


	}
}

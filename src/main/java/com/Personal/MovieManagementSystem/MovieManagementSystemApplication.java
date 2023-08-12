package com.Personal.MovieManagementSystem;

import com.Personal.MovieManagementSystem.Model.Genre;
import com.Personal.MovieManagementSystem.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MovieManagementSystemApplication implements CommandLineRunner {

	@Autowired
	MovieRepository movieRepository;
	public static void main(String[] args) {
		SpringApplication.run(MovieManagementSystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//System.out.println(movieRepository.promotionMovies(Genre.ACTION));

		//System.out.println(movieRepository.genreView(Genre.ACTION.toString()));
	}
}

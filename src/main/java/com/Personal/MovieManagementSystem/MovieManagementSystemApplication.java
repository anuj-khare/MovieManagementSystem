package com.Personal.MovieManagementSystem;

import com.Personal.MovieManagementSystem.Model.Genre;
import com.Personal.MovieManagementSystem.Model.MyUser;
import com.Personal.MovieManagementSystem.Repository.MovieRepository;
import com.Personal.MovieManagementSystem.Service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
public class MovieManagementSystemApplication implements CommandLineRunner {
	@Autowired
	MyUserDetailService userDetailService;
	public static void main(String[] args) {
		SpringApplication.run(MovieManagementSystemApplication.class, args);
	}
	@Autowired
	RedisTemplate template;
	@Override
	public void run(String... args) throws Exception {

	}
}

package com.project.plantOne;

import com.project.plantOne.post.PostController;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class PlantOneApplication {

	@Autowired
	PostController postController;

	public static void main(String[] args) {
		SpringApplication.run(PlantOneApplication.class, args);
	}

	@Bean
	InitializingBean createPostMetaData() {
		return () -> {
			postController.checkPlantType();
		};
	}




}

package com.springboot.blog;

import com.springboot.blog.models.Role;
import com.springboot.blog.respositories.RoleRepository;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Spring Boot Blog App REST APIs",
				description = "Spring Boot Blog App REST APIs Document",
				version = "v1.0",
				contact = @Contact(
						name = "Snigdha",
						email = "kathulasnigdha2k@gmail.com"
 				),
				license = @License(
						name ="Apache 2.0"
				)

		),
		externalDocs = @ExternalDocumentation(
				description = "Spring Boot Blog App Documentation"
		)
)
public class SpringbootBlogRestApiApplication implements CommandLineRunner {
//	@Bean
//	public ModelMapper modelMapper(){
//		return new ModelMapper();
//	}

	public static void main(String[] args) {

		SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
	}
	@Autowired
    private RoleRepository roleRepository;
	@Override
	public void run(String... args) throws Exception {
		Role adminRole = new Role();
		adminRole.setName("ROLE_ADMIN");
		roleRepository.save(adminRole);
		Role userRole = new Role();
		userRole.setName("ROLE_USER");
		roleRepository.save(userRole);

	}
}

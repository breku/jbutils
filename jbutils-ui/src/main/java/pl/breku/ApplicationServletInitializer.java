package pl.breku;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Created by breku on 04.10.17.
 */
@SpringBootApplication
public class ApplicationServletInitializer
		extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(ApplicationServletInitializer.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ApplicationServletInitializer.class);
	}
}

package pl.breku.backend.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Created by brekol on 12.02.16.
 */
@Configuration
@PropertySource("classpath:config.properties")
public class JbUtilsConfiguration {

	@Value("${pdfSaveDirectory}")
	private String pdfSaveDirectory;

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	public String getPdfSaveDirectory() {
		return pdfSaveDirectory;
	}

	public void setPdfSaveDirectory(String pdfSaveDirectory) {
		this.pdfSaveDirectory = pdfSaveDirectory;
	}
}

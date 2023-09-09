package com.authenticator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.authenticator.authenticate.service.LoadStartupDataService;

@SpringBootApplication
@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableCaching
@EnableScheduling
@EnableTransactionManagement
@EntityScan(basePackages = { "com.hibernate.model" })
public class AuthenticatorApplication extends SpringBootServletInitializer {

	private static Class<AuthenticatorApplication> applicationClass = AuthenticatorApplication.class;

	@Autowired
	private LoadStartupDataService loadStartupDataService;

	public static void main(String[] args) {
		SpringApplication.run(AuthenticatorApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(applicationClass);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("*").allowedOrigins("*").allowedHeaders("*");

			}
		};
	}

	@EventListener(ApplicationReadyEvent.class)
	public void startApplication() throws Exception {
		System.out.println("Authenticator Application Started");

		loadStartupDataService.loadStartupData();

	}

}

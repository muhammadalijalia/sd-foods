package org.caansoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@SpringBootApplication
@EnableScheduling
public class SdfoodApplication extends SpringBootServletInitializer{
	public SdfoodApplication() {
			
	}

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}

}

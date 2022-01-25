package hu.javorekdenes.hwtracer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("hu.javorekdenes.hwtracer.model")
public class HardwareTracerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HardwareTracerApplication.class, args);
	}

}

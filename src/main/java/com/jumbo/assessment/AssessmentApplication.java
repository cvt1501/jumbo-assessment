package com.jumbo.assessment;

import com.jumbo.assessment.infrastructure.configuration.MyResourcesRuntimeHints;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

@SpringBootApplication
@ImportRuntimeHints(MyResourcesRuntimeHints.class)
public class AssessmentApplication {
	public static void main(String[] args) {
		SpringApplication.run(AssessmentApplication.class, args);
	}
}

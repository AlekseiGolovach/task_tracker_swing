package com.golovach.task_tracker_swing;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.PropertySource;


@SpringBootApplication
@PropertySource({"application.properties"})
public class TaskTrackerSwingApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(TaskTrackerSwingApplication.class)
				.headless(false)
				.web(WebApplicationType.NONE)
				.run(args);
	}

}

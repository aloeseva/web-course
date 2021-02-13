/*
 * Copyright
 */

package webcourses.webcourse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebCourseApplication {

	private static final Logger logger
		= LoggerFactory.getLogger(WebCourseApplication.class);

	public static void main(String[] args) {

		SpringApplication.run(WebCourseApplication.class, args);
		logger.info("Example log from {}", WebCourseApplication.class.getSimpleName());
	}

}

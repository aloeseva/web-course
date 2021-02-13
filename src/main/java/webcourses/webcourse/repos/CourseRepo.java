/*
 * Copyright
 */

package webcourses.webcourse.repos;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import webcourses.webcourse.entity.Course;

public interface CourseRepo extends JpaRepository<Course, Long> {
    Optional<Course> findByName(String name);
}

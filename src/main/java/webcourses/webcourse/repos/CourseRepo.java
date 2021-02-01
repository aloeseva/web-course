package webcourses.webcourse.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import webcourses.webcourse.domain.Course;

public interface CourseRepo extends JpaRepository<Course, Long> {
    Course findByName(String name);
}

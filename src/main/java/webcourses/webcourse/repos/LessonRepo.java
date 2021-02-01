package webcourses.webcourse.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import webcourses.webcourse.domain.Lesson;

public interface LessonRepo extends JpaRepository<Lesson, Long> {
    Lesson findByName(String name);
}

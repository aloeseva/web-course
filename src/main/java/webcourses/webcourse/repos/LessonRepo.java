/*
 * Copyright
 */

package webcourses.webcourse.repos;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import webcourses.webcourse.entity.Lesson;

public interface LessonRepo extends JpaRepository<Lesson, Long> {
    Optional<Lesson> findByName(String name);
}

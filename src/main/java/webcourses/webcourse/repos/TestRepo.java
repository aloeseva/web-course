package webcourses.webcourse.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import webcourses.webcourse.domain.Test;

public interface TestRepo extends JpaRepository<Test, Long> {
    Test findByName(String name);
}

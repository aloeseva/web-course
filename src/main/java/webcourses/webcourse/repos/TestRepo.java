/*
 * Copyright
 */

package webcourses.webcourse.repos;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import webcourses.webcourse.entity.Test;

public interface TestRepo extends JpaRepository<Test, Long> {
    Optional<Test> findByName(String name);
}

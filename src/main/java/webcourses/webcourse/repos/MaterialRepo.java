/*
 * Copyright
 */

package webcourses.webcourse.repos;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import webcourses.webcourse.entity.Material;

public interface MaterialRepo extends JpaRepository<Material, Long> {
    Optional<Material>  findByName(String name);
}

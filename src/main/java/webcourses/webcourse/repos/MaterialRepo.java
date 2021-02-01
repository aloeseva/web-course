package webcourses.webcourse.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import webcourses.webcourse.domain.Material;

public interface MaterialRepo extends JpaRepository<Material, Long> {
    Material findByName(String name);
}

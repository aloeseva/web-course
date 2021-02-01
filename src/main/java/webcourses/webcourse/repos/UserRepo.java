package webcourses.webcourse.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import webcourses.webcourse.domain.User;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

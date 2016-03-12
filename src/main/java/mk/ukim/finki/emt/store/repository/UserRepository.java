package mk.ukim.finki.emt.store.repository;

import mk.ukim.finki.emt.store.model.Category;
import mk.ukim.finki.emt.store.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User, Long>,
  JpaSpecificationExecutor<User> {

  User findByUsername(String username);
}

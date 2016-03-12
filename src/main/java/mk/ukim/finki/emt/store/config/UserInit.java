package mk.ukim.finki.emt.store.config;

import mk.ukim.finki.emt.store.model.Role;
import mk.ukim.finki.emt.store.model.User;
import mk.ukim.finki.emt.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by ristes on 3/9/16.
 */
@Component
public class UserInit {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  PasswordEncoder encoder;

  @PostConstruct
  public void init() {
    User admin = userRepository.findByUsername("admin");
    if (admin == null) {
      admin = new User();
      admin.username = "admin";
      admin.password = encoder.encode("admin123");
      admin.role = Role.ROLE_ADMIN;
      userRepository.save(admin);
    }
  }
}

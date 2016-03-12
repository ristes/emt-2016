package mk.ukim.finki.emt.store.service.impl;

import mk.ukim.finki.emt.store.model.User;
import mk.ukim.finki.emt.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ristes on 3/9/16.
 */
@Service
public class UserServiceImpl implements UserDetailsService {

  @Autowired
  UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);

    if (user != null) {
      SimpleGrantedAuthority role = new SimpleGrantedAuthority(user.role.toString());
      List<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
      roles.add(role);

      return new org.springframework.security.core.userdetails.User(user.username, user.password, roles);
    } else {
      return null;
    }
  }
}

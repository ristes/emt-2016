package mk.ukim.finki.emt.store.authentication;

import mk.ukim.finki.emt.store.model.Provider;
import mk.ukim.finki.emt.store.model.Role;
import mk.ukim.finki.emt.store.model.User;
import mk.ukim.finki.emt.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by ristes on 3/15/16.
 */
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

  private Provider provider;
  private Role role;

  public LoginSuccessHandler(Provider provider, Role role) {
    this.provider = provider;
    this.role = role;
  }

  @Autowired
  private UserRepository userRepository;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
    HttpSession session = httpServletRequest.getSession();
    User user = userRepository.findByUsername(authentication.getName());
    if (user == null) {
      user = new User();
      user.username = authentication.getName();
      user.role = role;
      user.provider = provider;
      userRepository.save(user);
    }
    session.setAttribute("user", user);
    super.onAuthenticationSuccess(httpServletRequest, httpServletResponse, authentication);
  }
}

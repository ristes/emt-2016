package mk.ukim.finki.emt.store.authentication;

import mk.ukim.finki.emt.store.model.Provider;
import mk.ukim.finki.emt.store.model.User;
import mk.ukim.finki.emt.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ristes on 3/15/16.
 */
public class LoginFailureHandler implements AuthenticationFailureHandler {
  @Autowired
  private UserRepository userRepository;

  @Override
  public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
    User user = userRepository.findByUsername(httpServletRequest.getParameter("username"));
    if (user != null && user.provider != null && user.provider != Provider.LOCAL) {
      httpServletResponse.sendRedirect(user.provider.getLoginUrl());
    }
  }
}

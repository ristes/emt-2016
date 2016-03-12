package mk.ukim.finki.emt.store.config;

import mk.ukim.finki.emt.store.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;

/**
 * Created by ristes on 3/9/16.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

  @Autowired
  private UserDetailsService userDetailsService;


  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService)
      .passwordEncoder(passwordEncoder());
  }

  protected void configure(HttpSecurity http) throws Exception {


    http.csrf().disable();

    http.formLogin()
      .loginProcessingUrl("/doLogin")
      .loginPage("/login")
      .usernameParameter("username")
      .passwordParameter("password")
      .permitAll()
      .and()
      .logout()
      .logoutUrl("/logout")
      .deleteCookies("JSESSIONID")
      .permitAll();

    http.authorizeRequests()
      .antMatchers("/admin/**")
      .hasRole("ADMIN");

    http.authorizeRequests()
      .antMatchers("/**")
      .permitAll();




  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    return encoder;
  }

}

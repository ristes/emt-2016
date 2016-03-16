package mk.ukim.finki.emt.store.config;

import mk.ukim.finki.emt.store.model.Provider;
import mk.ukim.finki.emt.store.model.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CompositeFilter;
import org.springframework.web.filter.RequestContextFilter;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ristes on 3/9/16.
 */
@Configuration
@EnableWebSecurity
@EnableOAuth2Client
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  OAuth2ClientContext oauth2ClientContext;

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService)
      .passwordEncoder(passwordEncoder());
  }

  protected void configure(HttpSecurity http) throws Exception {
//    http.csrf().disable();

    http.requiresChannel().anyRequest().requiresSecure();

    http.logout()
      .logoutUrl("/logout")
      .deleteCookies("JSESSIONID")
      .permitAll();


    http.formLogin()
      .loginPage("/login")
      .usernameParameter("username")
      .passwordParameter("password")
      .loginProcessingUrl("/doLogin")
      .failureHandler(loginFailureHandler())
      .permitAll();


    http.authorizeRequests()
      .antMatchers("/admin/**")
      .hasRole("ADMIN");

    http.authorizeRequests()
      .antMatchers("/**")
      .permitAll();

    http.addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);

  }

  @Bean
  public AuthenticationFailureHandler loginFailureHandler() {
    return new LoginFailureHandler();
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    return encoder;
  }

  @Bean
  public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
    FilterRegistrationBean registration = new FilterRegistrationBean();
    registration.setFilter(filter);
    registration.setOrder(-100);
    return registration;
  }


  @Bean
  public FilterRegistrationBean requestContextFilter() {
    FilterRegistrationBean registration = new FilterRegistrationBean();
    registration.setFilter(new RequestContextFilter());
    registration.setOrder(-105);
    return registration;
  }

  @Bean
  @ConfigurationProperties("facebook.client")
  OAuth2ProtectedResourceDetails facebook() {
    return new AuthorizationCodeResourceDetails();
  }

  @Bean
  @ConfigurationProperties("facebook.resource")
  ResourceServerProperties facebookResource() {
    return new ResourceServerProperties();
  }

  @Bean
  @ConfigurationProperties("github.client")
  OAuth2ProtectedResourceDetails github() {
    return new AuthorizationCodeResourceDetails();
  }

  @Bean
  @ConfigurationProperties("github.resource")
  ResourceServerProperties githubResource() {
    return new ResourceServerProperties();
  }

  @Bean
  LoginSuccessHandler githubSuccessHandler() {
    return new LoginSuccessHandler(Provider.GITHUB, Role.ROLE_CUSTOMER);
  }


  @Bean
  LoginSuccessHandler facebookSuccessHandler() {
    return new LoginSuccessHandler(Provider.FACEBOOK, Role.ROLE_CUSTOMER);
  }

  @Bean
  LoginSuccessHandler localSuccessHandler() {
    return new LoginSuccessHandler(Provider.LOCAL, Role.ROLE_CUSTOMER);
  }

  private Filter ssoFilter() {

    CompositeFilter filter = new CompositeFilter();
    List<Filter> filters = new ArrayList<Filter>();

    filters.add(
      getOauthFilter(
        "/login/facebook",
        facebook(),
        facebookResource().getUserInfoUri(),
        facebookSuccessHandler()
      )
    );


    filters.add(
      getOauthFilter(
        "/login/github",
        github(),
        githubResource().getUserInfoUri(),
        githubSuccessHandler()
      )
    );

    filter.setFilters(filters);
    return filter;

  }

  public Filter getOauthFilter(
    String loginUrl,
    OAuth2ProtectedResourceDetails client,
    String userInfoUri,
    AuthenticationSuccessHandler successHandler) {
    OAuth2ClientAuthenticationProcessingFilter oauthFilter = new OAuth2ClientAuthenticationProcessingFilter(loginUrl);
    OAuth2RestTemplate template = new OAuth2RestTemplate(client, oauth2ClientContext);
    oauthFilter.setRestTemplate(template);
    oauthFilter.setTokenServices(new UserInfoTokenServices(userInfoUri, client.getClientId()));
    oauthFilter.setAuthenticationSuccessHandler(successHandler);
    return oauthFilter;
  }

}

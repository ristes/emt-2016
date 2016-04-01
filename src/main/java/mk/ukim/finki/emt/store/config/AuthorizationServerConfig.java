package mk.ukim.finki.emt.store.config;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

import java.io.File;
import java.io.IOException;

/**
 * Created by ristes on 3/15/16.
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

  @Override
  public void configure(AuthorizationServerSecurityConfigurer authorizationServerSecurityConfigurer) throws Exception {

  }


  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients
      .inMemory()
      .withClient("acme")
      .scopes("read", "write")
      .authorizedGrantTypes("authorization_code", "implicit", "password", "client_credentials")
      .secret("acmesecret");


  }


  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

  }
}

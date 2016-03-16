package mk.ukim.finki.emt.store.config;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

/**
 * Created by ristes on 3/15/16.
 */
@Configuration
public class ServerConnectorsConfig {

  @Value("${app.ssl.port}")
  private Integer sslPort;

  @Value("${app.ssl.key-store}")
  private String keyStore;

  @Value("${app.ssl.key-store.type}")
  private String keyStoreType;

  @Value("${app.ssl.key-store.alias}")
  private String keyAlias;

  @Value("${app.ssl.key-store.password}")
  private String keyStorePassword;

  @Bean
  public EmbeddedServletContainerFactory servletContainer() {
    TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
    tomcat.addAdditionalTomcatConnectors(createSslConnector());
    return tomcat;
  }

  private Connector createSslConnector() {
    Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
    Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
    try {
      connector.setScheme("https");
      connector.setSecure(true);
      connector.setPort(sslPort);

      File keystore = new ClassPathResource(keyStore).getFile();
      protocol.setSSLEnabled(true);
      protocol.setKeystoreFile(keystore.getAbsolutePath());
      protocol.setKeystoreType(keyStoreType);
      protocol.setKeyAlias(keyAlias);
      protocol.setKeystorePass(keyStorePassword);

      return connector;
    } catch (IOException ex) {
      throw new IllegalStateException("can't access keystore: [" + "keystore"
        + "] or truststore: [" + "keystore" + "]", ex);
    }
  }
}

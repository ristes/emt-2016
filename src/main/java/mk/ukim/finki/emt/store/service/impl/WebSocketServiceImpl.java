package mk.ukim.finki.emt.store.service.impl;

import mk.ukim.finki.emt.store.config.WebSocketConfig;
import mk.ukim.finki.emt.store.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;

/**
 * Created by ristes on 7/21/15.
 */
@Service
public class WebSocketServiceImpl implements WebSocketService {


  @Autowired
  private SimpMessagingTemplate template;

  @Override
  public void send(String topic, Object data, String... topicParams) {
    if (!topic.startsWith(WebSocketConfig.TOPIC_PREFIX)) {
      topic = WebSocketConfig.TOPIC_PREFIX + topic;
    }
    String uri = new UriTemplate(topic).expand((Object[]) topicParams).toString();
      template.convertAndSend(uri, data);
  }

  @Override
  public void sendToUser(String user, String topic, Object data, String... topicParams) {
    if (!topic.startsWith(WebSocketConfig.TOPIC_PREFIX)) {
      topic = WebSocketConfig.TOPIC_PREFIX + topic;
    }
    String uri = new UriTemplate(topic).expand((Object[]) topicParams).toString();
    template.convertAndSendToUser(user, uri, data);
  }
}

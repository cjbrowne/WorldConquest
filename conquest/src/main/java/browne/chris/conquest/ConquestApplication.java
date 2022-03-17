package browne.chris.conquest;

import browne.chris.conquest.serializers.SimpleTileSerializer;
import browne.chris.conquest.serializers.TileSerializer;
import browne.chris.conquest.services.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ConquestApplication {

    public ConquestApplication() {
    }

    public static void main(String[] args) {
        SpringApplication.run(ConquestApplication.class, args);
    }

    @Bean
    public Queue playerActionQueue() {
        return new Queue("player-actions", true);
    }

    @Bean
    public Queue playerManagementQueue() {
        return new Queue("player-management", true);
    }

    @Bean
    public TileSerializer tileSerializer() {
        return new SimpleTileSerializer();
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}

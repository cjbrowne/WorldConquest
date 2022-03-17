package browne.chris.conquest.services;

import browne.chris.conquest.exceptions.GameFrozenException;
import browne.chris.conquest.exceptions.IllegalMoveException;
import browne.chris.conquest.exceptions.PlayerNotFoundException;
import browne.chris.conquest.queue.PlayerAction;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PlayerActionQueueListenerService {
    private static final Logger log = LoggerFactory.getLogger(
        PlayerActionQueueListenerService.class);
    private final TroopService troopService;
    private final GameService game;
    private final PlayerService playerService;

    @Autowired
    public PlayerActionQueueListenerService(TroopService troopService,
                                            GameService game,
                                            PlayerService playerService) {
        this.troopService = troopService;
        this.game = game;
        this.playerService = playerService;
    }

    @RabbitListener(queues = "player-actions", ackMode = "MANUAL")
    public void getAction(PlayerAction action,
                          @Header(AmqpHeaders.DELIVERY_TAG) Long tag,
                          Channel channel) throws IOException {
        log.debug(action.toString());
        switch (action.getAction()) {
            case DEPLOY:
                try {
                    troopService
                        .deployTroops(playerService.get(action.getPlayerId())
                                .orElseThrow(() -> new PlayerNotFoundException(
                                    action.getPlayerId())),
                            game.getTileByCoordinate(action.getTarget()),
                            action.getAmount());
                    channel.basicAck(tag, false);
                } catch (IllegalMoveException e) {
                    log.warn("Illegal move attempted", e);
                    channel.basicNack(tag, false, false);
                } catch (GameFrozenException e) {
                    log.debug("Game frozen while processing troop deployment");
                    channel.basicNack(tag, false, true);
                } catch (PlayerNotFoundException e) {
                    log.debug("Player not found: {}", e.getUUID());
                    channel.basicNack(tag, false, false);
                }
                break;
            case MOVE:
                break;
            case WITHDRAW:
                break;
        }
    }
}

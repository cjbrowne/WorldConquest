package browne.chris.conquest.services;

import browne.chris.conquest.models.Player;
import browne.chris.conquest.queue.PlayerManagementMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerManagementQueueListenerService {
    private GameService game;
    private PlayerService playerService;

    @Autowired
    public PlayerManagementQueueListenerService(GameService game) {
        this.game = game;
    }

    @RabbitListener(queues = "player-actions", ackMode = "MANUAL")
    public void getPlayerManagement(PlayerManagementMessage message) {
        switch (message.getAction()) {
            case CREATE:
                Player p = playerService.create(message.getPlayerId());
                break;
            case NICK:
                playerService.setNick(message.getPlayerId(),
                    message.getPayload());
                break;
        }
    }
}

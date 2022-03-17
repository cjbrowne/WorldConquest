package browne.chris.conquest.services;

import browne.chris.conquest.exceptions.GameFrozenException;
import browne.chris.conquest.exceptions.IllegalMoveException;
import browne.chris.conquest.models.Player;
import browne.chris.conquest.models.Tile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TroopService {

    private static final long MOVEMENT_POINTS_PER_MOVE = 5;

    private final GameService game;

    @Autowired
    public TroopService(GameService game) {
        this.game = game;
    }

    public void deployTroops(Player player,
                             Tile target,
                             long quantity) throws IllegalMoveException,
        GameFrozenException {
        if (game.isFrozen()) {
            throw new GameFrozenException();
        }
        if (player.getAvailableTroops() < quantity) {
            throw new IllegalMoveException("Cannot deploy more troops than " +
                "you have available");
        }
        target.deployTroops(player, quantity);
    }

    public void moveTroops(Player player, Tile source, Tile target,
                           int quantity) throws IllegalMoveException,
        GameFrozenException {
        if (game.isFrozen()) {
            throw new GameFrozenException();
        }
        if (!source.hasPlayerTroops(player, quantity)) {
            throw new IllegalMoveException("Not enough troops available for " +
                "movement");
        }
        if (source.distanceTo(target) > MOVEMENT_POINTS_PER_MOVE) {
            throw new IllegalMoveException("Target tile is too far from troop" +
                " source.");
        }
        source.removeTroops(player, quantity);
        target.deployTroops(player, quantity);
    }
}

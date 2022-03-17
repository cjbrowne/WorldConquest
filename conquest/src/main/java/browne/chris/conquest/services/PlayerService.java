package browne.chris.conquest.services;

import browne.chris.conquest.models.Player;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

// dummy in-memory player service, to be replaced by a service that calls out
// to a player management microservice or database or something (architecture
// not yet decided)

@Service
public class PlayerService {
    private Map<UUID, Player> players;

    public void setNick(UUID playerId, String newNick) {
        Player p = players.get(playerId);
        if(p != null) {
            p.setNick(newNick);
        }
    }

    public Player create(UUID playerId) {
        Player p = new Player();
        p.setId(playerId);
        players.put(playerId, p);
        return p;
    }

    public Optional<Player> get(UUID id) {
        return Optional.ofNullable(players.get(id));
    }

    public Stream<Player> stream() {
        return players.values().stream();
    }
}

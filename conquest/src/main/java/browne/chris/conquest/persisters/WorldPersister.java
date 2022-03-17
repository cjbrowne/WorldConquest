package browne.chris.conquest.persisters;

import browne.chris.conquest.services.WorldService;

public interface WorldPersister {
    void save(WorldService world);
    void load(WorldService world);
}

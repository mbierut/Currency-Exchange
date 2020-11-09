package pl.mbierut.database.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.mbierut.database.entities.WalletEntryEntity;

public interface WalletEntryRepository extends CrudRepository<WalletEntryEntity, Long> {
}

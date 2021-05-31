package pl.mbierut.database.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.mbierut.database.entities.WalletEntryEntity;

import java.util.List;

public interface WalletEntryRepository extends CrudRepository<WalletEntryEntity, Long> {
    List<WalletEntryEntity> findByUser_Email(String username);
}

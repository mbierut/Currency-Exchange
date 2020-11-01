package pl.mbierut.database.entities;

import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.mbierut.models.Funds;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WalletEntryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    private Long id;

    @Embedded
    private Funds funds;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
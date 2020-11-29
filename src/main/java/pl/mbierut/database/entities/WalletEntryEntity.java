package pl.mbierut.database.entities;

import lombok.*;
import pl.mbierut.models.Funds;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
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

    WalletEntryEntity(Funds funds) {
        this.funds = funds;
    }

}
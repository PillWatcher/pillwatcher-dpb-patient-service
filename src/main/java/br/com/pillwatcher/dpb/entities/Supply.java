package br.com.pillwatcher.dpb.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Entity
@Table(name = "SUPPLY")
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class Supply extends Auditable {

    @Id
    @Column(name = "ID_SUPPLY")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SUPPLY")
    @SequenceGenerator(sequenceName = "SEQ_SUPPLY", allocationSize = 1, name = "SEQ_SUPPLY")
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_NURSE")
    private Nurse nurse;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_MEDICATION")
    private Medication medication;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "CONFIRMED_DATE")
    private LocalDateTime confirmedDate;

    @Column(name = "SUCCESS")
    private Boolean success;

    @PrePersist
    private void prePersist() {
        this.success = Boolean.TRUE;
    }
}

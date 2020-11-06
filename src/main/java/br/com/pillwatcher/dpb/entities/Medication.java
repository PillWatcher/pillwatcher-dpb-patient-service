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
@Table(name = "MEDICATION", uniqueConstraints = @UniqueConstraint(columnNames = {"ID_PRESCRIPTION", "ID_MEDICINE"}))
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class Medication extends Auditable {

    @Id
    @Column(name = "ID_MEDICATION")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MEDICATION")
    @SequenceGenerator(sequenceName = "SEQ_MEDICATION", allocationSize = 1, name = "SEQ_MEDICATION")
    private Long id;

    @JoinColumn(name = "ID_PRESCRIPTION")
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    private Prescription prescription;

    @JoinColumn(name = "ID_MEDICINE")
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    private Medicine medicine;

    @JoinColumn(name = "ID_CUP")
    @OneToOne(cascade = CascadeType.DETACH, orphanRemoval = true, fetch = FetchType.EAGER)
    private Cup cup;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "INTERVAL_TIME")
    private Integer intervalTime;

    @Column(name = "BATCH")
    private String batch;

    @Column(name = "OBSERVATION")
    private String observation;

    @Column(name = "AVAILABLE_QUANTITY")
    private Integer availableQuantity;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "EXPIRATION_DATE")
    private LocalDateTime expirationDate;

    @Column(name = "LOCATION")
    private Integer location;

}

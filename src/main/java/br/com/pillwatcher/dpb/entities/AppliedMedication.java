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
@Table(name = "APPLIED_MEDICATION")
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class AppliedMedication {

    @Id
    @Column(name = "ID_APPLIED_MEDICATION")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_APPLIED_MEDICATION")
    @SequenceGenerator(sequenceName = "SEQ_APPLIED_MEDICATION", allocationSize = 1, name = "SEQ_APPLIED_MEDICATION")
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_NURSE")
    private Nurse nurse;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_MEDICATION")
    private Medication medication;

    @Column(name = "MEDICATION_DATE")
    private LocalDateTime medicationDate;

    @Column(name = "NEXT_MEDICATION_DATE")
    private LocalDateTime nextMedicationDate;

    @PrePersist
    private void prePersist() {
        medicationDate = LocalDateTime.now();
    }

}

package br.com.pillwatcher.dpb.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Entity(name = "PATIENT")
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class Patient extends Auditable {

    @Id
    @Column(name = "ID_PATIENT")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PATIENT")
    @SequenceGenerator(sequenceName = "SEQ_PATIENT", allocationSize = 1, name = "SEQ_PATIENT")
    private Long id;

    @JoinColumn(name = "ID_NURSE")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Nurse nurse;

    @JoinColumn(name = "ID_USER")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private User user;

    @Column(name = "BORN_DATE")
    private LocalDateTime bornDate;

    @Column(name = "OBSERVATION")
    private String observation;
}

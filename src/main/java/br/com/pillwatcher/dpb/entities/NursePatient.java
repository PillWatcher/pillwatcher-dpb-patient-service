package br.com.pillwatcher.dpb.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Entity
@Table(name = "NURSE_PATIENT")
@EntityListeners(AuditingEntityListener.class)
public class NursePatient {

    @Id
    @Column(name = "ID_NURSE_PAT")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_NURSE_PAT")
    @SequenceGenerator(sequenceName = "SEQ_NURSE_PAT", allocationSize = 1, name = "SEQ_NURSE_PAT")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_NURSE")
    private Nurse nurse;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_PATIENT")
    private Patient patient;

}

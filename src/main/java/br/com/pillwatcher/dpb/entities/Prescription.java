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
@Table(name = "PRESCRIPTION")
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class Prescription extends Auditable {

    @Id
    @Column(name = "ID_PRESCRIPTION")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PRESCRIPTION")
    @SequenceGenerator(sequenceName = "SEQ_PRESCRIPTION", allocationSize = 1, name = "SEQ_PRESCRIPTION")
    private Long id;

    @JoinColumn(name = "ID_PATIENT")
    @OneToOne(cascade = CascadeType.DETACH, orphanRemoval = true, fetch = FetchType.EAGER)
    private Patient patient;

    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @Column(name = "VALIDITY_DATE")
    private LocalDateTime validityDate;

}

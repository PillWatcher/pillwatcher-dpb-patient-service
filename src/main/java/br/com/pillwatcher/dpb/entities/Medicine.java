package br.com.pillwatcher.dpb.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Entity(name = "MEDICINE")
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class Medicine extends Auditable {

    @Id
    @Column(name = "ID_MEDICINE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MEDICINE")
    @SequenceGenerator(sequenceName = "SEQ_MEDICINE", allocationSize = 1, name = "SEQ_MEDICINE")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DOSAGE")
    private Integer dosage;

    @Column(name = "DOSAGE_TYPE")
    //    private DosageTypeEnum dosageType; //TODO add DosageTypeEnum
    private String dosageType; //TODO add DosageTypeEnum

    //TODO should we put patient Id here?
    /*
        This medicine is from Patient <X>
     */

}
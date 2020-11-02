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
@Entity(name = "CUP")
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class Cup {

    @Id
    @Column(name = "ID_CUP")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CUP")
    @SequenceGenerator(sequenceName = "SEQ_CUP", allocationSize = 1, name = "SEQ_CUP")
    private Long id;

    @Column(name = "COLOR")
    private String color;

    @Column(name = "TAG", unique = true)
    private String tag;
}

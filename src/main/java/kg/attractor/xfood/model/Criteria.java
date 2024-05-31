package kg.attractor.xfood.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "criteria")
public class Criteria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id")
    private Zone zone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id")
    private Section section;

    @NotNull
    @Column(name = "description", nullable = false, length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "max_value")
    private Integer maxValue;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "value", nullable = false)
    private Integer value;

    @NotNull
    @ColumnDefault("1")
    @Column(name = "coefficient", nullable = false)
    private Integer coefficient;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "check_list_id", nullable = false)
    private CheckList checkList;

    @OneToMany(mappedBy = "criteria")
    private List<CriteriaPizzeria> criteriaPizzerias;

    @OneToMany(mappedBy = "criteria")
    private List<CriteriaType> criteriaTypes;

}
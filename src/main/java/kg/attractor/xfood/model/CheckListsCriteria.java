package kg.attractor.xfood.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "check_lists_criteria")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CheckListsCriteria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "criteria_id", nullable = false)
    private Criteria criteria;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "checklist_id", nullable = false)
    private CheckList checklist;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "value", nullable = false)
    private Integer value;
    
    @NotNull
    @ColumnDefault("0")
    @Column(name = "max_value", nullable = false)
    private Integer maxValue;

}
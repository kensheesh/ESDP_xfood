package kg.attractor.xfood.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "check_types")
public class CheckType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;
    
    @NotNull
    @Column(name = "total_max_value", nullable = false)
    private Integer totalMaxValue;

    @OneToMany(mappedBy = "type")
    private List<CriteriaType> criteriaTypes;

}
package kg.attractor.xfood.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kg.attractor.xfood.enums.Status;
import lombok.*;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "check_lists")
@ToString
public class CheckList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "work_schedule_id", nullable = false)
    private WorkSchedule workSchedule;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "opportunity_id", nullable = false)
    private Opportunity opportunity;

    @OneToMany(mappedBy = "checklist")
    private List<CheckListsCriteria> checkListsCriteria;
    
    @Column(name = "uuid_link")
    public String uuidLink;
    
    @Column(name = "feedback")
    public String feedback;
    
    @Column(name = "duration")
    public LocalTime duration;

    @Enumerated(EnumType.STRING)
    private Status status;

}
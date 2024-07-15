package kg.attractor.xfood.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kg.attractor.xfood.enums.Status;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "check_lists")
public class CheckList {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "work_schedule_id", nullable = false)
	private WorkSchedule workSchedule;
	
	@Column(name = "uuid_link", length = Integer.MAX_VALUE)
	private String uuidLink;
	
	@Column(name = "feedback", length = Integer.MAX_VALUE)
	private String feedback;
	
	@Column(name = "duration")
	private LocalTime duration;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "expert_id")
	private User expert;
	
	@Column(name = "end_time")
	private Instant endTime;
	@OneToMany(mappedBy = "checklist")
	private Set<CheckListsCriteria> checkListsCriteria = new LinkedHashSet<>();
	
	@ColumnDefault("NEW")
	@Column(name = "status", columnDefinition = "status not null")
	@Enumerated(EnumType.STRING)
	private Status status;
	
}
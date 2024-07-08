package kg.attractor.xfood.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "appeals")
public class Appeal {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Size(max = 255)
	@NotNull
	@Column(name = "email", nullable = false)
	private String email;
	
	@Size(max = 255)
	@NotNull
	@Column(name = "full_name", nullable = false)
	private String fullName;
	
	@NotNull
	@Column(name = "comment", nullable = false, length = Integer.MAX_VALUE)
	private String comment;
	
	@Size(max = 255)
	@Column(name = "tg_username")
	private String tgUsername;
	
	@Column(name = "is_accepted")
	private Boolean isAccepted;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "check_lists_criteria_id")
	private CheckListsCriteria checkListsCriteria;
	
	@OneToMany(mappedBy = "appeal")
	private Set<File> files = new LinkedHashSet<>();
	
}
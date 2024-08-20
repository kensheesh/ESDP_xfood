package kg.attractor.xfood.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@Table(name = "appeals")
@AllArgsConstructor
@NoArgsConstructor
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


	private String comment_supervisor, comment_expert;
	
	@Column(name = "tg_link_message", length = Integer.MAX_VALUE)
	private String tgLinkMessage;
	
	@Column(name = "link_to_external_src", length = Integer.MAX_VALUE)
	private String linkToExternalSrc;
	
	@Column(name = "is_accepted")
	private Boolean isAccepted;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "comment_id")
	private Comment comment;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "check_lists_criteria_id")
	private CheckListsCriteria checkListsCriteria;
	
	@OneToMany(mappedBy = "appeal")
	private Set<File> files = new LinkedHashSet<>();
	
}
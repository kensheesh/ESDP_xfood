package kg.attractor.xfood.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "check_type_fees")
public class CheckTypeFee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "check_type_id")
	private CheckType checkType;
	
	@NotNull
	@Column(name = "fees", nullable = false, precision = 10, scale = 2)
	private BigDecimal fees;
	
	@NotNull
	@Column(name = "enabled", nullable = false)
	private Boolean enabled;
	
}
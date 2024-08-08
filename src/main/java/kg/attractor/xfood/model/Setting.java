package kg.attractor.xfood.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "settings")
public class Setting {
	@Id
	@Size(max = 255)
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "description", length = Integer.MAX_VALUE)
	private String description;
	
	@Size(max = 255)
	@Column(name = "value_str")
	private String valueStr;
	
	@Column(name = "value_int")
	private Integer valueInt;
	
	@Column(name = "value_real")
	private Float valueReal;
	
}
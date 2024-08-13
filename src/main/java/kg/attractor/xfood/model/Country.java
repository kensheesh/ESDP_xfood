package kg.attractor.xfood.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "countries")
public class Country {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Size(max = 255)
	@NotNull
	@Column(name = "country_name", nullable = false)
	private String countryName;
	
	@Size(max = 2)
	@NotNull
	@Column(name = "country_code", nullable = false, length = 2)
	private String countryCode;
	
	@Size(max = 255)
	@NotNull
	@Column(name = "api_url", nullable = false)
	private String apiUrl;
	
	@Size(max = 255)
	@NotNull
	@Column(name = "auth_url", nullable = false)
	private String authUrl;
	
	@OneToMany(mappedBy = "country")
	private Set<Location> locations = new LinkedHashSet<>();
	
}
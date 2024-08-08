package kg.attractor.xfood.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDto {
	@NotBlank
	private String name, surname, role;
	
	@Email
	@NotBlank
	private String email;
	
	@NotBlank
	@Size(min = 8, message = "Минимальное количество знаков в пароле — 8.")
	@Pattern(
			regexp = "^(?=.*\\d)(?=.*[a-zA-Z]).+$",
			message = "Пароль должен содержать как минимум одну цифру и одну букву."
	)
	private String password;
	@Pattern(
			regexp = "^t\\.me/.*",
			message = "Ссылка на Телеграм должна начинаться с - t.me/(номер телефона или никнейм пользаветеля)"
	)
	@NotBlank
	private String tgLink;
}

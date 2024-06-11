package kg.attractor.xfood;

import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;


@Component
@NoArgsConstructor
public class AuthParams {
	
	public static Authentication getAuth() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	public static User getPrincipal() {
		return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
	}
}
package kg.attractor.xfood.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum Permission {
	
	ADMIN_READ("admin:read"),
	ADMIN_UPDATE("admin:update"),
	ADMIN_CREATE("admin:create"),
	ADMIN_DELETE("admin:delete"),
	
	SUPERVISOR_READ("supervisor:read"),
	SUPERVISOR_UPDATE("supervisor:update"),
	SUPERVISOR_CREATE("supervisor:create"),
	SUPERVISOR_DELETE("supervisor:delete"),
	
	EXPERT_READ("expert:read"),
	EXPERT_UPDATE("expert:update"),
	EXPERT_CREATE("expert:create"),
	EXPERT_DELETE("expert:delete");
	
	private final String permission;
}
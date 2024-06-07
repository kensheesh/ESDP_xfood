package kg.attractor.xfood.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
	
	NEW("new"),
	IN_PROGRESS("in_progress"),
	DONE("done");
	
	private final String status;
}
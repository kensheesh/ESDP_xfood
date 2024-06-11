package kg.attractor.xfood.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
	
	@ExceptionHandler(NoSuchElementException.class)
	private String notFound(HttpServletRequest request, Model model, NoSuchElementException ex) {
		log.error(ex.getMessage());
		
		model.addAttribute("status", HttpStatus.NOT_FOUND.value());
		model.addAttribute("reason", HttpStatus.NOT_FOUND.getReasonPhrase());
		model.addAttribute("details", request);
		return "/errors/error";
	}
	
}

package kg.attractor.xfood.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import kg.attractor.xfood.exception.NotFoundException;
import kg.attractor.xfood.exception.ShiftIntersectionException;
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

	@ExceptionHandler(NotFoundException.class)
	private String notFound(HttpServletRequest request, Model model, NotFoundException ex) {
		log.error(ex.getMessage());

		model.addAttribute("status", HttpStatus.NOT_FOUND.value());
		model.addAttribute("reason", HttpStatus.NOT_FOUND.getReasonPhrase());
		model.addAttribute("details", request);
		return "/errors/error";
	}

	@ExceptionHandler(ShiftIntersectionException.class)
	private String shiftIntersection(HttpServletRequest request, Model model, ShiftIntersectionException ex) {
		log.error(ex.getMessage());

		model.addAttribute("status", HttpStatus.CONFLICT.value());
		model.addAttribute("reason", HttpStatus.CONFLICT.getReasonPhrase());
		model.addAttribute("details", request);
		return "/errors/error";
	}

	@ExceptionHandler(IllegalArgumentException.class)
	private String illegalArgument(HttpServletRequest request, Model model, IllegalArgumentException ex) {
		log.error(ex.getMessage());

		model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
		model.addAttribute("reason", HttpStatus.BAD_REQUEST.getReasonPhrase());
		model.addAttribute("details", request);
		return "/errors/error";
	}
}

package kg.attractor.xfood.exception.handler;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import kg.attractor.xfood.exception.IncorrectDateException;
import kg.attractor.xfood.exception.NotFoundException;
import kg.attractor.xfood.exception.ShiftIntersectionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.NoSuchElementException;

@Slf4j
@ControllerAdvice
@Controller
@RequiredArgsConstructor
public class GlobalExceptionHandler implements ErrorController {

	@RequestMapping("/error")
	public String defaultErrorHandler(Model model, HttpServletRequest request) {
		var status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		if (status==null){
			status = HttpStatus.INTERNAL_SERVER_ERROR.value();
		}
		int statusCode = Integer.parseInt(status.toString());
		String reason = HttpStatus.valueOf(statusCode).getReasonPhrase();

		model.addAttribute("status", statusCode);
		model.addAttribute("reason", reason);
		model.addAttribute("details", request);

		return "/error/error";
	}
	
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

	@ExceptionHandler(IncorrectDateException.class)
	private String incorrectDate(HttpServletRequest request, Model model, IncorrectDateException ex) {
		log.error(ex.getMessage());
		model.addAttribute("errorMessage", ex.getMessage());
		model.addAttribute("status", HttpStatus.CONFLICT.value());
		model.addAttribute("reason", HttpStatus.CONFLICT.getReasonPhrase());
		model.addAttribute("details", request);
		return "error/error";
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

package lt.visma.GintautasButkus.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
public class ResponsiblePersonNotRemoveableException extends IllegalArgumentException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResponsiblePersonNotRemoveableException(String message) {
		super(message);
	}
}

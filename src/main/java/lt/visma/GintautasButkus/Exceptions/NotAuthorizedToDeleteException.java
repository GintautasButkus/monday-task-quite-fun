package lt.visma.GintautasButkus.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
public class NotAuthorizedToDeleteException extends IllegalArgumentException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotAuthorizedToDeleteException(String message) {
		super(message);
	}
}

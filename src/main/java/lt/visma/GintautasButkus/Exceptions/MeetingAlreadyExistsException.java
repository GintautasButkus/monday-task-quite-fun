package lt.visma.GintautasButkus.Exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class MeetingAlreadyExistsException extends IllegalArgumentException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MeetingAlreadyExistsException(String message) {
		super(message);
	}
}

package lt.visma.GintautasButkus.Exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class PersonAlreadyInAMeetingException extends IllegalArgumentException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PersonAlreadyInAMeetingException(String message) {
		super(message);
	}
}

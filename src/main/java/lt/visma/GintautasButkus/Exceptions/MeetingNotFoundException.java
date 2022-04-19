package lt.visma.GintautasButkus.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MeetingNotFoundException extends NullPointerException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MeetingNotFoundException(String message) {
		super(message);
	}
}

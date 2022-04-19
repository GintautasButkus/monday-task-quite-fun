package lt.visma.GintautasButkus.Controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lt.visma.GintautasButkus.Exceptions.MeetingNotFoundException;
import lt.visma.GintautasButkus.Exceptions.PersonAlreadyInAMeetingException;
import lt.visma.GintautasButkus.Exceptions.ResponsiblePersonNotRemoveableException;
import lt.visma.GintautasButkus.Model.Meeting;
import lt.visma.GintautasButkus.Model.PeopleInMeeting;
import lt.visma.GintautasButkus.Repository.MeetingsRepository;
import lt.visma.GintautasButkus.Repository.PeopleRepository;

@RestController
@RequestMapping("/api")
public class PersonController {
	@Autowired
	private MeetingsRepository meetingRepo;

	@Autowired
	private PeopleRepository attendeesRepo;

	@GetMapping("/attendees/{meeting_id}")
	public List<PeopleInMeeting> getAttendees(@PathVariable Long meeting_id) {
		if (!meetingRepo.existsById(meeting_id)) {
			throw new MeetingNotFoundException("No meeting exists with ID " + meeting_id);
		}
		return attendeesRepo.findByMeetingId(meeting_id);
	}

	@PostMapping("/add_people/{meeting_id}")
	@ResponseStatus(HttpStatus.CREATED)
	public void addAttendee(@PathVariable Long meeting_id, @RequestBody PeopleInMeeting attendeeDetails) {

		PeopleInMeeting newAttendee = meetingRepo.findById(meeting_id).map(meeting -> {
			if (getAttendees(meeting_id).stream()
					.filter(attendee -> attendee.getName().equals(attendeeDetails.getName())).findAny()
					.orElse(null) != null
					|| getAttendees(meeting_id).stream()
							.filter(attendee -> attendee.getMeeting().getStartDate().isEqual(meeting.getStartDate()))
							.isParallel()) {
				throw new PersonAlreadyInAMeetingException(
						"Person already invited to the meeting or has the meeting at the same time.");
			}
			attendeeDetails.setPersonDate(LocalDateTime.now());
			attendeeDetails.setMeeting(meeting);
			return attendeesRepo.save(attendeeDetails);
		}).orElseThrow(() -> new MeetingNotFoundException("No meeting exists with such ID " + meeting_id));
		attendeesRepo.save(newAttendee);
	}

	@DeleteMapping("/attendees/{meetingId}/{attendeeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeAttendee(@PathVariable Long meetingId, @PathVariable Long attendeeId) {
		if (meetingRepo.getById(meetingId).getResponsiblePersonName()
				.equals(attendeesRepo.getById(attendeeId).getName())) {
			throw new ResponsiblePersonNotRemoveableException("Responsible person cannot be removed from the meeting");
		}
		attendeesRepo.deleteById(attendeeId);
	}

	@GetMapping("attendees/find_size/{attendeesAmount}")
	public List<Meeting> getMeetingByAttendeesAmount(@PathVariable Integer attendeesAmount) {
		List<Meeting> filteredMeetings = new ArrayList<Meeting>();
		for (Meeting meeting : meetingRepo.findAll()) {
			if (getAttendees(meeting.getId()).size() >= attendeesAmount) {
				filteredMeetings.add(meeting);
			}
		}
		return filteredMeetings;
	}

}

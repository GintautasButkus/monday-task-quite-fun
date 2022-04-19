package lt.visma.GintautasButkus.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lt.visma.GintautasButkus.Exceptions.MeetingAlreadyExistsException;
import lt.visma.GintautasButkus.Exceptions.MeetingNotFoundException;
import lt.visma.GintautasButkus.Exceptions.NotAuthorizedToDeleteException;
import lt.visma.GintautasButkus.Model.Meeting;
import lt.visma.GintautasButkus.Repository.MeetingsRepository;
import lt.visma.GintautasButkus.Repository.PeopleRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/meeting")
public class MeetingController {

	@Autowired
	MeetingsRepository meetingRepo;

	@Autowired
	PeopleRepository attendees;

	@GetMapping
	public List<Meeting> getMeetings() {
		return meetingRepo.findAll();
	}

//		**************** FILTERING ***************************

	@GetMapping("find_description/{description}")
	public List<Meeting> getMeetingByDescription(@PathVariable String description) {
		return getMeetings().stream().filter(meeting -> meeting.getDescription().contains(description))
				.collect(Collectors.toList());
	}

	@GetMapping("find_resperson/{responsiblePerson}")
	public List<Meeting> getMeetingByResponsiblePerson(@PathVariable String responsiblePerson) {
		return getMeetings().stream().filter(meeting -> meeting.getResponsiblePersonName().contains(responsiblePerson))
				.collect(Collectors.toList());
	}

	@GetMapping("find_category/{category}")
	public List<Meeting> getMeetingByCategory(@PathVariable String category) {
		return getMeetings().stream().filter(meeting -> meeting.getCategory().name().equals(category))
				.collect(Collectors.toList());
	}

	@GetMapping("find_type/{type}")
	public List<Meeting> getMeetingByType(@PathVariable String type) {
		return getMeetings().stream().filter(meeting -> meeting.getType().name().equals(type))
				.collect(Collectors.toList());
	}

	@GetMapping("find_start/{startDate}")
	public List<Meeting> getMeetingByStartDate(@PathVariable String startDate) {
		return getMeetings().stream()
				.filter(meeting -> meeting.getStartDate().isAfter(LocalDate.parse(startDate).minusDays(1)))
				.collect(Collectors.toList());
	}

	@GetMapping("find_period/{startDate}/{periodEndDate}")
	public List<Meeting> getMeetingByPeriod(@PathVariable String startDate, @PathVariable String periodEndDate) {
		return getMeetings().stream()
				.filter(meeting -> meeting.getStartDate().isAfter(LocalDate.parse(startDate).minusDays(1))
						&& meeting.getStartDate().isBefore(LocalDate.parse(periodEndDate).plusDays(1)))
				.collect(Collectors.toList());
	}

//		***************** END-FILTERING*********************************************

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public void createMeeting(@RequestBody Meeting meetingObj) {
		if (getMeetings().stream()
				.anyMatch(meeting -> meeting.getName().equals(meetingObj.getName())
						&& meeting.getResponsiblePersonName().equals(meetingObj.getResponsiblePersonName())
						&& meeting.getDescription().equals(meetingObj.getDescription())
						&& meeting.getCategory().name().equals(meetingObj.getCategory().name())
						&& meeting.getType().name().equals(meetingObj.getType().name())
						&& meeting.getStartDate().equals(meetingObj.getStartDate())
						&& meeting.getEndDate().equals(meetingObj.getEndDate()))) {

			throw new MeetingAlreadyExistsException("Meeting already exists");
		} else {
			meetingRepo.save(meetingObj);
		}
	}

	@DeleteMapping("/{yourName}/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteMeeting(@PathVariable Long id, @PathVariable String yourName) {
		if (meetingRepo.getById(id).getResponsiblePersonName().equals(yourName)) {
			meetingRepo.deleteById(id);
		} else {
			throw new NotAuthorizedToDeleteException("Apology, you're not authorized to remove the meeting");
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Meeting> updateClient(@PathVariable Long id, @RequestBody Meeting meetingDetails) {
		Meeting meeting = meetingRepo.findById(id)
				.orElseThrow(() -> new MeetingNotFoundException("No meeting found with such ID " + id));
		meeting.setName(meetingDetails.getName());
		meeting.setResponsiblePersonName(meetingDetails.getResponsiblePersonName());
		meeting.setDescription(meetingDetails.getDescription());
		meeting.setCategory(meetingDetails.getCategory());
		meeting.setType(meetingDetails.getType());
		meeting.setStartDate(meetingDetails.getStartDate());
		meeting.setEndDate(meetingDetails.getEndDate());

		Meeting updatedMeeting = meetingRepo.save(meeting);
		return ResponseEntity.ok(updatedMeeting);
	}

}

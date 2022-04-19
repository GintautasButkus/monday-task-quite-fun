package lt.visma.GintautasButkus.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import lt.visma.GintautasButkus.Model.PeopleInMeeting;

public interface PeopleRepository extends JpaRepositoryImplementation<PeopleInMeeting, Long> {
	List<PeopleInMeeting> findByMeetingId(Long meetingId);
}

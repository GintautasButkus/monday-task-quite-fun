package lt.visma.GintautasButkus.Repository;

//import java.util.List;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import lt.visma.GintautasButkus.Model.Meeting;

public interface MeetingsRepository extends JpaRepositoryImplementation<Meeting, Long> {

}

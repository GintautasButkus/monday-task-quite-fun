package lt.visma.GintautasButkus.Model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;

@Entity
@Table(name = "meetings")
public class Meeting {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meeting_generator")
	@Column(name = "id")
	private Long id;

	@Column(name = "meetingName")
	private String name;

	@NotNull
	@Column(name = "responsible_person_name")
	private String responsiblePersonName;

	@Column(name = "description")
	private String description;

	@Column(name = "category")
	private Categories category;

	@Column(name = "type")
	private Type type;

	@Column(name = "start_date")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate;

	@Column(name = "end_date")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDate;

	public Meeting() {

	}

	public Meeting(Long id, String name, String responsiblePersonName, String description, Categories category,
			Type type, LocalDate startDate, LocalDate endDate) {
		super();
		this.id = id;
		this.name = name;
		this.responsiblePersonName = responsiblePersonName;
		this.description = description;
		this.category = category;
		this.type = type;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResponsiblePersonName() {
		return responsiblePersonName;
	}

	public void setResponsiblePersonName(String responsiblePersonName) {
		this.responsiblePersonName = responsiblePersonName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Categories getCategory() {
		return category;
	}

	public void setCategory(Categories category) {
		this.category = category;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
}

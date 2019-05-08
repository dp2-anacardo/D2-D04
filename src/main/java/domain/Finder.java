
package domain;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Date;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	private String					keyWord;
	private Date					deadline;
	private Date					maxDeadline;
	private Integer					minSalary;
	private Date					lastUpdate;

	//Relationships

	private Collection<Position>	positions;


	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getKeyWord() {
		return this.keyWord;
	}

	public void setKeyWord(final String keyWord) {
		if (keyWord == null)
			this.keyWord = keyWord;
		else
			this.keyWord = keyWord.trim();
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getDeadline() {
		return this.deadline;
	}

	public void setDeadline(final Date deadline) {
		this.deadline = deadline;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMaxDeadline() {
		return this.maxDeadline;
	}

	public void setMaxDeadline(final Date maxDeadline) {
		this.maxDeadline = maxDeadline;
	}

	public Integer getMinSalary() {
		return this.minSalary;
	}

	public void setMinSalary(final Integer minSalary) {
		this.minSalary = minSalary;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yy HH:mm")
	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(final Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	//Relationships

	@Valid
	@ManyToMany
	public Collection<Position> getPositions() {
		return this.positions;
	}

	public void setPositions(final Collection<Position> positions) {
		this.positions = positions;
	}

}

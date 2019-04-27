
package domain;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.Collection;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
		@Index(columnList = "isCopy")
})
public class Curricula extends DomainEntity {

	//Relationships ----------------------------------------------------------------------------------------------------
	private Collection<EducationalData>	educationalData;
	private MiscData					miscData;
	private PersonalData				personalData;
	private Collection<PositionData>	positionData;
	private boolean						isCopy;


	public boolean getIsCopy() {
		return this.isCopy;
	}

	public void setIsCopy(final boolean isCopy) {
		this.isCopy = isCopy;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<EducationalData> getEducationalData() {
		return this.educationalData;
	}

	public void setEducationalData(final Collection<EducationalData> educationalData) {
		this.educationalData = educationalData;
	}

	@OneToOne(optional = true,cascade = CascadeType.ALL)
	public MiscData getMiscData() {
		return this.miscData;
	}

	public void setMiscData(final MiscData miscData) {
		this.miscData = miscData;
	}

	@Valid
	@OneToOne(optional = false,cascade = CascadeType.ALL)
	public PersonalData getPersonalData() {
		return this.personalData;
	}

	public void setPersonalData(final PersonalData personalData) {
		this.personalData = personalData;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<PositionData> getPositionData() {
		return this.positionData;
	}

	public void setPositionData(final Collection<PositionData> positionData) {
		this.positionData = positionData;
	}

}

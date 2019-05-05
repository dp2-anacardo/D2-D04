
package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Company extends Actor {

	private String	commercialName;
	private Double auditScore;

	@NotNull
	@Range(min=0,max = 1)
	public Double getAuditScore() {
		return auditScore;
	}

	public void setAuditScore(Double auditScore) {
		this.auditScore = auditScore;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	@NotBlank
	public String getCommercialName() {
		return this.commercialName;
	}

	public void setCommercialName(final String commercialName) {
		this.commercialName = commercialName;
	}

}

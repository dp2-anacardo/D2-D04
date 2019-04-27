
package domain;

import com.google.gson.annotations.Expose;
import datatype.Url;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
		@Index(columnList = "isFinal")
})
public class Problem extends DomainEntity {

	// Attributes -------------------------------------------------------------
	@Expose
	private String			title;
	@Expose
	private String			statement;
	@Expose
	private String			hint;
	@Expose
	private Collection<Url>	attachment;
	@Expose
	private Boolean			isFinal;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getStatement() {
		return this.statement;
	}

	public void setStatement(final String statement) {
		this.statement = statement;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getHint() {
		return this.hint;
	}

	public void setHint(final String hint) {
		this.hint = hint;
	}

	@NotEmpty
	@ElementCollection(fetch = FetchType.EAGER)
	@Valid
	public Collection<Url> getAttachment() {
		return this.attachment;
	}

	public void setAttachment(final Collection<Url> attachment) {
		this.attachment = attachment;
	}

	@NotNull
	public Boolean getIsFinal() {
		return this.isFinal;
	}

	public void setIsFinal(final Boolean isFinal) {
		this.isFinal = isFinal;
	}


	// Relationships

	private Company	company;


	@Valid
	@ManyToOne(optional = false)
	public Company getCompany() {
		return this.company;
	}

	public void setCompany(final Company company) {
		this.company = company;
	}

}

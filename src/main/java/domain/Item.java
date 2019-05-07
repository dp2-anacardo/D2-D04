
package domain;

import datatype.Url;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Access(AccessType.PROPERTY)

public class Item extends DomainEntity {



	//Properties -----------------------------------------------------------------------------------
	private String				name;
	private String				description;
	private Collection<Url>				links;
	private Collection<Url>	pictures;

	//Getters and setters ---------------------------------------------------------------------------
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@NotEmpty
	@Valid
	@ElementCollection(fetch = FetchType.EAGER)
	public Collection<Url> getLinks() {
		return links;
	}

	public void setLinks(Collection<Url> links) {
		this.links = links;
	}

	@NotNull
	@Valid
	@ElementCollection(fetch = FetchType.EAGER)
	public Collection<Url> getPictures() {
		return pictures;
	}

	public void setPictures(Collection<Url> pictures) {
		this.pictures = pictures;
	}

	// Relationships ----------------------------------------------------------
	private Provider					provider;

	@ManyToOne(optional = false)
	@Valid
	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}
}

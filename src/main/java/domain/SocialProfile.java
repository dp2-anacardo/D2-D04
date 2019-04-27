
package domain;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class SocialProfile extends DomainEntity {

	@Expose
	private String	nick;
	@Expose
	private String	socialNetworkName;
	@Expose
	private String	profileLink;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getNick() {
		return this.nick;
	}

	public void setNick(final String nick) {
		this.nick = nick;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getSocialNetworkName() {
		return this.socialNetworkName;
	}

	public void setSocialNetworkName(final String socialNetworkName) {
		this.socialNetworkName = socialNetworkName;
	}

	@NotBlank
	@URL
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getProfileLink() {
		return this.profileLink;
	}

	public void setProfileLink(final String profileLink) {
		this.profileLink = profileLink;
	}
}

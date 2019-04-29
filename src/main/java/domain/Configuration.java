
package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	private int					maxResults;
	private int					maxTime;
	private String				systemName;
	private String				banner;
	private String				welcomeMessageEn;
	private String				welcomeMessageEs;
	private Collection<String>	spamWords;
	private String				countryCode;
	private Double				defaultVAT;
	private Double				flatRate;


	@Range(min = 10, max = 100)
	public int getMaxResults() {
		return this.maxResults;
	}

	public void setMaxResults(final int maxResults) {
		this.maxResults = maxResults;
	}

	@Range(min = 1, max = 24)
	public int getMaxTime() {
		return this.maxTime;
	}

	public void setMaxTime(final int maxTime) {
		this.maxTime = maxTime;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getSystemName() {
		return this.systemName;
	}

	public void setSystemName(final String systemName) {
		this.systemName = systemName;
	}

	@NotBlank
	@URL
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getWelcomeMessageEn() {
		return this.welcomeMessageEn;
	}

	public void setWelcomeMessageEn(final String welcomeMessageEn) {
		this.welcomeMessageEn = welcomeMessageEn;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getWelcomeMessageEs() {
		return this.welcomeMessageEs;
	}

	public void setWelcomeMessageEs(final String welcomeMessageEs) {
		this.welcomeMessageEs = welcomeMessageEs;
	}

	@ElementCollection
	public Collection<String> getSpamWords() {
		return this.spamWords;
	}

	public void setSpamWords(final Collection<String> spamWords) {
		this.spamWords = spamWords;
	}

	@NotBlank
	public String getCountryCode() {
		return this.countryCode;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	@NotNull
	public Double getDefaultVAT() {
		return this.defaultVAT;
	}

	public void setDefaultVAT(final Double defaultVAT) {
		this.defaultVAT = defaultVAT;
	}

	@NotNull
	public Double getFlatRate() {
		return this.flatRate;
	}

	public void setFlatRate(final Double flatRate) {
		this.flatRate = flatRate;
	}
}

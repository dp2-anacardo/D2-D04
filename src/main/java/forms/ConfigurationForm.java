package forms;

import domain.Configuration;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import java.util.Collection;

public class ConfigurationForm {

    private int					id;
    private int					version;
    private int					maxResults;
    private int					maxTime;
    private String				systemName;
    private String				banner;
    private String				welcomeMessageEn;
    private String				welcomeMessageEs;
    private Collection<String>	spamWords;
    private String				countryCode;
    private String				addSW;
    private Double				defaultVAT;
    private Double				flatRate;


    public ConfigurationForm(final Configuration config) {
        this.id = config.getId();
        this.version = config.getVersion();
        this.maxResults = config.getMaxResults();
        this.maxTime = config.getMaxTime();
        this.systemName = config.getSystemName();
        this.banner = config.getBanner();
        this.welcomeMessageEn = config.getWelcomeMessageEn();
        this.welcomeMessageEs = config.getWelcomeMessageEs();
        this.spamWords = config.getSpamWords();
        this.countryCode = config.getCountryCode();
        this.defaultVAT = config.getDefaultVAT();
        this.flatRate = config.getFlatRate();
    }

    public ConfigurationForm() {

    }

    public int getId() {
        return this.id;
    }

    public int getVersion() {
        return this.version;
    }

    public int getMaxResults() { return this.maxResults; }

    public int getMaxTime() { return this.maxTime; }

    public String getSystemName() {
        return this.systemName;
    }

    public String getBanner() {
        return this.banner;
    }

    public String getWelcomeMessageEn() {
        return this.welcomeMessageEn;
    }

    public String getWelcomeMessageEs() {
        return this.welcomeMessageEs;
    }

    public Collection<String> getSpamWords() {
        return this.spamWords;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    @SafeHtml(whitelistType = WhiteListType.NONE)
    public String getAddSW() {
        return this.addSW;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public void setVersion(final int version) {
        this.version = version;
    }

    public void setMaxResults(final int maxResults) { this.maxResults = maxResults; }

    public void setMaxTime(final int maxTime) { this.maxTime = maxTime; }

    public void setSystemName(final String systemName) {
        this.systemName = systemName;
    }

    public void setBanner(final String banner) {
        this.banner = banner;
    }

    public void setWelcomeMessageEn(final String welcomeMessageEn) {
        this.welcomeMessageEn = welcomeMessageEn;
    }

    public void setWelcomeMessageEs(final String welcomeMessageEs) {
        this.welcomeMessageEs = welcomeMessageEs;
    }

    public void setSpamWords(final Collection<String> spamWords) {
        this.spamWords = spamWords;
    }

    public void setCountryCode(final String countryCode) {
        this.countryCode = countryCode;
    }

    public void setAddSW(final String addSW) {
        this.addSW = addSW;
    }

    public Double getDefaultVAT() {
        return defaultVAT;
    }

    public void setDefaultVAT(Double defaultVAT) {
        this.defaultVAT = defaultVAT;
    }

    public Double getFlatRate() {
        return flatRate;
    }

    public void setFlatRate(Double flatRate) {
        this.flatRate = flatRate;
    }
}

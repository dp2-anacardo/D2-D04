
package domain;

import com.google.gson.annotations.Expose;
import datatype.CreditCard;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;
import security.UserAccount;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.Collection;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
        @Index(columnList = "isSpammer, isBanned")
})
public class Actor extends DomainEntity {

    @Expose
    private String name;
    @Expose
    private CreditCard creditCard;
    @Expose
    private int vatNumber;
    @Expose
    private String surname;
    @Expose
    private String photo;
    @Expose
    private String email;
    @Expose
    private String phoneNumber;
    @Expose
    private String address;
    @Expose
    private Boolean isSpammer;
    @Expose
    private Boolean isBanned;

    //Relationships
    @Expose
    private Collection<Message> messagesReceived;
    @Expose
    private Collection<Message> messagesSent;
    @Expose
    private UserAccount userAccount;
    @Expose
    private Collection<SocialProfile> socialProfiles;


    @NotBlank
    @SafeHtml(whitelistType = WhiteListType.NONE)
    public String getName() {
        return this.name;
    }

    @URL
    @SafeHtml(whitelistType = WhiteListType.NONE)
    public String getPhoto() {
        return this.photo;
    }

    @NotBlank
    @Email
    @SafeHtml(whitelistType = WhiteListType.NONE)
    public String getEmail() {
        return this.email;
    }

    @NotBlank
    @SafeHtml(whitelistType = WhiteListType.NONE)
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    @SafeHtml(whitelistType = WhiteListType.NONE)
    public String getAddress() {
        return this.address;
    }

    public Boolean getIsSpammer() {
        return this.isSpammer;
    }

    public Boolean getIsBanned() {
        return this.isBanned;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setPhoto(final String photo) {
        this.photo = photo;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public void setIsSpammer(final Boolean isSpammer) {
        this.isSpammer = isSpammer;
    }

    public void setIsBanned(final Boolean isBanned) {
        this.isBanned = isBanned;
    }

    //Relationships

    @Valid
    @OneToMany(cascade = CascadeType.ALL)
    public Collection<Message> getMessagesReceived() {
        return this.messagesReceived;
    }

    @Valid
    @OneToMany(cascade = CascadeType.ALL)
    public Collection<Message> getMessagesSent() {
        return this.messagesSent;
    }

    @Valid
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    public UserAccount getUserAccount() {
        return this.userAccount;
    }

    @Valid
    @OneToMany
    public Collection<SocialProfile> getSocialProfiles() {
        return this.socialProfiles;
    }

    public void setMessagesReceived(final Collection<Message> messagesR) {
        this.messagesReceived = messagesR;
    }

    public void setMessagesSent(final Collection<Message> messagesS) {
        this.messagesSent = messagesS;
    }


    public void setUserAccount(final UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public void setSocialProfiles(final Collection<SocialProfile> socialProfiles) {
        this.socialProfiles = socialProfiles;
    }

    public CreditCard getCreditCard() {
        return this.creditCard;
    }

    public void setCreditCard(final CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public int getVatNumber() {
        return this.vatNumber;
    }

    public void setVatNumber(final int vatNumber) {
        this.vatNumber = vatNumber;
    }

    @SafeHtml(whitelistType = WhiteListType.NONE)
    @Valid
    public String getSurname() {
        return this.surname;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }

}

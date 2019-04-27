package domain;

import datatype.Url;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.Collection;

@Entity
@Access(AccessType.PROPERTY)
public class MiscData extends DomainEntity{

    //Properties -----------------------------------------------------------------------------------

    private String freeText;
    private Collection<Url> attachment;

    //Getters and setters ---------------------------------------------------------------------------
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getFreeText() {
        return freeText;
    }

    public void setFreeText(String freeText) {
        this.freeText = freeText;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @Valid
    public Collection<Url> getAttachment() {
        return attachment;
    }

    public void setAttachment(Collection<Url> attachment) {
        this.attachment = attachment;
    }
}

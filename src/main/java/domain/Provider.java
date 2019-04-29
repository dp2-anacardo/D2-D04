
package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.Collection;

@Entity
@Access(AccessType.PROPERTY)
public class Provider extends Actor {

	private String make;

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}
}

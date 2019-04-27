
package datatype;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Embeddable
@Access(AccessType.PROPERTY)
public class CreditCard {

	private String	holder;
	private String	brandName;
	private String	number;
	private Date	expirationYear;
	private Integer	cvv;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getHolder() {
		return this.holder;
	}
	public void setHolder(final String holder) {
		this.holder = holder;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getBrandName() {
		return this.brandName;
	}
	public void setBrandName(final String brandName) {
		this.brandName = brandName;
	}

	@NotBlank
	@CreditCardNumber
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getNumber() {
		return this.number;
	}
	public void setNumber(final String number) {
		this.number = number;
	}

	@NotNull
	@Range(min = 100, max = 999)
	public Integer getCvv() {
		return this.cvv;
	}
	public void setCvv(final Integer cvv) {
		this.cvv = cvv;
	}
	@Override
	public String toString() {
		return ("Holder Name: " + this.getHolder() + "   Brand Name: " + this.getBrandName() + "   Number: " + this.getNumber() + "   Expiration:" + this.getExpirationYear() + "   CVV Code: " + this.getCvv());
	}

	@NotNull
	@Future
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "MM/YY")
	public Date getExpirationYear() {
		return this.expirationYear;
	}
	public void setExpirationYear(final Date expirationYear) {
		this.expirationYear = expirationYear;
	}
}

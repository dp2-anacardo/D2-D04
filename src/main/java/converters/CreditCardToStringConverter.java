
package converters;

import datatype.CreditCard;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Transactional
public class CreditCardToStringConverter implements Converter<CreditCard, String> {

	@Override
	public String convert(final CreditCard creditCard) {
		String result;
		StringBuilder builder;

		if (creditCard == null)
			result = null;
		else
			try {
				builder = new StringBuilder();
				builder.append(URLEncoder.encode(creditCard.getHolder(), "UTF-8"));
				builder.append("|");
				builder.append(URLEncoder.encode(creditCard.getBrandName(), "UTF-8"));
				builder.append("|");
				builder.append(URLEncoder.encode(creditCard.getNumber(), "UTF-8"));
				builder.append("|");

				if (creditCard.getCvv() == null)
					creditCard.setCvv(0);

				builder.append(URLEncoder.encode(Integer.toString(creditCard.getCvv()), "UTF-8"));
				builder.append("|");
				final DateFormat df1 = new SimpleDateFormat("MM/YY");

				if (creditCard.getExpirationYear() == null)
					creditCard.setExpirationYear(new Date());
				builder.append(URLEncoder.encode(df1.format(creditCard.getExpirationYear()), "UTF-8"));
				result = builder.toString();
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			}
		return result;
	}
}

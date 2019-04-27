
package converters;

import datatype.CreditCard;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Component
@Transactional
public class StringToCreditCardConverter implements Converter<String, CreditCard> {

	@Override
	public CreditCard convert(final String text) {
		CreditCard result;
		final String parts[];

		if (text == null)
			result = null;
		else
			try {
				parts = text.split("\\|");
				result = new CreditCard();
				result.setHolder(URLDecoder.decode(parts[0], "UTF-8"));
				result.setBrandName(URLDecoder.decode(parts[1], "UTF-8"));
				result.setNumber(URLDecoder.decode(parts[2], "UTF-8"));
				result.setCvv(Integer.getInteger(URLDecoder.decode(parts[3], "UTF-8")));
				final DateFormat df1 = new SimpleDateFormat("MM/YY");
				result.setExpirationYear(df1.parse(URLDecoder.decode(parts[4], "UTF-8")));
			} catch (final Throwable oops) {
				throw new IllegalArgumentException(oops);
			}

		return result;
	}

}

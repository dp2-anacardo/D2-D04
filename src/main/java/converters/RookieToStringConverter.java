
package converters;

import domain.Rookie;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class RookieToStringConverter implements Converter<Rookie, String> {

	@Override
	public String convert(final Rookie a) {
		String result;

		if (a == null)
			result = null;
		else
			result = String.valueOf(a.getId());

		return result;
	}
}

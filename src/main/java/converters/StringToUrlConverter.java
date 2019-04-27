
package converters;

import datatype.Url;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Component
@Transactional
public class StringToUrlConverter implements Converter<String, Url> {

	@Override
	public Url convert(final String text) {
		Url result;

		if (text == null)
			result = null;
		else
			try {
				result = new Url();
				result.setLink(URLDecoder.decode(text, StandardCharsets.ISO_8859_1.name()));
			} catch (final Throwable oops) {
				throw new IllegalArgumentException(oops);
			}

		return result;
	}

}

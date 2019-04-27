
package converters;

import domain.SocialProfile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class SocialProfileToStringConverter implements Converter<SocialProfile, String> {

	@Override
	public String convert(final SocialProfile sp) {
		String result;

		if (sp == null)
			result = null;
		else
			result = String.valueOf(sp.getId());

		return result;
	}

}

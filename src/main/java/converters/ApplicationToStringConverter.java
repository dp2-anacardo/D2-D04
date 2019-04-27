
package converters;

import domain.Application;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class ApplicationToStringConverter implements Converter<Application, String> {

	@Override
	public String convert(final Application application) {
		String result;

		if (application == null)
			result = null;
		else
			result = String.valueOf(application.getId());

		return result;
	}

}

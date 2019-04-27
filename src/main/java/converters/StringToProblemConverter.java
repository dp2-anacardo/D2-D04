
package converters;

import domain.Problem;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import repositories.ProblemRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToProblemConverter implements Converter<String, Problem> {

	@Autowired
	ProblemRepository problemRepository;


	@Override
	public Problem convert(final String text) {
		Problem result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.problemRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}

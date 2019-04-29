
package converters;

import domain.Actor;
import domain.Position;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import repositories.ActorRepository;
import repositories.PositionRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToPositionConverter implements Converter<String, Position> {

	@Autowired
	PositionRepository positionRepository;


	@Override
	public Position convert(final String text) {
		Position result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.positionRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}

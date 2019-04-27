package converters;

import domain.PositionData;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import repositories.PositionDataRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToPositionDataConverter implements Converter<String, PositionData> {
    @Autowired
    PositionDataRepository positionDataRepository;

    @Override
    public PositionData convert(final String text) {
        PositionData result;
        int id;
        try {
            if (StringUtils.isEmpty(text))
                result = null;
            else {
                id = Integer.valueOf(text);
                result = this.positionDataRepository.findOne(id);
            }
        } catch (final Throwable oops) {
            throw new IllegalArgumentException(oops);
        }
        return result;
    }
}

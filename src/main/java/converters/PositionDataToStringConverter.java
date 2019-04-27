package converters;

import domain.PositionData;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class PositionDataToStringConverter implements Converter<PositionData,String> {
    @Override
    public String convert(PositionData curricula) {
        String result;

        if (curricula == null)
            result = null;
        else
            result = String.valueOf(curricula.getId());

        return result;
    }
}

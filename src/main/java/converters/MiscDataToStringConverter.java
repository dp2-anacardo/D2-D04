package converters;

import domain.MiscData;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class MiscDataToStringConverter implements Converter<MiscData,String> {
    @Override
    public String convert(MiscData curricula) {
        String result;

        if (curricula == null)
            result = null;
        else
            result = String.valueOf(curricula.getId());

        return result;
    }
}

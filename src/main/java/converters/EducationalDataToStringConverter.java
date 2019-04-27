package converters;

import domain.EducationalData;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class EducationalDataToStringConverter implements Converter<EducationalData,String> {

    @Override
    public String convert(EducationalData curricula) {
        String result;

        if (curricula == null)
            result = null;
        else
            result = String.valueOf(curricula.getId());

        return result;
    }
}

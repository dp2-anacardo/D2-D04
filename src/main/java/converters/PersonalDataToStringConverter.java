package converters;

import domain.PersonalData;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class PersonalDataToStringConverter implements Converter<PersonalData,String> {
    @Override
    public String convert(PersonalData curricula) {
        String result;

        if (curricula == null)
            result = null;
        else
            result = String.valueOf(curricula.getId());

        return result;
    }
}

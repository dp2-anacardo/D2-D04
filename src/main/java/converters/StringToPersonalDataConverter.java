package converters;

import domain.PersonalData;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import repositories.PersonalDataRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToPersonalDataConverter implements Converter<String, PersonalData> {


    @Autowired
    PersonalDataRepository personalDataRepository;


    @Override
    public PersonalData convert(final String text) {
        PersonalData result;
        int id;
        try {
            if (StringUtils.isEmpty(text))
                result = null;
            else {
                id = Integer.valueOf(text);
                result = this.personalDataRepository.findOne(id);
            }
        } catch (final Throwable oops) {
            throw new IllegalArgumentException(oops);
        }
        return result;
    }
}

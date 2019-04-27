package converters;

import domain.EducationalData;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import repositories.EducationalDataRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToEducationalDataConverter implements Converter<String, EducationalData> {

    @Autowired
    EducationalDataRepository educationalDataRepository;

    @Override
    public EducationalData convert(final String text) {
        EducationalData result;
        int id;
        try {
            if (StringUtils.isEmpty(text))
                result = null;
            else {
                id = Integer.valueOf(text);
                result = this.educationalDataRepository.findOne(id);
            }
        } catch (final Throwable oops) {
            throw new IllegalArgumentException(oops);
        }
        return result;
    }
}

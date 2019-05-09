package converters;

import domain.Auditor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import repositories.AuditorRepository;

public class StringToAuditorConverter implements Converter<String, Auditor> {

    @Autowired
    AuditorRepository auditorRepository;


    @Override
    public Auditor convert(final String text) {
        Auditor result;
        int id;

        try {
            if (StringUtils.isEmpty(text))
                result = null;
            else {
                id = Integer.valueOf(text);
                result = this.auditorRepository.findOne(id);
            }
        } catch (final Throwable oops) {
            throw new IllegalArgumentException(oops);
        }

        return result;
    }
}

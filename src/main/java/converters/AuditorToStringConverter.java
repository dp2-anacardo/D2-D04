package converters;


import domain.Auditor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class AuditorToStringConverter implements Converter<Auditor, String> {

    @Override
    public String convert(final Auditor auditor) {
        String result;

        if (auditor == null)
        result = null;
        else
        result = String.valueOf(auditor.getId());

        return result;
        }
}

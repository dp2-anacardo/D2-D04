package converters;


import domain.Audit;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class AuditToStringConverter implements Converter<Audit, String> {

    @Override
    public String convert(final Audit audit) {
        String result;

        if (audit == null)
        result = null;
        else
        result = String.valueOf(audit.getId());

        return result;
        }
}

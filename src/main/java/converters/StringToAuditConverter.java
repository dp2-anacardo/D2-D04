package converters;

import domain.Audit;
import domain.Auditor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import repositories.AuditRepository;
import repositories.AuditorRepository;

public class StringToAuditConverter implements Converter<String, Audit> {

    @Autowired
    AuditRepository auditRepository;


    @Override
    public Audit convert(final String text) {
        Audit result;
        int id;

        try {
            if (StringUtils.isEmpty(text))
                result = null;
            else {
                id = Integer.valueOf(text);
                result = this.auditRepository.findOne(id);
            }
        } catch (final Throwable oops) {
            throw new IllegalArgumentException(oops);
        }

        return result;
    }
}

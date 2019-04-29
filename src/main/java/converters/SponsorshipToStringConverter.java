
package converters;

import domain.Sponsorship;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class SponsorshipToStringConverter implements Converter<Sponsorship, String> {

    @Override
    public String convert(final Sponsorship a) {
        String result;

        if (a == null)
            result = null;
        else
            result = String.valueOf(a.getId());

        return result;
    }

}

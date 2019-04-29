
package converters;

import domain.Provider;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class ProviderToStringConverter implements Converter<Provider, String> {

    @Override
    public String convert(final Provider a) {
        String result;

        if (a == null)
            result = null;
        else
            result = String.valueOf(a.getId());

        return result;
    }

}

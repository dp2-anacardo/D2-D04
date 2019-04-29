
package converters;

import domain.Provider;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import repositories.ProviderRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToProviderConverter implements Converter<String, Provider> {

    @Autowired
    ProviderRepository providerRepository;


    @Override
    public Provider convert(final String text) {
        Provider result;
        int id;

        try {
            if (StringUtils.isEmpty(text))
                result = null;
            else {
                id = Integer.valueOf(text);
                result = this.providerRepository.findOne(id);
            }
        } catch (final Throwable oops) {
            throw new IllegalArgumentException(oops);
        }

        return result;
    }

}

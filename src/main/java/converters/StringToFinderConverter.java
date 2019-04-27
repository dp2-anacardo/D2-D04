package converters;

import domain.Finder;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import repositories.FinderRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToFinderConverter implements Converter<String, Finder> {

    @Autowired
    FinderRepository	finderRepository;


    @Override
    public Finder convert(final String text) {
        Finder result;
        int id;

        try {
            if (StringUtils.isEmpty(text))
                result = null;
            else {
                id = Integer.valueOf(text);
                result = this.finderRepository.findOne(id);
            }
        } catch (final Throwable oops) {
            throw new IllegalArgumentException(oops);
        }

        return result;
    }

}

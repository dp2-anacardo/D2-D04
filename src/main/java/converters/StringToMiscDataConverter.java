package converters;

import domain.MiscData;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import repositories.MiscDataRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToMiscDataConverter implements Converter<String, MiscData> {
    @Autowired
    MiscDataRepository miscDataRepository;

    @Override
    public MiscData convert(final String text) {
        MiscData result;
        int id;
        try {
            if (StringUtils.isEmpty(text))
                result = null;
            else {
                id = Integer.valueOf(text);
                result = this.miscDataRepository.findOne(id);
            }
        } catch (final Throwable oops) {
            throw new IllegalArgumentException(oops);
        }
        return result;
    }
}

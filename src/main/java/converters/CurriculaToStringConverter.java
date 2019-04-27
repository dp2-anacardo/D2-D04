package converters;

import domain.Curricula;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class CurriculaToStringConverter implements Converter<Curricula,String> {

    @Override
    public String convert(Curricula curricula) {
        String result;

        if (curricula == null)
            result = null;
        else
            result = String.valueOf(curricula.getId());

        return result;
    }
}

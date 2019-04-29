
package converters;

import domain.Position;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class PositionToStringConverter implements Converter<Position, String> {

    @Override
    public String convert(final Position a) {
        String result;

        if (a == null)
            result = null;
        else
            result = String.valueOf(a.getId());

        return result;
    }

}

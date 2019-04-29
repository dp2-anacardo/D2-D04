
package converters;

import domain.Item;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class ItemToStringConverter implements Converter<Item, String> {

    @Override
    public String convert(final Item a) {
        String result;

        if (a == null)
            result = null;
        else
            result = String.valueOf(a.getId());

        return result;
    }

}

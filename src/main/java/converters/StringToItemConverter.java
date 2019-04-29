
package converters;

import domain.Item;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import repositories.ItemRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToItemConverter implements Converter<String, Item> {

    @Autowired
    ItemRepository itemRepository;


    @Override
    public Item convert(final String text) {
        Item result;
        int id;

        try {
            if (StringUtils.isEmpty(text))
                result = null;
            else {
                id = Integer.valueOf(text);
                result = this.itemRepository.findOne(id);
            }
        } catch (final Throwable oops) {
            throw new IllegalArgumentException(oops);
        }

        return result;
    }

}

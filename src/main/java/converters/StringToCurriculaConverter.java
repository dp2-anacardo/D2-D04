package converters;

import domain.Curricula;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import repositories.CurriculaRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToCurriculaConverter implements Converter<String, Curricula> {

    @Autowired
    CurriculaRepository curriculaRepository;

    @Override
    public Curricula convert(String text){
        Curricula result;
        int id;

        try{
            if(StringUtils.isEmpty(text)){
                result = null;
            }else{
                id = Integer.valueOf(text);
                result = this.curriculaRepository.findOne(id);
            }
        }catch(Throwable oops){
            throw new IllegalArgumentException(oops);
        }
        return result;
    }
}

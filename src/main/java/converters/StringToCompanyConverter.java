
package converters;

import domain.Company;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import repositories.CompanyRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToCompanyConverter implements Converter<String, Company> {

	@Autowired
	CompanyRepository	companyRepository;


	@Override
	public Company convert(final String text) {
		Company result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.companyRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}

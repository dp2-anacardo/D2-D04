
package domain;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.Collection;

@Entity
@Access(AccessType.PROPERTY)
public class Rookie extends Actor {

	//Relationships

	private Finder					finder;
	private Collection<Curricula>	curricula;


	@Valid
	@OneToOne(optional = false)
	public Finder getFinder() {
		return this.finder;
	}

	public void setFinder(final Finder finder) {
		this.finder = finder;
	}

	@Valid
	@OneToMany
	public Collection<Curricula> getCurricula() {
		return this.curricula;
	}

	public void setCurricula(final Collection<Curricula> curricula) {
		this.curricula = curricula;
	}

}

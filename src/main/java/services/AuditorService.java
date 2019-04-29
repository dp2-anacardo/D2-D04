package services;

import domain.Auditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.AuditorRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class AuditorService {

    @Autowired
    private AuditorRepository auditorRepository;

    public Auditor findOne(int auditorId){
        Auditor result;

        result = this.auditorRepository.findOne(auditorId);

        return result;
    }
}

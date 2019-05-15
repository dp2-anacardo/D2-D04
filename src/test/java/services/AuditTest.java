package services;

import domain.Audit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.DataBinder;
import utilities.AbstractTest;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AuditTest extends AbstractTest {

    @Autowired
    private AuditService auditService;

    //In this test we are testing the requirement 3(creating audits).
    //In the negative cases we are testing that auditors can not create an audit if they do not
    //submit a text or they submit a score out of the range between 0 and 10, and they can not create an
    //audit in a position that they already created an audit before.
    //Sequence coverage: 100%
    //Data coverage: 100%

    @Test
    public void createAuditDriver() {
        final Object testingData[][] = {
                {
                    "auditor1", "textTest", 8.0, "position1", null
                }, {
                "auditor1", "", 8.0, "position1", ValidationException.class
                }, {
                "auditor2", "textTest", 8.0, "position1", IllegalArgumentException.class
                }, {
                "auditor1", "textTest", 20.0, "position1", ValidationException.class
        }
        };
        for (int i = 0; i < testingData.length; i++)
            this.createAuditTemplate((String) testingData[i][0], (String) testingData[i][1], (Double) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
    }

    private void createAuditTemplate(final String username, final String text, final Double score, final String position, final Class<?> expected) {
        Class<?> caught;
        caught = null;

        try {
            this.authenticate(username);

            final int positionId = super.getEntityId(position);
            Audit audit = this.auditService.create();
            audit.setText(text);
            audit.setScore(score);
            final DataBinder binding = new DataBinder(new Audit());
            audit = this.auditService.reconstruct(audit, binding.getBindingResult());
            this.auditService.saveCreate(audit, positionId);
            super.unauthenticate();

        } catch (final Throwable oops) {
            caught = oops.getClass();
        }

        super.checkExceptions(expected, caught);

    }

    //In this test we are testing the requirement 3(updating audits).
    //In the negative cases we are testing that auditors can not update an audit if they do not
    //submit a text and a score between 0 and 10, and they can not update an audit created by another auditor.
    //Sequence coverage: 100%
    //Data coverage: 87.5%

    @Test
    public void updateAuditDriver() {
        final Object testingData[][] = {
                {
                    "auditor3", "audit2", "test", 5.0, false, null
                }, {
                "auditor3", "audit2", "test", 5.0, true, null
        }, {
                "auditor3", "audit2", "", 5.0, false, ValidationException.class
        }, {
                "auditor3", "audit2", "", 5.0, true, ValidationException.class
        }, {
                "auditor3", "audit1", "test", 5.0, false, IllegalArgumentException.class
        }, {
                "auditor3", "audit1", "test", 5.0, true, IllegalArgumentException.class
        }
        };
        for (int i = 0; i < testingData.length; i++)
            this.updateAuditTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Double) testingData[i][3], (Boolean) testingData[i][4], (Class<?>) testingData[i][5]);
    }

    private void updateAuditTemplate(final String username, final String audit, final String text, Double score, final Boolean isFinal, final Class<?> expected) {
        Class<?> caught;
        caught = null;

        try {
            this.authenticate(username);

            final int auditId = super.getEntityId(audit);
            Audit a = this.auditService.findOne(auditId);
            a.setText(text);
            a.setScore(score);
            a.setIsFinal(isFinal);
            final DataBinder binding = new DataBinder(new Audit());
            a = this.auditService.reconstruct(a, binding.getBindingResult());
            this.auditService.saveUpdate(a);

            this.unauthenticate();
        } catch (final Throwable oops) {
            caught = oops.getClass();
        }

        super.checkExceptions(expected, caught);

    }

    //In this test we are testing the requirement 3(deleting audits).
    //In the negative cases we are testing that auditors can not delete an audit if it is in final mode,
    //and they can not delete an audit created by another auditor.
    //Sequence coverage: 100%
    //Data coverage: 100%

    @Test
    public void deleteAuditDriver() {
        Object testingData[][] = {
                {
                        "auditor3", "audit2", null
                }, {
                "auditor2", "audit1", IllegalArgumentException.class
        }, {
                "auditor3", "audit1", IllegalArgumentException.class
        }
        };
        for (int i = 0; i < testingData.length; i++) {
            this.deleteProblemTemplate((String) testingData[i][0],
                    (String) testingData[i][1], (Class<?>) testingData[i][2]);
        }
    }

    private void deleteProblemTemplate(String username, String audit, Class<?> expected) {
        Class<?> caught;
        caught = null;

        try {
            this.authenticate(username);
            Audit a = this.auditService.findOne(super.getEntityId(audit));
            this.auditService.delete(a);
        } catch (Throwable oops) {
            caught = oops.getClass();
        }

        super.checkExceptions(expected, caught);
    }

}

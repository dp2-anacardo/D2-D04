package services;

import datatype.Url;
import domain.Problem;
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
public class ManageProblemsTest extends AbstractTest {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ProblemService problemService;


    /*
     * Testing functional requirement : 9.2 An actor that is authenticated as a company must be able to manage their problems
     * Positive: A company creates a problem
     * Negative: A company tries to create a problem with invalid data
     * Sentence coverage: 99%
     * Data coverage: 50%
     */

    @Test
    public void createProblemDriver() {
        Object testingData[][] = {
                {
                        "problem1", "This is the problem 1", "Check the attachment", "http://www.url.es", false, "company1", null
                }, {
                "problem2", "This is the problem 2", "Check the attachment", "http://www.url.es", true, "company2", null
        }, {
                "", "This is the problem 3", "Check the attachment", "http://www.url.es", false, "company1", ValidationException.class
        }, {
                "problem4", "", "Check the attachment", "http://www.url.es", false, "company2", ValidationException.class
        }
        };
        for (int i = 0; i < testingData.length; i++) {
            this.createProblemTemplate((String) testingData[i][0], (String) testingData[i][1],
                    (String) testingData[i][2], (String) testingData[i][3], (Boolean) testingData[i][4]
                    , (String) testingData[i][5], (Class<?>) testingData[i][6]);
        }
    }

    private void createProblemTemplate(String title, String statement, String hint,
                                       String attachment, Boolean isFinal,
                                       String company,
                                       Class<?> expected) {
        Class<?> caught;
        caught = null;

        try {
            this.authenticate(company);

            Problem p = this.problemService.create();
            p.setTitle(title);


            p.setHint(hint);
            Url attach = new Url();
            attach.setLink(attachment);
            p.getAttachment().add(attach);
            p.setIsFinal(isFinal);
            p.setStatement(statement);


            final DataBinder binding = new DataBinder(new Problem());
            p = this.problemService.reconstruct(p, binding.getBindingResult());

            this.problemService.save(p);

        } catch (Throwable oops) {
            caught = oops.getClass();
        }

        super.checkExceptions(expected, caught);
    }


    /*
     * Testing functional requirement : 9.2 An actor that is authenticated as a company must be able to manage their problems
     * Positive: A company edits a problem
     * Negative: A company tries to edit a problem with invalid data
     * Sentence coverage: 99%
     * Data coverage: 33%
     */

    @Test
    public void editProblemDriver() {
        Object testingData[][] = {
                {
                        "company1", "problem6", "This is the new title of problem6", null
                }, {
                "company1", "problem6", "", ValidationException.class
        }
        };
        for (int i = 0; i < testingData.length; i++) {
            this.editProblemTemplate((String) testingData[i][0],
                    (String) testingData[i][1], (String) testingData[i][2],
                    (Class<?>) testingData[i][3]);
        }
    }

    private void editProblemTemplate(String company, String problem, String title, Class<?> expected) {
        Class<?> caught;
        caught = null;


        try {
            this.authenticate(company);
            Problem p = this.problemService.findOne(super.getEntityId(problem));
            p.setTitle(title);
            final DataBinder binding = new DataBinder(new Problem());
            p = this.problemService.reconstruct(p, binding.getBindingResult());
            this.problemService.save(p);
        } catch (Throwable oops) {
            caught = oops.getClass();
        }

        super.checkExceptions(expected, caught);

    }

    /*
     * Testing functional requirement : 9.1 An actor that is authenticated as a company must be able to manage their problems
     * Positive: A company deletes a problem
     * Negative: A company tries to delete a problem in final mode
     * Sentence coverage: 100%
     * Data coverage: Not applicable
     */

    @Test
    public void deleteProblemDriver() {
        Object testingData[][] = {
                {
                        "company1", "problem6", null
                }, {
                "company2", "problem5", IllegalArgumentException.class
        }
        };
        for (int i = 0; i < testingData.length; i++) {
            this.deleteProblemTemplate((String) testingData[i][0],
                    (String) testingData[i][1], (Class<?>) testingData[i][2]);
        }
    }

    private void deleteProblemTemplate(String company, String problem, Class<?> expected) {
        Class<?> caught;
        caught = null;

        try {
            this.authenticate(company);
            Problem p = this.problemService.findOne(super.getEntityId(problem));
            this.problemService.delete(p);
        } catch (Throwable oops) {
            caught = oops.getClass();
        }

        super.checkExceptions(expected, caught);
    }


}

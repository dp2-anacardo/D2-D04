package services;

import domain.Finder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import utilities.AbstractTest;

import javax.transaction.Transactional;

@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FinderTest extends AbstractTest {

    @Autowired
    public FinderService finderService;

    //In this test we are testing the requirement 17.2(updating finder).
    //In the negative cases we are testing that other user that dont own the finder try to edit it
    //Sequence coverage: 100%
    //Data coverage: 100%

    @Test
    public void updatingFinderDriver() {
        final Object testingData[][] = {
                {
                        "rookie1", "finderRookie1", "keyword", null
                }, {
                "rookie2", "finderRookie1", "keyword", IllegalArgumentException.class
                }, {
                "company1", "finderRookie1", "keyword", IllegalArgumentException.class
        }
        };
        for (int i = 0; i < testingData.length; i++)
            this.updatingFinderTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
    }

    private void updatingFinderTemplate(final String username, final String finder, final String keyword, final Class<?> expected) {
        Class<?> caught;
        caught = null;

        try {
            this.authenticate(username);

            Finder finder1 = this.finderService.findOne(super.getEntityId(finder));
            finder1.setKeyWord(keyword);
            this.finderService.save(finder1);

        } catch (final Throwable oops) {
            caught = oops.getClass();
        }

        super.checkExceptions(expected, caught);

    }

    //In this test we are testing the requirement 17.2(clearing finder).
    //In the negative cases we are testing that other user that dont own the finder try to edit it
    //Sequence coverage: 100%
    //Data coverage: 100%

    @Test
    public void cleaningFinderDriver() {
        final Object testingData[][] = {
                {
                        "rookie1", "finderRookie1", null
                }, {
                "rookie2", "finderRookie1", IllegalArgumentException.class
        }, {
                "company1", "finderRookie1", IllegalArgumentException.class
        }
        };
        for (int i = 0; i < testingData.length; i++)
            this.cleaningFinderTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
    }

    private void cleaningFinderTemplate(final String username, final String finder, final Class<?> expected) {
        Class<?> caught;
        caught = null;

        try {
            this.authenticate(username);

            Finder finder1 = this.finderService.findOne(super.getEntityId(finder));
            finder1.setMaxDeadline(null);
            finder1.setDeadline(null);
            finder1.setMinSalary(0);
            finder1.setKeyWord(null);
            this.finderService.save(finder1);

        } catch (final Throwable oops) {
            caught = oops.getClass();
        }

        super.checkExceptions(expected, caught);

    }
}

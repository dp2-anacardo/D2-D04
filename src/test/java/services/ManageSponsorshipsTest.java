package services;

import datatype.CreditCard;
import domain.Position;
import domain.Sponsorship;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.DataBinder;
import utilities.AbstractTest;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.text.SimpleDateFormat;
import java.util.Date;

@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ManageSponsorshipsTest extends AbstractTest {

    @Autowired
    private SponsorshipService sponsorshipService;

    @Autowired
    private PositionService positionService;

    /*
     * Testing functional requirement : 9.2 An actor that is authenticated as a provider must be able to manage their sponsorships
     * Positive: A provider creates a sponsorship
     * Negative: A provider tries to create a sponsorship with invalid data
     * Sentence coverage: 99%
     * Data coverage: 50%
     */

    @Test
    public void createSponsorshipDriver() {
        Object testingData[][] = {
                {
                        "https://www.sponsorship5-link.com", "Provider 1", "VISA", 856, "4934124580909324", "2026/10/20", "position1", "provider1", null
                }, {
                "https://www.sponsorship6-link.com", "Provider 2", "VISA", 856, "4934124580909324", "2026/10/20", "position1", "provider2", null
        }, {
                "", "Provider 1", "VISA", 856, "4934124580909324", "2026/10/20", "position1", "provider1", ValidationException.class
        }, {
                "https://www.sponsorship8-link.com", "Provider 2", "VISA", 856, "", "2026/10/20", "position1", "provider2", ValidationException.class
        }
        };
        for (int i = 0; i < testingData.length; i++) {
            this.createSponsorshipTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (int) testingData[i][3],
                    (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7]
                    , (Class<?>) testingData[i][8]);
        }
    }

    private void createSponsorshipTemplate(String banner, String holder, String brand,
                                           int cvv, String number, String expiration, String position, String provider,
                                           Class<?> expected) {
        Class<?> caught;
        caught = null;

        try {
            this.authenticate(provider);

            Sponsorship p = this.sponsorshipService.create();
            Position pos = this.positionService.findOne(super.getEntityId(position));

            p.setBanner(banner);
            p.setPosition(pos);

            final CreditCard c = new CreditCard();
            c.setBrandName(brand);
            c.setCvv(cvv);
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            final Date date = sdf.parse(expiration);
            c.setExpirationYear(date);
            c.setHolder(holder);
            c.setNumber(number);

            p.setCreditCard(c);

            final DataBinder binding = new DataBinder(new Sponsorship());
            p = this.sponsorshipService.reconstruct(p, binding.getBindingResult());

            this.sponsorshipService.save(p);

        } catch (Throwable oops) {
            caught = oops.getClass();
        }

        super.checkExceptions(expected, caught);
    }


    /*
     * Testing functional requirement : 9.2 An actor that is authenticated as a provider must be able to manage their sponsorships
     * Positive: A provider edits a sponsorship
     * Negative: A provider tries to edit a sponsorship with invalid data
     * Sentence coverage: 99%
     * Data coverage: 33%
     */

    @Test
    public void editSponsorshipDriver() {
        Object testingData[][] = {
                {
                        "provider1", "sponsorship1", "https://www.new-banner.es", null
                }, {
                "provider2", "sponsorship3", "", ValidationException.class
        }
        };
        for (int i = 0; i < testingData.length; i++) {
            this.editSponsorshipTemplate((String) testingData[i][0],
                    (String) testingData[i][1], (String) testingData[i][2],
                    (Class<?>) testingData[i][3]);
        }
    }

    private void editSponsorshipTemplate(String provider, String sponsorship, String banner, Class<?> expected) {
        Class<?> caught;
        caught = null;


        try {
            this.authenticate(provider);
            Sponsorship p = this.sponsorshipService.findOne(super.getEntityId(sponsorship));
            p.setBanner(banner);
            final DataBinder binding = new DataBinder(new Sponsorship());
            p = this.sponsorshipService.reconstruct(p, binding.getBindingResult());
            this.sponsorshipService.save(p);
        } catch (Throwable oops) {
            caught = oops.getClass();
        }

        super.checkExceptions(expected, caught);

    }

    /*
     * Testing functional requirement : 9.1 An actor that is authenticated as a provider must be able to manage their sponsorships
     * Positive: A provider deletes a sponsorship
     * Negative: A provider tries to delete a sponsorship in final mode
     * Sentence coverage: 100%
     * Data coverage: Not applicable
     */

    @Test
    public void deleteSponsorshipDriver() {
        Object testingData[][] = {
                {
                        "provider1", "sponsorship1", null
                }, {
                "provider2", "sponsorship2", IllegalArgumentException.class
        }
        };
        for (int i = 0; i < testingData.length; i++) {
            this.deleteSponsorshipTemplate((String) testingData[i][0],
                    (String) testingData[i][1], (Class<?>) testingData[i][2]);
        }
    }

    private void deleteSponsorshipTemplate(String provider, String sponsorship, Class<?> expected) {
        Class<?> caught;
        caught = null;

        try {
            this.authenticate(provider);
            Sponsorship p = this.sponsorshipService.findOne(super.getEntityId(sponsorship));
            this.sponsorshipService.delete(p);
        } catch (Throwable oops) {
            caught = oops.getClass();
        }

        super.checkExceptions(expected, caught);
    }


}

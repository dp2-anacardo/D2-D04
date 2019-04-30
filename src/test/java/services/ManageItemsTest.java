package services;

import datatype.Url;
import domain.Item;
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
public class ManageItemsTest extends AbstractTest {

    @Autowired
    private ItemService itemService;

    /*
     * Testing functional requirement : 9.2 An actor that is authenticated as a provider must be able to manage their items
     * Positive: A provider creates a item
     * Negative: A provider tries to create a item with invalid data
     * Sentence coverage: 99%
     * Data coverage: 50%
     */

    @Test
    public void createItemDriver() {
        Object testingData[][] = {
                {
                        "Item 5", "This is the item 1", "http://www.item5-link.com", "http://www.item5-picture.com", "provider1", null
                }, {
                "Item 6", "This is the item 6", "http://www.item6-link.com", "http://www.item6-picture.com", "provider2", null
        }, {
                "", "This is the item 7", "http://www.item7-link.com", "http://www.item7-picture.com", "provider1", ValidationException.class
        }, {
                "Item 8", "", "http://www.item8-link.com", "http://www.item8-picture.com", "provider2", ValidationException.class
        }
        };
        for (int i = 0; i < testingData.length; i++) {
            this.createItemTemplate((String) testingData[i][0], (String) testingData[i][1],
                    (String) testingData[i][2], (String) testingData[i][3]
                    , (String) testingData[i][4], (Class<?>) testingData[i][5]);
        }
    }

    private void createItemTemplate(String name, String description, String link,
                                    String picture, String provider,
                                    Class<?> expected) {
        Class<?> caught;
        caught = null;

        try {
            this.authenticate(provider);

            Item p = this.itemService.create();

            p.setName(name);
            p.setDescription(description);
            Url linkURL = new Url();
            linkURL.setLink(link);
            p.getLinks().add(linkURL);
            Url pictureURL = new Url();
            pictureURL.setLink(picture);
            p.getPictures().add(pictureURL);

            final DataBinder binding = new DataBinder(new Item());
            p = this.itemService.reconstruct(p, binding.getBindingResult());

            this.itemService.save(p);

        } catch (Throwable oops) {
            caught = oops.getClass();
        }

        super.checkExceptions(expected, caught);
    }


    /*
     * Testing functional requirement : 9.2 An actor that is authenticated as a provider must be able to manage their items
     * Positive: A provider edits a item
     * Negative: A provider tries to edit a item with invalid data
     * Sentence coverage: 99%
     * Data coverage: 33%
     */

    @Test
    public void editItemDriver() {
        Object testingData[][] = {
                {
                        "provider1", "item1", "This is the new description", null
                }, {
                "provider2", "item3", "", ValidationException.class
        }
        };
        for (int i = 0; i < testingData.length; i++) {
            this.editItemTemplate((String) testingData[i][0],
                    (String) testingData[i][1], (String) testingData[i][2],
                    (Class<?>) testingData[i][3]);
        }
    }

    private void editItemTemplate(String provider, String item, String description, Class<?> expected) {
        Class<?> caught;
        caught = null;


        try {
            this.authenticate(provider);
            Item p = this.itemService.findOne(super.getEntityId(item));
            p.setDescription(description);
            final DataBinder binding = new DataBinder(new Item());
            p = this.itemService.reconstruct(p, binding.getBindingResult());
            this.itemService.save(p);
        } catch (Throwable oops) {
           caught = oops.getClass();
        }

        super.checkExceptions(expected, caught);

    }

    /*
     * Testing functional requirement : 9.1 An actor that is authenticated as a provider must be able to manage their items
     * Positive: A provider deletes a item
     * Negative: A provider tries to delete a item in final mode
     * Sentence coverage: 100%
     * Data coverage: Not applicable
     */

    @Test
    public void deleteItemDriver() {
        Object testingData[][] = {
                {
                        "provider1", "item1", null
                }, {
                "provider2", "item2", IllegalArgumentException.class
        }
        };
        for (int i = 0; i < testingData.length; i++) {
            this.deleteItemTemplate((String) testingData[i][0],
                    (String) testingData[i][1], (Class<?>) testingData[i][2]);
        }
    }

    private void deleteItemTemplate(String provider, String item, Class<?> expected) {
        Class<?> caught;
        caught = null;

        try {
            this.authenticate(provider);
            Item p = this.itemService.findOne(super.getEntityId(item));
            this.itemService.delete(p);
        } catch (Throwable oops) {
            caught = oops.getClass();
        }

        super.checkExceptions(expected, caught);
    }


}

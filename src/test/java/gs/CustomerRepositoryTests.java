package gs;


/**
 * ref
 * http://www.baeldung.com/queries-in-spring-data-mongodb
 * http://www.baeldung.com/spring-data-mongodb-tutorial
 * https://docs.spring.io/spring-data/data-mongo/docs/current/reference/html/
 *
 * ? continuous delivery ?
 * ? rollback several changes ?
 */

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerRepositoryTests {

    @Autowired
    CustomerRepository repository;

    Customer luke, han, annakin;

    @Rule
    public TestRule watcher = new TestWatcher() {
        protected void starting(Description description) {
            // TODO use LOGGER
            System.out.println("> " + description.getClassName() + "." + description.getMethodName() + "()");
        }
    };

    @Before
    public void setUp() {

        repository.deleteAll();

        han = repository.save(new Customer("Han", "Solo"));
        luke = repository.save(new Customer("Luke", "Skywalker"));
        annakin = repository.save(new Customer("Anakin", "Skywalker"));
    }

    @Test
    public void setsIdOnSave() {

        Customer dave = repository.save(new Customer("", "d2r2"));

        assertThat(dave.id).isNotNull();
    }

    @Test
    public void findsByLastName() {

        List<Customer> result = repository.findByLastName("Solo");

        assertThat(result).hasSize(1).extracting("firstName").contains("Han");
    }

    @Test
    public void findsByExample() {

        Customer c = new Customer(null, "Skywalker");

        List<Customer> result = repository.findAll(Example.of(c));

        assertThat(result).hasSize(2).extracting("firstName").contains("Luke", "Anakin");
    }

    @Test(expected = OptimisticLockingFailureException.class)
    public void OptimisticLocking() {

        Customer c1 = repository.findOne(annakin.id);
        Customer c2 = repository.findOne(annakin.id);

        c1.setFirstName("Darth");
        repository.save(c1);

        c2.setLastName("Vader");
        repository.save(c2);
    }
}
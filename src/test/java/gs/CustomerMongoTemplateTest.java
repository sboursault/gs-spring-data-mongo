package gs;


/**
 * ref
 * http://www.baeldung.com/queries-in-spring-data-mongodb
 * http://www.baeldung.com/spring-data-mongodb-tutorial
 * https://docs.spring.io/spring-data/data-mongo/docs/current/reference/html/
 *
 * mongo client: https://github.com/mongoclient/mongoclient/releases/tag/2.1.0
 *
 * ? continuous delivery ?
 * ? rollback several changes ?
 */

import gs.utils.TestNameLogger;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerMongoTemplateTest {

    @Autowired
    MongoTemplate mongoTemplate;


    @Rule
    public TestRule logTestNames = new TestNameLogger();

    @Before
    public void setUp() {
        mongoTemplate.dropCollection("customers");
    }

    @Test
    public void useMongoTemplate() {

        mongoTemplate.save(new Customer("", "d2r2"));
        Customer c = mongoTemplate.findOne(Query.query(Criteria.where("lastName").is("d2r2")), Customer.class);
        assertThat(c.getLastName()).isEqualTo("d2r2");
    }

}
import com.config.MongoConfig;
import com.config.WebConfig;
import com.mongoRepository.MongoOperationTest;
import com.mongo_domain.Item;
import com.mongo_domain.Order;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by mac on 2017/12/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class, MongoConfig.class})
public class MongoTest {
    @Autowired
    private MongoOperationTest mongoOperations;

    @Test
    public void test01() throws UnknownHostException {

        LinkedHashSet linkedHashSet=new LinkedHashSet();
        Item item=new Item(1L,"s",11.16,10);
        Item item2=new Item(2L,"a",12.16,12);

        linkedHashSet.add(item);
        linkedHashSet.add(item2);
        Order order=new Order("1","lili","toy",linkedHashSet);
        mongoOperations.saveOrder(order);
//        Order order1 = mongoOperations.findOrderById("1");
        Long order1 = mongoOperations.getCount("order");
        List<Order> lili = mongoOperations.query("lili");
        mongoOperations.delete("1");
    }
}

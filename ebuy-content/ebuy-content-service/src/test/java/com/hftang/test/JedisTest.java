import redis.clients.jedis.Jedis;

/**
 * @author hftang
 * @date 2019-07-16 16:32
 * @desc
 */
public class JedisTest {

//    @Test
    public void run01() {

        Jedis jedis = new Jedis("192.168.217.132", 6379);
        jedis.set("name", "aaaaaaaa");
        jedis.close();

    }
}

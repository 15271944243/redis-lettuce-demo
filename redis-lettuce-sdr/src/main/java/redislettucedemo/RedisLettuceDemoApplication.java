package redislettucedemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootApplication
public class RedisLettuceDemoApplication implements ApplicationListener<ApplicationReadyEvent> {

	public static void main(String[] args) {
		SpringApplication.run(RedisLettuceDemoApplication.class, args);
	}

	@Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
//        redisTemplate.opsForValue().set("key", "hello redis", 20L, TimeUnit.SECONDS);
        useCallback();
    }


    public void useCallback() {
        stringRedisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                Long size = connection.dbSize();
                // Can cast to StringRedisConnection if using a StringRedisTemplate
                ((StringRedisConnection)connection).setEx("key", 20L,"hello redis3");
                return null;
            }
        });
    }

}

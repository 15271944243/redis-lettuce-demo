package redislettucedemo;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class RedisLettuceDemoApplication implements ApplicationListener<ApplicationReadyEvent> {

	public static void main(String[] args) {
		SpringApplication.run(RedisLettuceDemoApplication.class, args);
	}

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        createConnection();
    }

    private void createConnection() {
        // 1. Create the RedisClient instance
        RedisClient redisClient = RedisClient.create("redis://redis-test1.o4r4dx.0001.cnn1.cache.amazonaws.com.cn:6379/0");
        redisClient.setDefaultTimeout(Duration.ofSeconds(10L));
        // 2. Open a Redis Standalone connection
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        // 3. Obtain the command API for synchronous execution.
        // Lettuce supports asynchronous and reactive execution models, too
        RedisCommands<String, String> syncCommands = connection.sync();

        syncCommands.setex("key", 10000L, "Hello, Redis!");
        // 4. Close the connection when you’re done.
        // This happens usually at the very end of your application.
        // Connections are designed to be long-lived
        connection.close();
        // 5. Shut down the client instance to free threads and resources
        // This happens usually at the very end of your application.
        redisClient.shutdown();



    }

    private void createConnectionByRedisURI() {
        RedisURI redisUri = RedisURI.Builder.redis("redis-test1.o4r4dx.0001.cnn1.cache.amazonaws.com.cn")
                .withPort(6379)
                .withDatabase(0)
                .build();
        RedisClient redisClient = RedisClient.create(redisUri);
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> syncCommands = connection.sync();
        syncCommands.setex("key", 10000L, "Hello, Redis!");
        connection.close();
        redisClient.shutdown();
    }
}

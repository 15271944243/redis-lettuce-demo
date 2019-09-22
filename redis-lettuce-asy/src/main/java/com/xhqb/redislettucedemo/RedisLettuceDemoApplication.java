package com.xhqb.redislettucedemo;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import java.time.Duration;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class RedisLettuceDemoApplication implements ApplicationListener<ApplicationReadyEvent> {

	public static void main(String[] args) {
		SpringApplication.run(RedisLettuceDemoApplication.class, args);
	}

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        createAsyConnection();
    }

    private void createAsyConnection() {
        // 1. Create the RedisClient instance
        RedisClient redisClient = RedisClient.create("redis://redis-test1.o4r4dx.0001.cnn1.cache.amazonaws.com.cn:6379/0");
        redisClient.setDefaultTimeout(Duration.ofSeconds(10L));
        // 2. Open a Redis Standalone connection
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        // 3. Obtain the command API for asynchronous execution.
        RedisAsyncCommands<String, String> syncCommands = connection.async();

        RedisFuture<String> set = syncCommands.set("key", "value");
        RedisFuture<String> future = syncCommands.get("key");

        try {
            String value = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // 4. Close the connection when you’re done.
        // This happens usually at the very end of your application.
        // Connections are designed to be long-lived
        connection.close();
        // 5. Shut down the client instance to free threads and resources
        // This happens usually at the very end of your application.
        redisClient.shutdown();
    }
}

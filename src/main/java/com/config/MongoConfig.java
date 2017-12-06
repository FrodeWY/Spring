package com.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Arrays;

/**
 * Created by mac on 2017/12/5.
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.mongoRepository")
@PropertySource("classpath:app.properties")
public class MongoConfig  extends AbstractMongoConfiguration{
    @Autowired
    private Environment environment;
    @Override
    protected String getDatabaseName() {
        return "OrderDB";
    }

    @Override
    public Mongo mongo() throws Exception {
        String username = environment.getProperty("mongo.username");
        String password = environment.getProperty("mongo.password");
        MongoCredential credential=MongoCredential.createScramSha1Credential(
                username,
                "OrderDB",
                password.toCharArray()
        );
        return new MongoClient(new ServerAddress("localhost",27017), Arrays.asList(credential));
    }
}

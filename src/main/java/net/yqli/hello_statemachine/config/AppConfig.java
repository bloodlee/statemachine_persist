package net.yqli.hello_statemachine.config;

import net.yqli.hello_statemachine.statemachine.Events;
import net.yqli.hello_statemachine.statemachine.States;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.data.mongodb.MongoDbPersistingStateMachineInterceptor;
import org.springframework.statemachine.data.mongodb.MongoDbStateMachineRepository;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;

@Configuration
public class AppConfig {

    @Bean
    public StateMachineRuntimePersister<States, Events, String> stateMachineRuntimePersister(
            MongoDbStateMachineRepository jpaStateMachineRepository) {
        return new MongoDbPersistingStateMachineInterceptor<>(jpaStateMachineRepository);
    }

}

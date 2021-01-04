package net.yqli.hello_statemachine.statemachine;

import net.yqli.hello_statemachine.SmStateMachineRepository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.data.mongodb.MongoDbPersistingStateMachineInterceptor;
import org.springframework.statemachine.data.mongodb.MongoDbStateMachineRepository;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import org.springframework.stereotype.Component;

@Component
public class SmBuilder {

    @Autowired
    private BeanFactory beanFactory;

    // @Autowired
    // private SmStateMachineRepository jpaStateMachineRepository;
    // private MongoDbStateMachineRepository jpaStateMachineRepository;

    @Autowired
    private StateMachineRuntimePersister<States, Events, String> stateMachineRuntimePersister;

    public StateMachine<States, Events> build(int jobId) throws Exception {
        StateMachineBuilder.Builder<States, Events> builder = StateMachineBuilder.builder();

        builder.configureConfiguration().withConfiguration()
                .machineId("job_state_machine_" + jobId)
                .autoStartup(false)
                .beanFactory(beanFactory);

        builder.configureConfiguration().withPersistence()
                .runtimePersister(stateMachineRuntimePersister);

        builder.configureStates().withStates()
                .initial(States.S1)
                .state(States.S2);

        builder.configureTransitions()
                .withExternal()
                .source(States.S1).target(States.S2).event(Events.E1)
                .and()
                .withExternal()
                .source(States.S2).target(States.S1).event(Events.E2);

        return builder.build();
    }


}

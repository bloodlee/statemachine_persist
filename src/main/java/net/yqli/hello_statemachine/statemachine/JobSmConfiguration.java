package net.yqli.hello_statemachine.statemachine;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@Slf4j
@Configuration
@EnableStateMachineFactory(name = "jobSmFactory")
public class JobSmConfiguration extends StateMachineConfigurerAdapter<States, Events> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
        super.configure(config);
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        states.withStates()
                .initial(States.S1)
                .state(States.S2);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions.withExternal()
                .source(States.S1).target(States.S2).event(Events.E1)
                .and()
                .withExternal()
                .source(States.S2).target(States.S1).event(Events.E2);
    }
}

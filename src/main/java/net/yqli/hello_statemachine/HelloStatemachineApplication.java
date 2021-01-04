package net.yqli.hello_statemachine;

import lombok.extern.slf4j.Slf4j;
import net.yqli.hello_statemachine.domain.Job;
import net.yqli.hello_statemachine.services.JobService;
import net.yqli.hello_statemachine.statemachine.Events;
import net.yqli.hello_statemachine.statemachine.SmBuilder;
import net.yqli.hello_statemachine.statemachine.States;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;

import java.util.Optional;

@Slf4j
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class HelloStatemachineApplication implements ApplicationRunner {

    @Autowired
    private SmBuilder smBuilder;

    @Autowired
    @Qualifier("jobSmFactory")
    private StateMachineFactory<States, Events> smFactory;

    @Autowired
    private JobService jobService;

    public static void main(String[] args) {
        SpringApplication.run(HelloStatemachineApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        /*
        log.info("Hello, this is the demo of spring boot state machine.");

        log.info("start the state machine");
        stateMachine.start();

        log.info("current state is {}", stateMachine.getState().getId());

        log.info("send event E1");
        log.info("E1 event was accepted? {}", stateMachine.sendEvent(Events.E1));

        log.info("current state is {}", stateMachine.getState().getId());

        log.info("send event E2");
        log.info("E2 event was accepted? {}", stateMachine.sendEvent(Events.E2));

        log.info("current state is {}", stateMachine.getState().getId());

         */

        // StateMachine<States, Events> sm = smBuilder.build(1);

        /*
        StateMachine<States, Events> sm = smFactory.getStateMachine("job1");

        log.info("Sm {} is created.", sm.getId());

        log.info("start the state machine");
        sm.start();

        log.info("current state is {}", sm.getState().getId());

        log.info("send Event E1");
        sm.sendEvent(Events.E1);

        log.info("current state is {}", sm.getState().getId());
         */

//        log.info("creating a new job...");
//        Job job = jobService.create(1L);
//        log.info("job {} is created.", job.getId());

        log.info("finding a job...");
        Job job = jobService.getById(1L).get();
        log.info("job {} was founded.", job.getId());

        StateMachine<States, Events> sm = jobService.getSm(job.getId());
        log.info("Current sm state is {}", sm.getState().getId());

//        log.info("Sending E1...");
//        jobService.sendS1(job.getId());

        log.info("Sending E2...");
        jobService.sendS2(job.getId());

        sm = jobService.getSm(job.getId());
        log.info("Current sm state is {}", sm.getState().getId());
    }
}

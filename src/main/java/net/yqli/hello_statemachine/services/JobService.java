package net.yqli.hello_statemachine.services;

import lombok.extern.slf4j.Slf4j;
import net.yqli.hello_statemachine.domain.Job;
import net.yqli.hello_statemachine.statemachine.Events;
import net.yqli.hello_statemachine.statemachine.SmMessageHeaders;
import net.yqli.hello_statemachine.statemachine.States;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class JobService {

    private final JobRepository jobRepository;

    @Autowired
    @Qualifier("jobSmFactory")
    private StateMachineFactory<States, Events> smFactory;

    @Autowired
    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Job create(Long id) {
        return this.jobRepository.save(new Job(id, States.S1));
    }

    public Optional<Job> getById(Long id) {
        return this.jobRepository.findById(id);
    }

    public StateMachine<States, Events> sendS1(Long jobId) {
        StateMachine<States, Events> sm = getSm(jobId);

        if (sm != null) {
            Message<Events> event = MessageBuilder.withPayload(Events.E1)
                    .setHeader(SmMessageHeaders.JOB_ID, jobId)
                    .build();

            sm.sendEvent(event);
        }

        return sm;
    }

    public StateMachine<States, Events> sendS2(Long jobId) {
        StateMachine<States, Events> sm = getSm(jobId);

        if (sm != null) {
            Message<Events> event = MessageBuilder.withPayload(Events.E2)
                    .setHeader(SmMessageHeaders.JOB_ID, jobId)
                    .build();

            sm.sendEvent(event);
        }

        return sm;
    }

    public StateMachine<States, Events> getSm(Long jobId) {
        Optional<Job> job = this.jobRepository.findById(jobId);

        if (job.isPresent()) {
            StateMachine<States, Events> sm = smFactory.getStateMachine(Long.toString(job.get().getId()));

            log.info("State machine {} was created.", sm.getId());

            sm.stop();

            log.info("State machine {} was created.", sm.getId());

            sm.getStateMachineAccessor()
                    .doWithAllRegions(sma -> {

                        sma.addStateMachineInterceptor(new StateMachineInterceptorAdapter<>() {
                            @Override
                            public void preStateChange(State<States, Events> state,
                                                       Message<Events> message,
                                                       Transition<States, Events> transition,
                                                       StateMachine<States, Events> stateMachine) {
                                Optional.ofNullable(message).ifPresent(msg -> {
                                    Optional.ofNullable(Long.class.cast(msg.getHeaders().getOrDefault(SmMessageHeaders.JOB_ID, -1L)))
                                            .ifPresent(jobId1 -> {
                                                Optional<Job> job1 = jobRepository.findById(jobId1);
                                                job1.ifPresent(theJob -> {
                                                    theJob.setState(state.getId());
                                                    jobRepository.save(theJob);
                                                });
                                            });
                                });
                            }
                        });
                        sma.resetStateMachine(new DefaultStateMachineContext<>(job.get().getState(), null, null, null, null, Long.toString(job.get().getId())));
                    });

            sm.start();
            log.info("State machine {} was created.", sm.getId());

            return sm;
        } else {
            return null;
        }
    }
}

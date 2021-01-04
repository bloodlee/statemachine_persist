package net.yqli.hello_statemachine;

import org.springframework.statemachine.data.mongodb.MongoDbStateMachineRepository;
import org.springframework.stereotype.Repository;

public interface SmStateMachineRepository extends MongoDbStateMachineRepository  {
}

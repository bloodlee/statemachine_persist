package net.yqli.hello_statemachine.services;

import net.yqli.hello_statemachine.domain.Job;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends MongoRepository<Job, Long> {


}

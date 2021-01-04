package net.yqli.hello_statemachine.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.yqli.hello_statemachine.statemachine.States;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@Document("jobs")
@NoArgsConstructor
public class Job {

    @Id
    private Long id;

    private String state;

    public Job(Long id, States state) {
        this.id = id;
        setState(state);
    }

    public States getState() {
        return States.valueOf(this.state);
    }

    public void setState(States state) {
        this.state = state.name();
    }
}

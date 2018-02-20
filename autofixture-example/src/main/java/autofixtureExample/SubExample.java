package autofixtureExample;

import autofixture.Fixture;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Fixture
public class SubExample {
    private int id;
    private String anotherDescription;
}

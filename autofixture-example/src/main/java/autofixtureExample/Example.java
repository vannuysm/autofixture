package autofixtureExample;

import autofixture.Fixture;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Fixture
public class Example {
    private Integer id;
    private String description;
    private Boolean isExample;
}
package autofixtureExample;

import autofixture.Fixture;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Fixture
public class Example {
    private Integer id;
    private String description;
    private Boolean isExample;
    private SubExample subExample;
    private List<SubExample> subExampleList;
}
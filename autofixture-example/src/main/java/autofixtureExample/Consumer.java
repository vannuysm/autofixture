package autofixtureExample;

import java.util.List;

public class Consumer {
    public List<Example> generateExample() {
        ExampleFixture fixture = new ExampleFixture();
        return fixture
                .withId(782)
                .createMany(5);
    }
}

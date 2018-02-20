package autofixtureExample;

import java.util.List;

public class Consumer {
    public List<Example> generateExample() {
        ExampleFixture exampleFixture = new ExampleFixture();
        SubExampleFixture subExampleFixture = new SubExampleFixture();

        return exampleFixture
                .withId(782)
                .withSubExample(() -> subExampleFixture.create())
                .withSubExampleList(() -> subExampleFixture.createMany(4))
                .createMany(5);
    }
}

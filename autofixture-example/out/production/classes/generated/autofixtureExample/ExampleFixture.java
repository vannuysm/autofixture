package autofixtureExample;

import autofixture.DataFixture;
import java.lang.Boolean;
import java.lang.IllegalAccessException;
import java.lang.InstantiationException;
import java.lang.Integer;
import java.lang.String;

public class ExampleFixture extends DataFixture<Example> {
  public ExampleFixture() throws IllegalAccessException, InstantiationException {
    super(Example.class);
  }

  public ExampleFixture withId(Integer value) {
    instance.setId(value);
    mappedSetters.add("setId");
    return this;
  }

  public ExampleFixture withDescription(String value) {
    instance.setDescription(value);
    mappedSetters.add("setDescription");
    return this;
  }

  public ExampleFixture withIsExample(Boolean value) {
    instance.setIsExample(value);
    mappedSetters.add("setIsExample");
    return this;
  }
}

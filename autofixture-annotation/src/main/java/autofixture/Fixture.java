package autofixture;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(TYPE)
@Retention(SOURCE)
public @interface Fixture {
    /**
     * Name of the fixture class.
     *
     * Default for {@code @autofixture.DataFixture} on types and constructors: {@code (TypeName)autofixture.Fixture}.
     *
     * @return Name of the fixture class that will be generated (or if it already exists, will be filled with fixture elements).
     */
    String fixtureClassName() default "";
}

package autofixture;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class MethodDataGenerator {
    private final Random random = ThreadLocalRandom.current();

    public Object generateValue(Method setter) {
        Class<?> valueType = setter.getParameterTypes()[0];

        if (valueType.equals(String.class)) {
            return UUID.randomUUID().toString();
        }

        if (isBoolean(valueType)) {
            return random.nextBoolean();
        }

        if (isFloat(valueType)) {
            return random.nextFloat();
        }

        if (isDouble(valueType)) {
            return random.nextDouble();
        }

        if (isInteger(valueType)) {
            return random.nextInt();
        }

        if (isLong(valueType)) {
            return random.nextLong();
        }

        return null;
    }

    private boolean isBoolean(Class<?> fieldType) {
        return fieldType.equals(Boolean.class) || fieldType.equals(Boolean.TYPE);
    }

    private boolean isFloat(Class<?> fieldType) {
        return fieldType.equals(Float.class) || fieldType.equals(Float.TYPE);
    }

    private boolean isDouble(Class<?> fieldType) {
        return fieldType.equals(Double.class) || fieldType.equals(Double.TYPE);
    }

    private boolean isInteger(Class<?> fieldType) {
        return fieldType.equals(Integer.class) || fieldType.equals(Integer.TYPE);
    }

    private boolean isLong(Class<?> fieldType) {
        return fieldType.equals(Long.class) || fieldType.equals(Long.TYPE);
    }
}

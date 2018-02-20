package autofixture;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class DataFixture<T> {
    private final MethodDataGenerator methodDataGenerator = new MethodDataGenerator();
    private final Class<T> fixtureClass;

    protected HashMap<String, Supplier<Object>> valueMap = new HashMap<>();

    protected DataFixture(Class<T> fixtureClass) {
        this.fixtureClass = fixtureClass;
    }

    public T create() {
        try {
            T instance = fixtureClass.newInstance();
            populate(instance);
            return instance;
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
    }

    public List<T> createMany(int size) {
        return IntStream
                .range(0, size)
                .mapToObj(i -> create())
                .collect(Collectors.toList());
    }

    private void populate(T instance) {
        List<Method> setters = Arrays.stream(instance.getClass().getMethods())
                .filter(this::isSetter)
                .collect(Collectors.toList());

        for (Method setter : setters) {
            Object value;

            if (valueMap.containsKey(setter.getName())) {
                value = valueMap.get(setter.getName()).get();
            } else {
                value = methodDataGenerator.generateValue(setter);
            }

            if (value != null) try {
                setter.invoke(instance, value);
            } catch (InvocationTargetException | IllegalAccessException ignored) { }
        }
    }

    private boolean isSetter(Method method) {
        return method.getName().startsWith("set")
                && Modifier.isPublic(method.getModifiers())
                && method.getParameters().length == 1;
    }
}

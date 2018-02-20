package autofixture.processor;

import com.squareup.javapoet.TypeName;

import javax.lang.model.element.VariableElement;

public class FixtureField {
    private final VariableElement field;

    public FixtureField(VariableElement field) {
        this.field = field;
    }

    public String getName() {
        return field.getSimpleName().toString();
    }

    public String getPascalCaseName() {
        return Character.toUpperCase(getName().charAt(0)) + getName().substring(1);
    }

    public TypeName getTypeName() {
        return TypeName.get(field.asType());
    }
}

package autofixture.processor;

import autofixture.DataFixture;
import autofixture.Fixture;
import com.squareup.javapoet.*;

import javax.annotation.processing.Filer;
import javax.lang.model.element.*;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class FixtureGenerator {
    private final TypeElement annotatedClassElement;
    private final TypeName annotatedClassTypeName;
    private final String fixtureClassName;
    private final List<? extends Element> annoatedClassEnclosedElements;

    public FixtureGenerator(TypeElement annotatedClassElement) {
        this.annotatedClassElement = annotatedClassElement;
        this.annoatedClassEnclosedElements = annotatedClassElement.getEnclosedElements();
        this.annotatedClassTypeName = TypeName.get(annotatedClassElement.asType());

        Fixture annotation = annotatedClassElement.getAnnotation(Fixture.class);
        if (annotation.fixtureClassName().isEmpty()) {
            fixtureClassName = annotatedClassElement.getSimpleName().toString() + "Fixture";
        } else {
            fixtureClassName = annotation.fixtureClassName();
        }
    }

    public void generate(Elements elementUtils, Filer filer) throws IOException {
        String packageName = elementUtils.getPackageOf(annotatedClassElement).getQualifiedName().toString();

        TypeSpec.Builder fixtureBuilder = TypeSpec.classBuilder(this.fixtureClassName)
                .addModifiers(Modifier.PUBLIC)
                .superclass(ParameterizedTypeName.get(ClassName.get(DataFixture.class), annotatedClassTypeName))
                .addMethod(generateConstructor());

        List<VariableElement> fields = ElementFilter.fieldsIn(annoatedClassEnclosedElements).stream()
                .filter(e -> !e.getModifiers().contains(Modifier.FINAL))
                .collect(Collectors.toList());

        if (!fields.isEmpty()) {
            for (VariableElement field : fields) {
                FixtureField fixtureField = new FixtureField(field);

                boolean hasSetter = ElementFilter.methodsIn(annoatedClassEnclosedElements).stream().anyMatch(e -> isSetter(e, fixtureField));
                if (hasSetter) {
                    fixtureBuilder.addMethod(generateWith(fixtureField, packageName));
                }
            }
        }

        JavaFile javaFile = JavaFile.builder(packageName, fixtureBuilder.build()).build();
        javaFile.writeTo(filer);
    }

    private boolean isSetter(ExecutableElement element, FixtureField fixtureField) {
        return element.getSimpleName().toString().equals("set" + fixtureField.getPascalCaseName())
                && element.getModifiers().contains(Modifier.PUBLIC)
                && element.getParameters().size() == 1;
    }

    private MethodSpec generateWith(FixtureField field, String packageName) {
        String setterName = "set" + field.getPascalCaseName();

        return MethodSpec.methodBuilder("with" + field.getPascalCaseName())
                .addModifiers(Modifier.PUBLIC)
                .addParameter(field.getTypeName(), "value")
                .addCode(CodeBlock.builder()
                        .addStatement("valueMap.put($S, value)", setterName)
                        .addStatement("return this")
                        .build())
                .returns(ClassName.get(packageName, this.fixtureClassName))
                .build();
    }

    private MethodSpec generateConstructor() {
        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addStatement("super($T.class)", annotatedClassTypeName)
                .build();
    }
}

package com.example.starter.base;

import jakarta.inject.Named;
import jakarta.inject.Singleton;

import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.router.Route;
import com.vaadin.quarkus.QuarkusInstantiatorFactory;
import com.vaadin.quarkus.annotation.VaadinServiceEnabled;
import com.vaadin.testbench.unit.quarkus.QuarkusUIUnitTest;
import io.quarkus.arc.Unremovable;
import io.quarkus.arc.processor.AnnotationsTransformer;
import io.quarkus.test.component.QuarkusComponentTest;
import org.jboss.jandex.AnnotationTarget;
import org.jboss.jandex.ClassInfo;
import org.jboss.jandex.DotName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Unfortunately QuarkusComponentTest is not working well with UI Unit Testing for several reasons:
 * - All components required by test needs to be explicitly registered ({@link QuarkusComponentTest#value()}),
 * - Vaadin Quarkus extension is not applied, so @{@link Route} is not considered a bean defining annotation.
 * - Even if manually registered, views are removed from Arc container because they are considered "unused" bean.
 * <p>
 * Trying to add the @{@link Unremovable} annotation with the {@link AnnotationsTransformer} does not work, because
 * {@link io.quarkus.test.component.QuarkusComponentTestExtension} looks for it on the original class, not taking into
 * account the transformation.
 * In addition, it is not possible to disable the removal (e.g. `quarkus.arc.remove-unused-beans=false` property)
 * because setting value is hard-coded.
 * <p>
 * Adding @{@link Unremovable} annotation to the view makes the test work.
 */
@QuarkusComponentTest(value = {
        VaadinServiceEnabled.class,
        Route.class,
        QuarkusInstantiatorFactory.class,
        MainView.class,
        GreetService.class
},
        annotationsTransformers = MainViewComponentTest.Annotator.class,
        addNestedClassesAsComponents = false
)
@Disabled("Work only if MainView is annotated with @Unremovable")
class MainViewComponentTest extends QuarkusUIUnitTest {

    @Test
    void accessView() {
        MainView mainView = navigate(MainView.class);
        Assertions.assertNotNull(mainView);
    }

    @Test
    void useInjectedService() {
        AnnotationsTransformer.appliedToClass()
                .whenClass(info -> info.hasAnnotation(DotName.createSimple(Route.class)))
                .thenTransform(t -> t.add(DotName.createSimple(Named.class)));
        MainView mainView = navigate(MainView.class);
        test(mainView.textField).setValue("User");
        test(mainView.button).click();
        Assertions.assertEquals("Hello User", test($(Paragraph.class, mainView).single()).getText());
    }


    public static class Annotator implements AnnotationsTransformer {
        @Override
        public boolean appliesTo(AnnotationTarget.Kind kind) {
            return AnnotationTarget.Kind.CLASS == kind;
        }

        @Override
        public void transform(TransformationContext context) {
            ClassInfo c = context.getTarget().asClass();
            if (c.hasAnnotation(DotName.createSimple(Route.class))) {
                context.transform().add(Singleton.class).done();
            }
        }
    }

}

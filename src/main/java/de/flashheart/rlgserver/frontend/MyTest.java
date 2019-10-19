package de.flashheart.rlgserver.frontend;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;

/**
 * A Designer generated component for the my-test template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("my-test")
@JsModule("./my-test.js")
@Route("mytest")
public class MyTest extends PolymerTemplate<MyTest.MyTestModel> {

    @Id("vaadinVerticalLayout")
    private VerticalLayout vaadinVerticalLayout;

    /**
     * Creates a new MyTest.
     */
    public MyTest() {
        // You can initialise any data required for the connected UI components here.
    }

    /**
     * This model binds properties between MyTest and my-test
     */
    public interface MyTestModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}

package com.example.talanta.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("admin-signup")
@AnonymousAllowed
public class AdminSignupView extends VerticalLayout {

    public AdminSignupView() {
        // Fullscreen background image from correct folder
        getStyle()
            .set("background-image", "url('images/SignupLogin.jpg')")
            .set("background-size", "cover")
            .set("background-position", "center")
            .set("background-repeat", "no-repeat")
            .set("height", "100vh")
            .set("margin", "0");

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        // Outer container
        Div container = new Div();
        container.getStyle()
            .set("display", "flex")
            .set("justify-content", "center")
            .set("align-items", "center")
            .set("height", "100%");

        // Form card
        Div formContainer = new Div();
        formContainer.getStyle()
            .set("background", "rgba(255, 255, 255, 0.1)") // Semi-transparent background
            .set("padding", "30px")
            .set("border-radius", "12px")
            .set("box-shadow", "0 4px 20px rgba(0, 0, 0, 0.3)")
            .set("width", "400px") // Reduced width for better layout
            .set("max-width", "90%");

        // Header
        Label header = new Label("Sign Up");
        header.getStyle()
            .set("font-size", "28px") // Larger font size for emphasis
            .set("font-weight", "bold") // Bold font
            .set("color", "#E65100") // Orange color for header
            .set("margin-bottom", "1em");

        // Add to container
        formContainer.add(header);
        formContainer.add(createLabeledField("First Name", "Enter First Name"));
        formContainer.add(createLabeledField("Last Name", "Enter Last Name"));
        formContainer.add(createLabeledField("Email", "Enter your Email"));
        formContainer.add(createLabeledPasswordField("Password", "Enter Password"));

        // Button
        Button signUpButton = new Button("Sign Up", event ->
                Notification.show("Admin registered (UI only)", 3000, Notification.Position.TOP_CENTER)
        );
        signUpButton.getStyle()
            .set("background-color", "#ff6600") // Orange button color
            .set("color", "#ffffff") // White text color
            .set("font-weight", "bold")
            .set("width", "100%")
            .set("height", "40px")
            .set("border-radius", "8px")
            .set("margin-top", "20px");

        formContainer.add(signUpButton);
        container.add(formContainer);
        add(container);
    }

    private VerticalLayout createLabeledField(String labelText, String placeholder) {
        Label label = new Label(labelText);
        label.getStyle()
            .set("font-weight", "bold")
            .set("font-size", "14px")
            .set("color", "#ffffff") // White text color for label
            .set("margin-top", "10px");

        TextField field = new TextField();
        field.setPlaceholder(placeholder);
        field.setWidth("95%"); // Reduced width for better layout

        field.getStyle()
            .set("background-color", "transparent") // Transparent background
            .set("color", "#ffffff") // White text color for input text
            .set("border-color", "#ffffff") // White border color
            .set("border-width", "2px") // Thicker border for visibility
            .set("border-radius", "5px") // Rounded corners for input fields
            .set("padding-left", "10px") // Padding inside the input field
            .set("line-height", "1.6"); // Line height for better readability

        VerticalLayout layout = new VerticalLayout(label, field);
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.setWidthFull();
        return layout;
    }

    private VerticalLayout createLabeledPasswordField(String labelText, String placeholder) {
        Label label = new Label(labelText);
        label.getStyle()
            .set("font-weight", "bold")
            .set("font-size", "14px")
            .set("color", "#ffffff") // White text color for label
            .set("margin-top", "10px");

        PasswordField field = new PasswordField();
        field.setPlaceholder(placeholder);
        field.setWidth("95%"); // Reduced width for better layout

        field.getStyle()
            .set("background-color", "#ffffff") // Transparent background
            .set("border-width", "2px") // Thicker border for visibility
            .set("border-radius", "5px") // Rounded corners for input fields
            .set("padding-left", "10px") // Padding inside the input field
            .set("line-height", "1.6"); // Line height for better readability

        VerticalLayout layout = new VerticalLayout(label, field);
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.setWidthFull();

        return layout;
    }
}

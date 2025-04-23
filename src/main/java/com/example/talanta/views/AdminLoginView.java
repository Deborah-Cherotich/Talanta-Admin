//package com.example.talanta.views;
//
//import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.html.Div;
//import com.vaadin.flow.component.html.Label;
//import com.vaadin.flow.component.notification.Notification;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.component.textfield.PasswordField;
//import com.vaadin.flow.component.textfield.TextField;
//import com.vaadin.flow.router.Route;
//import com.vaadin.flow.server.auth.AnonymousAllowed;
//
//@Route("")
//@AnonymousAllowed
//public class AdminLoginView extends VerticalLayout {
//
//    public AdminLoginView() {
//        // Fullscreen background image
//        getStyle()
//            .set("background-image", "url('images/SignupLogin.jpg')")
//            .set("background-size", "cover")
//            .set("background-position", "center")
//            .set("background-repeat", "no-repeat")
//            .set("height", "100vh")
//            .set("margin", "0");
//
//        setSizeFull();
//        setAlignItems(Alignment.CENTER);
//        setJustifyContentMode(JustifyContentMode.CENTER);
//
//        // Outer container
//        Div container = new Div();
//        container.getStyle()
//            .set("display", "flex")
//            .set("justify-content", "center")
//            .set("align-items", "center")
//            .set("height", "100%");
//
//        // Form card
//        Div formContainer = new Div();
//        formContainer.getStyle()
//            .set("background", "rgba(255, 255, 255, 0.1)")
//            .set("padding", "30px")
//            .set("border-radius", "12px")
//            .set("box-shadow", "0 4px 20px rgba(0, 0, 0, 0.3)")
//            .set("width", "400px")
//            .set("max-width", "90%");
//
//        // Header
//        Label header = new Label("Admin Login");
//        header.getStyle()
//            .set("font-size", "28px")
//            .set("font-weight", "bold")
//            .set("color", "#E65100")
//            .set("margin-bottom", "1em");
//
//        // Add components
//        formContainer.add(header);
//        formContainer.add(createLabeledField("Email", "Enter your Email"));
//        formContainer.add(createLabeledPasswordField("Password", "Enter Password"));
//
//        // Login button
//        Button loginButton = new Button("Login", event ->
//                Notification.show("Logged in (UI only)", 3000, Notification.Position.TOP_CENTER)
//        );
//        loginButton.getStyle()
//            .set("background-color", "#ff6600")
//            .set("color", "#ffffff")
//            .set("font-weight", "bold")
//            .set("width", "100%")
//            .set("height", "40px")
//            .set("border-radius", "8px")
//            .set("margin-top", "20px");
//
//        formContainer.add(loginButton);
//        container.add(formContainer);
//        add(container);
//    }
//
//    private VerticalLayout createLabeledField(String labelText, String placeholder) {
//        Label label = new Label(labelText);
//        label.getStyle()
//            .set("font-weight", "bold")
//            .set("font-size", "14px")
//            .set("color", "#ffffff")
//            .set("margin-top", "10px");
//
//        TextField field = new TextField();
//        field.setPlaceholder(placeholder);
//        field.setWidth("95%");
//
//        field.getStyle()
//            .set("background-color", "#ffffff")
//            .set("color", "#ffffff")
//            .set("border-color", "#ffffff")
//            .set("border-width", "2px")
//            .set("border-radius", "5px")
//            .set("padding-left", "10px")
//            .set("line-height", "1.6");
//
//        VerticalLayout layout = new VerticalLayout(label, field);
//        layout.setPadding(false);
//        layout.setSpacing(false);
//        layout.setWidthFull();
//        return layout;
//    }
//
//    private VerticalLayout createLabeledPasswordField(String labelText, String placeholder) {
//        Label label = new Label(labelText);
//        label.getStyle()
//            .set("font-weight", "bold")
//            .set("font-size", "14px")
//            .set("color", "#ffffff")
//            .set("margin-top", "10px");
//
//        PasswordField field = new PasswordField();
//        field.setPlaceholder(placeholder);
//        field.setWidth("95%");
//
//        field.getStyle()
//            .set("background-color", "#ffffff")
//            .set("color", "#ffffff")
//            .set("border-color", "#ffffff")
//            .set("border-width", "2px")
//            .set("border-radius", "5px")
//            .set("padding-left", "10px")
//            .set("line-height", "1.6");
//
//        VerticalLayout layout = new VerticalLayout(label, field);
//        layout.setPadding(false);
//        layout.setSpacing(false);
//        layout.setWidthFull();
//        return layout;
//    }
//}


package com.example.talanta.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("")
@AnonymousAllowed
public class AdminLoginView extends VerticalLayout {

    // Store fields as class members for access in the button click handler
    private TextField emailField;
    private PasswordField passwordField;

    public AdminLoginView() {
        // Fullscreen background image
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
            .set("background", "rgba(255, 255, 255, 0.1)")
            .set("padding", "30px")
            .set("border-radius", "12px")
            .set("box-shadow", "0 4px 20px rgba(0, 0, 0, 0.3)")
            .set("width", "400px")
            .set("max-width", "90%");

        // Header
        Label header = new Label("Admin Login");
        header.getStyle()
            .set("font-size", "28px")
            .set("font-weight", "bold")
            .set("color", "#E65100")
            .set("margin-bottom", "1em");

        // Initialize fields
        emailField = new TextField();
        emailField.setPlaceholder("Enter your Email");
        emailField.setWidth("95%");
        emailField.getStyle()
            .set("background-color", "#ffffff")
            .set("color", "#000000")
            .set("border-color", "#ffffff")
            .set("border-width", "2px")
            .set("border-radius", "5px")
            .set("padding-left", "10px")
            .set("line-height", "1.6");

        passwordField = new PasswordField();
        passwordField.setPlaceholder("Enter Password");
        passwordField.setWidth("95%");
        passwordField.getStyle()
            .set("background-color", "#ffffff")
            .set("color", "#000000")
            .set("border-color", "#ffffff")
            .set("border-width", "2px")
            .set("border-radius", "5px")
            .set("padding-left", "10px")
            .set("line-height", "1.6");

        // Add components
        formContainer.add(header);
        formContainer.add(createLabeledField("Email", emailField));
        formContainer.add(createLabeledField("Password", passwordField));

        // Login button
        Button loginButton = new Button("Login", event -> {
            String email = emailField.getValue();
            String password = passwordField.getValue();

            // Simple check (replace with real authentication as needed)
            if (!email.isEmpty() && !password.isEmpty()) {
                UI.getCurrent().navigate("Admindashboard");
            } else {
                Notification.show("Please enter email and password", 3000, Notification.Position.TOP_CENTER);
            }
        });
        loginButton.getStyle()
            .set("background-color", "#ff6600")
            .set("color", "#ffffff")
            .set("font-weight", "bold")
            .set("width", "100%")
            .set("height", "40px")
            .set("border-radius", "8px")
            .set("margin-top", "20px");

        formContainer.add(loginButton);
        container.add(formContainer);
        add(container);
    }

    // Helper to add label and field
    private VerticalLayout createLabeledField(String labelText, TextField field) {
        Label label = new Label(labelText);
        label.getStyle()
            .set("font-weight", "bold")
            .set("font-size", "14px")
            .set("color", "#ffffff")
            .set("margin-top", "10px");

        VerticalLayout layout = new VerticalLayout(label, field);
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.setWidthFull();
        return layout;
    }

    // Overload for PasswordField
    private VerticalLayout createLabeledField(String labelText, PasswordField field) {
        Label label = new Label(labelText);
        label.getStyle()
            .set("font-weight", "bold")
            .set("font-size", "14px")
            .set("color", "#ffffff")
            .set("margin-top", "10px");

        VerticalLayout layout = new VerticalLayout(label, field);
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.setWidthFull();
        return layout;
    }
}

package com.example.talanta.views;

import com.example.talanta.ConfigUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;

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
        Button loginButton = new Button("Login", event -> authenticateUser(
                emailField.getValue(),
                passwordField.getValue()
        ));
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

    private void authenticateUser(String email, String password) {
        try {
            String apiUrl = ConfigUtil.BASE_URL + "/login";  // Base URL + Endpoint
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            JSONObject json = new JSONObject();
            json.put("email", email);
            json.put("password", password);

            // Send JSON request body
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = json.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Read Response
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }

                    // Parse the response JSON
                    JSONObject jsonResponse = new JSONObject(response.toString());
                    if (jsonResponse.has("id") && jsonResponse.has("token")) {
                        String userId = jsonResponse.getString("id");  // Extract ID
                        String token = jsonResponse.getString("token");
                        String firstName = jsonResponse.getString("firstName");
                        String lastName = jsonResponse.getString("lastName");
                        String fullName = firstName + " " + lastName;
                        // Store ID in localStorage using Vaadin's JavaScript API
                        UI.getCurrent().getPage().executeJs("localStorage.setItem('userId', '" + userId + "');");
                        UI.getCurrent().getPage().executeJs("localStorage.setItem('token', '" + token + "');");
                        UI.getCurrent().getPage().executeJs("localStorage.setItem('fullName', '" + fullName + "');");

                        showCustomNotification("Login successful!");
                        // Redirect to VerifyAccountView after verification
                        UI.getCurrent().getPage().executeJs(
                                "setTimeout(() => { window.location.href='" + AdminDashboardView.class.getAnnotation(Route.class).value() + "'; }, 5000);"
                        );

                    } else {
                        showCustomNotification("Login successful, but no ID received.");
                    }
                }
            } else {
                showCustomNotification("Login failed. Please try again.");
            }
        } catch (Exception e) {
            Notification.show("Error: " + e.getMessage());
        }
    }

    private void showCustomNotification(String message) {
        Notification notification = new Notification();
        notification.setDuration(4000); // Show for 5 seconds
        notification.setPosition(Notification.Position.TOP_END); // Top-right corner

        // Remove the default white background from the notification's overlay
        notification.getElement().executeJs(
                "this.shadowRoot.querySelector('[part=\"overlay\"]').style.background = 'none';"
                + "this.shadowRoot.querySelector('[part=\"overlay\"]').style.boxShadow = 'none';"
        );

        // Checkmark icon
        Icon checkIcon = new Icon(VaadinIcon.CHECK_CIRCLE);
        checkIcon.getStyle().set("color", "white");
        checkIcon.getStyle().set("width", "24px").set("height", "24px");

        // Message text
        Span text = new Span(message);
        text.getStyle().set("color", "white");
        text.getStyle().set("font-size", "14px");

        // Layout with icon and message
        HorizontalLayout layout = new HorizontalLayout(checkIcon, text);
        layout.setPadding(true);
        layout.setSpacing(true);
        layout.getStyle()
                .set("background-color", "orange") // Only this background should be visible
                .set("border-radius", "10px")
                .set("padding", "10px")
                .set("width", "400px")
                .set("height", "100px")
                .set("display", "flex")
                .set("align-items", "center");

        notification.add(layout);
        notification.open();
    }
}

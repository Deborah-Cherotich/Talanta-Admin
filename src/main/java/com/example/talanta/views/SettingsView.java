////package com.example.talanta.views;
////
////import com.vaadin.flow.component.Component;
////import com.vaadin.flow.component.UI;
////import com.vaadin.flow.component.button.Button;
////import com.vaadin.flow.component.button.ButtonVariant;
////import com.vaadin.flow.component.checkbox.Checkbox;
////import com.vaadin.flow.component.checkbox.CheckboxGroup;
////import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
////import com.vaadin.flow.component.combobox.ComboBox;
////import com.vaadin.flow.component.formlayout.FormLayout;
////import com.vaadin.flow.component.html.*;
////import com.vaadin.flow.component.icon.Icon;
////import com.vaadin.flow.component.icon.VaadinIcon;
////import com.vaadin.flow.component.notification.Notification;
////import com.vaadin.flow.component.notification.NotificationVariant;
////import com.vaadin.flow.component.orderedlayout.FlexComponent;
////import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
////import com.vaadin.flow.component.orderedlayout.VerticalLayout;
////import com.vaadin.flow.component.textfield.IntegerField;
////import com.vaadin.flow.component.textfield.PasswordField;
////import com.vaadin.flow.component.textfield.TextField;
////import com.vaadin.flow.component.upload.Upload;
////import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
////import com.vaadin.flow.router.PageTitle;
////import com.vaadin.flow.router.Route;
////import com.vaadin.flow.spring.annotation.SpringComponent;
////import org.springframework.beans.factory.config.ConfigurableBeanFactory;
////import org.springframework.context.annotation.Scope;
////
////import java.util.Arrays;
////import java.util.Set;
////import java.util.TimeZone;
////
////@PageTitle("Settings")
////@Route("settings")
////@SpringComponent
////@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
////public class SettingsView extends VerticalLayout {
////
////    private final String primaryColor = "#E65100"; // Orange theme color
////    private final String primaryContrastColor = "#FFFFFF"; // White text for primary buttons
////
////    // Example fields - replace with actual settings loading/saving logic
////    private TextField siteNameField;
////    private ComboBox<String> languageComboBox;
////    private ComboBox<String> timezoneComboBox;
////    private Checkbox maintenanceModeCheckbox;
////    private Upload logoUpload;
////    private MemoryBuffer logoBuffer;
////
////    private IntegerField minPasswordLengthField;
////    private CheckboxGroup<String> passwordComplexityCheckboxGroup;
////    private Checkbox enforce2FACheckbox;
////    private IntegerField sessionTimeoutField;
////
////    private TextField smtpHostField;
////    private IntegerField smtpPortField;
////    private TextField smtpUsernameField;
////    private PasswordField smtpPasswordField;
////    private ComboBox<String> smtpEncryptionComboBox;
////
////    private Checkbox notifyNewUserCheckbox;
////    private Checkbox notifyPaymentCheckbox;
////    private Checkbox notifyTestCompletionCheckbox;
////
////
////    public SettingsView() {
////        setSizeFull();
////        setPadding(false);
////        setSpacing(false);
////
////        HorizontalLayout layout = new HorizontalLayout();
////        layout.setSizeFull();
////        layout.setSpacing(false);
////
////        VerticalLayout sidebar = createSidebar();
////        VerticalLayout mainContent = createMainContent();
////
////        layout.add(sidebar, mainContent);
////        layout.setFlexGrow(1, mainContent);
////
////        add(layout);
////
////        // Load current settings (placeholder)
////        loadSettingsData();
////    }
////
////    // --- Sidebar (Reused and adapted from UsersView) ---
////    private VerticalLayout createSidebar() {
////        VerticalLayout sidebar = new VerticalLayout();
////        sidebar.setWidth("240px");
////        sidebar.setHeightFull();
////        sidebar.getStyle()
////                .set("background", "#F4F4F4")
////                .set("padding", "0");
////        sidebar.setSpacing(false);
////        sidebar.setAlignItems(FlexComponent.Alignment.STRETCH);
////
////        Image logo = new Image("images/2.png", "Talanta Logo");
////        logo.setWidth("150px");
////        logo.setHeight("auto");
////        Div logoWrapper = new Div(logo);
////        logoWrapper.getStyle()
////            .set("text-align", "center")
////            .set("padding", "15px 0");
////        sidebar.add(logoWrapper);
////
////        // Add Navigation Items - Mark "Settings" as active
////        sidebar.add(
////                createNavItem("Dashboard", VaadinIcon.DASHBOARD, "Admindashboard", false),
////                createNavItem("Users", VaadinIcon.USERS, "users", false),
////                createNavItem("Personality Tests", VaadinIcon.CLIPBOARD_TEXT, "personality-tests", false),
////                createNavItem("Courses & Careers", VaadinIcon.BOOK, "courses-careers", false),
////                createNavItem("Institutions", VaadinIcon.ACADEMY_CAP, "institutions", false),
////                createNavItem("Payments", VaadinIcon.CREDIT_CARD, "payments", false),
////                createNavItem("Reports", VaadinIcon.CHART, "reports", false),
////                createNavItem("Content", VaadinIcon.FILE_TEXT, "content", false),
////                createNavItem("Notifications", VaadinIcon.BELL, "notifications", false),
////                createNavItem("Settings", VaadinIcon.COG, "settings", true) // Active
////        );
////
////        return sidebar;
////    }
////
////    private HorizontalLayout createNavItem(String title, VaadinIcon icon, String route, boolean isActive) {
////        Icon menuIcon = icon.create();
////        menuIcon.setColor(isActive ? primaryColor : "#6C757D");
////        menuIcon.getStyle().set("margin-right", "15px");
////
////        Span label = new Span(title);
////        label.getStyle()
////                .set("font-weight", isActive ? "600" : "400")
////                .set("color", isActive ? primaryColor : "#343A40");
////
////        HorizontalLayout nav = new HorizontalLayout(menuIcon, label);
////        nav.setAlignItems(FlexComponent.Alignment.CENTER);
////        nav.getStyle()
////                .set("padding", "12px 20px")
////                .set("cursor", "pointer")
////                .set("transition", "background-color 0.2s ease, color 0.2s ease");
////
////        if (isActive) {
////            nav.getStyle()
////                    .set("background-color", "#FDEEE2")
////                    .set("border-left", "4px solid " + primaryColor);
////             nav.getStyle().set("padding-left","16px");
////        } else {
////            nav.getElement().addEventListener("mouseover", e -> {
////                 nav.getStyle().set("background-color", "#E9ECEF");
////            });
////            nav.getElement().addEventListener("mouseout", e -> {
////                 nav.getStyle().remove("background-color");
////            });
////        }
////
////        nav.addClickListener(e -> UI.getCurrent().navigate(route));
////        return nav;
////    }
////    // --- End Sidebar ---
////
////    // --- Main Content Area ---
////    private VerticalLayout createMainContent() {
////        VerticalLayout main = new VerticalLayout();
////        main.setSizeFull(); // Allow vertical scrolling if content overflows
////        main.setPadding(true);
////        main.setSpacing(true); // Add space between sections
////
////        // Header
////        H2 title = new H2("Settings");
////        title.getStyle()
////            .set("color", primaryColor)
////            .set("margin", "0 0 10px 0");
////        main.add(title);
////
////        // Add setting sections
////        main.add(createGeneralSettingsSection());
////        main.add(new Hr()); // Divider
////        main.add(createSecuritySettingsSection());
////        main.add(new Hr()); // Divider
////        main.add(createEmailSettingsSection());
////        main.add(new Hr()); // Divider
////        main.add(createNotificationSettingsSection());
////
////        // Add a final save button for all settings, or rely on section saves
////        // Button saveAllButton = new Button("Save All Settings");
////        // saveAllButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
////        // saveAllButton.getStyle().set("background-color", primaryColor).set("color", primaryContrastColor);
////        // saveAllButton.addClickListener(e -> saveAllSettings());
////        // main.add(saveAllButton);
////
////        return main;
////    }
////
////    // --- Settings Sections ---
////
////    private Component createGeneralSettingsSection() {
////        VerticalLayout sectionLayout = new VerticalLayout();
////        sectionLayout.setWidthFull();
////        sectionLayout.setPadding(false);
////        sectionLayout.setSpacing(false); // No extra space within the section itself
////
////        H3 sectionTitle = new H3("General Settings");
////        sectionTitle.getStyle().set("color", primaryColor).set("margin-top", "0");
////
////        siteNameField = new TextField("Site Name");
////        languageComboBox = new ComboBox<>("Default Language", "English", "Spanish", "French"); // Example languages
////        timezoneComboBox = new ComboBox<>("Timezone");
////        timezoneComboBox.setItems(TimeZone.getAvailableIDs()); // Populate with available timezone IDs
////        maintenanceModeCheckbox = new Checkbox("Enable Maintenance Mode");
////
////        // Logo Upload
////        logoBuffer = new MemoryBuffer();
////        logoUpload = new Upload(logoBuffer);
////        logoUpload.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif");
////        logoUpload.setMaxFiles(1);
////        logoUpload.setDropLabel(new Span("Drop logo file here (PNG, JPG, GIF)"));
////        // Add upload listeners if needed (e.g., SucceededEvent)
////
////        FormLayout form = new FormLayout();
////        form.add(siteNameField, languageComboBox, timezoneComboBox, maintenanceModeCheckbox);
////        // Span logo label manually
////        Span logoLabel = new Span("Site Logo");
////        logoLabel.getStyle().set("font-weight", "500").set("font-size", "var(--lumo-font-size-s)");
////        form.add(logoLabel, logoUpload);
////        form.setResponsiveSteps(
////           new FormLayout.ResponsiveStep("0", 1), // 1 column on small screens
////           new FormLayout.ResponsiveStep("600px", 2) // 2 columns on wider screens
////        );
////
////        Button saveButton = new Button("Save General Settings", VaadinIcon.CHECK.create());
////        saveButton.getStyle().set("background-color", primaryColor).set("color", primaryContrastColor);
////        saveButton.addClickListener(e -> saveGeneralSettings());
////
////        sectionLayout.add(sectionTitle, form, saveButton);
////        sectionLayout.setAlignSelf(FlexComponent.Alignment.START, saveButton); // Align button left
////
////        return sectionLayout;
////    }
////
////    private Component createSecuritySettingsSection() {
////        VerticalLayout sectionLayout = new VerticalLayout();
////        sectionLayout.setWidthFull();
////        sectionLayout.setPadding(false);
////        sectionLayout.setSpacing(false);
////
////        H3 sectionTitle = new H3("Security Settings");
////        sectionTitle.getStyle().set("color", primaryColor).set("margin-top", "0");
////
////        minPasswordLengthField = new IntegerField("Minimum Password Length");
////        minPasswordLengthField.setStepButtonsVisible(true);
////        minPasswordLengthField.setMin(8); // Example minimum
////
////        passwordComplexityCheckboxGroup = new CheckboxGroup<>();
////        passwordComplexityCheckboxGroup.setLabel("Password Complexity Requirements");
////        passwordComplexityCheckboxGroup.setItems("Require Uppercase", "Require Lowercase", "Require Number", "Require Symbol");
////        passwordComplexityCheckboxGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
////
////        enforce2FACheckbox = new Checkbox("Enforce Two-Factor Authentication (2FA) for Admins");
////
////        sessionTimeoutField = new IntegerField("Admin Session Timeout (minutes)");
////        sessionTimeoutField.setStepButtonsVisible(true);
////        sessionTimeoutField.setMin(5);
////
////        FormLayout form = new FormLayout();
////        form.add(minPasswordLengthField, sessionTimeoutField, passwordComplexityCheckboxGroup, enforce2FACheckbox);
////        form.setColspan(passwordComplexityCheckboxGroup, 2); // Span complexity across columns if needed
////        form.setColspan(enforce2FACheckbox, 2);
////         form.setResponsiveSteps(
////           new FormLayout.ResponsiveStep("0", 1),
////           new FormLayout.ResponsiveStep("600px", 2)
////        );
////
////
////        Button saveButton = new Button("Save Security Settings", VaadinIcon.CHECK.create());
////        saveButton.getStyle().set("background-color", primaryColor).set("color", primaryContrastColor);
////        saveButton.addClickListener(e -> saveSecuritySettings());
////
////        sectionLayout.add(sectionTitle, form, saveButton);
////        sectionLayout.setAlignSelf(FlexComponent.Alignment.START, saveButton);
////
////        return sectionLayout;
////    }
////
////     private Component createEmailSettingsSection() {
////        VerticalLayout sectionLayout = new VerticalLayout();
////        sectionLayout.setWidthFull();
////        sectionLayout.setPadding(false);
////        sectionLayout.setSpacing(false);
////
////        H3 sectionTitle = new H3("Email Settings (SMTP)");
////        sectionTitle.getStyle().set("color", primaryColor).set("margin-top", "0");
////
////        smtpHostField = new TextField("SMTP Host");
////        smtpPortField = new IntegerField("SMTP Port");
////        smtpUsernameField = new TextField("SMTP Username");
////        smtpPasswordField = new PasswordField("SMTP Password");
////        smtpEncryptionComboBox = new ComboBox<>("Encryption", "None", "SSL/TLS", "STARTTLS");
////
////        FormLayout form = new FormLayout();
////        form.add(smtpHostField, smtpPortField, smtpUsernameField, smtpPasswordField, smtpEncryptionComboBox);
////        form.setResponsiveSteps(
////           new FormLayout.ResponsiveStep("0", 1),
////           new FormLayout.ResponsiveStep("600px", 2)
////        );
////
////        Button testButton = new Button("Send Test Email", VaadinIcon.PAPERPLANE.create());
////        testButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY); // Tertiary style for test button
////        testButton.addClickListener(e -> sendTestEmail());
////
////        Button saveButton = new Button("Save Email Settings", VaadinIcon.CHECK.create());
////        saveButton.getStyle().set("background-color", primaryColor).set("color", primaryContrastColor);
////        saveButton.addClickListener(e -> saveEmailSettings());
////
////        HorizontalLayout buttonLayout = new HorizontalLayout(testButton, saveButton);
////        buttonLayout.setSpacing(true);
////
////        sectionLayout.add(sectionTitle, form, buttonLayout);
////        // Align button layout left
////        sectionLayout.setAlignSelf(FlexComponent.Alignment.START, buttonLayout);
////
////        return sectionLayout;
////    }
////
////    private Component createNotificationSettingsSection() {
////        VerticalLayout sectionLayout = new VerticalLayout();
////        sectionLayout.setWidthFull();
////        sectionLayout.setPadding(false);
////        sectionLayout.setSpacing(false);
////
////        H3 sectionTitle = new H3("Admin Notifications");
////        sectionTitle.getStyle().set("color", primaryColor).set("margin-top", "0");
////
////        notifyNewUserCheckbox = new Checkbox("Notify admins on new user registration");
////        notifyPaymentCheckbox = new Checkbox("Notify admins on successful payment");
////        notifyTestCompletionCheckbox = new Checkbox("Notify admins on personality test completion");
////
////        VerticalLayout checkboxLayout = new VerticalLayout(
////            notifyNewUserCheckbox, notifyPaymentCheckbox, notifyTestCompletionCheckbox
////        );
////        checkboxLayout.setPadding(false);
////        checkboxLayout.setSpacing(true); // Add space between checkboxes
////
////        Button saveButton = new Button("Save Notification Settings", VaadinIcon.CHECK.create());
////        saveButton.getStyle().set("background-color", primaryColor).set("color", primaryContrastColor);
////        saveButton.addClickListener(e -> saveNotificationSettings());
////
////        sectionLayout.add(sectionTitle, checkboxLayout, saveButton);
////        sectionLayout.setAlignSelf(FlexComponent.Alignment.START, saveButton);
////
////        return sectionLayout;
////    }
////
////
////    // --- Placeholder Load & Save Methods ---
////
////    private void loadSettingsData() {
////        // Replace with actual logic to load settings from your backend/database
////        siteNameField.setValue("Talanta Platform");
////        languageComboBox.setValue("English");
////        timezoneComboBox.setValue(TimeZone.getDefault().getID()); // Default to server timezone
////        maintenanceModeCheckbox.setValue(false);
////
////        minPasswordLengthField.setValue(10);
////        passwordComplexityCheckboxGroup.setValue(Set.of("Require Uppercase", "Require Number"));
////        enforce2FACheckbox.setValue(false);
////        sessionTimeoutField.setValue(30);
////
////        smtpHostField.setValue("smtp.example.com");
////        smtpPortField.setValue(587);
////        smtpUsernameField.setValue("user@example.com");
////        smtpEncryptionComboBox.setValue("STARTTLS");
////
////        notifyNewUserCheckbox.setValue(true);
////        notifyPaymentCheckbox.setValue(true);
////        notifyTestCompletionCheckbox.setValue(false);
////
////        showNotification("Settings loaded (placeholder data)", NotificationVariant.LUMO_CONTRAST);
////    }
////
////    private void saveGeneralSettings() {
////        // Get values: siteNameField.getValue(), logoBuffer, etc.
////        // --- Implement backend save logic here ---
////        String siteName = siteNameField.getValue();
////        String language = languageComboBox.getValue();
////        String timezone = timezoneComboBox.getValue();
////        boolean maintenance = maintenanceModeCheckbox.getValue();
////        // Handle logoUpload.getInputStream() if a file was uploaded in logoBuffer
////
////        showNotification("General settings saved (placeholder)", NotificationVariant.LUMO_SUCCESS);
////        // --- End placeholder ---
////    }
////
////     private void saveSecuritySettings() {
////        // Get values: minPasswordLengthField.getValue(), etc.
////        // --- Implement backend save logic here ---
////        Integer minPass = minPasswordLengthField.getValue();
////        Set<String> complexity = passwordComplexityCheckboxGroup.getValue();
////        boolean enforce2FA = enforce2FACheckbox.getValue();
////        Integer timeout = sessionTimeoutField.getValue();
////
////        showNotification("Security settings saved (placeholder)", NotificationVariant.LUMO_SUCCESS);
////        // --- End placeholder ---
////    }
////
////     private void saveEmailSettings() {
////        // Get values: smtpHostField.getValue(), etc.
////        // --- Implement backend save logic here ---
////         String host = smtpHostField.getValue();
////         // ... other fields
////
////        showNotification("Email settings saved (placeholder)", NotificationVariant.LUMO_SUCCESS);
////        // --- End placeholder ---
////    }
////
////    private void sendTestEmail() {
////         // --- Implement logic to send a test email using current settings ---
////         showNotification("Sending test email... (placeholder)", NotificationVariant.LUMO_CONTRAST);
////         // --- End placeholder ---
////    }
////
////     private void saveNotificationSettings() {
////        // Get values: notifyNewUserCheckbox.getValue(), etc.
////        // --- Implement backend save logic here ---
////        boolean notifyUser = notifyNewUserCheckbox.getValue();
////        // ... other fields
////
////        showNotification("Notification settings saved (placeholder)", NotificationVariant.LUMO_SUCCESS);
////        // --- End placeholder ---
////    }
////
////    // Optional: Method to save all settings at once
////    // private void saveAllSettings() {
////    //     saveGeneralSettings();
////    //     saveSecuritySettings();
////    //     saveEmailSettings();
////    //     saveNotificationSettings();
////    //     showNotification("All settings saved (placeholder)", NotificationVariant.LUMO_SUCCESS);
////    // }
////
////
////    // --- Helper Methods ---
////    private void showNotification(String message, NotificationVariant variant) {
////        Notification notification = new Notification(message, 3000, Notification.Position.BOTTOM_CENTER);
////        notification.addThemeVariants(variant);
////        notification.open();
////    }
////
////}
//
////
////package com.example.talanta.views;
////
////import com.vaadin.flow.component.UI;
////import com.vaadin.flow.component.button.Button;
////import com.vaadin.flow.component.dependency.CssImport;
////import com.vaadin.flow.component.html.*;
////import com.vaadin.flow.component.icon.Icon;
////import com.vaadin.flow.component.icon.VaadinIcon;
////import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
////import com.vaadin.flow.component.orderedlayout.VerticalLayout;
////import com.vaadin.flow.router.Route;
////import com.vaadin.flow.component.textfield.TextField;
////import com.vaadin.flow.component.checkbox.Checkbox;
////import com.vaadin.flow.component.notification.Notification;
////
////@Route("settings")
////public class SettingsView extends VerticalLayout {
////
////    public SettingsView() {
////        HorizontalLayout layout = new HorizontalLayout();
////        VerticalLayout sidebar = new AdminDashboardView().createSidebar();
////        VerticalLayout mainContent = createMainContent();
////
////        layout.setSizeFull();
////        sidebar.setWidth("240px");
////        sidebar.getStyle().set("background", "#F4F4F4");
////        layout.add(sidebar, mainContent);
////        add(layout);
////    }
////
////    private VerticalLayout createMainContent() {
////        VerticalLayout main = new VerticalLayout();
////        main.setPadding(true);
////        main.setSpacing(true);
////        main.setWidthFull();
////
////        H2 title = new H2("Admin Settings");
////        title.getStyle().set("color", "#E65100");
////        main.add(title);
////
////        // --- Profile Settings ---
////        H3 profileTitle = new H3("Profile Settings");
////        TextField email = new TextField("Email");
////        email.setPlaceholder("admin@example.com");
////        TextField password = new TextField("New Password");
////        password.setPlaceholder("••••••••");
////        Button updateProfile = new Button("Update Profile", new Icon(VaadinIcon.USER_CHECK));
////        updateProfile.getStyle().set("background-color", "#E65100").set("color", "white");
////        updateProfile.addClickListener(e -> Notification.show("Profile Updated"));
////
////        main.add(profileTitle, email, password, updateProfile);
////
////        // --- Notifications ---
////        H3 notifTitle = new H3("Notifications");
////        Checkbox emailNotif = new Checkbox("Enable Email Notifications", true);
////        Checkbox smsNotif = new Checkbox("Enable SMS Alerts");
////        Button saveNotif = new Button("Save Notifications", new Icon(VaadinIcon.BELL));
////        saveNotif.getStyle().set("background-color", "#E65100").set("color", "white");
////        saveNotif.addClickListener(e -> Notification.show("Notification Preferences Saved"));
////
////        main.add(notifTitle, emailNotif, smsNotif, saveNotif);
////
////        // --- System Preferences ---
////        H3 systemTitle = new H3("System Preferences");
////        TextField systemName = new TextField("Platform Name");
////        systemName.setPlaceholder("Talanta Careers");
////        Checkbox maintenanceMode = new Checkbox("Enable Maintenance Mode");
////        Button saveSystem = new Button("Save System Settings", new Icon(VaadinIcon.COG));
////        saveSystem.getStyle().set("background-color", "#E65100").set("color", "white");
////        saveSystem.addClickListener(e -> Notification.show("System Settings Updated"));
////
////        main.add(systemTitle, systemName, maintenanceMode, saveSystem);
////
////        return main;
////    }
////}
//
//
//package com.example.talanta.views;
//
//import com.vaadin.flow.component.UI;
//import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.dependency.CssImport;
//import com.vaadin.flow.component.html.*;
//import com.vaadin.flow.component.icon.Icon;
//import com.vaadin.flow.component.icon.VaadinIcon;
//import com.vaadin.flow.component.notification.Notification;
//import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.component.textfield.TextField;
//import com.vaadin.flow.component.checkbox.Checkbox;
//import com.vaadin.flow.router.Route;
//
//@Route("settings")
//public class SettingsView extends VerticalLayout {
//
//    public SettingsView() {
//        setSizeFull();
//        HorizontalLayout layout = new HorizontalLayout();
//        layout.setSizeFull();
//
//        VerticalLayout sidebar = new AdminDashboardView().createSidebar();
//        sidebar.setWidth("240px");
//        sidebar.getStyle().set("background", "#F4F4F4");
//
//        VerticalLayout mainContent = createMainContent();
//        mainContent.setWidthFull();
//        mainContent.getStyle().set("padding", "30px");
//
//        layout.add(sidebar, mainContent);
//        add(layout);
//    }
//
//    private VerticalLayout createMainContent() {
//        VerticalLayout main = new VerticalLayout();
//        main.setWidthFull();
//        main.setSpacing(true);
//        main.setPadding(false);
//        main.getStyle().set("max-width", "800px").set("margin", "auto");
//
//        H2 title = new H2("⚙️ Admin Settings");
//        title.getStyle().set("color", "#E65100");
//
//        // --- Profile Settings ---
//        H3 profileTitle = new H3("👤 Profile Settings");
//        TextField email = new TextField("Email");
//        email.setPlaceholder("admin@example.com");
//
//        TextField password = new TextField("New Password");
//        password.setPlaceholder("••••••••");
//
//        Button updateProfile = new Button("Update Profile", new Icon(VaadinIcon.USER_CHECK));
//        updateProfile.getStyle().set("background-color", "#E65100").set("color", "white");
//        updateProfile.addClickListener(e -> Notification.show("Profile Updated"));
//
//        // --- Notifications ---
//        H3 notifTitle = new H3("🔔 Notification Preferences");
//        Checkbox emailNotif = new Checkbox("Enable Email Notifications", true);
//        Checkbox smsNotif = new Checkbox("Enable SMS Alerts");
//
//        Button saveNotif = new Button("Save Notifications", new Icon(VaadinIcon.BELL));
//        saveNotif.getStyle().set("background-color", "#E65100").set("color", "white");
//        saveNotif.addClickListener(e -> Notification.show("Notification Preferences Saved"));
//
//        // --- System Preferences ---
//        H3 systemTitle = new H3("🛠️ System Preferences");
//        TextField systemName = new TextField("Platform Name");
//        systemName.setPlaceholder("Talanta Careers");
//
//        Checkbox maintenanceMode = new Checkbox("Enable Maintenance Mode");
//
//        Button saveSystem = new Button("Save System Settings", new Icon(VaadinIcon.COG));
//        saveSystem.getStyle().set("background-color", "#E65100").set("color", "white");
//        saveSystem.addClickListener(e -> Notification.show("System Settings Updated"));
//
//        // Group and organize
//        main.add(
//                title,
//                new Hr(),
//
//                profileTitle,
//                email, password, updateProfile,
//                new Hr(),
//
//                notifTitle,
//                emailNotif, smsNotif, saveNotif,
//                new Hr(),
//
//                systemTitle,
//                systemName, maintenanceMode, saveSystem
//        );
//
//        return main;
//    }
//}



package com.example.talanta.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("settings")
public class SettingsView extends VerticalLayout {

    public SettingsView() {
        setSizeFull();
        setPadding(false);
        setSpacing(false);

        HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();
        
        // Reuse admin sidebar
        VerticalLayout sidebar = new AdminDashboardView().createSidebar();
        sidebar.setWidth("240px");
        sidebar.getStyle()
                .set("background", "#F4F4F4")
                .set("border-right", "1px solid #E0E0E0");

        VerticalLayout mainContent = createMainContent();
        mainContent.getStyle().set("background", "#FAFAFA");

        layout.add(sidebar, mainContent);
        add(layout);
    }

    private VerticalLayout createMainContent() {
        VerticalLayout content = new VerticalLayout();
        content.setPadding(false);
        content.setSpacing(false);
        content.setSizeFull();

        // Header section
        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.getStyle()
                .set("background", "white")
                .set("padding", "1rem")
                .set("border-bottom", "1px solid #E0E0E0");

        H2 title = new H2("Platform Settings");
        title.getStyle()
                .set("margin", "0")
                .set("color", "#E65100");

        header.add(title);
        header.expand(title);

        // Main settings content
        Accordion settingsAccordion = new Accordion();
        settingsAccordion.setWidthFull();

        // Platform Settings Panel
        AccordionPanel platformPanel = new AccordionPanel("Platform Settings", createPlatformSettings());
        platformPanel.addThemeName("bordered");
        settingsAccordion.add(platformPanel);

        // User Settings Panel
        AccordionPanel userPanel = new AccordionPanel("User Settings", createUserSettings());
        userPanel.addThemeName("bordered");
        settingsAccordion.add(userPanel);

        // Payment Settings Panel
        AccordionPanel paymentPanel = new AccordionPanel("Payment Settings", createPaymentSettings());
        paymentPanel.addThemeName("bordered");
        settingsAccordion.add(paymentPanel);

        // Privacy & Security Panel
        AccordionPanel securityPanel = new AccordionPanel("Privacy & Security", createSecuritySettings());
        securityPanel.addThemeName("bordered");
        settingsAccordion.add(securityPanel);

        // Email/SMS Settings Panel
        AccordionPanel emailPanel = new AccordionPanel("Email & SMS Settings", createEmailSettings());
        emailPanel.addThemeName("bordered");
        settingsAccordion.add(emailPanel);

        // Backup & Recovery Panel
        AccordionPanel backupPanel = new AccordionPanel("Backup & Recovery", createBackupSettings());
        backupPanel.addThemeName("bordered");
        settingsAccordion.add(backupPanel);

        // Action buttons
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setWidthFull();
        buttons.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        buttons.setPadding(true);
        buttons.getStyle().set("background", "white")
                .set("border-top", "1px solid #E0E0E0");

        Button cancelButton = new Button("Cancel");
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        Button saveButton = new Button("Save Settings", new Icon(VaadinIcon.CHECK));
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveButton.getStyle().set("background-color", "#E65100");
        saveButton.addClickListener(e -> {
            Notification.show("Settings saved successfully", 3000, Notification.Position.MIDDLE);
        });

        buttons.add(cancelButton, saveButton);

        content.add(header, settingsAccordion, buttons);
        content.expand(settingsAccordion);
        return content;
    }

    private VerticalLayout createPlatformSettings() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setPadding(false);

        // Site Information
        H3 siteHeader = new H3("Site Information");
        siteHeader.getStyle().set("margin-top", "0");

        TextField siteName = new TextField("Site Name");
        siteName.setWidth("100%");

        TextArea siteDescription = new TextArea("Site Description");
        siteDescription.setWidth("100%");

        // Logo Upload
        Button uploadButton = new Button("Upload Logo", new Icon(VaadinIcon.UPLOAD));
        uploadButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        HorizontalLayout logoLayout = new HorizontalLayout(uploadButton);
        logoLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
        logoLayout.add(new Span("Current logo: "));
        Image logo = new Image("images/logo.png", "Site Logo");
        logo.setHeight("40px");
        logoLayout.add(logo);

        // Language and Theme
        H3 preferencesHeader = new H3("Preferences");

        ComboBox<String> languageSelect = new ComboBox<>("Default Language");
        languageSelect.setItems("English", "Swahili", "French", "Spanish");
        languageSelect.setWidth("100%");

        RadioButtonGroup<String> themeSelect = new RadioButtonGroup<>();
        themeSelect.setLabel("Theme");
        themeSelect.setItems("Light", "Dark", "System Default");

        // Support Info
        H3 supportHeader = new H3("Support Information");

        EmailField supportEmail = new EmailField("Support Email");
        supportEmail.setWidth("100%");

        TextField supportPhone = new TextField("Support Phone");
        supportPhone.setWidth("100%");

        layout.add(
                siteHeader,
                siteName,
                siteDescription,
                logoLayout,
                preferencesHeader,
                languageSelect,
                themeSelect,
                supportHeader,
                supportEmail,
                supportPhone
        );

        return layout;
    }

    private VerticalLayout createUserSettings() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setPadding(false);

        // Registration Settings
        H3 registrationHeader = new H3("Registration Settings");
        registrationHeader.getStyle().set("margin-top", "0");

        Checkbox allowRegistration = new Checkbox("Allow new user registration");
        allowRegistration.setValue(true);

        // Access Settings
        H3 accessHeader = new H3("Access Settings");

        TextField defaultAccessDuration = new TextField("Default access duration");
        defaultAccessDuration.setWidth("100%");
        defaultAccessDuration.setHelperText("e.g. '30 days', '1 year'");

        NumberField maxLoginAttempts = new NumberField("Max login attempts before lockout");
        maxLoginAttempts.setWidth("100%");
        maxLoginAttempts.setValue(5.0);
        maxLoginAttempts.setMin(1);
        maxLoginAttempts.setStepButtonsVisible(true);

        layout.add(
                registrationHeader,
                allowRegistration,
                accessHeader,
                defaultAccessDuration,
                maxLoginAttempts
        );

        return layout;
    }

    private VerticalLayout createPaymentSettings() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setPadding(false);

        // Payment Gateways
        H3 gatewaysHeader = new H3("Payment Gateways");
        gatewaysHeader.getStyle().set("margin-top", "0");

        Checkbox mpesaEnabled = new Checkbox("Enable MPesa");
        mpesaEnabled.setValue(true);

        Checkbox cardEnabled = new Checkbox("Enable Credit/Debit Cards");
        cardEnabled.setValue(true);

        Checkbox paypalEnabled = new Checkbox("Enable PayPal");
        paypalEnabled.setValue(false);

        // Currency Options
        H3 currencyHeader = new H3("Currency Options");

        ComboBox<String> currencySelect = new ComboBox<>("Default Currency");
        currencySelect.setItems("KES (Kenyan Shilling)", "USD (US Dollar)", "EUR (Euro)", "GBP (British Pound)");
        currencySelect.setWidth("100%");

        // Commission
        H3 commissionHeader = new H3("Commission Handling");

        NumberField commissionRate = new NumberField("Commission Rate (%)");
        commissionRate.setWidth("100%");
        commissionRate.setValue(5.0);
        commissionRate.setMin(0);
        commissionRate.setMax(100);
        commissionRate.setStepButtonsVisible(true);

        layout.add(
                gatewaysHeader,
                mpesaEnabled,
                cardEnabled,
                paypalEnabled,
                currencyHeader,
                currencySelect,
                commissionHeader,
                commissionRate
        );

        return layout;
    }

    private VerticalLayout createSecuritySettings() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setPadding(false);

        // Privacy Policy
        H3 privacyHeader = new H3("Privacy Policy");
        privacyHeader.getStyle().set("margin-top", "0");

        TextArea privacyPolicy = new TextArea("Privacy Policy Text");
        privacyPolicy.setWidth("100%");
        privacyPolicy.setHeight("150px");

        // User Data
        H3 dataHeader = new H3("User Data Handling");

        Checkbox allowDataExport = new Checkbox("Allow users to export their data");
        allowDataExport.setValue(true);

        Checkbox allowDataDeletion = new Checkbox("Allow users to request data deletion");
        allowDataDeletion.setValue(true);

        // 2FA
        H3 twoFactorHeader = new H3("Two-Factor Authentication");

        Checkbox enable2FA = new Checkbox("Enable Two-Factor Authentication (2FA)");
        enable2FA.setValue(false);

        layout.add(
                privacyHeader,
                privacyPolicy,
                dataHeader,
                allowDataExport,
                allowDataDeletion,
                twoFactorHeader,
                enable2FA
        );

        return layout;
    }

    private VerticalLayout createEmailSettings() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setPadding(false);

        // Email Settings
        H3 emailHeader = new H3("Email Settings");
        emailHeader.getStyle().set("margin-top", "0");

        EmailField senderEmail = new EmailField("Sender Email Address");
        senderEmail.setWidth("100%");

        TextField mailgunApiKey = new TextField("Mailgun API Key");
        mailgunApiKey.setWidth("100%");

        Button testEmailButton = new Button("Send Test Email", new Icon(VaadinIcon.ENVELOPE_O));
        testEmailButton.addThemeVariants(ButtonVariant.LUMO_SMALL);

        // SMS Settings
        H3 smsHeader = new H3("SMS Settings");

        TextField twilioApiKey = new TextField("Twilio API Key");
        twilioApiKey.setWidth("100%");

        TextField twilioPhoneNumber = new TextField("Twilio Phone Number");
        twilioPhoneNumber.setWidth("100%");

        Button testSmsButton = new Button("Send Test SMS", new Icon(VaadinIcon.MOBILE));
        testSmsButton.addThemeVariants(ButtonVariant.LUMO_SMALL);

        layout.add(
                emailHeader,
                senderEmail,
                mailgunApiKey,
                testEmailButton,
                smsHeader,
                twilioApiKey,
                twilioPhoneNumber,
                testSmsButton
        );

        return layout;
    }

    private VerticalLayout createBackupSettings() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setPadding(false);

        // Backup Settings
        H3 backupHeader = new H3("Backup Settings");
        backupHeader.getStyle().set("margin-top", "0");

        ComboBox<String> backupFrequency = new ComboBox<>("Backup Frequency");
        backupFrequency.setItems("Daily", "Weekly", "Monthly", "Manual Only");
        backupFrequency.setWidth("100%");

        Button exportBackupButton = new Button("Export Backup Now", new Icon(VaadinIcon.DOWNLOAD));
        exportBackupButton.addThemeVariants(ButtonVariant.LUMO_SMALL);

        // Restore Settings
        H3 restoreHeader = new H3("Restore Settings");

        ComboBox<String> restorePoint = new ComboBox<>("Select Restore Point");
        restorePoint.setItems("Backup 2023-10-01", "Backup 2023-09-15", "Backup 2023-09-01");
        restorePoint.setWidth("100%");

        Button restoreButton = new Button("Restore Selected Backup", new Icon(VaadinIcon.REFRESH));
        restoreButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_ERROR);
        restoreButton.addClickListener(e -> {
            ConfirmDialog confirm = new ConfirmDialog();
            confirm.setHeader("Confirm Restore");
            confirm.setText("This will overwrite all current data with the selected backup. Are you sure?");
            confirm.setCancelable(true);
            confirm.setConfirmText("Restore");
            confirm.setConfirmButtonTheme("error primary");
            confirm.open();
        });

        layout.add(
                backupHeader,
                backupFrequency,
                exportBackupButton,
                restoreHeader,
                restorePoint,
                restoreButton
        );

        return layout;
    }
}
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
        setSizeFull(); // Ensure this fills the viewport
        getStyle().set("overflow", "auto");
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();

        // Reuse admin sidebar
        VerticalLayout sidebar = new AdminDashboardView().createSidebar();
        sidebar.setWidth("240px");
        sidebar.getStyle()
                .set("height", "880px")
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

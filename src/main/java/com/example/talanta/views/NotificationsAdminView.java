package com.example.talanta.views;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;

import java.time.LocalDateTime;
import java.util.*;

@Route("notifications")
public class NotificationsAdminView extends VerticalLayout {

    private final Grid<AdminNotification> notificationsGrid = new Grid<>();
    private final List<AdminNotification> allNotifications = new ArrayList<>();
    private ListDataProvider<AdminNotification> dataProvider;
    
    // Filter components
    private final ComboBox<String> typeFilter = new ComboBox<>("Type");
    private final ComboBox<String> statusFilter = new ComboBox<>("Status");
    private final ComboBox<String> audienceFilter = new ComboBox<>("Audience");
    private final DatePicker dateFilter = new DatePicker("Date");
    
    // Auto-notification settings
    private final Map<String, Boolean> autoNotificationSettings = new LinkedHashMap<>();

    public NotificationsAdminView() {
        setSizeFull();
        setPadding(false);
        setSpacing(false);

        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.setSizeFull();
        mainLayout.setPadding(false);
        mainLayout.setSpacing(false);

        // Sidebar
        AdminSidebar sidebar = new AdminSidebar();

        // Main content area
        VerticalLayout content = new VerticalLayout();
        content.setPadding(true);
        content.setSpacing(true);
        content.setSizeFull();

        // Initialize data and UI components
        initializeData();
        initializeAutoNotificationSettings();
        configureGrid();

        content.add(
            createHeader(),
            createFilterLayout(),
            notificationsGrid,
            createAutoNotificationsSection()
        );

        add(mainLayout);
    }

    private void initializeData() {
        allNotifications.addAll(Arrays.asList(
            new AdminNotification("System Alert", "Server maintenance scheduled", 
                "All Users", "Email", "Scheduled", LocalDateTime.now().plusHours(2)),
            new AdminNotification("Payment", "Payment received from user123", 
                "Specific User", "In-App", "Sent", LocalDateTime.now().minusMinutes(30)),
            new AdminNotification("Reminder", "Course deadline approaching", 
                "Paid Users", "SMS", "Sent", LocalDateTime.now().minusHours(5)),
            new AdminNotification("Promo", "New year discount offer", 
                "Free Users", "Email", "Failed", LocalDateTime.now().minusDays(1)),
            new AdminNotification("Recommendation", "Suggested courses based on test", 
                "Specific User", "In-App", "Sent", LocalDateTime.now().minusDays(2))
        ));
        
        dataProvider = new ListDataProvider<>(allNotifications);
        notificationsGrid.setDataProvider(dataProvider);
    }

    private void initializeAutoNotificationSettings() {
        autoNotificationSettings.put("Personality Test", true);
        autoNotificationSettings.put("Payment Confirmation", true);
        autoNotificationSettings.put("Course Recommendation", false);
        autoNotificationSettings.put("Subscription Renewal", true);
    }

    private HorizontalLayout createHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.setAlignItems(Alignment.CENTER);
        header.setSpacing(true);
        
        H1 title = new H1("Notifications Management");
        Button createButton = new Button("Create New Notification", 
            new Icon(VaadinIcon.PLUS), e -> showCreateDialog());
        createButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        
        header.add(title);
        header.addAndExpand(new Span());
        header.add(createButton);
        
        return header;
    }

    private HorizontalLayout createFilterLayout() {
        HorizontalLayout filterLayout = new HorizontalLayout();
        filterLayout.setWidthFull();
        filterLayout.setAlignItems(Alignment.BASELINE);
        filterLayout.setSpacing(true);
        
        typeFilter.setItems("System Alert", "Promo", "Reminder", "Payment", "Recommendation");
        typeFilter.setPlaceholder("All Types");
        typeFilter.addValueChangeListener(e -> applyFilters());
        
        statusFilter.setItems("Sent", "Scheduled", "Failed");
        statusFilter.setPlaceholder("All Statuses");
        statusFilter.addValueChangeListener(e -> applyFilters());
        
        audienceFilter.setItems("All Users", "Specific User", "Paid Users", "Free Users");
        audienceFilter.setPlaceholder("All Audiences");
        audienceFilter.addValueChangeListener(e -> applyFilters());
        
        dateFilter.setPlaceholder("Any Date");
        dateFilter.addValueChangeListener(e -> applyFilters());
        
        Button clearFilters = new Button("Clear Filters", e -> clearFilters());
        clearFilters.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        
        filterLayout.add(typeFilter, statusFilter, audienceFilter, dateFilter, clearFilters);
        
        return filterLayout;
    }

    private void clearFilters() {
        typeFilter.clear();
        statusFilter.clear();
        audienceFilter.clear();
        dateFilter.clear();
        applyFilters();
    }

    private void configureGrid() {
        notificationsGrid.removeAllColumns();
        
        notificationsGrid.addColumn(AdminNotification::getType)
            .setHeader("Type")
            .setSortable(true)
            .setResizable(true);
            
        notificationsGrid.addColumn(AdminNotification::getMessage)
            .setHeader("Message")
            .setSortable(true)
            .setResizable(true);
            
        notificationsGrid.addColumn(AdminNotification::getAudience)
            .setHeader("Audience")
            .setSortable(true)
            .setResizable(true);
            
        notificationsGrid.addColumn(AdminNotification::getDeliveryMethod)
            .setHeader("Delivery Method")
            .setSortable(true)
            .setResizable(true);
            
        notificationsGrid.addColumn(AdminNotification::getStatus)
            .setHeader("Status")
            .setSortable(true)
            .setResizable(true);
            
        notificationsGrid.addColumn(AdminNotification::getTimestamp)
            .setHeader("Timestamp")
            .setSortable(true)
            .setResizable(true);
        
        // Add action column
        notificationsGrid.addColumn(new ComponentRenderer<>(notification -> {
            HorizontalLayout actions = new HorizontalLayout();
            actions.setSpacing(true);
            
            if ("Failed".equals(notification.getStatus())) {
                Button resend = new Button("Resend", e -> resendNotification(notification));
                resend.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_PRIMARY);
                actions.add(resend);
            }
            
            if ("Scheduled".equals(notification.getStatus())) {
                Button edit = new Button("Edit", e -> editNotification(notification));
                edit.addThemeVariants(ButtonVariant.LUMO_SMALL);
                actions.add(edit);
                
                Button delete = new Button("Delete", e -> deleteNotification(notification));
                delete.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_ERROR);
                actions.add(delete);
            }
            
            return actions;
        })).setHeader("Actions").setResizable(true);
        
        notificationsGrid.setWidthFull();
        notificationsGrid.setHeight("400px");
    }
    
    private void applyFilters() {
        dataProvider.setFilter(notification -> 
            (typeFilter.isEmpty() || notification.getType().equals(typeFilter.getValue())) &&
            (statusFilter.isEmpty() || notification.getStatus().equals(statusFilter.getValue())) &&
            (audienceFilter.isEmpty() || notification.getAudience().equals(audienceFilter.getValue())) &&
            (dateFilter.isEmpty() || notification.getTimestamp().toLocalDate().equals(dateFilter.getValue()))
        );
    }
    
    private VerticalLayout createAutoNotificationsSection() {
        VerticalLayout section = new VerticalLayout();
        section.setPadding(false);
        section.setSpacing(true);
        
        H2 autoHeader = new H2("Auto Notifications Settings");
        section.add(autoHeader);
        
        for (Map.Entry<String, Boolean> entry : autoNotificationSettings.entrySet()) {
            HorizontalLayout row = new HorizontalLayout();
            row.setAlignItems(Alignment.CENTER);
            row.setWidthFull();
            row.setSpacing(true);
            
            Span label = new Span(entry.getKey());
            Checkbox toggle = new Checkbox();
            toggle.setValue(entry.getValue());
            toggle.addValueChangeListener(e -> 
                autoNotificationSettings.put(entry.getKey(), e.getValue()));
            
            row.add(label);
            row.addAndExpand(new Span());
            row.add(toggle);
            section.add(row);
        }
        
        return section;
    }
    
    private void showCreateDialog() {
        Dialog dialog = new Dialog();
        dialog.setCloseOnEsc(true);
        dialog.setCloseOnOutsideClick(false);
        
        H2 dialogTitle = new H2("Create New Notification");
        
        FormLayout form = new FormLayout();
        form.setResponsiveSteps(
            new FormLayout.ResponsiveStep("0", 1),
            new FormLayout.ResponsiveStep("500px", 2)
        );
        
        TextField titleField = new TextField("Title");
        titleField.setWidthFull();
        
        TextArea messageField = new TextArea("Message Body");
        messageField.setWidthFull();
        messageField.setHeight("150px");
        
        Select<String> audienceSelect = new Select<>();
        audienceSelect.setLabel("Target Audience");
        audienceSelect.setItems("All Users", "Specific User", "Paid Users", "Free Users");
        audienceSelect.setWidthFull();
        
        Select<String> typeSelect = new Select<>();
        typeSelect.setLabel("Notification Type");
        typeSelect.setItems("System Alert", "Promo", "Reminder", "Payment", "Recommendation");
        typeSelect.setWidthFull();
        
        Select<String> deliverySelect = new Select<>();
        deliverySelect.setLabel("Delivery Method");
        deliverySelect.setItems("Email", "SMS", "In-App");
        deliverySelect.setWidthFull();
        
        DatePicker scheduleDate = new DatePicker("Schedule Date");
        scheduleDate.setWidthFull();
        
        TimePicker scheduleTime = new TimePicker("Schedule Time");
        scheduleTime.setWidthFull();
        
        HorizontalLayout scheduleLayout = new HorizontalLayout(scheduleDate, scheduleTime);
        scheduleLayout.setWidthFull();
        scheduleLayout.setAlignItems(Alignment.BASELINE);
        
        form.add(titleField, messageField, audienceSelect, typeSelect, deliverySelect, scheduleLayout);
        
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        buttons.setSpacing(true);
        
        Button cancel = new Button("Cancel", e -> dialog.close());
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        
        Button save = new Button("Schedule Notification", e -> {
            if (scheduleDate.getValue() == null || scheduleTime.getValue() == null) {
                Notification.show("Please select both date and time", 3000, 
                    Notification.Position.MIDDLE);
                return;
            }
            
            createNotification(
                titleField.getValue(),
                messageField.getValue(),
                audienceSelect.getValue(),
                typeSelect.getValue(),
                deliverySelect.getValue(),
                LocalDateTime.of(scheduleDate.getValue(), scheduleTime.getValue())
            );
            dialog.close();
        });
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        
        buttons.add(cancel, save);
        
        VerticalLayout dialogLayout = new VerticalLayout(dialogTitle, form, buttons);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(true);
        dialog.add(dialogLayout);
        
        dialog.open();
    }
    
    private void createNotification(String title, String message, String audience, 
                                  String type, String deliveryMethod, LocalDateTime scheduleTime) {
        AdminNotification newNotification = new AdminNotification(
            type, message, audience, deliveryMethod, "Scheduled", scheduleTime
        );
        
        allNotifications.add(newNotification);
        dataProvider.refreshAll();
    }
    
    private void editNotification(AdminNotification notification) {
        Dialog dialog = new Dialog();
        dialog.setCloseOnEsc(true);
        dialog.setCloseOnOutsideClick(false);
        
        H2 dialogTitle = new H2("Edit Notification");
        
        FormLayout form = new FormLayout();
        form.setResponsiveSteps(
            new FormLayout.ResponsiveStep("0", 1),
            new FormLayout.ResponsiveStep("500px", 2)
        );
        
        TextField titleField = new TextField("Title");
        titleField.setValue(notification.getType());
        titleField.setWidthFull();
        
        TextArea messageField = new TextArea("Message Body");
        messageField.setValue(notification.getMessage());
        messageField.setWidthFull();
        messageField.setHeight("150px");
        
        Select<String> audienceSelect = new Select<>();
        audienceSelect.setLabel("Target Audience");
        audienceSelect.setItems("All Users", "Specific User", "Paid Users", "Free Users");
        audienceSelect.setValue(notification.getAudience());
        audienceSelect.setWidthFull();
        
        Select<String> deliverySelect = new Select<>();
        deliverySelect.setLabel("Delivery Method");
        deliverySelect.setItems("Email", "SMS", "In-App");
        deliverySelect.setValue(notification.getDeliveryMethod());
        deliverySelect.setWidthFull();
        
        DatePicker scheduleDate = new DatePicker("Schedule Date");
        scheduleDate.setValue(notification.getTimestamp().toLocalDate());
        scheduleDate.setWidthFull();
        
        TimePicker scheduleTime = new TimePicker("Schedule Time");
        scheduleTime.setValue(notification.getTimestamp().toLocalTime());
        scheduleTime.setWidthFull();
        
        HorizontalLayout scheduleLayout = new HorizontalLayout(scheduleDate, scheduleTime);
        scheduleLayout.setWidthFull();
        scheduleLayout.setAlignItems(Alignment.BASELINE);
        
        form.add(titleField, messageField, audienceSelect, deliverySelect, scheduleLayout);
        
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        buttons.setSpacing(true);
        
        Button cancel = new Button("Cancel", e -> dialog.close());
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        
        Button save = new Button("Save Changes", e -> {
            if (scheduleDate.getValue() == null || scheduleTime.getValue() == null) {
                Notification.show("Please select both date and time", 3000, 
                    Notification.Position.MIDDLE);
                return;
            }
            
            notification.setType(titleField.getValue());
            notification.setMessage(messageField.getValue());
            notification.setAudience(audienceSelect.getValue());
            notification.setDeliveryMethod(deliverySelect.getValue());
            notification.setTimestamp(LocalDateTime.of(
                scheduleDate.getValue(), scheduleTime.getValue()));
            
            dataProvider.refreshItem(notification);
            dialog.close();
        });
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        
        buttons.add(cancel, save);
        
        VerticalLayout dialogLayout = new VerticalLayout(dialogTitle, form, buttons);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(true);
        dialog.add(dialogLayout);
        
        dialog.open();
    }
    
    private void deleteNotification(AdminNotification notification) {
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Confirm Deletion");
        dialog.setText("Are you sure you want to delete this scheduled notification?");
        
        dialog.setCancelable(true);
        dialog.setConfirmText("Delete");
        dialog.setConfirmButtonTheme("error primary");
        dialog.addConfirmListener(e -> {
            allNotifications.remove(notification);
            dataProvider.refreshAll();
        });
        
        dialog.open();
    }
    
    private void resendNotification(AdminNotification notification) {
        notification.setStatus("Sent");
        notification.setTimestamp(LocalDateTime.now());
        dataProvider.refreshItem(notification);
        Notification.show("Notification resent successfully", 3000, 
            Notification.Position.MIDDLE);
    }

    public static class AdminNotification {
        private String type;
        private String message;
        private String audience;
        private String deliveryMethod;
        private String status;
        private LocalDateTime timestamp;

        public AdminNotification(String type, String message, String audience, 
                               String deliveryMethod, String status, LocalDateTime timestamp) {
            this.type = type;
            this.message = message;
            this.audience = audience;
            this.deliveryMethod = deliveryMethod;
            this.status = status;
            this.timestamp = timestamp;
        }

        // Getters and setters
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        
        public String getAudience() { return audience; }
        public void setAudience(String audience) { this.audience = audience; }
        
        public String getDeliveryMethod() { return deliveryMethod; }
        public void setDeliveryMethod(String deliveryMethod) { this.deliveryMethod = deliveryMethod; }
        
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        
        public LocalDateTime getTimestamp() { return timestamp; }
        public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    }
}

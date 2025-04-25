package com.example.talanta.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import elemental.json.Json;
import elemental.json.JsonArray;

import java.util.*;

@Route("payments")
public class PaymentsAdminView extends VerticalLayout {

    public PaymentsAdminView() {
        // Make the main layout scrollable
        setHeight("100vh");
        setPadding(false);
        setSpacing(false);
        
        HorizontalLayout layout = new HorizontalLayout();
        VerticalLayout sidebar = createSidebar();
        VerticalLayout mainContent = createMainContent();

        layout.setSizeFull();
        sidebar.setWidth("240px");
        sidebar.getStyle().set("background", "#F4F4F4");
        layout.add(sidebar, mainContent);
        add(layout);
    }

    // Reused from your existing code
    private VerticalLayout createSidebar() {
        VerticalLayout sidebar = new VerticalLayout();
        sidebar.addClassName("sidebar");
        sidebar.getStyle().set("padding-top", "2px");

        Image logo = new Image("images/2.png", "Talanta Logo");
        logo.setWidth("150px");
        logo.setHeight("auto");
        logo.addClassName("logo");

        sidebar.add(
            logo,
            createNavItem("Dashboard", VaadinIcon.DASHBOARD, "Admindashboard"),
            createNavItem("Users", VaadinIcon.USER, "users"),
            createNavItem("Personality Tests", VaadinIcon.CLIPBOARD_TEXT, "personality-tests"),
            createNavItem("Courses & Careers", VaadinIcon.BOOK, "courses-careers"),
            createNavItem("Institutions", VaadinIcon.ACADEMY_CAP, "institutions"),
            createNavItem("Payments", VaadinIcon.CREDIT_CARD, "payments"),
            createNavItem("Reports", VaadinIcon.CHART, "reports"),
            createNavItem("Content", VaadinIcon.FILE_TEXT, "content"),
            createNavItem("Notifications", VaadinIcon.BELL, "notifications"),
            createNavItem("Settings", VaadinIcon.COG, "settings")
        );

        return sidebar;
    }

    // Reused from your existing code
    private HorizontalLayout createNavItem(String title, VaadinIcon icon, String route) {
        Icon menuIcon = icon.create();
        menuIcon.setColor("#E65100");
        Span label = new Span(title);

        HorizontalLayout nav = new HorizontalLayout(menuIcon, label);
        nav.setPadding(true);
        nav.setSpacing(true);
        nav.setAlignItems(FlexComponent.Alignment.CENTER);

        nav.getStyle()
           .set("padding", "10px")
           .set("cursor", "pointer")
           .set("border-radius", "5px")
           .set("transition", "background-color 0.3s ease");

        nav.addClickListener(e -> UI.getCurrent().navigate(route));

        return nav;
    }

    private VerticalLayout createMainContent() {
        VerticalLayout main = new VerticalLayout();
        main.setPadding(true);
        main.setSpacing(true);
        main.setSizeFull();
        main.getStyle().set("overflow-y", "auto"); // Make content scrollable

        // Header section
        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.setWidthFull();
        headerLayout.setJustifyContentMode(FlexLayout.JustifyContentMode.BETWEEN);

        VerticalLayout headingLayout = new VerticalLayout();
        H1 heading = new H1("Payment Management");
        heading.getStyle().set("color", "#E65100").set("margin", "0");
        Paragraph subtitle = new Paragraph("Manage user payments and subscriptions");
        headingLayout.add(heading, subtitle);

        Button refreshBtn = new Button("Refresh", VaadinIcon.REFRESH.create());
        Button exportBtn = new Button("Export", VaadinIcon.DOWNLOAD.create());
        exportBtn.getStyle().set("background-color", "#E65100").set("color", "white");

        headerLayout.add(headingLayout, refreshBtn, exportBtn);
        main.add(headerLayout);

        // Stats cards
        HorizontalLayout statsLayout = new HorizontalLayout();
        statsLayout.setWidthFull();
        statsLayout.setSpacing(true);

        statsLayout.add(
            createStatCard("$24,875", "Total Revenue", VaadinIcon.MONEY),
            createStatCard("1,248", "Successful Payments", VaadinIcon.CHECK),
            createStatCard("42", "Failed Payments", VaadinIcon.CLOSE),
            createStatCard("89%", "Payment Success Rate", VaadinIcon.TRENDING_UP)
        );
        main.add(statsLayout);

        // Revenue chart - replaced Vaadin Charts with Chart.js
        Div chartContainer = new Div();
        chartContainer.setWidthFull();
        chartContainer.setHeight("400px");
        chartContainer.setId("revenueChart");
        chartContainer.getStyle()
            .set("background-color", "white")
            .set("border-radius", "10px")
            .set("box-shadow", "0 2px 5px rgba(0,0,0,0.05)")
            .set("padding", "20px");
        
        // Initialize the chart using JavaScript
        UI.getCurrent().getPage().executeJs("""
            const ctx = document.getElementById('revenueChart').getContext('2d');
            new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
                    datasets: [{
                        label: 'Revenue',
                        data: [1200, 1900, 1500, 2100, 2400, 2800, 3000, 2700, 2500, 3100, 2900, 3500],
                        backgroundColor: '#E65100',
                        borderColor: '#E65100',
                        borderWidth: 1
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    scales: {
                        y: {
                            beginAtZero: true,
                            title: {
                                display: true,
                                text: 'Amount ($)'
                            }
                        }
                    },
                    plugins: {
                        title: {
                            display: true,
                            text: 'Monthly Revenue'
                        }
                    }
                }
            });
        """);
        
        main.add(chartContainer);

        // Main content tabs
        Div tabs = new Div();
        tabs.getStyle().set("display", "flex").set("gap", "10px").set("margin-bottom", "10px");

        Button transactionsTab = new Button("Transactions");
        Button subscriptionsTab = new Button("Subscriptions");
        Button refundsTab = new Button("Refunds");
        
        tabs.add(transactionsTab, subscriptionsTab, refundsTab);
        main.add(tabs);

        // Transactions section
        VerticalLayout transactionsSection = new VerticalLayout();
        transactionsSection.setPadding(false);
        transactionsSection.setSpacing(false);
        
        // Transactions grid
        Grid<Transaction> transactionsGrid = createTransactionsGrid();
        transactionsSection.add(transactionsGrid);
        
        // Subscriptions section (hidden by default)
        VerticalLayout subscriptionsSection = new VerticalLayout();
        subscriptionsSection.setPadding(false);
        subscriptionsSection.setSpacing(false);
        subscriptionsSection.setVisible(false);
        
        Grid<Subscription> subscriptionsGrid = createSubscriptionsGrid();
        subscriptionsSection.add(subscriptionsGrid);
        
        // Refunds section (hidden by default)
        VerticalLayout refundsSection = new VerticalLayout();
        refundsSection.setPadding(false);
        refundsSection.setSpacing(false);
        refundsSection.setVisible(false);
        
        Grid<Refund> refundsGrid = createRefundsGrid();
        refundsSection.add(refundsGrid);
        
        // Tab switching logic
        transactionsTab.addClickListener(e -> {
            transactionsSection.setVisible(true);
            subscriptionsSection.setVisible(false);
            refundsSection.setVisible(false);
            transactionsTab.getStyle().set("background-color", "#E65100").set("color", "white");
            subscriptionsTab.getStyle().remove("background-color").set("color", "inherit");
            refundsTab.getStyle().remove("background-color").set("color", "inherit");
        });
        
        subscriptionsTab.addClickListener(e -> {
            transactionsSection.setVisible(false);
            subscriptionsSection.setVisible(true);
            refundsSection.setVisible(false);
            subscriptionsTab.getStyle().set("background-color", "#E65100").set("color", "white");
            transactionsTab.getStyle().remove("background-color").set("color", "inherit");
            refundsTab.getStyle().remove("background-color").set("color", "inherit");
        });
        
        refundsTab.addClickListener(e -> {
            transactionsSection.setVisible(false);
            subscriptionsSection.setVisible(false);
            refundsSection.setVisible(true);
            refundsTab.getStyle().set("background-color", "#E65100").set("color", "white");
            transactionsTab.getStyle().remove("background-color").set("color", "inherit");
            subscriptionsTab.getStyle().remove("background-color").set("color", "inherit");
        });
        
        // Set default tab
        transactionsTab.getStyle().set("background-color", "#E65100").set("color", "white");
        
        main.add(transactionsSection, subscriptionsSection, refundsSection);
        
        // Add Chart.js library
        UI.getCurrent().getPage().addJavaScript("https://cdn.jsdelivr.net/npm/chart.js");
        
        return main;
    }

       private VerticalLayout createStatCard(String value, String label, VaadinIcon icon) {
        VerticalLayout card = new VerticalLayout();
        card.setPadding(true);
        card.setSpacing(false);
        card.setAlignItems(Alignment.CENTER);

        Icon cardIcon = icon.create();
        cardIcon.setSize("24px");
        cardIcon.setColor("#E65100");

        H2 valueLabel = new H2(value);
        valueLabel.getStyle().set("margin", "0");

        Paragraph description = new Paragraph(label);
        description.getStyle().set("margin", "0").set("text-align", "center");

        card.add(cardIcon, valueLabel, description);
        card.getStyle()
           .set("background-color", "white")
           .set("padding", "20px")
           .set("border-radius", "10px")
           .set("box-shadow", "0 2px 5px rgba(0,0,0,0.05)")
           .set("width", "100%")
           .set("align-items", "center");

        return card;
    }

    private Grid<Transaction> createTransactionsGrid() {
        Grid<Transaction> grid = new Grid<>();
        
        // Sample data
        List<Transaction> transactions = Arrays.asList(
            new Transaction("TXN001", "USR001", "John Doe", "Premium Subscription", 
                          "2023-05-15 10:30", 49.99, "Credit Card", "Completed"),
            new Transaction("TXN002", "USR002", "Jane Smith", "Career Match", 
                          "2023-05-14 14:15", 9.99, "M-Pesa", "Completed"),
            new Transaction("TXN003", "USR003", "Alice Johnson", "Institution Search", 
                          "2023-05-14 09:45", 4.99, "Credit Card", "Failed"),
            new Transaction("TXN004", "USR004", "Bob Brown", "Premium Subscription", 
                          "2023-05-13 16:20", 49.99, "PayPal", "Completed"),
            new Transaction("TXN005", "USR005", "Charlie Davis", "Course Recommendation", 
                          "2023-05-12 11:10", 7.99, "M-Pesa", "Completed")
        );
        
        grid.setItems(transactions);
        grid.addColumn(Transaction::getId).setHeader("ID").setWidth("100px");
        grid.addColumn(Transaction::getUserId).setHeader("User ID");
        grid.addColumn(Transaction::getUserName).setHeader("User");
        grid.addColumn(Transaction::getDescription).setHeader("Description");
        grid.addColumn(Transaction::getTimestamp).setHeader("Date/Time");
        grid.addColumn(t -> "$" + t.getAmount()).setHeader("Amount");
        grid.addColumn(Transaction::getPaymentMethod).setHeader("Method");
        
        grid.addColumn(transaction -> {
            Span status = new Span(transaction.getStatus());
            if ("Completed".equals(transaction.getStatus())) {
                status.getStyle().set("color", "green");
            } else if ("Failed".equals(transaction.getStatus())) {
                status.getStyle().set("color", "red");
            } else {
                status.getStyle().set("color", "orange");
            }
            return status;
        }).setHeader("Status");
        
        grid.addComponentColumn(transaction -> {
            HorizontalLayout actions = new HorizontalLayout();
            Button viewBtn = new Button(VaadinIcon.EYE.create());
            Button refundBtn = new Button(VaadinIcon.MONEY_EXCHANGE.create());
            
            viewBtn.addClickListener(e -> showTransactionDetails(transaction));
            refundBtn.addClickListener(e -> showRefundDialog(transaction));
            refundBtn.setEnabled("Completed".equals(transaction.getStatus()));
            
            actions.add(viewBtn, refundBtn);
            return actions;
        }).setHeader("Actions");
        
        grid.getStyle()
           .set("background-color", "white")
           .set("border-radius", "10px")
           .set("box-shadow", "0 2px 5px rgba(0,0,0,0.05)");
        
        return grid;
    }

    private Grid<Subscription> createSubscriptionsGrid() {
        Grid<Subscription> grid = new Grid<>();
        
        // Sample data
        List<Subscription> subscriptions = Arrays.asList(
            new Subscription("SUB001", "USR001", "John Doe", "Premium", 
                           "2023-01-15", "2024-01-15", "Active", 49.99, "Monthly"),
            new Subscription("SUB002", "USR002", "Jane Smith", "Basic", 
                           "2023-03-10", "2023-06-10", "Active", 19.99, "Quarterly"),
            new Subscription("SUB003", "USR004", "Bob Brown", "Premium", 
                           "2022-11-20", "2023-11-20", "Active", 49.99, "Monthly"),
            new Subscription("SUB004", "USR005", "Charlie Davis", "Basic", 
                           "2023-04-05", "2023-05-05", "Expired", 19.99, "Monthly"),
            new Subscription("SUB005", "USR003", "Alice Johnson", "Premium", 
                           "2023-02-28", "2023-05-28", "Cancelled", 49.99, "Quarterly")
        );
        
        grid.setItems(subscriptions);
        grid.addColumn(Subscription::getId).setHeader("ID").setWidth("100px");
        grid.addColumn(Subscription::getUserId).setHeader("User ID");
        grid.addColumn(Subscription::getUserName).setHeader("User");
        grid.addColumn(Subscription::getPlan).setHeader("Plan");
        grid.addColumn(Subscription::getStartDate).setHeader("Start Date");
        grid.addColumn(Subscription::getEndDate).setHeader("End Date");
        
        grid.addColumn(subscription -> {
            Span status = new Span(subscription.getStatus());
            if ("Active".equals(subscription.getStatus())) {
                status.getStyle().set("color", "green");
            } else if ("Expired".equals(subscription.getStatus())) {
                status.getStyle().set("color", "red");
            } else {
                status.getStyle().set("color", "orange");
            }
            return status;
        }).setHeader("Status");
        
        grid.addColumn(s -> "$" + s.getAmount()).setHeader("Amount");
        grid.addColumn(Subscription::getBillingCycle).setHeader("Billing");
        
        grid.addComponentColumn(subscription -> {
            HorizontalLayout actions = new HorizontalLayout();
            Button viewBtn = new Button(VaadinIcon.EYE.create());
            Button editBtn = new Button(VaadinIcon.EDIT.create());
            Button cancelBtn = new Button(VaadinIcon.CLOSE.create());
            
            viewBtn.addClickListener(e -> showSubscriptionDetails(subscription));
            editBtn.addClickListener(e -> showEditSubscriptionDialog(subscription));
            cancelBtn.addClickListener(e -> cancelSubscription(subscription));
            cancelBtn.setEnabled("Active".equals(subscription.getStatus()));
            
            actions.add(viewBtn, editBtn, cancelBtn);
            return actions;
        }).setHeader("Actions");
        
        grid.getStyle()
           .set("background-color", "white")
           .set("border-radius", "10px")
           .set("box-shadow", "0 2px 5px rgba(0,0,0,0.05)");
        
        return grid;
    }

    private Grid<Refund> createRefundsGrid() {
        Grid<Refund> grid = new Grid<>();
        
        // Sample data
        List<Refund> refunds = Arrays.asList(
            new Refund("REF001", "TXN004", "USR004", "Bob Brown", 49.99, 
                      "2023-05-14", "Completed", "User requested refund"),
            new Refund("REF002", "TXN007", "USR006", "David Wilson", 9.99, 
                      "2023-05-10", "Pending", "Duplicate payment"),
            new Refund("REF003", "TXN009", "USR008", "Eve Adams", 4.99, 
                      "2023-05-05", "Rejected", "Service already used"),
            new Refund("REF004", "TXN011", "USR010", "Frank Miller", 19.99, 
                      "2023-04-28", "Completed", "Subscription cancellation")
        );
        
        grid.setItems(refunds);
        grid.addColumn(Refund::getId).setHeader("ID").setWidth("100px");
        grid.addColumn(Refund::getTransactionId).setHeader("Transaction ID");
        grid.addColumn(Refund::getUserId).setHeader("User ID");
        grid.addColumn(Refund::getUserName).setHeader("User");
        grid.addColumn(r -> "$" + r.getAmount()).setHeader("Amount");
        grid.addColumn(Refund::getDateProcessed).setHeader("Date");
        
        grid.addColumn(refund -> {
            Span status = new Span(refund.getStatus());
            if ("Completed".equals(refund.getStatus())) {
                status.getStyle().set("color", "green");
            } else if ("Rejected".equals(refund.getStatus())) {
                status.getStyle().set("color", "red");
            } else {
                status.getStyle().set("color", "orange");
            }
            return status;
        }).setHeader("Status");
        
        grid.addColumn(Refund::getReason).setHeader("Reason");
        
        grid.addComponentColumn(refund -> {
            HorizontalLayout actions = new HorizontalLayout();
            Button viewBtn = new Button(VaadinIcon.EYE.create());
            Button processBtn = new Button(VaadinIcon.CHECK.create());
            
            viewBtn.addClickListener(e -> showRefundDetails(refund));
            processBtn.addClickListener(e -> processRefund(refund));
            processBtn.setEnabled("Pending".equals(refund.getStatus()));
            
            actions.add(viewBtn, processBtn);
            return actions;
        }).setHeader("Actions");
        
        grid.getStyle()
           .set("background-color", "white")
           .set("border-radius", "10px")
           .set("box-shadow", "0 2px 5px rgba(0,0,0,0.05)");
        
        return grid;
    }

    private void showTransactionDetails(Transaction transaction) {
        Dialog dialog = new Dialog();
        dialog.setCloseOnEsc(true);
        dialog.setCloseOnOutsideClick(true);
        dialog.setWidth("700px");
        
        H2 header = new H2("Transaction #" + transaction.getId());
        
        VerticalLayout details = new VerticalLayout();
        details.setSpacing(false);
        details.setPadding(false);
        
        details.add(new H3("Transaction Information"));
        details.add(createDetailRow("User:", transaction.getUserName() + " (" + transaction.getUserId() + ")"));
        details.add(createDetailRow("Description:", transaction.getDescription()));
        details.add(createDetailRow("Date/Time:", transaction.getTimestamp()));
        details.add(createDetailRow("Amount:", "$" + transaction.getAmount()));
        details.add(createDetailRow("Payment Method:", transaction.getPaymentMethod()));
        details.add(createDetailRow("Status:", transaction.getStatus()));
        
        if ("Failed".equals(transaction.getStatus())) {
            details.add(new H3("Failure Reason"));
            details.add(new Paragraph("Payment gateway timeout (Code: PG-504)"));
        }
        
        Button closeBtn = new Button("Close", VaadinIcon.CLOSE.create());
        closeBtn.addClickListener(e -> dialog.close());
        
        dialog.add(header, details, closeBtn);
        dialog.open();
    }

    private void showRefundDialog(Transaction transaction) {
        Dialog dialog = new Dialog();
        dialog.setCloseOnEsc(true);
        dialog.setCloseOnOutsideClick(true);
        dialog.setWidth("500px");
        
        H2 header = new H2("Process Refund for Transaction #" + transaction.getId());
        
        FormLayout form = new FormLayout();
        TextField amountField = new TextField("Amount to Refund");
        amountField.setValue("$" + transaction.getAmount());
        amountField.setReadOnly(true);
        
        TextArea reasonField = new TextArea("Reason for Refund");
        reasonField.setHeight("100px");
        
        form.add(amountField, reasonField);
        
        HorizontalLayout buttons = new HorizontalLayout();
        Button processBtn = new Button("Process Refund", VaadinIcon.CHECK.create());
        Button cancelBtn = new Button("Cancel", VaadinIcon.CLOSE.create());
        buttons.add(processBtn, cancelBtn);
        
        processBtn.addClickListener(e -> {
            // In a real app, you would process the refund here
            Notification.show("Refund processed successfully");
            dialog.close();
        });
        
        cancelBtn.addClickListener(e -> dialog.close());
        
        dialog.add(header, form, buttons);
        dialog.open();
    }

    private void showSubscriptionDetails(Subscription subscription) {
        Dialog dialog = new Dialog();
        dialog.setCloseOnEsc(true);
        dialog.setCloseOnOutsideClick(true);
        dialog.setWidth("700px");
        
        H2 header = new H2("Subscription #" + subscription.getId());
        
        VerticalLayout details = new VerticalLayout();
        details.setSpacing(false);
        details.setPadding(false);
        
        details.add(new H3("Subscription Information"));
        details.add(createDetailRow("User:", subscription.getUserName() + " (" + subscription.getUserId() + ")"));
        details.add(createDetailRow("Plan:", subscription.getPlan()));
        details.add(createDetailRow("Start Date:", subscription.getStartDate()));
        details.add(createDetailRow("End Date:", subscription.getEndDate()));
        details.add(createDetailRow("Status:", subscription.getStatus()));
        details.add(createDetailRow("Amount:", "$" + subscription.getAmount()));
        details.add(createDetailRow("Billing Cycle:", subscription.getBillingCycle()));
        
        details.add(new H3("Payment History"));
        // In a real app, you would show actual payment history
        Grid<Transaction> paymentHistory = new Grid<>();
        paymentHistory.addColumn(Transaction::getId).setHeader("Transaction ID");
        paymentHistory.addColumn(Transaction::getTimestamp).setHeader("Date");
        paymentHistory.addColumn(t -> "$" + t.getAmount()).setHeader("Amount");
        paymentHistory.addColumn(Transaction::getStatus).setHeader("Status");
        paymentHistory.setHeight("200px");
        
        details.add(paymentHistory);
        
        Button closeBtn = new Button("Close", VaadinIcon.CLOSE.create());
        closeBtn.addClickListener(e -> dialog.close());
        
        dialog.add(header, details, closeBtn);
        dialog.open();
    }

    private void showEditSubscriptionDialog(Subscription subscription) {
        Dialog dialog = new Dialog();
        dialog.setCloseOnEsc(true);
        dialog.setCloseOnOutsideClick(true);
        dialog.setWidth("500px");
        
        H2 header = new H2("Edit Subscription #" + subscription.getId());
        
        FormLayout form = new FormLayout();
        TextField idField = new TextField("Subscription ID");
        idField.setValue(subscription.getId());
        idField.setReadOnly(true);
        
        TextField userField = new TextField("User");
        userField.setValue(subscription.getUserName() + " (" + subscription.getUserId() + ")");
        userField.setReadOnly(true);
        
        ComboBox<String> planField = new ComboBox<>("Plan");
        planField.setItems("Basic", "Premium", "Enterprise");
        planField.setValue(subscription.getPlan());
        
        TextField startDateField = new TextField("Start Date");
        startDateField.setValue(subscription.getStartDate());
        startDateField.setReadOnly(true);
        
        TextField endDateField = new TextField("End Date");
        endDateField.setValue(subscription.getEndDate());
        
        ComboBox<String> statusField = new ComboBox<>("Status");
        statusField.setItems("Active", "Cancelled", "Expired", "Paused");
        statusField.setValue(subscription.getStatus());
        
        NumberField amountField = new NumberField("Amount");
        amountField.setValue(subscription.getAmount());
        
        ComboBox<String> billingField = new ComboBox<>("Billing Cycle");
        billingField.setItems("Monthly", "Quarterly", "Annual");
        billingField.setValue(subscription.getBillingCycle());
        
        form.add(idField, userField, planField, startDateField, endDateField, 
                statusField, amountField, billingField);
        
        HorizontalLayout buttons = new HorizontalLayout();
        Button saveBtn = new Button("Save Changes", VaadinIcon.CHECK.create());
        Button cancelBtn = new Button("Cancel", VaadinIcon.CLOSE.create());
        buttons.add(saveBtn, cancelBtn);
        
        saveBtn.addClickListener(e -> {
            // In a real app, you would save the changes here
            Notification.show("Subscription updated successfully");
            dialog.close();
        });
        
        cancelBtn.addClickListener(e -> dialog.close());
        
        dialog.add(header, form, buttons);
        dialog.open();
    }

    private void cancelSubscription(Subscription subscription) {
        Dialog confirmDialog = new Dialog();
        confirmDialog.setCloseOnEsc(true);
        confirmDialog.setCloseOnOutsideClick(true);
        confirmDialog.setWidth("400px");
        
        H3 header = new H3("Confirm Subscription Cancellation");
        Paragraph message = new Paragraph("Are you sure you want to cancel this subscription?");
        
        HorizontalLayout buttons = new HorizontalLayout();
        Button confirmBtn = new Button("Confirm", VaadinIcon.CHECK.create());
        Button cancelBtn = new Button("Cancel", VaadinIcon.CLOSE.create());
        buttons.add(confirmBtn, cancelBtn);
        
        confirmBtn.addClickListener(e -> {
            // In a real app, you would cancel the subscription here
            subscription.setStatus("Cancelled");
            Notification.show("Subscription cancelled successfully");
            confirmDialog.close();
        });
        
        cancelBtn.addClickListener(e -> confirmDialog.close());
        
        confirmDialog.add(header, message, buttons);
        confirmDialog.open();
    }

    private void showRefundDetails(Refund refund) {
        Dialog dialog = new Dialog();
        dialog.setCloseOnEsc(true);
        dialog.setCloseOnOutsideClick(true);
        dialog.setWidth("700px");
        
        H2 header = new H2("Refund #" + refund.getId());
        
        VerticalLayout details = new VerticalLayout();
        details.setSpacing(false);
        details.setPadding(false);
        
        details.add(new H3("Refund Information"));
        details.add(createDetailRow("Transaction ID:", refund.getTransactionId()));
        details.add(createDetailRow("User:", refund.getUserName() + " (" + refund.getUserId() + ")"));
        details.add(createDetailRow("Amount:", "$" + refund.getAmount()));
        details.add(createDetailRow("Date Processed:", refund.getDateProcessed()));
        details.add(createDetailRow("Status:", refund.getStatus()));
        details.add(createDetailRow("Reason:", refund.getReason()));
        
        if ("Pending".equals(refund.getStatus())) {
            details.add(new H3("Process Refund"));
            Button approveBtn = new Button("Approve Refund", VaadinIcon.CHECK.create());
            Button rejectBtn = new Button("Reject Refund", VaadinIcon.CLOSE.create());
            
            approveBtn.addClickListener(e -> {
                refund.setStatus("Completed");
                Notification.show("Refund approved and processed");
                dialog.close();
            });
            
            rejectBtn.addClickListener(e -> {
                refund.setStatus("Rejected");
                Notification.show("Refund request rejected");
                dialog.close();
            });
            
            details.add(new HorizontalLayout(approveBtn, rejectBtn));
        }
        
        Button closeBtn = new Button("Close", VaadinIcon.CLOSE.create());
        closeBtn.addClickListener(e -> dialog.close());
        
        dialog.add(header, details, closeBtn);
        dialog.open();
    }

    private void processRefund(Refund refund) {
        // In a real app, you would process the refund with the payment gateway
        refund.setStatus("Completed");
        Notification.show("Refund processed successfully");
    }

    private HorizontalLayout createDetailRow(String label, String value) {
        HorizontalLayout row = new HorizontalLayout();
        row.setSpacing(true);
        row.setAlignItems(Alignment.BASELINE);
        
        Paragraph labelPara = new Paragraph(label);
        labelPara.getStyle().set("font-weight", "bold").set("margin", "0");
        
        Paragraph valuePara = new Paragraph(value);
        valuePara.getStyle().set("margin", "0");
        
        row.add(labelPara, valuePara);
        return row;
    }

    // Dummy data classes
    private static class Transaction {
        private String id;
        private String userId;
        private String userName;
        private String description;
        private String timestamp;
        private double amount;
        private String paymentMethod;
        private String status;

        public Transaction(String id, String userId, String userName, String description, 
                          String timestamp, double amount, String paymentMethod, String status) {
            this.id = id;
            this.userId = userId;
            this.userName = userName;
            this.description = description;
            this.timestamp = timestamp;
            this.amount = amount;
            this.paymentMethod = paymentMethod;
            this.status = status;
        }

        // Getters
        public String getId() { return id; }
        public String getUserId() { return userId; }
        public String getUserName() { return userName; }
        public String getDescription() { return description; }
        public String getTimestamp() { return timestamp; }
        public double getAmount() { return amount; }
        public String getPaymentMethod() { return paymentMethod; }
        public String getStatus() { return status; }
    }

    private static class Subscription {
        private String id;
        private String userId;
        private String userName;
        private String plan;
        private String startDate;
        private String endDate;
        private String status;
        private double amount;
        private String billingCycle;

        public Subscription(String id, String userId, String userName, String plan, 
                          String startDate, String endDate, String status, 
                          double amount, String billingCycle) {
            this.id = id;
            this.userId = userId;
            this.userName = userName;
            this.plan = plan;
            this.startDate = startDate;
            this.endDate = endDate;
            this.status = status;
            this.amount = amount;
            this.billingCycle = billingCycle;
        }

        // Getters
        public String getId() { return id; }
        public String getUserId() { return userId; }
        public String getUserName() { return userName; }
        public String getPlan() { return plan; }
        public String getStartDate() { return startDate; }
        public String getEndDate() { return endDate; }
        public String getStatus() { return status; }
        public double getAmount() { return amount; }
        public String getBillingCycle() { return billingCycle; }
        
        // Setters
        public void setStatus(String status) { this.status = status; }
        public void setEndDate(String endDate) { this.endDate = endDate; }
    }

    private static class Refund {
        private String id;
        private String transactionId;
        private String userId;
        private String userName;
        private double amount;
        private String dateProcessed;
        private String status;
        private String reason;

        public Refund(String id, String transactionId, String userId, String userName, 
                     double amount, String dateProcessed, String status, String reason) {
            this.id = id;
            this.transactionId = transactionId;
            this.userId = userId;
            this.userName = userName;
            this.amount = amount;
            this.dateProcessed = dateProcessed;
            this.status = status;
            this.reason = reason;
        }

        public String getId() { return id; }
        public String getTransactionId() { return transactionId; }
        public String getUserId() { return userId; }
        public String getUserName() { return userName; }
        public double getAmount() { return amount; }
        public String getDateProcessed() { return dateProcessed; }
        public String getStatus() { return status; }
        public String getReason() { return reason; }
        
        // Setters
        public void setStatus(String status) { this.status = status; }
    }
}

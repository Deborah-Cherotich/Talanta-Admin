package com.example.talanta.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Route("users")
public class UsersView extends VerticalLayout {

    public UsersView() {
        setSizeFull(); // Ensure this fills the viewport
        getStyle().set("overflow", "auto");
        HorizontalLayout layout = new HorizontalLayout();
        VerticalLayout sidebar = createSidebar();
        VerticalLayout mainContent = createMainContent();

        layout.setSizeFull();
        sidebar.setWidth("240px");
        sidebar.getStyle().set("background", "#F4F4F4");
        sidebar.getStyle().set("height", "850px");
        layout.add(sidebar, mainContent);
        add(layout);

    }

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
        main.getStyle()
                .set("height", "100vh");

        // Header section
        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.setWidthFull();
        headerLayout.setJustifyContentMode(FlexLayout.JustifyContentMode.BETWEEN);

        VerticalLayout headingLayout = new VerticalLayout();
        H1 heading = new H1("User Management");
        heading.getStyle().set("color", "#E65100").set("margin", "0");
        Paragraph subtitle = new Paragraph("Manage student accounts and activities");
        headingLayout.add(heading, subtitle);

        Button exportBtn = new Button("Export Data", VaadinIcon.DOWNLOAD.create());
        exportBtn.addClickListener(e -> exportUserData());

        headerLayout.add(headingLayout, exportBtn);
        main.add(headerLayout);

        // Stats cards
        HorizontalLayout statsLayout = new HorizontalLayout();
        statsLayout.setWidthFull();
        statsLayout.setSpacing(true);

        statsLayout.add(
                createStatCard("1,234", "Total Users", VaadinIcon.USERS),
                createStatCard("876", "Active Users", VaadinIcon.USER_CHECK),
                createStatCard("358", "Completed Tests", VaadinIcon.CLIPBOARD_CHECK),
                createStatCard("642", "Applied to Institutions", VaadinIcon.ACADEMY_CAP)
        );
        main.add(statsLayout);

        // Filter and search bar
        HorizontalLayout filterLayout = new HorizontalLayout();
        filterLayout.setWidthFull();
        filterLayout.setAlignItems(Alignment.BASELINE);

        TextField searchField = new TextField();
        searchField.setPlaceholder("Search users...");
        searchField.setPrefixComponent(VaadinIcon.SEARCH.create());
        searchField.setWidth("300px");

        ComboBox<String> statusFilter = new ComboBox<>("Status");
        statusFilter.setItems("All", "Active", "Inactive", "Suspended");
        statusFilter.setValue("All");

        ComboBox<String> testFilter = new ComboBox<>("Test Status");
        testFilter.setItems("All", "Completed", "Incomplete");
        testFilter.setValue("All");

        Button clearFiltersBtn = new Button("Clear Filters", VaadinIcon.CLOSE_SMALL.create());

        filterLayout.add(searchField, statusFilter, testFilter, clearFiltersBtn);
        main.add(filterLayout);

        // Users grid
        Grid<StudentUser> usersGrid = createUsersGrid();
        usersGrid.setHeight("350px");

        // Pagination controls
        HorizontalLayout paginationLayout = new HorizontalLayout();
        paginationLayout.setWidthFull();
        paginationLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        Button prevPageBtn = new Button("Previous", VaadinIcon.ARROW_LEFT.create());
        Span pageInfo = new Span("Page 1 of 5");
        Button nextPageBtn = new Button("Next", VaadinIcon.ARROW_RIGHT.create());

        paginationLayout.add(prevPageBtn, pageInfo, nextPageBtn);

        // Wrap grid and pagination in a container
        VerticalLayout gridContainer = new VerticalLayout(usersGrid, paginationLayout);
        gridContainer.setSizeFull();
        gridContainer.setFlexGrow(1, usersGrid);
        main.add(gridContainer);
        main.setFlexGrow(1, gridContainer);

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

    private Grid<StudentUser> createUsersGrid() {
        Grid<StudentUser> grid = new Grid<>();

        List<StudentUser> users = Arrays.asList(
                new StudentUser("1001", "john.doe@example.com", "John Doe", "Active",
                        LocalDate.now().minusDays(10), true, "Analytical", "Software Engineer"),
                new StudentUser("1002", "jane.smith@example.com", "Jane Smith", "Active",
                        LocalDate.now().minusDays(25), true, "Social", "Teacher"),
                new StudentUser("1003", "alice.johnson@example.com", "Alice Johnson", "Active",
                        LocalDate.now().minusDays(45), false, null, null),
                new StudentUser("1004", "bob.brown@example.com", "Bob Brown", "Inactive",
                        LocalDate.now().minusDays(60), true, "Practical", "Electrician"),
                new StudentUser("1005", "charlie.davis@example.com", "Charlie Davis", "Suspended",
                        LocalDate.now().minusDays(90), false, null, null)
        );

        grid.setItems(users);

        grid.addColumn(StudentUser::getId).setHeader("ID").setWidth("80px");
        grid.addColumn(StudentUser::getName).setHeader("Name");
        grid.addColumn(StudentUser::getEmail).setHeader("Email");

        grid.addComponentColumn(user -> {
            Span status = new Span(user.getStatus());
            switch (user.getStatus()) {
                case "Active":
                    status.getStyle().set("color", "green");
                    break;
                case "Inactive":
                    status.getStyle().set("color", "gray");
                    break;
                case "Suspended":
                    status.getStyle().set("color", "red");
                    break;
            }
            return status;
        }).setHeader("Status");

        grid.addColumn(user -> user.getJoinDate().format(DateTimeFormatter.ofPattern("MMM dd, yyyy")))
                .setHeader("Join Date")
                .setWidth("120px");

        grid.addComponentColumn(user -> {
            Icon icon = user.isTestCompleted()
                    ? VaadinIcon.CHECK.create() : VaadinIcon.CLOSE.create();
            icon.setColor(user.isTestCompleted() ? "green" : "red");
            return icon;
        }).setHeader("Test Completed").setWidth("100px");

        grid.addColumn(user -> user.getPersonalityType() != null ? user.getPersonalityType() : "-")
                .setHeader("Personality");
        grid.addColumn(user -> user.getCareerRecommendation() != null ? user.getCareerRecommendation() : "-")
                .setHeader("Career Match");

        grid.addComponentColumn(user -> {
            HorizontalLayout actions = new HorizontalLayout();

            Button viewBtn = new Button(VaadinIcon.EYE.create());
            viewBtn.addClickListener(e -> showUserDetails(user));

            Button editBtn = new Button(VaadinIcon.EDIT.create());
            editBtn.addClickListener(e -> showEditUserDialog(user));

            Button statusBtn = new Button();
            if (user.getStatus().equals("Suspended")) {
                statusBtn.setIcon(VaadinIcon.UNLOCK.create());
                statusBtn.addClickListener(e -> unsuspendUser(user));
            } else {
                statusBtn.setIcon(VaadinIcon.LOCK.create());
                statusBtn.addClickListener(e -> suspendUser(user));
            }

            Button deleteBtn = new Button(VaadinIcon.TRASH.create());
            deleteBtn.addClickListener(e -> confirmDeleteUser(user));
            deleteBtn.getStyle().set("color", "red");

            actions.add(viewBtn, editBtn, statusBtn, deleteBtn);
            actions.setSpacing(true);
            return actions;
        }).setHeader("Actions").setWidth("200px");

        grid.getStyle()
                .set("background-color", "white")
                .set("height", "auto")
                .set("border-radius", "10px")
                .set("box-shadow", "0 2px 5px rgba(0,0,0,0.05)");

        return grid;
    }

    private void showUserDetails(StudentUser user) {
        Dialog dialog = new Dialog();
        dialog.setCloseOnEsc(true);
        dialog.setCloseOnOutsideClick(true);
        dialog.setWidth("700px");

        H2 header = new H2("User Details: " + user.getName());

        VerticalLayout details = new VerticalLayout();
        details.setSpacing(false);
        details.setPadding(false);

        // Basic Information
        details.add(new H3("Basic Information"));
        details.add(createDetailRow("ID:", user.getId()));
        details.add(createDetailRow("Name:", user.getName()));
        details.add(createDetailRow("Email:", user.getEmail()));
        details.add(createDetailRow("Status:", user.getStatus()));
        details.add(createDetailRow("Join Date:",
                user.getJoinDate().format(DateTimeFormatter.ofPattern("MMMM d, yyyy"))));

        // Test Information
        details.add(new H3("Personality Test"));
        if (user.isTestCompleted()) {
            details.add(createDetailRow("Completed:", "Yes"));
            details.add(createDetailRow("Personality Type:", user.getPersonalityType()));
            details.add(createDetailRow("Career Recommendation:", user.getCareerRecommendation()));
        } else {
            details.add(new Paragraph("Test not completed yet"));
        }

        // Institution Applications
        details.add(new H3("Institution Applications"));
        // In a real app, you would show actual applications
        VerticalLayout applicationsList = new VerticalLayout();
        if (user.getName().equals("John Doe")) {
            applicationsList.add(new Paragraph("• University of Nairobi - Computer Science (Pending)"));
            applicationsList.add(new Paragraph("• Strathmore University - Business Admin (Approved)"));
        } else if (user.getName().equals("Jane Smith")) {
            applicationsList.add(new Paragraph("• Kenyatta University - Medicine (Rejected)"));
        } else {
            applicationsList.add(new Paragraph("No applications submitted"));
        }
        details.add(applicationsList);

        Button closeBtn = new Button("Close", VaadinIcon.CLOSE.create());
        closeBtn.addClickListener(e -> dialog.close());

        dialog.add(header, details, closeBtn);
        dialog.open();
    }

    private void showEditUserDialog(StudentUser user) {
        Dialog dialog = new Dialog();
        dialog.setCloseOnEsc(true);
        dialog.setCloseOnOutsideClick(true);
        dialog.setWidth("500px");

        H2 header = new H2("Edit User: " + user.getName());

        FormLayout form = new FormLayout();

        // Read-only fields
        TextField idField = new TextField("User ID");
        idField.setValue(user.getId());
        idField.setReadOnly(true);

        TextField joinDateField = new TextField("Join Date");
        joinDateField.setValue(user.getJoinDate().format(DateTimeFormatter.ofPattern("MMMM d, yyyy")));
        joinDateField.setReadOnly(true);

        // Editable fields
        TextField nameField = new TextField("Full Name");
        nameField.setValue(user.getName());

        EmailField emailField = new EmailField("Email");
        emailField.setValue(user.getEmail());

        ComboBox<String> statusField = new ComboBox<>("Status");
        statusField.setItems("Active", "Inactive", "Suspended");
        statusField.setValue(user.getStatus());

        form.add(idField, joinDateField, nameField, emailField, statusField);
        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));

        HorizontalLayout buttons = new HorizontalLayout();
        Button saveBtn = new Button("Save Changes", VaadinIcon.CHECK.create());
        saveBtn.addClickListener(e -> {
            // In a real app, you would save the changes here
            user.setName(nameField.getValue());
            user.setEmail(emailField.getValue());
            user.setStatus(statusField.getValue());
            Notification.show("User updated successfully");
            dialog.close();
        });

        Button cancelBtn = new Button("Cancel", VaadinIcon.CLOSE.create());
        cancelBtn.addClickListener(e -> dialog.close());

        buttons.add(saveBtn, cancelBtn);

        dialog.add(header, form, buttons);
        dialog.open();
    }

    private void suspendUser(StudentUser user) {
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Suspend User");
        dialog.setText("Are you sure you want to suspend " + user.getName() + "?");

        dialog.setCancelable(true);
        dialog.setConfirmText("Suspend");
        dialog.setConfirmButtonTheme("error primary");

        dialog.addConfirmListener(e -> {
            user.setStatus("Suspended");
            Notification.show(user.getName() + " has been suspended");
            // In a real app, you would update the database here
        });

        dialog.open();
    }

    private void unsuspendUser(StudentUser user) {
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Unsuspend User");
        dialog.setText("Are you sure you want to unsuspend " + user.getName() + "?");

        dialog.setCancelable(true);
        dialog.setConfirmText("Unsuspend");
        dialog.setConfirmButtonTheme("primary");

        dialog.addConfirmListener(e -> {
            user.setStatus("Active");
            Notification.show(user.getName() + " has been unsuspended");
            // In a real app, you would update the database here
        });

        dialog.open();
    }

    private void confirmDeleteUser(StudentUser user) {
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Delete User");
        dialog.setText("Are you sure you want to permanently delete " + user.getName() + "? This cannot be undone.");

        dialog.setCancelable(true);
        dialog.setConfirmText("Delete");
        dialog.setConfirmButtonTheme("error primary");

        dialog.addConfirmListener(e -> {
            // In a real app, you would delete from database here
            Notification.show(user.getName() + " has been deleted");
            // You would also refresh the grid here
        });

        dialog.open();
    }

    private void exportUserData() {
        Notification.show("Preparing user data export...");
        // In a real app, you would generate a CSV or Excel file here
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

    // Dummy data class
    private static class StudentUser {

        private String id;
        private String email;
        private String name;
        private String status;
        private LocalDate joinDate;
        private boolean testCompleted;
        private String personalityType;
        private String careerRecommendation;

        public StudentUser(String id, String email, String name, String status,
                LocalDate joinDate, boolean testCompleted,
                String personalityType, String careerRecommendation) {
            this.id = id;
            this.email = email;
            this.name = name;
            this.status = status;
            this.joinDate = joinDate;
            this.testCompleted = testCompleted;
            this.personalityType = personalityType;
            this.careerRecommendation = careerRecommendation;
        }

        // Getters
        public String getId() {
            return id;
        }

        public String getEmail() {
            return email;
        }

        public String getName() {
            return name;
        }

        public String getStatus() {
            return status;
        }

        public LocalDate getJoinDate() {
            return joinDate;
        }

        public boolean isTestCompleted() {
            return testCompleted;
        }

        public String getPersonalityType() {
            return personalityType;
        }

        public String getCareerRecommendation() {
            return careerRecommendation;
        }

        // Setters
        public void setName(String name) {
            this.name = name;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}

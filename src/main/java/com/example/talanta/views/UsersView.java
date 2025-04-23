//
//package com.example.talanta.views;
//
//import com.vaadin.flow.component.Component;
//import com.vaadin.flow.component.Html;
//import com.vaadin.flow.component.UI; // Added for navigation
//import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.button.ButtonVariant;
//import com.vaadin.flow.component.combobox.ComboBox;
//import com.vaadin.flow.component.datepicker.DatePicker;
//import com.vaadin.flow.component.dialog.Dialog;
//import com.vaadin.flow.component.grid.Grid;
//import com.vaadin.flow.component.grid.GridVariant;
//import com.vaadin.flow.component.html.*;
//import com.vaadin.flow.component.icon.Icon;
//import com.vaadin.flow.component.icon.VaadinIcon;
//import com.vaadin.flow.component.notification.Notification;
//import com.vaadin.flow.component.notification.NotificationVariant;
//import com.vaadin.flow.component.orderedlayout.FlexComponent;
//import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.component.textfield.EmailField;
//// import com.vaadin.flow.component.textfield.PasswordField; // No longer needed
//import com.vaadin.flow.component.textfield.TextField;
//import com.vaadin.flow.data.binder.Binder;
//import com.vaadin.flow.data.renderer.ComponentRenderer;
//import com.vaadin.flow.data.value.ValueChangeMode;
//import com.vaadin.flow.router.PageTitle;
//import com.vaadin.flow.router.Route;
//import com.vaadin.flow.spring.annotation.SpringComponent;
//import org.springframework.beans.factory.config.ConfigurableBeanFactory;
//import org.springframework.context.annotation.Scope;
//
//import java.time.LocalDate;
//import java.time.ZoneId;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@PageTitle("Users Management")
//@Route("users")
//@SpringComponent
//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//public class UsersView extends VerticalLayout {
//
//    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM d, uuuu"); // Consistent format
//    private final String primaryColor = "#E65100"; // Orange theme color
//    private final String primaryContrastColor = "#FFFFFF"; // White text for primary buttons
//
//    private Grid<User> userGrid;
//    private List<User> allUsers; // In-memory user list (replace with service/repository)
//
//    // Filter components
//    private TextField searchField;
//    private ComboBox<User.Role> roleFilter;
//    private ComboBox<User.Status> statusFilter;
//    private DatePicker dateFilter;
//
//    public UsersView() {
//        setSizeFull();
//        setPadding(false);
//        setSpacing(false);
//
//        HorizontalLayout layout = new HorizontalLayout();
//        layout.setSizeFull();
//        layout.setSpacing(false);
//
//        VerticalLayout sidebar = createSidebar();
//        VerticalLayout mainContent = createMainContent();
//
//        layout.add(sidebar, mainContent);
//        layout.setFlexGrow(1, mainContent);
//
//        add(layout);
//
//        loadUsers();
//        configureGrid();
//        refreshGrid();
//    }
//
//    // --- Sidebar (Reused and adapted from AdminDashboardView) ---
//    private VerticalLayout createSidebar() {
//        VerticalLayout sidebar = new VerticalLayout();
//        sidebar.setWidth("240px");
//        sidebar.setHeightFull();
//        sidebar.getStyle()
//                .set("background", "#F4F4F4")
//                .set("padding", "0");
//        sidebar.setSpacing(false);
//        sidebar.setAlignItems(FlexComponent.Alignment.STRETCH);
//
//        Image logo = new Image("images/2.png", "Talanta Logo");
//        logo.setWidth("150px");
//        logo.setHeight("auto");
//        Div logoWrapper = new Div(logo);
//        logoWrapper.getStyle()
//            .set("text-align", "center")
//            .set("padding", "15px 0");
//        sidebar.add(logoWrapper);
//
//        sidebar.add(
//                createNavItem("Dashboard", VaadinIcon.DASHBOARD, "Admindashboard", false),
//                createNavItem("Users", VaadinIcon.USERS, "users", true), // Active
//                createNavItem("Personality Tests", VaadinIcon.CLIPBOARD_TEXT, "personality-tests", false),
//                createNavItem("Courses & Careers", VaadinIcon.BOOK, "courses-careers", false),
//                createNavItem("Institutions", VaadinIcon.ACADEMY_CAP, "institutions", false),
//                createNavItem("Payments", VaadinIcon.CREDIT_CARD, "payments", false),
//                createNavItem("Reports", VaadinIcon.CHART, "reports", false),
//                createNavItem("Content", VaadinIcon.FILE_TEXT, "content", false),
//                createNavItem("Notifications", VaadinIcon.BELL, "notifications", false),
//                createNavItem("Settings", VaadinIcon.COG, "settings", false)
//        );
//
//        return sidebar;
//    }
//
//    private HorizontalLayout createNavItem(String title, VaadinIcon icon, String route, boolean isActive) {
//        Icon menuIcon = icon.create();
//        menuIcon.setColor(isActive ? primaryColor : "#6C757D");
//        menuIcon.getStyle().set("margin-right", "15px");
//
//        Span label = new Span(title);
//        label.getStyle()
//                .set("font-weight", isActive ? "600" : "400")
//                .set("color", isActive ? primaryColor : "#343A40");
//
//        HorizontalLayout nav = new HorizontalLayout(menuIcon, label);
//        nav.setAlignItems(FlexComponent.Alignment.CENTER);
//        nav.getStyle()
//                .set("padding", "12px 20px")
//                .set("cursor", "pointer")
//                .set("transition", "background-color 0.2s ease, color 0.2s ease");
//
//        if (isActive) {
//            nav.getStyle()
//                    .set("background-color", "#FDEEE2")
//                    .set("border-left", "4px solid " + primaryColor);
//             nav.getStyle().set("padding-left","16px");
//        } else {
//            nav.getElement().addEventListener("mouseover", e -> {
//                 nav.getStyle().set("background-color", "#E9ECEF");
//            });
//            nav.getElement().addEventListener("mouseout", e -> {
//                 nav.getStyle().remove("background-color");
//            });
//        }
//
//        nav.addClickListener(e -> UI.getCurrent().navigate(route));
//        return nav;
//    }
//    // --- End Sidebar ---
//
//    // --- Main Content Area ---
//    private VerticalLayout createMainContent() {
//        VerticalLayout main = new VerticalLayout();
//        main.setSizeFull();
//        main.setPadding(true);
//        main.setSpacing(true);
//
//        // Header - Apply primary color
//        H2 title = new H2("User Management");
//        title.getStyle()
//            .set("color", primaryColor) // Use theme color for header
//            .set("margin", "0 0 10px 0");
//
//        HorizontalLayout toolbar = createToolbar();
//
//        // User Grid - Use LUMO_COMFORTABLE for more spacing
//        userGrid = new Grid<>(User.class, false);
//        userGrid.setSizeFull(); // Grid takes full size within its container
//
//        main.add(title, toolbar, userGrid);
//        main.setFlexGrow(1, userGrid); // Grid expands to fill vertical space, enables scrolling
//
//        return main;
//    }
//
//    private HorizontalLayout createToolbar() {
//        HorizontalLayout toolbar = new HorizontalLayout();
//        toolbar.setWidthFull();
//        toolbar.setPadding(false);
//        toolbar.setSpacing(true);
//        toolbar.setAlignItems(FlexComponent.Alignment.BASELINE);
//
//        searchField = new TextField();
//        searchField.setPlaceholder("Search by name or email...");
//        searchField.setPrefixComponent(VaadinIcon.SEARCH.create());
//        searchField.setValueChangeMode(ValueChangeMode.LAZY);
//        searchField.addValueChangeListener(e -> refreshGrid());
//        searchField.getStyle().set("min-width", "250px");
//
//        roleFilter = new ComboBox<>("Role");
//        roleFilter.setItems(User.Role.values());
//        roleFilter.setPlaceholder("Any Role");
//        roleFilter.setClearButtonVisible(true);
//        roleFilter.addValueChangeListener(e -> refreshGrid());
//
//        statusFilter = new ComboBox<>("Status");
//        statusFilter.setItems(User.Status.values());
//        statusFilter.setPlaceholder("Any Status");
//        statusFilter.setClearButtonVisible(true);
//        statusFilter.addValueChangeListener(e -> refreshGrid());
//
//        dateFilter = new DatePicker("Registered After");
//        dateFilter.setClearButtonVisible(true);
//        dateFilter.addValueChangeListener(e -> refreshGrid());
//
//        HorizontalLayout filterLayout = new HorizontalLayout(roleFilter, statusFilter, dateFilter);
//        filterLayout.setSpacing(true);
//        filterLayout.setPadding(false);
//        filterLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
//
//        Button addUserButton = new Button("Add User", VaadinIcon.PLUS.create());
//        // Apply theme color directly
//        addUserButton.getStyle()
//                     .set("background-color", primaryColor)
//                     .set("color", primaryContrastColor);
//        // Optionally remove Lumo variant if applying custom background:
//        // addUserButton.removeThemeVariants(ButtonVariant.LUMO_PRIMARY);
//        addUserButton.addClickListener(e -> openUserForm(new User()));
//
//        HorizontalLayout actionButtons = new HorizontalLayout(addUserButton);
//        actionButtons.setSpacing(true);
//        actionButtons.setPadding(false);
//
//        toolbar.add(searchField, filterLayout, actionButtons);
//        toolbar.setFlexGrow(1, filterLayout);
//        toolbar.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
//
//        return toolbar;
//    }
//
//    private void configureGrid() {
//        userGrid.addColumn(User::getFullName).setHeader("Full Name").setSortable(true).setAutoWidth(true).setFlexGrow(1).setFrozen(true);
//        userGrid.addColumn(User::getEmail).setHeader("Email").setSortable(true).setAutoWidth(true).setFlexGrow(1);
//        userGrid.addColumn(User::getPhoneNumber).setHeader("Phone Number").setSortable(false).setAutoWidth(true).setFlexGrow(0);
//        userGrid.addColumn(User::getRole).setHeader("Role").setSortable(true).setAutoWidth(true).setFlexGrow(0);
//
//        userGrid.addColumn(new ComponentRenderer<>(user -> {
//            Span statusBadge = new Span(user.getStatus().name());
//            statusBadge.getElement().getThemeList().add("badge small");
//            if (user.getStatus() == User.Status.ACTIVE) {
//                statusBadge.getElement().getThemeList().add("success primary");
//            } else {
//                statusBadge.getElement().getThemeList().add("error primary");
//            }
//            statusBadge.getStyle().set("text-transform", "capitalize");
//            return statusBadge;
//        })).setHeader("Status").setSortable(true).setComparator(User::getStatus).setAutoWidth(true).setFlexGrow(0);
//
//        userGrid.addColumn(user -> user.getDateRegistered().format(dateFormatter))
//                .setHeader("Date Registered").setSortable(true)
//                .setComparator(User::getDateRegistered)
//                .setAutoWidth(true).setFlexGrow(0);
//
//        // --- Actions Column ---
//        userGrid.addColumn(new ComponentRenderer<>(user -> {
//            String iconSize = "var(--lumo-icon-size-m)"; // Use Lumo medium icon size
//
//            // Edit Button - Apply theme color to icon
//            Button editButton = new Button(VaadinIcon.EDIT.create());
//            editButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
//            editButton.setTooltipText("Edit User");
//            editButton.getStyle().set("font-size", iconSize);
//            editButton.addClickListener(e -> openUserForm(user));
//
//            // Toggle Status Button - Apply theme color to icon
//            Icon toggleIconVaadin = user.getStatus() == User.Status.ACTIVE ? VaadinIcon.LOCK.create() : VaadinIcon.UNLOCK.create();
//            Button toggleStatusButton = new Button(toggleIconVaadin);
//            toggleStatusButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
//            toggleStatusButton.setTooltipText(user.getStatus() == User.Status.ACTIVE ? "Deactivate User" : "Activate User");
//            toggleStatusButton.getStyle().set("font-size", iconSize);
//            toggleStatusButton.addClickListener(e -> toggleUserStatus(user));
//
//            // Delete Button - Keep error color
//            Button deleteButton = new Button(VaadinIcon.TRASH.create());
//            deleteButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE, ButtonVariant.LUMO_ERROR);
//            deleteButton.setTooltipText("Delete User");
//            deleteButton.getStyle().set("font-size", iconSize);
//            // Icon color is handled by LUMO_ERROR variant
//            deleteButton.addClickListener(e -> confirmDeleteUser(user));
//
//            // View Activity Button - Apply theme color to icon
//            Button activityButton = new Button(VaadinIcon.ARCHIVES.create());
//             activityButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
//             activityButton.setTooltipText("View Activity History");
//             activityButton.getStyle().set("font-size", iconSize);
//             activityButton.addClickListener(e -> viewActivityHistory(user));
//
//            // Layout for action buttons
//            HorizontalLayout actions = new HorizontalLayout(editButton, toggleStatusButton, deleteButton, activityButton);
//            actions.setSpacing(true);
//            actions.setPadding(false);
//            actions.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
//            return actions;
//        })).setHeader("Actions").setFlexGrow(0).setAutoWidth(true).setKey("actions");
//
//        userGrid.asSingleSelect().addValueChangeListener(event -> {
//            // Optional: Handle selection
//        });
//    }
//
//    // --- Data Loading and Filtering ---
//    private void loadUsers() {
//        allUsers = new ArrayList<>(Arrays.asList(
//                new User(1L, "Eleanor Vance", "eleanor.vance@email.com", "555-1234", User.Role.ADMIN, User.Status.ACTIVE, LocalDate.now().minusDays(15)),
//                new User(2L, "Marcus Holloway", "marcus.h@dedsec.net", "555-5678", User.Role.USER, User.Status.ACTIVE, LocalDate.now().minusDays(5)),
//                new User(3L, "Anya Sharma", "anya.sharma@corp.com", "555-9876", User.Role.USER, User.Status.INACTIVE, LocalDate.now().minusDays(30)),
//                new User(4L, "Kenji Tanaka", "kenji.t@gamestudio.jp", "555-1122", User.Role.MODERATOR, User.Status.ACTIVE, LocalDate.now().minusDays(2)),
//                new User(5L, "Sofia Petrova", "sofia.p@research.org", "555-3344", User.Role.USER, User.Status.ACTIVE, LocalDate.now().minusDays(90)),
//                new User(6L, "David Miller", "d.miller@consulting.biz", "555-5566", User.Role.USER, User.Status.INACTIVE, LocalDate.now().minusDays(1)),
//                new User(7L, "Isabelle Moreau", "isabelle.m@artgallery.fr", "555-7788", User.Role.ADMIN, User.Status.ACTIVE, LocalDate.now().minusMonths(5)),
//                new User(8L, "Carlos Rodriguez", "carlos.r@foodtruck.mx", "555-9900", User.Role.USER, User.Status.ACTIVE, LocalDate.now().minusDays(45))
//        ));
//    }
//
//    private void refreshGrid() {
//        List<User> filteredUsers = allUsers.stream()
//                .filter(user -> matchesSearch(user, searchField.getValue()))
//                .filter(user -> matchesFilter(user, roleFilter.getValue()))
//                .filter(user -> matchesFilter(user, statusFilter.getValue()))
//                .filter(user -> matchesFilter(user, dateFilter.getValue()))
//                .collect(Collectors.toList());
//        userGrid.setItems(filteredUsers);
//    }
//
//    // --- Filtering Logic Helpers ---
//    private boolean matchesSearch(User user, String searchTerm) {
//        if (searchTerm == null || searchTerm.isEmpty()) return true;
//        String lowerCaseTerm = searchTerm.toLowerCase();
//        return (user.getFullName() != null && user.getFullName().toLowerCase().contains(lowerCaseTerm)) ||
//               (user.getEmail() != null && user.getEmail().toLowerCase().contains(lowerCaseTerm));
//    }
//    private boolean matchesFilter(User user, User.Role role) { return role == null || user.getRole() == role; }
//    private boolean matchesFilter(User user, User.Status status) { return status == null || user.getStatus() == status; }
//    private boolean matchesFilter(User user, LocalDate date) {
//        return date == null || user.getDateRegistered() == null || !user.getDateRegistered().isBefore(date);
//    }
//
//    // --- Action Handlers (Placeholders - Implement actual logic) ---
//
//    private void openUserForm(User user) {
//        Dialog dialog = new Dialog();
//        H3 dialogTitle = new H3(user.getId() == null ? "Add New User" : "Edit User");
//        dialogTitle.getStyle().set("color", primaryColor).set("margin-top", "0");
//        dialog.add(dialogTitle);
//
//        dialog.setWidth("400px");
//
//        TextField fullName = new TextField("Full Name");
//        fullName.setWidthFull();
//        EmailField email = new EmailField("Email");
//        email.setWidthFull();
//        TextField phone = new TextField("Phone Number");
//        phone.setWidthFull();
//        ComboBox<User.Role> role = new ComboBox<>("Role", User.Role.values());
//        role.setWidthFull();
//        ComboBox<User.Status> status = new ComboBox<>("Status", User.Status.values());
//        status.setWidthFull();
//
//        Binder<User> binder = new Binder<>(User.class);
//        binder.forField(fullName).asRequired("Full name is required").bind(User::getFullName, User::setFullName);
//        binder.forField(email).asRequired("Email is required").bind(User::getEmail, User::setEmail);
//        binder.bind(phone, User::getPhoneNumber, User::setPhoneNumber);
//        binder.forField(role).asRequired("Role is required").bind(User::getRole, User::setRole);
//        binder.forField(status).asRequired("Status is required").bind(User::getStatus, User::setStatus);
//
//        if (user.getId() != null) {
//            binder.readBean(user);
//        } else {
//             role.setValue(User.Role.USER);
//             status.setValue(User.Status.ACTIVE);
//        }
//
//        VerticalLayout formLayout = new VerticalLayout(fullName, email, phone, role, status);
//        formLayout.setPadding(false);
//        formLayout.setSpacing(true);
//        dialog.add(formLayout);
//
//        Button saveButton = new Button("Save", VaadinIcon.CHECK.create());
//        // Apply theme color directly
//        saveButton.getStyle()
//                  .set("background-color", primaryColor)
//                  .set("color", primaryContrastColor);
//        // Optionally remove Lumo variant:
//        // saveButton.removeThemeVariants(ButtonVariant.LUMO_PRIMARY);
//        saveButton.addClickListener(e -> {
//            if (binder.validate().isOk()) {
//                User userToSave = (user.getId() == null) ? new User() : user;
//                boolean success = binder.writeBeanIfValid(userToSave);
//                if (success) {
//                   saveUser(userToSave);
//                   dialog.close();
//                   refreshGrid();
//                } else {
//                     showNotification("Could not save user data.", NotificationVariant.LUMO_ERROR);
//                }
//            } else {
//                 showNotification("Please check the form for errors.", NotificationVariant.LUMO_WARNING);
//            }
//        });
//
//        Button cancelButton = new Button("Cancel");
//        cancelButton.addClickListener(e -> dialog.close());
//        dialog.getFooter().add(cancelButton, saveButton);
//        dialog.open();
//    }
//
//     private void saveUser(User user) {
//        // --- Backend save/update logic placeholder ---
//        if (user.getId() == null) {
//            user.setId(System.currentTimeMillis() % 10000);
//            if (user.getDateRegistered() == null) {
//                 user.setDateRegistered(LocalDate.now());
//            }
//            allUsers.add(user);
//            showNotification("User " + user.getFullName() + " added.", NotificationVariant.LUMO_SUCCESS);
//        } else {
//            allUsers.stream()
//                    .filter(u -> u.getId() != null && u.getId().equals(user.getId()))
//                    .findFirst()
//                    .ifPresent(existingUser -> {
//                        int index = allUsers.indexOf(existingUser);
//                        allUsers.set(index, user);
//                         showNotification("User " + user.getFullName() + " updated.", NotificationVariant.LUMO_SUCCESS);
//                    });
//        }
//        // --- End placeholder ---
//    }
//
//    private void confirmDeleteUser(User user) {
//        Dialog confirmationDialog = new Dialog();
//        H3 dialogTitle = new H3("Confirm Delete");
//        dialogTitle.getStyle().set("color", primaryColor).set("margin-top", "0");
//
//        confirmationDialog.add(
//            dialogTitle,
//            new Html("<p>Are you sure you want to delete user <strong>" + user.getFullName() + "</strong>? <br/>This action cannot be undone.</p>")
//        );
//
//        Button deleteButton = new Button("Delete", VaadinIcon.TRASH.create());
//        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
//        deleteButton.getStyle().set("margin-left", "auto");
//        deleteButton.addClickListener(e -> {
//            deleteUser(user);
//            confirmationDialog.close();
//            refreshGrid();
//        });
//
//        Button cancelButton = new Button("Cancel");
//        cancelButton.addClickListener(e -> confirmationDialog.close());
//        confirmationDialog.getFooter().add(cancelButton, deleteButton);
//        confirmationDialog.open();
//    }
//
//     private void deleteUser(User user) {
//        // --- Backend delete logic placeholder ---
//        allUsers.removeIf(u -> u.getId() != null && u.getId().equals(user.getId()));
//        showNotification("User " + user.getFullName() + " deleted.", NotificationVariant.LUMO_SUCCESS);
//        // --- End placeholder ---
//    }
//
//    private void toggleUserStatus(User user) {
//        // --- Backend status toggle logic placeholder ---
//         user.setStatus(user.getStatus() == User.Status.ACTIVE ? User.Status.INACTIVE : User.Status.ACTIVE);
//         showNotification("Status for " + user.getFullName() + " updated to " + user.getStatus(), NotificationVariant.LUMO_SUCCESS);
//         // --- End placeholder ---
//         userGrid.getDataProvider().refreshItem(user);
//    }
//
//     private void viewActivityHistory(User user) {
//         Dialog activityDialog = new Dialog();
//         H3 dialogTitle = new H3("Activity History: " + user.getFullName());
//         dialogTitle.getStyle().set("color", primaryColor).set("margin-top", "0");
//
//         activityDialog.setWidth("500px");
//         activityDialog.setModal(false);
//         activityDialog.setDraggable(true);
//
//         VerticalLayout content = new VerticalLayout();
//         content.setPadding(false);
//         content.setSpacing(true);
//         content.getStyle().set("padding", "0 1em 1em 1em");
//
//         List<String> activities = Arrays.asList(
//             "Logged in - " + LocalDate.now().minusDays(1).format(dateFormatter),
//             "Updated profile details - " + LocalDate.now().minusDays(3).format(dateFormatter),
//             "Password reset requested - " + LocalDate.now().minusWeeks(1).format(dateFormatter),
//             "Registered - " + user.getDateRegistered().format(dateFormatter)
//         );
//
//         activities.forEach(activity -> {
//             Span activitySpan = new Span(activity);
//             activitySpan.getStyle()
//                .set("font-size", "var(--lumo-font-size-s)")
//                .set("color", "var(--lumo-secondary-text-color)");
//             content.add(activitySpan);
//         });
//
//         activityDialog.add(dialogTitle, content);
//
//         Button closeButton = new Button("Close", e -> activityDialog.close());
//         closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
//         activityDialog.getFooter().add(closeButton);
//         activityDialog.open();
//     }
//
//    // --- Helper Methods ---
//    private void showNotification(String message, NotificationVariant variant) {
//        Notification notification = new Notification(message, 3000, Notification.Position.BOTTOM_CENTER);
//        notification.addThemeVariants(variant);
//        notification.open();
//    }
//
//    // --- User Data Class ---
//    public static class User {
//        public enum Role { ADMIN, MODERATOR, USER }
//        public enum Status { ACTIVE, INACTIVE }
//        private Long id;
//        private String fullName;
//        private String email;
//        private String phoneNumber;
//        private Role role;
//        private Status status;
//        private LocalDate dateRegistered;
//        public User() {}
//        public User(Long id, String fullName, String email, String phoneNumber, Role role, Status status, LocalDate dateRegistered) {
//            this.id = id; this.fullName = fullName; this.email = email; this.phoneNumber = phoneNumber; this.role = role; this.status = status; this.dateRegistered = dateRegistered;
//        }
//        // Getters and Setters...
//        public Long getId() { return id; }
//        public void setId(Long id) { this.id = id; }
//        public String getFullName() { return fullName; }
//        public void setFullName(String fullName) { this.fullName = fullName; }
//        public String getEmail() { return email; }
//        public void setEmail(String email) { this.email = email; }
//        public String getPhoneNumber() { return phoneNumber; }
//        public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
//        public Role getRole() { return role; }
//        public void setRole(Role role) { this.role = role; }
//        public Status getStatus() { return status; }
//        public void setStatus(Status status) { this.status = status; }
//        public LocalDate getDateRegistered() { return dateRegistered; }
//        public void setDateRegistered(LocalDate dateRegistered) { this.dateRegistered = dateRegistered; }
//        @Override public int hashCode() { return id != null ? id.hashCode() : 0; }
//        @Override public boolean equals(Object obj) {
//            if (this == obj) return true; if (obj == null || getClass() != obj.getClass()) return false; User user = (User) obj;
//            if (id == null) { return user.id == null && super.equals(obj); } return id.equals(user.id);
//        }
//    }
//}


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
        HorizontalLayout layout = new HorizontalLayout();
        VerticalLayout sidebar = createSidebar(); // Reusing your existing sidebar
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
        usersGrid.setHeight("500px");
        
        // Pagination controls
        HorizontalLayout paginationLayout = new HorizontalLayout();
        paginationLayout.setWidthFull();
        paginationLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        
        Button prevPageBtn = new Button("Previous", VaadinIcon.ARROW_LEFT.create());
        Span pageInfo = new Span("Page 1 of 5");
        Button nextPageBtn = new Button("Next", VaadinIcon.ARROW_RIGHT.create());
        
        paginationLayout.add(prevPageBtn, pageInfo, nextPageBtn);
        
        main.add(usersGrid, paginationLayout);
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
        
        // Sample data
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
        
        // Basic info columns
        grid.addColumn(StudentUser::getId).setHeader("ID").setWidth("80px");
        grid.addColumn(StudentUser::getName).setHeader("Name");
        grid.addColumn(StudentUser::getEmail).setHeader("Email");
        
        // Status column with styling
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
        
        // Date formatted column
        grid.addColumn(user -> user.getJoinDate().format(DateTimeFormatter.ofPattern("MMM dd, yyyy")))
           .setHeader("Join Date")
           .setWidth("120px");
        
        // Test completion status
        grid.addComponentColumn(user -> {
            Icon icon = user.isTestCompleted() ? 
                VaadinIcon.CHECK.create() : VaadinIcon.CLOSE.create();
            icon.setColor(user.isTestCompleted() ? "green" : "red");
            return icon;
        }).setHeader("Test Completed").setWidth("100px");
        
        // Personality and career columns
        grid.addColumn(user -> user.getPersonalityType() != null ? user.getPersonalityType() : "-")
           .setHeader("Personality");
        grid.addColumn(user -> user.getCareerRecommendation() != null ? user.getCareerRecommendation() : "-")
           .setHeader("Career Match");
        
        // Action buttons
        grid.addComponentColumn(user -> {
            HorizontalLayout actions = new HorizontalLayout();
            
            // View button
            Button viewBtn = new Button(VaadinIcon.EYE.create());
            viewBtn.addClickListener(e -> showUserDetails(user));
            
            // Edit button
            Button editBtn = new Button(VaadinIcon.EDIT.create());
            editBtn.addClickListener(e -> showEditUserDialog(user));
            
            // Status toggle button
            Button statusBtn = new Button();
            if (user.getStatus().equals("Suspended")) {
                statusBtn.setIcon(VaadinIcon.UNLOCK.create());
                statusBtn.addClickListener(e -> unsuspendUser(user));
            } else {
                statusBtn.setIcon(VaadinIcon.LOCK.create());
                statusBtn.addClickListener(e -> suspendUser(user));
            }
            
            // Delete button
            Button deleteBtn = new Button(VaadinIcon.TRASH.create());
            deleteBtn.addClickListener(e -> confirmDeleteUser(user));
            deleteBtn.getStyle().set("color", "red");
            
            actions.add(viewBtn, editBtn, statusBtn, deleteBtn);
            actions.setSpacing(true);
            return actions;
        }).setHeader("Actions").setWidth("200px");
        
        grid.getStyle()
           .set("background-color", "white")
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
        public String getId() { return id; }
        public String getEmail() { return email; }
        public String getName() { return name; }
        public String getStatus() { return status; }
        public LocalDate getJoinDate() { return joinDate; }
        public boolean isTestCompleted() { return testCompleted; }
        public String getPersonalityType() { return personalityType; }
        public String getCareerRecommendation() { return careerRecommendation; }
        
        // Setters
        public void setName(String name) { this.name = name; }
        public void setEmail(String email) { this.email = email; }
        public void setStatus(String status) { this.status = status; }
    }
}
package com.example.talanta.views;

import com.example.talanta.ConfigUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import com.vaadin.flow.component.combobox.ComboBox;  // ← Add this
import com.vaadin.flow.component.html.Label;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

@Route("careers")
public class CareersView extends VerticalLayout {

    private Grid<Occupation> occupationGrid = new Grid<>(Occupation.class, false);
    private List<Occupation> occupationList = new ArrayList<>();
    private List<Occupation> allOccupation = new ArrayList<>();
    private Button prevPageButton;
    private Button nextPageButton;
    private Span pageInfo;
    private int currentPage = 0;
    private final int PAGE_SIZE = 5;
    private String institution;
    private ComboBox<String> institutionDropdown;
    private List<Occupation> occupations = new ArrayList<>();

    private Binder<Occupation> occupationBinder = new Binder<>(Occupation.class);

    public CareersView() {
        try {
            initializeView();
        } catch (Exception e) {
            showError("Failed to initialize view: " + e.getMessage());
        }
    }

    private void initializeView() {
        prevPageButton = new Button("Previous", VaadinIcon.ARROW_LEFT.create());
        nextPageButton = new Button("Next", VaadinIcon.ARROW_RIGHT.create());
        pageInfo = new Span();
        setSizeFull(); // Ensure this fills the viewport
        getStyle().set("overflow", "auto");
        setSizeFull();
        setPadding(false);
        setSpacing(false);

        HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();

        // Sidebar
        VerticalLayout sidebar = createSidebar();
        sidebar.setWidth("240px");
        sidebar.getStyle().set("background", "#F4F4F4");
        sidebar.getStyle().set("height", "850px");

        // Main Content
        VerticalLayout mainContent = createMainContent();
        mainContent.setSizeFull();

        layout.add(sidebar, mainContent);
        add(layout);

        // Initialize sample data
        fetchCoursedData();
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
                createNavItem("Dashboard", VaadinIcon.DASHBOARD, "admin-dashboard"),
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
        nav.setAlignItems(FlexLayout.Alignment.CENTER);

        nav.getStyle()
                .set("padding", "10px")
                .set("cursor", "pointer")
                .set("border-radius", "5px")
                .set("transition", "background-color 0.3s ease");

        nav.addClickListener(e -> {
            try {
                UI.getCurrent().navigate(route);
            } catch (Exception ex) {
                showError("Navigation failed: " + ex.getMessage());
            }
        });

        return nav;
    }

    private VerticalLayout createMainContent() {
        VerticalLayout main = new VerticalLayout();
        main.setPadding(true);
        main.setSpacing(true);
        main.setSizeFull();

        // Header
        H1 header = new H1("Courses");
        header.getStyle().set("color", "#E65100");
        header.getStyle().set("margin-top", "50px");
        header.getStyle().set("margin-left", "50px");
        main.add(header);

        // Add Course Button
        Button addCourseButton = new Button("Add Course", new Icon(VaadinIcon.PLUS));
        addCourseButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addCourseButton.getStyle().set("background-color", "#E65100");
        addCourseButton.addClickListener(e -> showCourseForm(null));
        addCourseButton.getStyle().set("margin-right", "50px");
        addCourseButton.getStyle().set("margin-top", "50px");
        main.add(addCourseButton);

        // Create a horizontal layout for header and button
        HorizontalLayout headerLayout = new HorizontalLayout(header, addCourseButton);
        headerLayout.getStyle().set("width", "800px");
        headerLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        headerLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN); // Moves button to right
        headerLayout.getStyle().set("margin-top", "50px");

        main.add(headerLayout);

        // Courses Section
        VerticalLayout coursesSection = createCoursesSection();
        main.add(coursesSection);
        fetchCoursedData();
        return main;
    }

    private VerticalLayout createCoursesSection() {
        VerticalLayout section = new VerticalLayout();
        section.setPadding(false);
        section.setSpacing(true);
        // Initialize Grid
        occupationGrid = new Grid<>();
        occupationGrid.setWidth("70%");
        occupationGrid.getStyle()
                .set("margin-left", "50px")
                .set("margin-top", "20px");

        occupationGrid.addClassName("third-table");

        // Configure columns
        occupationGrid.addColumn(Occupation::getId)
                .setHeader("#")
                .setWidth("100px")
                .setFlexGrow(0)
                .setSortable(false);
        occupationGrid.addColumn(new ComponentRenderer<>(course -> {
            Div container = new Div();
            container.setText(course.getName() != null ? course.getName() : "");
            container.getStyle()
                    .set("font-size", "14px") // Reduced font size
                    .set("line-height", "1.5")
                    .set("display", "block")
                    .set("color", "black")
                    .set("font-weight", "bold")
                    .set("width", "100%");
            return container;
        })).setHeader("course Name")
                .setAutoWidth(true)
                .setFlexGrow(1);

        // Action column
        occupationGrid.addComponentColumn(course -> {
            HorizontalLayout actions = new HorizontalLayout();
            actions.setSpacing(true);

            Icon editIcon = VaadinIcon.EDIT.create();
            editIcon.addClassName("force-icon-size");

            Button edit = new Button(editIcon);
            edit.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            edit.getStyle().set("color", "#E65100");
            edit.addClickListener(e -> showCourseForm(course));

            Icon deleteIcon = VaadinIcon.TRASH.create();
            deleteIcon.addClassName("force-icon-size");
            Button delete = new Button(deleteIcon);
            delete.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY);
            delete.addClickListener(e -> confirmDeleteCourse(course));

            actions.add(edit, delete);
            return actions;
        }).setHeader("Actions").setFlexGrow(1);

        HorizontalLayout paginationControls = new HorizontalLayout(prevPageButton, pageInfo, nextPageButton);
        paginationControls.setAlignItems(FlexComponent.Alignment.CENTER);
        paginationControls.setSpacing(true);
        paginationControls.setWidthFull();
        paginationControls.getStyle().set("margin-top", "0");
        paginationControls.getStyle().set("margin-left", "50px");

        section.add(occupationGrid, paginationControls);
        configurePagination();

        return section;
    }

    private void showCourseForm(Occupation course) {
        try {
            Dialog dialog = new Dialog();
            dialog.setCloseOnEsc(true);
            dialog.setCloseOnOutsideClick(true);

            boolean isNew = course == null;
            Occupation editedCourse = isNew ? new Occupation() : new Occupation(course);

            H2 dialogTitle = new H2(isNew ? "Add New Occuption" : "Edit Occupation");
            dialogTitle.getStyle().set("color", "#E65100");

            // Form Fields
            TextField nameField = new TextField("Occupation Name");
            nameField.setWidthFull();
            nameField.getStyle().set("border", "1px solid grey");
            nameField.setRequired(true);

            Select<String> categoryField = new Select<>();
            categoryField.setLabel("Levels");
            categoryField.setItems("Artisan", "Tertiary Certificate", "Diploma", "Higher Diploma", "Postgraduate diploma", "Bachelors", "Masters", "phD");
            categoryField.setWidthFull();

            Select<String> institutionSelect = new Select<>();
            institutionSelect.setLabel("Institution");
            // Get institutions from API
            List<String> apiInstitutions = fetchInstitutionNames();
            institutionSelect.setItems(apiInstitutions);
            institutionSelect.setPlaceholder("Select Institution");
            institutionSelect.addValueChangeListener(e -> {
                this.institution = e.getValue();

            });

            // Configure binder
            occupationBinder.forField(nameField)
                    .asRequired("Course name is required")
                    .bind(Occupation::getName, Occupation::setName);
            // Set initial values
            occupationBinder.readBean(editedCourse);

            FormLayout form = new FormLayout();
            form.add(nameField, categoryField, institutionSelect);

            // Save Button
            Button saveButton = new Button("Save", e -> {
                try {
                    if (occupationBinder.writeBeanIfValid(editedCourse)) {
                        if (isNew) {
                            occupations.add(editedCourse);
                            showSuccess("Course added successfully");
                        } else {
                            course.setName(editedCourse.getName());

                            showSuccess("Occupation updated successfully");
                        }
                        occupationGrid.getDataProvider().refreshAll();
                        dialog.close();
                    }
                } catch (Exception ex) {
                    showError("Failed to save course: " + ex.getMessage());
                }
            });
            saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            saveButton.getStyle().set("background-color", "#E65100");

            // Cancel Button
            Button cancelButton = new Button("Cancel", e -> dialog.close());
            cancelButton.getStyle().set("color", "#E65100");

            HorizontalLayout buttons = new HorizontalLayout(saveButton, cancelButton);
            buttons.setSpacing(true);

            VerticalLayout dialogLayout = new VerticalLayout(dialogTitle, form, buttons);
            dialogLayout.setPadding(true);
            dialogLayout.setSpacing(true);

            dialog.add(dialogLayout);
            dialog.open();
        } catch (Exception e) {
            showError("Failed to open course form: " + e.getMessage());
        }
    }

    private void confirmDeleteCourse(Occupation course) {
        try {
            ConfirmDialog dialog = new ConfirmDialog(
                    "Delete Course",
                    "Are you sure you want to delete '" + course.getName() + "'?",
                    "Delete", e -> {
                        try {
                            occupations.remove(course);
                            occupationGrid.getDataProvider().refreshAll();
                            showSuccess("Course deleted successfully");
                        } catch (Exception ex) {
                            showError("Failed to delete course: " + ex.getMessage());
                        }
                    },
                    "Cancel", e -> {
                    }
            );

            dialog.setConfirmButtonTheme("error primary");
            dialog.open();
        } catch (Exception e) {
            showError("Failed to create confirmation dialog: " + e.getMessage());
        }
    }

    private void showSuccess(String message) {
        Notification notification = Notification.show(message);
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        notification.setPosition(Notification.Position.TOP_CENTER);
    }

    private void showError(String message) {
        Notification notification = Notification.show(message, 5000, Notification.Position.TOP_CENTER);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
    }

    private void configurePagination() {
        prevPageButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        prevPageButton.setEnabled(false);
        prevPageButton.addClickListener(e -> {
            if (currentPage > 0) {
                currentPage--;
                updateGrid();
            }
        });

        nextPageButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        nextPageButton.getStyle().set("background-color", " #F94D00");
        nextPageButton.setEnabled(false);
        nextPageButton.addClickListener(e -> {
            if ((currentPage + 1) * PAGE_SIZE < occupationList.size()) {
                currentPage++;
                updateGrid();
            }
        });
    }

    private void updateGrid() {
        int fromIndex = currentPage * PAGE_SIZE;
        int toIndex = Math.min(fromIndex + PAGE_SIZE, occupationList.size());

        if (fromIndex >= occupationList.size()) {
            currentPage = 0;
            fromIndex = 0;
            toIndex = Math.min(PAGE_SIZE, occupationList.size());
        }

        List<Occupation> pageItems = occupationList.subList(fromIndex, toIndex);
        occupationGrid.setItems(pageItems);
        updatePageInfo();

        boolean atStart = currentPage == 0;
        boolean atEnd = (currentPage + 1) * PAGE_SIZE >= occupationList.size();

        prevPageButton.setEnabled(!atStart);
        nextPageButton.setEnabled(!atEnd);

        prevPageButton.getStyle()
                .set("background-color", atStart ? "#CCCCCC" : "#FE691E")
                .set("color", atStart ? "#666666" : "white")
                .set("border", "1px solid " + (atStart ? "#CCCCCC" : "#FE691E"));

        nextPageButton.getStyle()
                .set("background-color", atEnd ? "#CCCCCC" : "#FE691E")
                .set("color", atEnd ? "#666666" : "white")
                .set("border", "1px solid " + (atEnd ? "#CCCCCC" : "#FE691E"));
    }

    private void updatePageInfo() {
        int totalItems = occupationList.size();
        int from = Math.min(currentPage * PAGE_SIZE + 1, totalItems);
        int to = Math.min((currentPage + 1) * PAGE_SIZE, totalItems);
        pageInfo.setText(from + " - " + to + " of " + totalItems);
    }

    private void fetchCoursedData() {
        UI ui = UI.getCurrent();
        List<Occupation> newCourseList = new ArrayList<>();

        try {
            String apiUrl = ConfigUtil.BASE_URL + "/getAllOccupation";
            System.out.println("Fetching All fields data from: " + apiUrl);

            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            int responseCode = connection.getResponseCode();
            System.out.println("Courses Response Code: " + responseCode);

            if (responseCode == 200) {
                String response = IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
                System.out.println("Courses API Response: " + response);

                JSONObject jsonResponse = new JSONObject(response);
                JSONArray gradeScores = jsonResponse.getJSONArray("data");

                for (int i = 0; i < gradeScores.length(); i++) {

                    JSONObject occupationObj = gradeScores.getJSONObject(i);
                    Long id = occupationObj.getLong("id");
                    String name = occupationObj.getString("name");

                    newCourseList.add(new Occupation(id, name));

                }
            } else {
                System.out.println("All fields API request failed with response code: " + responseCode);

            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ui.access(() -> {
            allOccupation = newCourseList;
            occupationList = new ArrayList<>(allOccupation);
            updateGrid();
            System.out.println("Grid updated with " + occupationList.size() + " items.");
        });
    }

    private List<String> fetchInstitutionNames() {
        List<String> institutionNames = new ArrayList<>();

        try {
            String institutionUrl = "http://localhost:8080/talantaAuth/getinstitutes";
            HttpURLConnection connection = (HttpURLConnection) new URL(institutionUrl).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            if (connection.getResponseCode() == 200) {
                String response = IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
                JSONArray institutions = new JSONObject(response).getJSONArray("data");

                for (int i = 0; i < institutions.length(); i++) {
                    String name = institutions.getJSONObject(i).getString("name");
                    institutionNames.add(name);
                }
            }
            connection.disconnect();
        } catch (Exception e) {
            System.err.println("Error fetching institutions: " + e.getMessage());
            // Consider returning a default list here if needed
        }

        return institutionNames;
    }

    // Model Classes
    public static class Occupation {

        private long id;
        private String name;

        public Occupation(long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Occupation() {
        }

        public Occupation(Occupation other) {
            this.id = other.id;
            this.name = other.name;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

}

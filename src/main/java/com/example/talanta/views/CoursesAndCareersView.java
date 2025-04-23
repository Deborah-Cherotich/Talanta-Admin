//package com.example.talanta.views;
//
//import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.combobox.ComboBox;
//import com.vaadin.flow.component.dialog.Dialog;
//import com.vaadin.flow.component.grid.Grid;
//import com.vaadin.flow.component.html.H1;
//import com.vaadin.flow.component.html.Paragraph;
//import com.vaadin.flow.component.html.Image;
//import com.vaadin.flow.component.html.Span;
//import com.vaadin.flow.component.icon.Icon;
//import com.vaadin.flow.component.icon.VaadinIcon;
//import com.vaadin.flow.component.notification.Notification;
//import com.vaadin.flow.component.orderedlayout.*;
//import com.vaadin.flow.component.textfield.TextArea;
//import com.vaadin.flow.component.textfield.TextField;
//import com.vaadin.flow.router.Route;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Route("courses-careers")
//public class CoursesCareersView extends VerticalLayout {
//
//    private Grid<Course> courseGrid = new Grid<>(Course.class);
//    private List<Course> courseList = new ArrayList<>();
//
//    public CoursesCareersView() {
//        setSizeFull();
//        setPadding(false);
//
//        HorizontalLayout layout = new HorizontalLayout();
//        VerticalLayout sidebar = createSidebar();
//        VerticalLayout mainContent = createMainContent();
//
//        layout.setSizeFull();
//        sidebar.setWidth("240px");
//        sidebar.getStyle().set("background", "#F4F4F4");
//        layout.add(sidebar, mainContent);
//        add(layout);
//    }
//
//    private VerticalLayout createSidebar() {
//        VerticalLayout sidebar = new VerticalLayout();
//        sidebar.getStyle().set("padding-top", "2px");
//
//        Image logo = new Image("images/2.png", "Talanta Logo");
//        logo.setWidth("150px");
//
//        sidebar.add(
//            logo,
//            createNavItem("Dashboard", VaadinIcon.DASHBOARD),
//            createNavItem("Users", VaadinIcon.USER),
//            createNavItem("Personality Tests", VaadinIcon.CLIPBOARD_TEXT),
//            createNavItem("Courses & Careers", VaadinIcon.BOOK),
//            createNavItem("Institutions", VaadinIcon.ACADEMY_CAP),
//            createNavItem("Payments", VaadinIcon.CREDIT_CARD),
//            createNavItem("Reports", VaadinIcon.CHART),
//            createNavItem("Content", VaadinIcon.FILE_TEXT),
//            createNavItem("Notifications", VaadinIcon.BELL),
//            createNavItem("Settings", VaadinIcon.COG)
//        );
//
//        return sidebar;
//    }
//
//    private HorizontalLayout createNavItem(String title, VaadinIcon icon) {
//        Icon menuIcon = icon.create();
//        menuIcon.setColor("#E65100");
//        Span label = new Span(title);
//
//        HorizontalLayout nav = new HorizontalLayout(menuIcon, label);
//        nav.setPadding(true);
//        nav.setSpacing(true);
//        nav.setAlignItems(Alignment.CENTER);
//        nav.getStyle().set("padding", "10px").set("cursor", "pointer");
//
//        return nav;
//    }
//
//    private VerticalLayout createMainContent() {
//        VerticalLayout main = new VerticalLayout();
//        main.setPadding(true);
//
//        H1 heading = new H1("Courses & Careers");
//        heading.getStyle().set("color", "#E65100");
//        main.add(heading);
//
//        // Top filters and add button
//        HorizontalLayout filters = new HorizontalLayout();
//        ComboBox<String> filterCareer = new ComboBox<>("Filter by Career");
//        filterCareer.setItems("Engineer", "Doctor", "Artist");
//
//        ComboBox<String> filterInstitution = new ComboBox<>("Filter by Institution");
//        filterInstitution.setItems("Talanta College", "Nairobi Uni", "Online Academy");
//
//        Button addButton = new Button("➕ Add Course", e -> showAddCourseDialog());
//
//        filters.add(filterCareer, filterInstitution, addButton);
//        main.add(filters);
//
//        // Table setup
//        courseGrid.setColumns("title", "careerMatch", "duration", "price", "institution");
//        courseGrid.getColumnByKey("title").setHeader("Title");
//        courseGrid.getColumnByKey("careerMatch").setHeader("Career Match");
//        courseGrid.getColumnByKey("duration").setHeader("Duration");
//        courseGrid.getColumnByKey("price").setHeader("Price");
//        courseGrid.getColumnByKey("institution").setHeader("Institution");
//
//        // Dummy data
//        courseList.add(new Course("Software Dev", "Engineer", "3 months", "$300", "Talanta College"));
//        courseGrid.setItems(courseList);
//
//        main.add(courseGrid);
//        return main;
//    }
//
//    private void showAddCourseDialog() {
//        Dialog dialog = new Dialog();
//        dialog.setWidth("400px");
//
//        TextField titleField = new TextField("Course Title");
//        TextArea descriptionField = new TextArea("Description");
//        TextField durationField = new TextField("Duration");
//        ComboBox<String> careerMatchField = new ComboBox<>("Career Match");
//        careerMatchField.setItems("Engineer", "Doctor", "Artist");
//
//        TextField priceField = new TextField("Price");
//        ComboBox<String> institutionField = new ComboBox<>("Institution");
//        institutionField.setItems("Talanta College", "Nairobi Uni", "Online Academy");
//
//        Button saveBtn = new Button("Save", event -> {
//            Course course = new Course(
//                titleField.getValue(),
//                careerMatchField.getValue(),
//                durationField.getValue(),
//                priceField.getValue(),
//                institutionField.getValue()
//            );
//
//            courseList.add(course);
//            courseGrid.setItems(courseList);
//            Notification.show("Course Added");
//            dialog.close();
//        });
//
//        VerticalLayout form = new VerticalLayout(
//            titleField,
//            descriptionField,
//            durationField,
//            careerMatchField,
//            priceField,
//            institutionField,
//            saveBtn
//        );
//
//        dialog.add(form);
//        dialog.open();
//    }
//
//    // Course POJO
//    public static class Course {
//        private String title;
//        private String careerMatch;
//        private String duration;
//        private String price;
//        private String institution;
//
//        public Course(String title, String careerMatch, String duration, String price, String institution) {
//            this.title = title;
//            this.careerMatch = careerMatch;
//            this.duration = duration;
//            this.price = price;
//            this.institution = institution;
//        }
//
//        public String getTitle() { return title; }
//        public String getCareerMatch() { return careerMatch; }
//        public String getDuration() { return duration; }
//        public String getPrice() { return price; }
//        public String getInstitution() { return institution; }
//    }
//}


package com.example.talanta.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
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
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.html.Image;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Route("courses-careers")
public class CoursesAndCareersView extends VerticalLayout {

    private List<Course> courses = new ArrayList<>();
    private List<Career> careers = new ArrayList<>();
    private Grid<Course> coursesGrid;
    private Grid<Career> careersGrid;
    private Binder<Course> courseBinder = new Binder<>(Course.class);

    public CoursesAndCareersView() {
        try {
            initializeView();
        } catch (Exception e) {
            showError("Failed to initialize view: " + e.getMessage());
        }
    }

    private void initializeView() {
        setSizeFull();
        setPadding(false);
        setSpacing(false);

        HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();

        // Sidebar
        VerticalLayout sidebar = createSidebar();
        sidebar.setWidth("240px");
        sidebar.getStyle().set("background", "#F4F4F4");

        // Main Content
        VerticalLayout mainContent = createMainContent();
        mainContent.setSizeFull();

        layout.add(sidebar, mainContent);
        add(layout);

        // Initialize sample data
        initializeSampleData();
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
        H1 header = new H1("Courses and Career");
        header.getStyle().set("color", "#E65100");
        main.add(header);

        // Courses Section
        VerticalLayout coursesSection = createCoursesSection();
        main.add(coursesSection);

        // Careers Section
        VerticalLayout careersSection = createCareersSection();
        main.add(careersSection);

        return main;
    }

    private VerticalLayout createCoursesSection() {
        VerticalLayout section = new VerticalLayout();
        section.setPadding(false);
        section.setSpacing(true);
        
        H2 sectionHeader = new H2("Courses");
        sectionHeader.getStyle().set("color", "#E65100");
        section.add(sectionHeader);

        // Initialize Grid
        coursesGrid = new Grid<>();
        coursesGrid.setWidthFull();
        
        // Configure columns
        coursesGrid.addColumn(Course::getName)
                 .setHeader("Course")
                 .setFlexGrow(2)
                 .setSortable(true);
        
        coursesGrid.addColumn(Course::getCategory)
                 .setHeader("Category")
                 .setFlexGrow(1)
                 .setSortable(true);
        
        // Action column
        coursesGrid.addComponentColumn(course -> {
            HorizontalLayout actions = new HorizontalLayout();
            actions.setSpacing(true);
            
            Button edit = new Button(new Icon(VaadinIcon.EDIT));
            edit.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            edit.getStyle().set("color", "#E65100");
            edit.addClickListener(e -> showCourseForm(course));
            
            Button delete = new Button(new Icon(VaadinIcon.TRASH));
            delete.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY);
            delete.addClickListener(e -> confirmDeleteCourse(course));
            
            actions.add(edit, delete);
            return actions;
        }).setHeader("Actions").setFlexGrow(1);

        section.add(coursesGrid);

        // Add Course Button
        Button addCourseButton = new Button("Add Course", new Icon(VaadinIcon.PLUS));
        addCourseButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addCourseButton.getStyle().set("background-color", "#E65100");
        addCourseButton.addClickListener(e -> showCourseForm(null));
        section.add(addCourseButton);

        return section;
    }

    private VerticalLayout createCareersSection() {
        VerticalLayout section = new VerticalLayout();
        section.setPadding(false);
        section.setSpacing(true);
        
        H2 sectionHeader = new H2("Careers");
        sectionHeader.getStyle().set("color", "#E65100");
        section.add(sectionHeader);

        // Initialize Grid
        careersGrid = new Grid<>();
        careersGrid.setWidthFull();
        
        // Configure columns
        careersGrid.addColumn(Career::getTitle)
                  .setHeader("Career")
                  .setFlexGrow(1)
                  .setSortable(true);
        
        careersGrid.addColumn(Career::getCategory)
                  .setHeader("Category")
                  .setFlexGrow(1)
                  .setSortable(true);
        
        careersGrid.addColumn(Career::getDescription)
                  .setHeader("Description")
                  .setFlexGrow(2);

        section.add(careersGrid);

        return section;
    }

    private void initializeSampleData() {
        try {
            // Sample Courses
            courses.addAll(Arrays.asList(
                new Course("Introduction to Programming", "Technology"),
                new Course("Business Management Fundamentals", "Business"),
                new Course("Graphic Design Principles", "Creative Arts"),
                new Course("Data Science Basics", "Technology"),
                new Course("Digital Marketing Strategies", "Business"),
                new Course("Photography Techniques", "Creative Arts")
            ));

            // Sample Careers
            careers.addAll(Arrays.asList(
                new Career("Software Developer", "Technology", 
                    "Design, develop, and test software applications"),
                new Career("Marketing Manager", "Business", 
                    "Plan and execute marketing campaigns to promote products"),
                new Career("UI/UX Designer", "Creative Arts", 
                    "Create user-friendly interfaces and experiences"),
                new Career("Data Analyst", "Technology", 
                    "Interpret complex data and provide business insights"),
                new Career("Financial Advisor", "Business", 
                    "Provide financial planning services to clients"),
                new Career("Art Director", "Creative Arts", 
                    "Oversee visual style and content in media productions")
            ));

            // Refresh grids
            coursesGrid.setItems(courses);
            careersGrid.setItems(careers);
        } catch (Exception e) {
            showError("Failed to initialize sample data: " + e.getMessage());
        }
    }

    private void showCourseForm(Course course) {
        try {
            Dialog dialog = new Dialog();
            dialog.setCloseOnEsc(true);
            dialog.setCloseOnOutsideClick(true);

            boolean isNew = course == null;
            Course editedCourse = isNew ? new Course() : new Course(course);

            H2 dialogTitle = new H2(isNew ? "Add New Course" : "Edit Course");
            dialogTitle.getStyle().set("color", "#E65100");

            // Form Fields
            TextField nameField = new TextField("Course Name");
            nameField.setWidthFull();
            nameField.setRequired(true);

            Select<String> categoryField = new Select<>();
            categoryField.setLabel("Category");
            categoryField.setItems("Technology", "Business", "Creative Arts", "Science", "Health", "Education");
            categoryField.setWidthFull();

            // Configure binder
            courseBinder.forField(nameField)
                       .asRequired("Course name is required")
                       .bind(Course::getName, Course::setName);
            
            courseBinder.forField(categoryField)
                       .asRequired("Category is required")
                       .bind(Course::getCategory, Course::setCategory);

            // Set initial values
            courseBinder.readBean(editedCourse);

            FormLayout form = new FormLayout();
            form.add(nameField, categoryField);

            // Save Button
            Button saveButton = new Button("Save", e -> {
                try {
                    if (courseBinder.writeBeanIfValid(editedCourse)) {
                        if (isNew) {
                            courses.add(editedCourse);
                            showSuccess("Course added successfully");
                        } else {
                            course.setName(editedCourse.getName());
                            course.setCategory(editedCourse.getCategory());
                            showSuccess("Course updated successfully");
                        }
                        coursesGrid.getDataProvider().refreshAll();
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

    private void confirmDeleteCourse(Course course) {
        try {
            ConfirmDialog dialog = new ConfirmDialog(
                "Delete Course",
                "Are you sure you want to delete '" + course.getName() + "'?",
                "Delete", e -> {
                    try {
                        courses.remove(course);
                        coursesGrid.getDataProvider().refreshAll();
                        showSuccess("Course deleted successfully");
                    } catch (Exception ex) {
                        showError("Failed to delete course: " + ex.getMessage());
                    }
                },
                "Cancel", e -> {}
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

    // Model Classes
    public static class Course {
        private static int nextId = 1;
        private int id;
        private String name;
        private String category;

        public Course() {
            this.id = nextId++;
        }

        public Course(String name, String category) {
            this();
            this.name = name;
            this.category = category;
        }

        public Course(Course other) {
            this.id = other.id;
            this.name = other.name;
            this.category = other.category;
        }

        // Getters and Setters
        public int getId() { return id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
    }

    public static class Career {
        private String title;
        private String category;
        private String description;

        public Career(String title, String category, String description) {
            this.title = title;
            this.category = category;
            this.description = description;
        }

        // Getters
        public String getTitle() { return title; }
        public String getCategory() { return category; }
        public String getDescription() { return description; }
    }
}
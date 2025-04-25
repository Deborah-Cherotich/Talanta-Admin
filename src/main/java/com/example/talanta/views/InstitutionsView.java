package com.example.talanta.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
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

import java.util.*;

@Route("institutions")
public class InstitutionsView extends VerticalLayout {

    public InstitutionsView() {
        setSizeFull(); // Ensure this fills the viewport
        getStyle().set("overflow", "auto");
        HorizontalLayout layout = new HorizontalLayout();
        VerticalLayout sidebar = createSidebar(); // Reusing your existing sidebar
        VerticalLayout mainContent = createMainContent();

        layout.setSizeFull();
        sidebar.setWidth("240px");
        sidebar.getStyle().set("background", "#F4F4F4");
        sidebar.getStyle().set("height", "850px");
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
        H1 heading = new H1("Institution Management");
        heading.getStyle().set("color", "#E65100").set("margin", "0");
        Paragraph subtitle = new Paragraph("Manage educational institutions and their course offerings");
        headingLayout.add(heading, subtitle);

        Button addInstitutionBtn = new Button("Add Institution", VaadinIcon.PLUS.create());
        addInstitutionBtn.addClickListener(e -> showAddInstitutionDialog());
        addInstitutionBtn.getStyle().set("background-color", "#E65100").set("color", "white");

        headerLayout.add(headingLayout, addInstitutionBtn);
        main.add(headerLayout);

        // Stats cards
        HorizontalLayout statsLayout = new HorizontalLayout();
        statsLayout.setWidthFull();
        statsLayout.setSpacing(true);

        statsLayout.add(
                createStatCard("42", "Total Institutions", VaadinIcon.ACADEMY_CAP),
                createStatCard("156", "Courses Offered", VaadinIcon.BOOK),
                createStatCard("$248,750", "Total Revenue", VaadinIcon.MONEY),
                createStatCard("89%", "Approval Rating", VaadinIcon.STAR)
        );
        main.add(statsLayout);

        // Main content tabs
        Div tabs = new Div();
        tabs.getStyle().set("display", "flex").set("gap", "10px").set("margin-bottom", "10px");

        Button institutionsTab = new Button("Institutions");
        Button coursesTab = new Button("Courses");
        Button applicationsTab = new Button("Applications");

        tabs.add(institutionsTab, coursesTab, applicationsTab);
        main.add(tabs);

        // Institutions section
        VerticalLayout institutionsSection = new VerticalLayout();
        institutionsSection.setPadding(false);
        institutionsSection.setSpacing(false);

        // Institutions grid
        Grid<Institution> institutionsGrid = createInstitutionsGrid();
        institutionsSection.add(institutionsGrid);

        // Courses section (hidden by default)
        VerticalLayout coursesSection = new VerticalLayout();
        coursesSection.setPadding(false);
        coursesSection.setSpacing(false);
        coursesSection.setVisible(false);

        Grid<Course> coursesGrid = createCoursesGrid();
        coursesSection.add(coursesGrid);

        // Applications section (hidden by default)
        VerticalLayout applicationsSection = new VerticalLayout();
        applicationsSection.setPadding(false);
        applicationsSection.setSpacing(false);
        applicationsSection.setVisible(false);

        Grid<Application> applicationsGrid = createApplicationsGrid();
        applicationsSection.add(applicationsGrid);

        // Tab switching logic
        institutionsTab.addClickListener(e -> {
            institutionsSection.setVisible(true);
            coursesSection.setVisible(false);
            applicationsSection.setVisible(false);
            institutionsTab.getStyle().set("background-color", "#E65100").set("color", "white");
            coursesTab.getStyle().remove("background-color").set("color", "inherit");
            applicationsTab.getStyle().remove("background-color").set("color", "inherit");
        });

        coursesTab.addClickListener(e -> {
            institutionsSection.setVisible(false);
            coursesSection.setVisible(true);
            applicationsSection.setVisible(false);
            coursesTab.getStyle().set("background-color", "#E65100").set("color", "white");
            institutionsTab.getStyle().remove("background-color").set("color", "inherit");
            applicationsTab.getStyle().remove("background-color").set("color", "inherit");
        });

        applicationsTab.addClickListener(e -> {
            institutionsSection.setVisible(false);
            coursesSection.setVisible(false);
            applicationsSection.setVisible(true);
            applicationsTab.getStyle().set("background-color", "#E65100").set("color", "white");
            institutionsTab.getStyle().remove("background-color").set("color", "inherit");
            coursesTab.getStyle().remove("background-color").set("color", "inherit");
        });

        // Set default tab
        institutionsTab.getStyle().set("background-color", "#E65100").set("color", "white");

        main.add(institutionsSection, coursesSection, applicationsSection);

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

    private Grid<Institution> createInstitutionsGrid() {
        Grid<Institution> grid = new Grid<>();
        grid.setHeight("350px");

        // Sample data
        List<Institution> institutions = Arrays.asList(
                new Institution("UNI001", "University of Nairobi", "Nairobi, Kenya", "info@uonbi.ac.ke", "254-20-231-0000", 85, 12000),
                new Institution("COL002", "Strathmore University", "Nairobi, Kenya", "info@strathmore.edu", "254-20-600-0000", 92, 8000),
                new Institution("TEC003", "Kenyatta University", "Nairobi, Kenya", "info@ku.ac.ke", "254-20-871-0000", 78, 15000),
                new Institution("POL004", "Technical University of Kenya", "Nairobi, Kenya", "info@tukenya.ac.ke", "254-20-342-1000", 81, 5000),
                new Institution("UNI005", "Moi University", "Eldoret, Kenya", "info@mu.ac.ke", "254-53-436-0000", 76, 10000)
        );

        grid.setItems(institutions);
        grid.addColumn(Institution::getId).setHeader("ID").setWidth("100px");
        grid.addColumn(Institution::getName).setHeader("Name");
        grid.addColumn(Institution::getLocation).setHeader("Location");
        grid.addColumn(Institution::getEmail).setHeader("Email");
        grid.addColumn(i -> i.getRating() + "%").setHeader("Rating");
        grid.addColumn(i -> "$" + i.getAverageFee()).setHeader("Avg. Fee");

        grid.addComponentColumn(institution -> {
            HorizontalLayout actions = new HorizontalLayout();
            Button viewBtn = new Button(VaadinIcon.EYE.create());
            Button editBtn = new Button(VaadinIcon.EDIT.create());
            Button coursesBtn = new Button(VaadinIcon.BOOK.create());
            Button deleteBtn = new Button(VaadinIcon.TRASH.create());
            deleteBtn.getStyle().set("color", "red");

            viewBtn.addClickListener(e -> showInstitutionDetails(institution));
            editBtn.addClickListener(e -> showEditInstitutionDialog(institution));
            coursesBtn.addClickListener(e -> showInstitutionCourses(institution));

            actions.add(viewBtn, editBtn, coursesBtn, deleteBtn);
            return actions;
        }).setHeader("Actions");

        grid.getStyle()
                .set("background-color", "white")
                .set("border-radius", "10px")
                .set("box-shadow", "0 2px 5px rgba(0,0,0,0.05)");

        return grid;
    }

    private Grid<Course> createCoursesGrid() {
        Grid<Course> grid = new Grid<>();

        // Sample data
        List<Course> courses = Arrays.asList(
                new Course("CS101", "Computer Science", "University of Nairobi", 4, 1200),
                new Course("BBA202", "Business Administration", "Strathmore University", 3, 1500),
                new Course("ENG303", "Electrical Engineering", "Kenyatta University", 5, 1000),
                new Course("ART404", "Graphic Design", "Technical University of Kenya", 2, 800),
                new Course("MED505", "Medicine", "Moi University", 6, 2000)
        );

        grid.setItems(courses);
        grid.addColumn(Course::getCode).setHeader("Code").setWidth("100px");
        grid.addColumn(Course::getName).setHeader("Course Name");
        grid.addColumn(Course::getInstitution).setHeader("Institution");
        grid.addColumn(Course::getDuration).setHeader("Duration (yrs)");
        grid.addColumn(c -> "$" + c.getFee()).setHeader("Annual Fee");

        grid.addComponentColumn(course -> {
            HorizontalLayout actions = new HorizontalLayout();
            Button editBtn = new Button(VaadinIcon.EDIT.create());
            Button studentsBtn = new Button(VaadinIcon.USER.create());
            Button deleteBtn = new Button(VaadinIcon.TRASH.create());
            deleteBtn.getStyle().set("color", "red");

            editBtn.addClickListener(e -> showEditCourseDialog(course));
            studentsBtn.addClickListener(e -> showCourseStudents(course));

            actions.add(editBtn, studentsBtn, deleteBtn);
            return actions;
        }).setHeader("Actions");

        grid.getStyle()
                .set("background-color", "white")
                .set("border-radius", "10px")
                .set("box-shadow", "0 2px 5px rgba(0,0,0,0.05)");

        return grid;
    }

    private Grid<Application> createApplicationsGrid() {
        Grid<Application> grid = new Grid<>();

        // Sample data
        List<Application> applications = Arrays.asList(
                new Application("APP001", "John Doe", "Computer Science", "University of Nairobi", "Pending", "2023-05-01"),
                new Application("APP002", "Jane Smith", "Business Administration", "Strathmore University", "Approved", "2023-04-25"),
                new Application("APP003", "Alice Johnson", "Electrical Engineering", "Kenyatta University", "Rejected", "2023-04-20"),
                new Application("APP004", "Bob Brown", "Graphic Design", "Technical University of Kenya", "Approved", "2023-04-15"),
                new Application("APP005", "Charlie Davis", "Medicine", "Moi University", "Pending", "2023-04-10")
        );

        grid.setItems(applications);
        grid.addColumn(Application::getId).setHeader("ID").setWidth("100px");
        grid.addColumn(Application::getStudentName).setHeader("Student");
        grid.addColumn(Application::getCourse).setHeader("Course");
        grid.addColumn(Application::getInstitution).setHeader("Institution");

        grid.addColumn(application -> {
            Span status = new Span(application.getStatus());
            switch (application.getStatus()) {
                case "Approved":
                    status.getStyle().set("color", "green");
                    break;
                case "Rejected":
                    status.getStyle().set("color", "red");
                    break;
                default:
                    status.getStyle().set("color", "orange");
            }
            return status;
        }).setHeader("Status");

        grid.addColumn(Application::getDateApplied).setHeader("Date Applied");

        grid.addComponentColumn(application -> {
            HorizontalLayout actions = new HorizontalLayout();
            Button approveBtn = new Button("Approve", VaadinIcon.CHECK.create());
            Button rejectBtn = new Button("Reject", VaadinIcon.CLOSE.create());
            Button viewBtn = new Button(VaadinIcon.EYE.create());

            approveBtn.getStyle().set("color", "green");
            rejectBtn.getStyle().set("color", "red");

            approveBtn.addClickListener(e -> updateApplicationStatus(application, "Approved"));
            rejectBtn.addClickListener(e -> updateApplicationStatus(application, "Rejected"));
            viewBtn.addClickListener(e -> showApplicationDetails(application));

            actions.add(approveBtn, rejectBtn, viewBtn);
            return actions;
        }).setHeader("Actions");

        grid.getStyle()
                .set("background-color", "white")
                .set("border-radius", "10px")
                .set("box-shadow", "0 2px 5px rgba(0,0,0,0.05)");

        return grid;
    }

    private void showAddInstitutionDialog() {
        Dialog dialog = new Dialog();
        dialog.setCloseOnEsc(true);
        dialog.setCloseOnOutsideClick(true);
        dialog.setWidth("600px");

        H2 header = new H2("Add New Institution");

        FormLayout form = new FormLayout();
        TextField idField = new TextField("Institution ID");
        TextField nameField = new TextField("Institution Name");
        TextField locationField = new TextField("Location");
        EmailField emailField = new EmailField("Email");
        TextField phoneField = new TextField("Phone Number");
        NumberField ratingField = new NumberField("Rating (0-100)");
        ratingField.setMin(0);
        ratingField.setMax(100);
        NumberField avgFeeField = new NumberField("Average Course Fee ($)");

        TextArea descriptionArea = new TextArea("Description");
        descriptionArea.setHeight("100px");

        form.add(idField, nameField, locationField, emailField, phoneField,
                ratingField, avgFeeField, descriptionArea);
        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));

        HorizontalLayout buttons = new HorizontalLayout();
        Button saveBtn = new Button("Save", VaadinIcon.CHECK.create());
        Button cancelBtn = new Button("Cancel", VaadinIcon.CLOSE.create());
        buttons.add(saveBtn, cancelBtn);

        dialog.add(header, form, buttons);
        cancelBtn.addClickListener(e -> dialog.close());

        dialog.open();
    }

    private void showEditInstitutionDialog(Institution institution) {
        Dialog dialog = new Dialog();
        dialog.setCloseOnEsc(true);
        dialog.setCloseOnOutsideClick(true);
        dialog.setWidth("600px");

        H2 header = new H2("Edit Institution: " + institution.getName());

        FormLayout form = new FormLayout();
        TextField idField = new TextField("Institution ID");
        idField.setValue(institution.getId());
        idField.setReadOnly(true);

        TextField nameField = new TextField("Institution Name");
        nameField.setValue(institution.getName());

        TextField locationField = new TextField("Location");
        locationField.setValue(institution.getLocation());

        EmailField emailField = new EmailField("Email");
        emailField.setValue(institution.getEmail());

        TextField phoneField = new TextField("Phone Number");
        phoneField.setValue(institution.getPhone());

        NumberField ratingField = new NumberField("Rating (0-100)");
        ratingField.setValue((double) institution.getRating());
        ratingField.setMin(0);
        ratingField.setMax(100);

        NumberField avgFeeField = new NumberField("Average Course Fee ($)");
        avgFeeField.setValue((double) institution.getAverageFee());

        TextArea descriptionArea = new TextArea("Description");
        descriptionArea.setHeight("100px");

        form.add(idField, nameField, locationField, emailField, phoneField,
                ratingField, avgFeeField, descriptionArea);
        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));

        HorizontalLayout buttons = new HorizontalLayout();
        Button saveBtn = new Button("Save Changes", VaadinIcon.CHECK.create());
        Button cancelBtn = new Button("Cancel", VaadinIcon.CLOSE.create());
        buttons.add(saveBtn, cancelBtn);

        dialog.add(header, form, buttons);
        cancelBtn.addClickListener(e -> dialog.close());

        dialog.open();
    }

    private void showInstitutionDetails(Institution institution) {
        Dialog dialog = new Dialog();
        dialog.setCloseOnEsc(true);
        dialog.setCloseOnOutsideClick(true);
        dialog.setWidth("700px");

        H2 header = new H2(institution.getName());

        VerticalLayout details = new VerticalLayout();
        details.setSpacing(false);
        details.setPadding(false);

        details.add(new H3("Basic Information"));
        details.add(createDetailRow("ID:", institution.getId()));
        details.add(createDetailRow("Location:", institution.getLocation()));
        details.add(createDetailRow("Email:", institution.getEmail()));
        details.add(createDetailRow("Phone:", institution.getPhone()));
        details.add(createDetailRow("Rating:", institution.getRating() + "%"));
        details.add(createDetailRow("Average Fee:", "$" + institution.getAverageFee()));

        details.add(new H3("Courses Offered"));
        // In a real app, you would show actual courses here
        VerticalLayout coursesList = new VerticalLayout();
        coursesList.add(new Paragraph("• Computer Science ($1200/yr)"));
        coursesList.add(new Paragraph("• Business Administration ($1500/yr)"));
        coursesList.add(new Paragraph("• Electrical Engineering ($1000/yr)"));
        details.add(coursesList);

        Button closeBtn = new Button("Close", VaadinIcon.CLOSE.create());
        closeBtn.addClickListener(e -> dialog.close());

        dialog.add(header, details, closeBtn);
        dialog.open();
    }

    private void showInstitutionCourses(Institution institution) {
        UI.getCurrent().navigate("institutions?tab=courses");
        // In a real app, you would filter the courses grid to show only this institution's courses
    }

    private void showEditCourseDialog(Course course) {
        Dialog dialog = new Dialog();
        dialog.setCloseOnEsc(true);
        dialog.setCloseOnOutsideClick(true);
        dialog.setWidth("500px");

        H2 header = new H2("Edit Course: " + course.getName());

        FormLayout form = new FormLayout();
        TextField codeField = new TextField("Course Code");
        codeField.setValue(course.getCode());

        TextField nameField = new TextField("Course Name");
        nameField.setValue(course.getName());

        ComboBox<String> institutionField = new ComboBox<>("Institution");
        institutionField.setItems("University of Nairobi", "Strathmore University", "Kenyatta University",
                "Technical University of Kenya", "Moi University");
        institutionField.setValue(course.getInstitution());

        NumberField durationField = new NumberField("Duration (years)");
        durationField.setValue((double) course.getDuration());

        NumberField feeField = new NumberField("Annual Fee ($)");
        feeField.setValue((double) course.getFee());

        TextArea descriptionArea = new TextArea("Description");
        descriptionArea.setHeight("100px");

        form.add(codeField, nameField, institutionField, durationField, feeField, descriptionArea);
        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));

        HorizontalLayout buttons = new HorizontalLayout();
        Button saveBtn = new Button("Save Changes", VaadinIcon.CHECK.create());
        Button cancelBtn = new Button("Cancel", VaadinIcon.CLOSE.create());
        buttons.add(saveBtn, cancelBtn);

        dialog.add(header, form, buttons);
        cancelBtn.addClickListener(e -> dialog.close());

        dialog.open();
    }

    private void showCourseStudents(Course course) {
        Dialog dialog = new Dialog();
        dialog.setCloseOnEsc(true);
        dialog.setCloseOnOutsideClick(true);
        dialog.setWidth("800px");

        H2 header = new H2("Students enrolled in " + course.getName());

        // In a real app, you would show actual students here
        Grid<Student> studentsGrid = new Grid<>();
        studentsGrid.addColumn(Student::getId).setHeader("ID");
        studentsGrid.addColumn(Student::getName).setHeader("Name");
        studentsGrid.addColumn(Student::getEmail).setHeader("Email");
        studentsGrid.addColumn(Student::getJoinDate).setHeader("Enrollment Date");

        dialog.add(header, studentsGrid);
        dialog.open();
    }

    private void showApplicationDetails(Application application) {
        Dialog dialog = new Dialog();
        dialog.setCloseOnEsc(true);
        dialog.setCloseOnOutsideClick(true);
        dialog.setWidth("700px");

        H2 header = new H2("Application #" + application.getId());

        VerticalLayout details = new VerticalLayout();
        details.setSpacing(false);
        details.setPadding(false);

        details.add(new H3("Application Details"));
        details.add(createDetailRow("Student:", application.getStudentName()));
        details.add(createDetailRow("Course:", application.getCourse()));
        details.add(createDetailRow("Institution:", application.getInstitution()));
        details.add(createDetailRow("Status:", application.getStatus()));
        details.add(createDetailRow("Date Applied:", application.getDateApplied()));

        details.add(new H3("Supporting Documents"));
        // In a real app, you would show actual documents here
        VerticalLayout documentsList = new VerticalLayout();
        documentsList.add(new Paragraph("• High School Transcript"));
        documentsList.add(new Paragraph("• Recommendation Letter"));
        documentsList.add(new Paragraph("• Personal Statement"));
        details.add(documentsList);

        Button closeBtn = new Button("Close", VaadinIcon.CLOSE.create());
        closeBtn.addClickListener(e -> dialog.close());

        dialog.add(header, details, closeBtn);
        dialog.open();
    }

    private void updateApplicationStatus(Application application, String status) {
        // In a real app, you would update the application status in the database
        application.setStatus(status);
        Notification.show("Application status updated to " + status);
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
    private static class Institution {

        private String id;
        private String name;
        private String location;
        private String email;
        private String phone;
        private int rating;
        private int averageFee;

        public Institution(String id, String name, String location, String email, String phone, int rating, int averageFee) {
            this.id = id;
            this.name = name;
            this.location = location;
            this.email = email;
            this.phone = phone;
            this.rating = rating;
            this.averageFee = averageFee;
        }

        // Getters
        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getLocation() {
            return location;
        }

        public String getEmail() {
            return email;
        }

        public String getPhone() {
            return phone;
        }

        public int getRating() {
            return rating;
        }

        public int getAverageFee() {
            return averageFee;
        }

        // Setters for edit functionality
        public void setName(String name) {
            this.name = name;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public void setAverageFee(int averageFee) {
            this.averageFee = averageFee;
        }
    }

    private static class Course {

        private String code;
        private String name;
        private String institution;
        private int duration;
        private int fee;

        public Course(String code, String name, String institution, int duration, int fee) {
            this.code = code;
            this.name = name;
            this.institution = institution;
            this.duration = duration;
            this.fee = fee;
        }

        // Getters
        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public String getInstitution() {
            return institution;
        }

        public int getDuration() {
            return duration;
        }

        public int getFee() {
            return fee;
        }
    }

    private static class Application {

        private String id;
        private String studentName;
        private String course;
        private String institution;
        private String status;
        private String dateApplied;

        public Application(String id, String studentName, String course, String institution, String status, String dateApplied) {
            this.id = id;
            this.studentName = studentName;
            this.course = course;
            this.institution = institution;
            this.status = status;
            this.dateApplied = dateApplied;
        }

        // Getters
        public String getId() {
            return id;
        }

        public String getStudentName() {
            return studentName;
        }

        public String getCourse() {
            return course;
        }

        public String getInstitution() {
            return institution;
        }

        public String getStatus() {
            return status;
        }

        public String getDateApplied() {
            return dateApplied;
        }

        // Setters
        public void setStatus(String status) {
            this.status = status;
        }
    }

    private static class Student {

        private String id;
        private String name;
        private String email;
        private String joinDate;

        public Student(String id, String name, String email, String joinDate) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.joinDate = joinDate;
        }

        // Getters
        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getJoinDate() {
            return joinDate;
        }
    }
}

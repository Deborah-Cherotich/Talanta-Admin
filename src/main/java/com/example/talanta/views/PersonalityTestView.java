//package com.example.talanta.views;
//
//import com.vaadin.flow.component.UI;
//import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.charts.Chart;
//import com.vaadin.flow.component.charts.model.*;
//import com.vaadin.flow.component.combobox.ComboBox;
//import com.vaadin.flow.component.dialog.Dialog;
//import com.vaadin.flow.component.formlayout.FormLayout;
//import com.vaadin.flow.component.grid.Grid;
//import com.vaadin.flow.component.html.*;
//import com.vaadin.flow.component.icon.Icon;
//import com.vaadin.flow.component.icon.VaadinIcon;
//import com.vaadin.flow.component.orderedlayout.FlexComponent;
//import com.vaadin.flow.component.orderedlayout.FlexLayout;
//import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.component.textfield.TextArea;
//import com.vaadin.flow.component.textfield.TextField;
//import com.vaadin.flow.data.provider.ListDataProvider;
//import com.vaadin.flow.router.Route;
//
//import java.util.*;
//
//@Route("personality-tests")
//public class PersonalityTestView extends VerticalLayout {
//
//    public PersonalityTestView() {
//        HorizontalLayout layout = new HorizontalLayout();
//        VerticalLayout sidebar = createSidebar(); // Reusing your existing sidebar
//        VerticalLayout mainContent = createMainContent();
//
//        layout.setSizeFull();
//        sidebar.setWidth("240px");
//        sidebar.getStyle().set("background", "#F4F4F4");
//        layout.add(sidebar, mainContent);
//        add(layout);
//    }
//
//    // Reused from your existing code
//    private VerticalLayout createSidebar() {
//        VerticalLayout sidebar = new VerticalLayout();
//        sidebar.addClassName("sidebar");
//        sidebar.getStyle().set("padding-top", "2px");
//
//        Image logo = new Image("images/2.png", "Talanta Logo");
//        logo.setWidth("150px");
//        logo.setHeight("auto");
//        logo.addClassName("logo");
//
//        sidebar.add(
//            logo,
//            createNavItem("Dashboard", VaadinIcon.DASHBOARD, "Admindashboard"),
//            createNavItem("Users", VaadinIcon.USER, "users"),
//            createNavItem("Personality Tests", VaadinIcon.CLIPBOARD_TEXT, "personality-tests"),
//            createNavItem("Courses & Careers", VaadinIcon.BOOK, "courses-careers"),
//            createNavItem("Institutions", VaadinIcon.ACADEMY_CAP, "institutions"),
//            createNavItem("Payments", VaadinIcon.CREDIT_CARD, "payments"),
//            createNavItem("Reports", VaadinIcon.CHART, "reports"),
//            createNavItem("Content", VaadinIcon.FILE_TEXT, "content"),
//            createNavItem("Notifications", VaadinIcon.BELL, "notifications"),
//            createNavItem("Settings", VaadinIcon.COG, "settings")
//        );
//
//        return sidebar;
//    }
//
//    // Reused from your existing code
//    private HorizontalLayout createNavItem(String title, VaadinIcon icon, String route) {
//        Icon menuIcon = icon.create();
//        menuIcon.setColor("#E65100");
//        Span label = new Span(title);
//
//        HorizontalLayout nav = new HorizontalLayout(menuIcon, label);
//        nav.setPadding(true);
//        nav.setSpacing(true);
//        nav.setAlignItems(FlexComponent.Alignment.CENTER);
//
//        nav.getStyle()
//           .set("padding", "10px")
//           .set("cursor", "pointer")
//           .set("border-radius", "5px")
//           .set("transition", "background-color 0.3s ease");
//
//        nav.addClickListener(e -> UI.getCurrent().navigate(route));
//
//        return nav;
//    }
//
//    private VerticalLayout createMainContent() {
//        VerticalLayout main = new VerticalLayout();
//        main.setPadding(true);
//        main.setSpacing(true);
//
//        // Header section
//        HorizontalLayout headerLayout = new HorizontalLayout();
//        headerLayout.setWidthFull();
//        headerLayout.setJustifyContentMode(FlexLayout.JustifyContentMode.BETWEEN);
//
//        VerticalLayout headingLayout = new VerticalLayout();
//        H1 heading = new H1("Personality Test Management");
//        heading.getStyle().set("color", "#E65100").set("margin", "0");
//        Paragraph subtitle = new Paragraph("View and manage personality tests and results");
//        headingLayout.add(heading, subtitle);
//
//        Button addQuestionBtn = new Button("Add New Question", VaadinIcon.PLUS.create());
//        addQuestionBtn.addClickListener(e -> showAddQuestionDialog());
//        addQuestionBtn.getStyle().set("background-color", "#E65100").set("color", "white");
//
//        headerLayout.add(headingLayout, addQuestionBtn);
//        main.add(headerLayout);
//
//        // Stats cards
//        HorizontalLayout statsLayout = new HorizontalLayout();
//        statsLayout.setWidthFull();
//        statsLayout.setSpacing(true);
//
//        statsLayout.add(
//            createStatCard("1,234", "Total Tests Taken", VaadinIcon.CLIPBOARD_TEXT),
//            createStatCard("87%", "Average Match Rate", VaadinIcon.CHECK_CIRCLE),
//            createStatCard("12", "Career Categories", VaadinIcon.BOOKMARK)
//        );
//        main.add(statsLayout);
//
//        // Main content tabs
//        Div tabs = new Div();
//        tabs.getStyle().set("display", "flex").set("gap", "10px").set("margin-bottom", "10px");
//
//        Button resultsTab = new Button("Test Results");
//        Button questionsTab = new Button("Question Bank");
//        Button mappingTab = new Button("Career Mapping");
//        
//        tabs.add(resultsTab, questionsTab, mappingTab);
//        main.add(tabs);
//
//        // Results section
//        VerticalLayout resultsSection = new VerticalLayout();
//        resultsSection.setPadding(false);
//        resultsSection.setSpacing(false);
//        
//        // Results charts
//        HorizontalLayout chartsLayout = new HorizontalLayout();
//        chartsLayout.setWidthFull();
//        chartsLayout.setSpacing(true);
//        
//        chartsLayout.add(
//            createPersonalityTypeChart(),
//            createCareerMatchChart()
//        );
//        resultsSection.add(chartsLayout);
//        
//        // Results grid
//        H3 resultsHeader = new H3("Recent Test Results");
//        Grid<TestResult> resultsGrid = createResultsGrid();
//        resultsSection.add(resultsHeader, resultsGrid);
//        
//        // Question bank section (hidden by default)
//        VerticalLayout questionsSection = new VerticalLayout();
//        questionsSection.setPadding(false);
//        questionsSection.setSpacing(false);
//        questionsSection.setVisible(false);
//        
//        Grid<TestQuestion> questionsGrid = createQuestionsGrid();
//        questionsSection.add(new H3("Personality Test Questions"), questionsGrid);
//        
//        // Career mapping section (hidden by default)
//        VerticalLayout mappingSection = new VerticalLayout();
//        mappingSection.setPadding(false);
//        mappingSection.setSpacing(false);
//        mappingSection.setVisible(false);
//        
//        Grid<CareerMapping> mappingGrid = createMappingGrid();
//        Button addMappingBtn = new Button("Add Career Mapping", VaadinIcon.PLUS.create());
//        addMappingBtn.addClickListener(e -> showAddMappingDialog());
//        
//        mappingSection.add(new H3("Career Personality Mapping"), addMappingBtn, mappingGrid);
//        
//        // Tab switching logic
//        resultsTab.addClickListener(e -> {
//            resultsSection.setVisible(true);
//            questionsSection.setVisible(false);
//            mappingSection.setVisible(false);
//            resultsTab.getStyle().set("background-color", "#E65100").set("color", "white");
//            questionsTab.getStyle().remove("background-color").set("color", "inherit");
//            mappingTab.getStyle().remove("background-color").set("color", "inherit");
//        });
//        
//        questionsTab.addClickListener(e -> {
//            resultsSection.setVisible(false);
//            questionsSection.setVisible(true);
//            mappingSection.setVisible(false);
//            questionsTab.getStyle().set("background-color", "#E65100").set("color", "white");
//            resultsTab.getStyle().remove("background-color").set("color", "inherit");
//            mappingTab.getStyle().remove("background-color").set("color", "inherit");
//        });
//        
//        mappingTab.addClickListener(e -> {
//            resultsSection.setVisible(false);
//            questionsSection.setVisible(false);
//            mappingSection.setVisible(true);
//            mappingTab.getStyle().set("background-color", "#E65100").set("color", "white");
//            resultsTab.getStyle().remove("background-color").set("color", "inherit");
//            questionsTab.getStyle().remove("background-color").set("color", "inherit");
//        });
//        
//        // Set default tab
//        resultsTab.getStyle().set("background-color", "#E65100").set("color", "white");
//        
//        main.add(resultsSection, questionsSection, mappingSection);
//        
//        return main;
//    }
//
//    private VerticalLayout createStatCard(String value, String label, VaadinIcon icon) {
//        VerticalLayout card = new VerticalLayout();
//        card.setPadding(true);
//        card.setSpacing(false);
//        card.setAlignItems(Alignment.CENTER);
//
//        Icon cardIcon = icon.create();
//        cardIcon.setSize("24px");
//        cardIcon.setColor("#E65100");
//
//        H2 valueLabel = new H2(value);
//        valueLabel.getStyle().set("margin", "0");
//
//        Paragraph description = new Paragraph(label);
//        description.getStyle().set("margin", "0").set("text-align", "center");
//
//        card.add(cardIcon, valueLabel, description);
//        card.getStyle()
//           .set("background-color", "white")
//           .set("padding", "20px")
//           .set("border-radius", "10px")
//           .set("box-shadow", "0 2px 5px rgba(0,0,0,0.05)")
//           .set("width", "100%")
//           .set("align-items", "center");
//
//        return card;
//    }
//
//    private VerticalLayout createPersonalityTypeChart() {
//        Chart chart = new Chart(ChartType.PIE);
//        Configuration conf = chart.getConfiguration();
//        conf.setTitle("Personality Type Distribution");
//
//        DataSeries series = new DataSeries();
//        series.add(new DataSeriesItem("Analytical", 35));
//        series.add(new DataSeriesItem("Social", 25));
//        series.add(new DataSeriesItem("Creative", 20));
//        series.add(new DataSeriesItem("Practical", 15));
//        series.add(new DataSeriesItem("Leadership", 5));
//
//        conf.addSeries(series);
//        chart.setWidthFull();
//        
//        VerticalLayout chartContainer = new VerticalLayout();
//        chartContainer.getStyle()
//           .set("background-color", "white")
//           .set("padding", "20px")
//           .set("border-radius", "10px")
//           .set("box-shadow", "0 2px 5px rgba(0,0,0,0.05)")
//           .set("width", "48%");
//        chartContainer.add(chart);
//        
//        return chartContainer;
//    }
//
//    private VerticalLayout createCareerMatchChart() {
//        Chart chart = new Chart(ChartType.COLUMN);
//        Configuration conf = chart.getConfiguration();
//        conf.setTitle("Top Career Matches");
//
//        List<String> categories = Arrays.asList(
//            "Software Engineer", 
//            "Teacher", 
//            "Doctor", 
//            "Artist", 
//            "Business Manager"
//        );
//        conf.getxAxis().setCategories(categories.toArray(new String[0]));
//
//        Number[] matches = {120, 95, 80, 65, 50};
//        DataSeries series = new DataSeries("Matches");
//        series.setData(matches);
//
//        conf.addSeries(series);
//        chart.setWidthFull();
//        
//        VerticalLayout chartContainer = new VerticalLayout();
//        chartContainer.getStyle()
//           .set("background-color", "white")
//           .set("padding", "20px")
//           .set("border-radius", "10px")
//           .set("box-shadow", "0 2px 5px rgba(0,0,0,0.05)")
//           .set("width", "48%");
//        chartContainer.add(chart);
//        
//        return chartContainer;
//    }
//
//    private Grid<TestResult> createResultsGrid() {
//        Grid<TestResult> grid = new Grid<>();
//        
//        // Sample data
//        List<TestResult> results = Arrays.asList(
//            new TestResult("1001", "John Doe", "Analytical", "Software Engineer", "92%", "2023-05-15"),
//            new TestResult("1002", "Jane Smith", "Social", "Teacher", "88%", "2023-05-14"),
//            new TestResult("1003", "Alice Johnson", "Creative", "Graphic Designer", "85%", "2023-05-13"),
//            new TestResult("1004", "Bob Brown", "Practical", "Electrician", "90%", "2023-05-12"),
//            new TestResult("1005", "Charlie Davis", "Leadership", "Manager", "82%", "2023-05-11")
//        );
//        
//        grid.setItems(results);
//        grid.addColumn(TestResult::getId).setHeader("ID").setWidth("80px");
//        grid.addColumn(TestResult::getName).setHeader("Name");
//        grid.addColumn(TestResult::getPersonalityType).setHeader("Personality Type");
//        grid.addColumn(TestResult::getRecommendedCareer).setHeader("Recommended Career");
//        grid.addColumn(TestResult::getMatchRate).setHeader("Match Rate");
//        grid.addColumn(TestResult::getDateTaken).setHeader("Date Taken");
//        
//        grid.addComponentColumn(result -> {
//            Button viewBtn = new Button("View", VaadinIcon.EYE.create());
//            viewBtn.addClickListener(e -> showResultDetails(result));
//            return viewBtn;
//        }).setHeader("Actions");
//        
//        grid.getStyle()
//           .set("background-color", "white")
//           .set("border-radius", "10px")
//           .set("box-shadow", "0 2px 5px rgba(0,0,0,0.05)");
//        
//        return grid;
//    }
//
//    private Grid<TestQuestion> createQuestionsGrid() {
//        Grid<TestQuestion> grid = new Grid<>();
//        
//        // Sample data
//        List<TestQuestion> questions = Arrays.asList(
//            new TestQuestion("Q1", "Do you enjoy solving complex problems?", "Analytical"),
//            new TestQuestion("Q2", "Do you prefer working in teams?", "Social"),
//            new TestQuestion("Q3", "Do you like creating new things?", "Creative"),
//            new TestQuestion("Q4", "Do you prefer hands-on work?", "Practical"),
//            new TestQuestion("Q5", "Do you like taking charge of situations?", "Leadership")
//        );
//        
//        grid.setItems(questions);
//        grid.addColumn(TestQuestion::getId).setHeader("ID").setWidth("80px");
//        grid.addColumn(TestQuestion::getQuestionText).setHeader("Question");
//        grid.addColumn(TestQuestion::getCategory).setHeader("Category");
//        
//        grid.addComponentColumn(question -> {
//            HorizontalLayout actions = new HorizontalLayout();
//            Button editBtn = new Button(VaadinIcon.EDIT.create());
//            Button deleteBtn = new Button(VaadinIcon.TRASH.create());
//            deleteBtn.getStyle().set("color", "red");
//            actions.add(editBtn, deleteBtn);
//            return actions;
//        }).setHeader("Actions");
//        
//        grid.getStyle()
//           .set("background-color", "white")
//           .set("border-radius", "10px")
//           .set("box-shadow", "0 2px 5px rgba(0,0,0,0.05)");
//        
//        return grid;
//    }
//
//    private Grid<CareerMapping> createMappingGrid() {
//        Grid<CareerMapping> grid = new Grid<>();
//        
//        // Sample data
//        List<CareerMapping> mappings = Arrays.asList(
//            new CareerMapping("Software Engineer", "Analytical", "High"),
//            new CareerMapping("Teacher", "Social", "High"),
//            new CareerMapping("Graphic Designer", "Creative", "High"),
//            new CareerMapping("Electrician", "Practical", "Medium"),
//            new CareerMapping("Manager", "Leadership", "High")
//        );
//        
//        grid.setItems(mappings);
//        grid.addColumn(CareerMapping::getCareer).setHeader("Career");
//        grid.addColumn(CareerMapping::getPersonalityType).setHeader("Personality Type");
//        grid.addColumn(CareerMapping::getMatchStrength).setHeader("Match Strength");
//        
//        grid.addComponentColumn(mapping -> {
//            HorizontalLayout actions = new HorizontalLayout();
//            Button editBtn = new Button(VaadinIcon.EDIT.create());
//            Button deleteBtn = new Button(VaadinIcon.TRASH.create());
//            deleteBtn.getStyle().set("color", "red");
//            actions.add(editBtn, deleteBtn);
//            return actions;
//        }).setHeader("Actions");
//        
//        grid.getStyle()
//           .set("background-color", "white")
//           .set("border-radius", "10px")
//           .set("box-shadow", "0 2px 5px rgba(0,0,0,0.05)");
//        
//        return grid;
//    }
//
//    private void showAddQuestionDialog() {
//        Dialog dialog = new Dialog();
//        dialog.setCloseOnEsc(true);
//        dialog.setCloseOnOutsideClick(true);
//        
//        H2 header = new H2("Add New Question");
//        
//        FormLayout form = new FormLayout();
//        TextField questionId = new TextField("Question ID");
//        TextArea questionText = new TextArea("Question Text");
//        ComboBox<String> category = new ComboBox<>("Category");
//        category.setItems("Analytical", "Social", "Creative", "Practical", "Leadership");
//        
//        form.add(questionId, questionText, category);
//        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));
//        
//        HorizontalLayout buttons = new HorizontalLayout();
//        Button saveBtn = new Button("Save", VaadinIcon.CHECK.create());
//        Button cancelBtn = new Button("Cancel", VaadinIcon.CLOSE.create());
//        buttons.add(saveBtn, cancelBtn);
//        
//        dialog.add(header, form, buttons);
//        cancelBtn.addClickListener(e -> dialog.close());
//        
//        dialog.open();
//    }
//
//    private void showAddMappingDialog() {
//        Dialog dialog = new Dialog();
//        dialog.setCloseOnEsc(true);
//        dialog.setCloseOnOutsideClick(true);
//        
//        H2 header = new H2("Add Career Mapping");
//        
//        FormLayout form = new FormLayout();
//        ComboBox<String> career = new ComboBox<>("Career");
//        career.setItems("Software Engineer", "Teacher", "Doctor", "Artist", "Business Manager");
//        
//        ComboBox<String> personalityType = new ComboBox<>("Personality Type");
//        personalityType.setItems("Analytical", "Social", "Creative", "Practical", "Leadership");
//        
//        ComboBox<String> matchStrength = new ComboBox<>("Match Strength");
//        matchStrength.setItems("High", "Medium", "Low");
//        
//        form.add(career, personalityType, matchStrength);
//        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));
//        
//        HorizontalLayout buttons = new HorizontalLayout();
//        Button saveBtn = new Button("Save", VaadinIcon.CHECK.create());
//        Button cancelBtn = new Button("Cancel", VaadinIcon.CLOSE.create());
//        buttons.add(saveBtn, cancelBtn);
//        
//        dialog.add(header, form, buttons);
//        cancelBtn.addClickListener(e -> dialog.close());
//        
//        dialog.open();
//    }
//
//    private void showResultDetails(TestResult result) {
//        Dialog dialog = new Dialog();
//        dialog.setCloseOnEsc(true);
//        dialog.setCloseOnOutsideClick(true);
//        
//        H2 header = new H2("Test Result Details: " + result.getName());
//        
//        VerticalLayout details = new VerticalLayout();
//        details.add(new Paragraph("ID: " + result.getId()));
//        details.add(new Paragraph("Name: " + result.getName()));
//        details.add(new Paragraph("Personality Type: " + result.getPersonalityType()));
//        details.add(new Paragraph("Recommended Career: " + result.getRecommendedCareer()));
//        details.add(new Paragraph("Match Rate: " + result.getMatchRate()));
//        details.add(new Paragraph("Date Taken: " + result.getDateTaken()));
//        
//        // In a real app, you would show the actual answers here
//        H3 answersHeader = new H3("Selected Answers");
//        VerticalLayout answers = new VerticalLayout();
//        answers.add(new Paragraph("1. Yes - Enjoy solving complex problems"));
//        answers.add(new Paragraph("2. No - Prefer working alone"));
//        answers.add(new Paragraph("3. Yes - Like creating new things"));
//        
//        Button closeBtn = new Button("Close", VaadinIcon.CLOSE.create());
//        closeBtn.addClickListener(e -> dialog.close());
//        
//        dialog.add(header, details, answersHeader, answers, closeBtn);
//        dialog.open();
//    }
//
//    // Dummy data classes
//    private static class TestResult {
//        private String id;
//        private String name;
//        private String personalityType;
//        private String recommendedCareer;
//        private String matchRate;
//        private String dateTaken;
//
//        public TestResult(String id, String name, String personalityType, String recommendedCareer, String matchRate, String dateTaken) {
//            this.id = id;
//            this.name = name;
//            this.personalityType = personalityType;
//            this.recommendedCareer = recommendedCareer;
//            this.matchRate = matchRate;
//            this.dateTaken = dateTaken;
//        }
//
//        // Getters
//        public String getId() { return id; }
//        public String getName() { return name; }
//        public String getPersonalityType() { return personalityType; }
//        public String getRecommendedCareer() { return recommendedCareer; }
//        public String getMatchRate() { return matchRate; }
//        public String getDateTaken() { return dateTaken; }
//    }
//
//    private static class TestQuestion {
//        private String id;
//        private String questionText;
//        private String category;
//
//        public TestQuestion(String id, String questionText, String category) {
//            this.id = id;
//            this.questionText = questionText;
//            this.category = category;
//        }
//
//        // Getters
//        public String getId() { return id; }
//        public String getQuestionText() { return questionText; }
//        public String getCategory() { return category; }
//    }
//
//    private static class CareerMapping {
//        private String career;
//        private String personalityType;
//        private String matchStrength;
//
//        public CareerMapping(String career, String personalityType, String matchStrength) {
//            this.career = career;
//            this.personalityType = personalityType;
//            this.matchStrength = matchStrength;
//        }
//
//        // Getters
//        public String getCareer() { return career; }
//        public String getPersonalityType() { return personalityType; }
//        public String getMatchStrength() { return matchStrength; }
//    }
//}


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
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.util.Arrays;
import java.util.List;

@Route("personality-tests")
public class PersonalityTestView extends VerticalLayout {

    public PersonalityTestView() {
        // Main layout with scrollable content
        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.setSizeFull();
        mainLayout.setPadding(false);
        mainLayout.setSpacing(false);

        // Sidebar
        VerticalLayout sidebar = createSidebar();
        sidebar.setWidth("240px");
        sidebar.getStyle()
            .set("background", "#F4F4F4")
            .set("box-shadow", "2px 0 5px rgba(0,0,0,0.1)")
            .set("height", "100vh") // Full viewport height
            .set("position", "fixed"); // Fixed position

        // Main content area with scrolling
        Div scrollableContent = new Div();
        scrollableContent.getStyle()
            .set("margin-left", "240px") // Account for fixed sidebar
            .set("width", "calc(100% - 240px)")
            .set("padding", "20px")
            .set("overflow-y", "auto")
            .set("height", "100vh"); // Full viewport height with scroll

        // Main content
        VerticalLayout mainContent = createMainContent();
        scrollableContent.add(mainContent);

        mainLayout.add(sidebar, scrollableContent);
        add(mainLayout);
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
        main.setPadding(false);
        main.setSpacing(true);
        main.setWidthFull();

        // Header section
        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.setWidthFull();
        headerLayout.setJustifyContentMode(FlexLayout.JustifyContentMode.BETWEEN);

        VerticalLayout headingLayout = new VerticalLayout();
        H1 heading = new H1("Personality Test Management");
        heading.getStyle().set("color", "#E65100").set("margin", "0");
        Paragraph subtitle = new Paragraph("View and manage personality tests and results");
        headingLayout.add(heading, subtitle);

        Button addQuestionBtn = new Button("Add New Question", VaadinIcon.PLUS.create());
        addQuestionBtn.addClickListener(e -> showAddQuestionDialog());
        addQuestionBtn.getStyle().set("background-color", "#E65100").set("color", "white");

        headerLayout.add(headingLayout, addQuestionBtn);
        main.add(headerLayout);

        // Stats cards
        HorizontalLayout statsLayout = new HorizontalLayout();
        statsLayout.setWidthFull();
        statsLayout.setSpacing(true);

        statsLayout.add(
            createStatCard("1,234", "Total Tests Taken", VaadinIcon.CLIPBOARD_TEXT),
            createStatCard("87%", "Average Match Rate", VaadinIcon.CHECK_CIRCLE),
            createStatCard("12", "Career Categories", VaadinIcon.BOOKMARK)
        );
        main.add(statsLayout);

        // Main content tabs
        Div tabs = new Div();
        tabs.getStyle().set("display", "flex").set("gap", "10px").set("margin-bottom", "10px");

        Button resultsTab = new Button("Test Results");
        Button questionsTab = new Button("Question Bank");
        Button mappingTab = new Button("Career Mapping");
        
        tabs.add(resultsTab, questionsTab, mappingTab);
        main.add(tabs);

        // Results section
        VerticalLayout resultsSection = new VerticalLayout();
        resultsSection.setPadding(false);
        resultsSection.setSpacing(false);
        
        // Results charts
        HorizontalLayout chartsLayout = new HorizontalLayout();
        chartsLayout.setWidthFull();
        chartsLayout.setSpacing(true);
        
        // Create chart containers
        Div personalityChartContainer = new Div();
        personalityChartContainer.setId("personality-chart");
        personalityChartContainer.getStyle()
            .set("background-color", "white")
            .set("padding", "20px")
            .set("border-radius", "10px")
            .set("box-shadow", "0 2px 5px rgba(0,0,0,0.05)")
            .set("width", "48%")
            .set("height", "300px");
        
        Div careerChartContainer = new Div();
        careerChartContainer.setId("career-chart");
        careerChartContainer.getStyle()
            .set("background-color", "white")
            .set("padding", "20px")
            .set("border-radius", "10px")
            .set("box-shadow", "0 2px 5px rgba(0,0,0,0.05)")
            .set("width", "48%")
            .set("height", "300px");
        
        chartsLayout.add(personalityChartContainer, careerChartContainer);
        resultsSection.add(chartsLayout);
        
        // Results grid
        H3 resultsHeader = new H3("Recent Test Results");
        Grid<TestResult> resultsGrid = createResultsGrid();
        resultsSection.add(resultsHeader, resultsGrid);
        
        // Question bank section (hidden by default)
        VerticalLayout questionsSection = new VerticalLayout();
        questionsSection.setPadding(false);
        questionsSection.setSpacing(false);
        questionsSection.setVisible(false);
        
        Grid<TestQuestion> questionsGrid = createQuestionsGrid();
        questionsSection.add(new H3("Personality Test Questions"), questionsGrid);
        
        // Career mapping section (hidden by default)
        VerticalLayout mappingSection = new VerticalLayout();
        mappingSection.setPadding(false);
        mappingSection.setSpacing(false);
        mappingSection.setVisible(false);
        
        Grid<CareerMapping> mappingGrid = createMappingGrid();
        Button addMappingBtn = new Button("Add Career Mapping", VaadinIcon.PLUS.create());
        addMappingBtn.addClickListener(e -> showAddMappingDialog());
        
        mappingSection.add(new H3("Career Personality Mapping"), addMappingBtn, mappingGrid);
        
        // Tab switching logic
        resultsTab.addClickListener(e -> {
            resultsSection.setVisible(true);
            questionsSection.setVisible(false);
            mappingSection.setVisible(false);
            resultsTab.getStyle().set("background-color", "#E65100").set("color", "white");
            questionsTab.getStyle().remove("background-color").set("color", "inherit");
            mappingTab.getStyle().remove("background-color").set("color", "inherit");
        });
        
        questionsTab.addClickListener(e -> {
            resultsSection.setVisible(false);
            questionsSection.setVisible(true);
            mappingSection.setVisible(false);
            questionsTab.getStyle().set("background-color", "#E65100").set("color", "white");
            resultsTab.getStyle().remove("background-color").set("color", "inherit");
            mappingTab.getStyle().remove("background-color").set("color", "inherit");
        });
        
        mappingTab.addClickListener(e -> {
            resultsSection.setVisible(false);
            questionsSection.setVisible(false);
            mappingSection.setVisible(true);
            mappingTab.getStyle().set("background-color", "#E65100").set("color", "white");
            resultsTab.getStyle().remove("background-color").set("color", "inherit");
            questionsTab.getStyle().remove("background-color").set("color", "inherit");
        });
        
        // Set default tab
        resultsTab.getStyle().set("background-color", "#E65100").set("color", "white");
        
        main.add(resultsSection, questionsSection, mappingSection);

        // Add Chart.js and initialize charts
        initializeCharts();
        
        return main;
    }

    private void initializeCharts() {
        // Load Chart.js library
        UI.getCurrent().getPage().addJavaScript("https://cdn.jsdelivr.net/npm/chart.js");

        // Initialize personality type chart (pie chart)
        String personalityChartJs = """
            const personalityCtx = document.getElementById('personality-chart').appendChild(document.createElement('canvas'));
            
            new Chart(personalityCtx, {
                type: 'pie',
                data: {
                    labels: ['Analytical', 'Social', 'Creative', 'Practical', 'Leadership'],
                    datasets: [{
                        data: [35, 25, 20, 15, 5],
                        backgroundColor: [
                            'rgba(230, 81, 0, 0.8)',
                            'rgba(230, 81, 0, 0.6)',
                            'rgba(230, 81, 0, 0.4)',
                            'rgba(230, 81, 0, 0.3)',
                            'rgba(230, 81, 0, 0.2)'
                        ],
                        borderColor: '#ffffff',
                        borderWidth: 2
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    plugins: {
                        title: {
                            display: true,
                            text: 'Personality Type Distribution',
                            font: {
                                size: 16
                            }
                        },
                        legend: {
                            position: 'right'
                        }
                    }
                }
            });
        """;

        // Initialize career matches chart (bar chart)
        String careerChartJs = """
            const careerCtx = document.getElementById('career-chart').appendChild(document.createElement('canvas'));
            
            new Chart(careerCtx, {
                type: 'bar',
                data: {
                    labels: ['Software Engineer', 'Teacher', 'Doctor', 'Artist', 'Manager'],
                    datasets: [{
                        label: 'Matches',
                        data: [120, 95, 80, 65, 50],
                        backgroundColor: 'rgba(230, 81, 0, 0.7)',
                        borderColor: 'rgba(230, 81, 0, 1)',
                        borderWidth: 1
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    plugins: {
                        title: {
                            display: true,
                            text: 'Top Career Matches',
                            font: {
                                size: 16
                            }
                        },
                        legend: {
                            display: false
                        }
                    },
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });
        """;

        // Execute the JavaScript
        UI.getCurrent().getPage().executeJs(personalityChartJs);
        UI.getCurrent().getPage().executeJs(careerChartJs);
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

    private Grid<TestResult> createResultsGrid() {
        Grid<TestResult> grid = new Grid<>();
        
        // Sample data
        List<TestResult> results = Arrays.asList(
            new TestResult("1001", "John Doe", "Analytical", "Software Engineer", "92%", "2023-05-15"),
            new TestResult("1002", "Jane Smith", "Social", "Teacher", "88%", "2023-05-14"),
            new TestResult("1003", "Alice Johnson", "Creative", "Graphic Designer", "85%", "2023-05-13"),
            new TestResult("1004", "Bob Brown", "Practical", "Electrician", "90%", "2023-05-12"),
            new TestResult("1005", "Charlie Davis", "Leadership", "Manager", "82%", "2023-05-11")
        );
        
        grid.setItems(results);
        grid.addColumn(TestResult::getId).setHeader("ID").setWidth("80px");
        grid.addColumn(TestResult::getName).setHeader("Name");
        grid.addColumn(TestResult::getPersonalityType).setHeader("Personality Type");
        grid.addColumn(TestResult::getRecommendedCareer).setHeader("Recommended Career");
        grid.addColumn(TestResult::getMatchRate).setHeader("Match Rate");
        grid.addColumn(TestResult::getDateTaken).setHeader("Date Taken");
        
        grid.addComponentColumn(result -> {
            Button viewBtn = new Button("View", VaadinIcon.EYE.create());
            viewBtn.addClickListener(e -> showResultDetails(result));
            return viewBtn;
        }).setHeader("Actions");
        
        grid.getStyle()
           .set("background-color", "white")
           .set("border-radius", "10px")
           .set("box-shadow", "0 2px 5px rgba(0,0,0,0.05)")
           .set("width", "100%");
        
        return grid;
    }

    private Grid<TestQuestion> createQuestionsGrid() {
        Grid<TestQuestion> grid = new Grid<>();
        
        // Sample data
        List<TestQuestion> questions = Arrays.asList(
            new TestQuestion("Q1", "Do you enjoy solving complex problems?", "Analytical"),
            new TestQuestion("Q2", "Do you prefer working in teams?", "Social"),
            new TestQuestion("Q3", "Do you like creating new things?", "Creative"),
            new TestQuestion("Q4", "Do you prefer hands-on work?", "Practical"),
            new TestQuestion("Q5", "Do you like taking charge of situations?", "Leadership")
        );
        
        grid.setItems(questions);
        grid.addColumn(TestQuestion::getId).setHeader("ID").setWidth("80px");
        grid.addColumn(TestQuestion::getQuestionText).setHeader("Question");
        grid.addColumn(TestQuestion::getCategory).setHeader("Category");
        
        grid.addComponentColumn(question -> {
            HorizontalLayout actions = new HorizontalLayout();
            Button editBtn = new Button(VaadinIcon.EDIT.create());
            Button deleteBtn = new Button(VaadinIcon.TRASH.create());
            deleteBtn.getStyle().set("color", "red");
            actions.add(editBtn, deleteBtn);
            return actions;
        }).setHeader("Actions");
        
        grid.getStyle()
           .set("background-color", "white")
           .set("border-radius", "10px")
           .set("box-shadow", "0 2px 5px rgba(0,0,0,0.05)")
           .set("width", "100%");
        
        return grid;
    }

    private Grid<CareerMapping> createMappingGrid() {
        Grid<CareerMapping> grid = new Grid<>();
        
        // Sample data
        List<CareerMapping> mappings = Arrays.asList(
            new CareerMapping("Software Engineer", "Analytical", "High"),
            new CareerMapping("Teacher", "Social", "High"),
            new CareerMapping("Graphic Designer", "Creative", "High"),
            new CareerMapping("Electrician", "Practical", "Medium"),
            new CareerMapping("Manager", "Leadership", "High")
        );
        
        grid.setItems(mappings);
        grid.addColumn(CareerMapping::getCareer).setHeader("Career");
        grid.addColumn(CareerMapping::getPersonalityType).setHeader("Personality Type");
        grid.addColumn(CareerMapping::getMatchStrength).setHeader("Match Strength");
        
        grid.addComponentColumn(mapping -> {
            HorizontalLayout actions = new HorizontalLayout();
            Button editBtn = new Button(VaadinIcon.EDIT.create());
            Button deleteBtn = new Button(VaadinIcon.TRASH.create());
            deleteBtn.getStyle().set("color", "red");
            actions.add(editBtn, deleteBtn);
            return actions;
        }).setHeader("Actions");
        
        grid.getStyle()
           .set("background-color", "white")
           .set("border-radius", "10px")
           .set("box-shadow", "0 2px 5px rgba(0,0,0,0.05)")
           .set("width", "100%");
        
        return grid;
    }

    private void showAddQuestionDialog() {
        Dialog dialog = new Dialog();
        dialog.setCloseOnEsc(true);
        dialog.setCloseOnOutsideClick(true);
        
        H2 header = new H2("Add New Question");
        
        FormLayout form = new FormLayout();
        TextField questionId = new TextField("Question ID");
        TextArea questionText = new TextArea("Question Text");
        ComboBox<String> category = new ComboBox<>("Category");
        category.setItems("Analytical", "Social", "Creative", "Practical", "Leadership");
        
        form.add(questionId, questionText, category);
        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));
        
        HorizontalLayout buttons = new HorizontalLayout();
        Button saveBtn = new Button("Save", VaadinIcon.CHECK.create());
        Button cancelBtn = new Button("Cancel", VaadinIcon.CLOSE.create());
        buttons.add(saveBtn, cancelBtn);
        
        dialog.add(header, form, buttons);
        cancelBtn.addClickListener(e -> dialog.close());
        
        dialog.open();
    }

    private void showAddMappingDialog() {
        Dialog dialog = new Dialog();
        dialog.setCloseOnEsc(true);
        dialog.setCloseOnOutsideClick(true);
        
        H2 header = new H2("Add Career Mapping");
        
        FormLayout form = new FormLayout();
        ComboBox<String> career = new ComboBox<>("Career");
        career.setItems("Software Engineer", "Teacher", "Doctor", "Artist", "Business Manager");
        
        ComboBox<String> personalityType = new ComboBox<>("Personality Type");
        personalityType.setItems("Analytical", "Social", "Creative", "Practical", "Leadership");
        
        ComboBox<String> matchStrength = new ComboBox<>("Match Strength");
        matchStrength.setItems("High", "Medium", "Low");
        
        form.add(career, personalityType, matchStrength);
        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));
        
        HorizontalLayout buttons = new HorizontalLayout();
        Button saveBtn = new Button("Save", VaadinIcon.CHECK.create());
        Button cancelBtn = new Button("Cancel", VaadinIcon.CLOSE.create());
        buttons.add(saveBtn, cancelBtn);
        
        dialog.add(header, form, buttons);
        cancelBtn.addClickListener(e -> dialog.close());
        
        dialog.open();
    }

    private void showResultDetails(TestResult result) {
        Dialog dialog = new Dialog();
        dialog.setCloseOnEsc(true);
        dialog.setCloseOnOutsideClick(true);
        
        H2 header = new H2("Test Result Details: " + result.getName());
        
        VerticalLayout details = new VerticalLayout();
        details.add(new Paragraph("ID: " + result.getId()));
        details.add(new Paragraph("Name: " + result.getName()));
        details.add(new Paragraph("Personality Type: " + result.getPersonalityType()));
        details.add(new Paragraph("Recommended Career: " + result.getRecommendedCareer()));
        details.add(new Paragraph("Match Rate: " + result.getMatchRate()));
        details.add(new Paragraph("Date Taken: " + result.getDateTaken()));
        
        // In a real app, you would show the actual answers here
        H3 answersHeader = new H3("Selected Answers");
        VerticalLayout answers = new VerticalLayout();
        answers.add(new Paragraph("1. Yes - Enjoy solving complex problems"));
        answers.add(new Paragraph("2. No - Prefer working alone"));
        answers.add(new Paragraph("3. Yes - Like creating new things"));
        
        Button closeBtn = new Button("Close", VaadinIcon.CLOSE.create());
        closeBtn.addClickListener(e -> dialog.close());
        
        dialog.add(header, details, answersHeader, answers, closeBtn);
        dialog.open();
    }

    // Dummy data classes
    private static class TestResult {
        private String id;
        private String name;
        private String personalityType;
        private String recommendedCareer;
        private String matchRate;
        private String dateTaken;

        public TestResult(String id, String name, String personalityType, String recommendedCareer, String matchRate, String dateTaken) {
            this.id = id;
            this.name = name;
            this.personalityType = personalityType;
            this.recommendedCareer = recommendedCareer;
            this.matchRate = matchRate;
            this.dateTaken = dateTaken;
        }

        // Getters
        public String getId() { return id; }
        public String getName() { return name; }
        public String getPersonalityType() { return personalityType; }
        public String getRecommendedCareer() { return recommendedCareer; }
        public String getMatchRate() { return matchRate; }
        public String getDateTaken() { return dateTaken; }
    }

    private static class TestQuestion {
        private String id;
        private String questionText;
        private String category;

        public TestQuestion(String id, String questionText, String category) {
            this.id = id;
            this.questionText = questionText;
            this.category = category;
        }

        // Getters
        public String getId() { return id; }
        public String getQuestionText() { return questionText; }
        public String getCategory() { return category; }
    }

    private static class CareerMapping {
        private String career;
        private String personalityType;
        private String matchStrength;

        public CareerMapping(String career, String personalityType, String matchStrength) {
            this.career = career;
            this.personalityType = personalityType;
            this.matchStrength = matchStrength;
        }

        // Getters
        public String getCareer() { return career; }
        public String getPersonalityType() { return personalityType; }
        public String getMatchStrength() { return matchStrength; }
    }
}
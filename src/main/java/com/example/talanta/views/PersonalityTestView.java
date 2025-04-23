//package com.example.talanta.views;
//
//import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.dialog.Dialog;
//import com.vaadin.flow.component.formlayout.FormLayout;
//import com.vaadin.flow.component.grid.Grid;
//import com.vaadin.flow.component.html.H2;
//import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.component.textfield.TextField;
//import com.vaadin.flow.component.combobox.ComboBox;
//import com.vaadin.flow.component.html.Div;
//import com.vaadin.flow.router.Route;
//import com.vaadin.flow.component.icon.VaadinIcon;
//import com.vaadin.flow.component.icon.Icon;
//import com.vaadin.flow.component.html.Span;
//import com.vaadin.flow.component.orderedlayout.FlexComponent;
//import com.vaadin.flow.data.renderer.ComponentRenderer;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Route("personality-tests")
//public class PersonalityTestView extends HorizontalLayout {
//
//    private Grid<Question> questionGrid;
//    private List<Question> questions;
//    private final String primaryColor = "#E65100";
//    private final String secondaryColor = "#F5F5F5";
//
//    private static final String CSS = """
//        .personality-test-view {
//            display: flex;
//            gap: 0;
//            height: 100vh;
//            margin: 0;
//            padding: 0;
//        }
//
//        /* Enhanced Sidebar Styling */
//        .sidebar {
//            width: 280px;
//            background-color: #2C3E50;
//            padding: 20px 0;
//            box-shadow: 2px 0 10px rgba(0, 0, 0, 0.1);
//            z-index: 100;
//            height: 100vh;
//            position: relative;
//        }
//
//        .sidebar .logo {
//            width: 80%;
//            max-width: 180px;
//            margin: 0 auto 30px;
//            display: block;
//            padding: 0 20px;
//        }
//
//        .sidebar .nav-item {
//            padding: 12px 25px;
//            cursor: pointer;
//            transition: all 0.3s ease;
//            align-items: center;
//            margin: 5px 10px;
//            color: #ECF0F1;
//            border-radius: 4px;
//            font-size: 0.95em;
//        }
//
//        .sidebar .nav-item:hover {
//            background-color: #34495E;
//        }
//
//        .sidebar .nav-item.active {
//            background-color: #E65100;
//            font-weight: 500;
//            color: white;
//        }
//
//        .sidebar .nav-item.active .vaadin-icon {
//            color: white;
//        }
//
//        .sidebar .nav-item .vaadin-icon {
//            margin-right: 12px;
//            color: #BDC3C7;
//        }
//
//        /* Main Content Area */
//        .main-content-container {
//            flex-grow: 1;
//            background-color: #F8F9FA;
//            height: 100vh;
//            overflow-y: auto;
//            padding: 0;
//        }
//
//        .main-content {
//            max-width: 1200px;
//            margin: 0 auto;
//            padding: 30px 40px;
//        }
//
//        .view-title {
//            color: #2C3E50;
//            margin: 0 0 25px 0;
//            font-size: 2.2em;
//            font-weight: 600;
//        }
//
//        .top-bar {
//            display: flex;
//            align-items: center;
//            gap: 15px;
//            margin-bottom: 30px;
//            justify-content: space-between;
//        }
//
//        .add-button, .preview-button {
//            background-color: #E65100;
//            color: white;
//            padding: 12px 24px;
//            border-radius: 6px;
//            border: none;
//            cursor: pointer;
//            font-size: 1em;
//            font-weight: 500;
//            transition: background-color 0.2s;
//            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
//        }
//
//        .add-button:hover, .preview-button:hover {
//            background-color: #D84315;
//            box-shadow: 0 3px 8px rgba(0, 0, 0, 0.15);
//        }
//
//        .question-grid {
//            border: 1px solid #E0E0E0;
//            border-radius: 8px;
//            background-color: white;
//            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
//            overflow: hidden;
//        }
//
//        /* Grid Header Styling */
//        .question-grid [part~="header-cell"] {
//            background-color: #F5F5F5;
//            color: #2C3E50;
//            font-weight: 600;
//            border-bottom: 1px solid #E0E0E0;
//        }
//
//        /* Grid Row Styling */
//        .question-grid [part~="row"] {
//            border-bottom: 1px solid #F0F0F0;
//        }
//
//        .question-grid [part~="row"]:hover {
//            background-color: #F9F9F9;
//        }
//
//        .edit-button, .delete-button {
//            padding: 8px 16px;
//            border-radius: 4px;
//            border: none;
//            cursor: pointer;
//            font-size: 0.9em;
//            font-weight: 500;
//            margin-right: 8px;
//            transition: all 0.2s;
//        }
//
//        .edit-button {
//            background-color: #3498DB;
//            color: white;
//        }
//
//        .delete-button {
//            background-color: #E74C3C;
//            color: white;
//        }
//
//        .edit-button:hover {
//            background-color: #2980B9;
//        }
//
//        .delete-button:hover {
//            background-color: #C0392B;
//        }
//
//        .question-dialog {
//            width: 600px;
//            padding: 25px;
//            border-radius: 10px;
//            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.15);
//            background-color: white;
//            border: none;
//        }
//
//        .dialog-title {
//            color: #2C3E50;
//            margin-bottom: 25px;
//            font-size: 1.8em;
//            font-weight: 500;
//        }
//
//        .dialog-actions {
//            margin-top: 30px;
//            display: flex;
//            justify-content: flex-end;
//            gap: 15px;
//        }
//
//        .dialog-actions button {
//            padding: 12px 24px;
//            border-radius: 6px;
//            font-weight: 500;
//            transition: all 0.2s;
//        }
//
//        .dialog-actions .save {
//            background-color: #E65100;
//            color: white;
//        }
//
//        .dialog-actions .save:hover {
//            background-color: #D84315;
//        }
//
//        .dialog-actions .cancel {
//            background-color: #ECF0F1;
//            color: #7F8C8D;
//        }
//
//        .dialog-actions .cancel:hover {
//            background-color: #D5DBDB;
//        }
//    """;
//
//    public PersonalityTestView() {
//        setSizeFull();
//        setPadding(false);
//        setMargin(false);
//        setSpacing(false);
//        addClassName("personality-test-view");
//
//        VerticalLayout sidebar = createSidebar();
//        Div mainContentContainer = new Div();
//        mainContentContainer.addClassName("main-content-container");
//        VerticalLayout mainContent = createMainContent();
//        mainContentContainer.add(mainContent);
//
//        add(sidebar, mainContentContainer);
//        expand(mainContentContainer);
//    }
//
//    private VerticalLayout createSidebar() {
//        VerticalLayout sidebar = new VerticalLayout();
//        sidebar.addClassName("sidebar");
//
//        com.vaadin.flow.component.html.Image logo = new com.vaadin.flow.component.html.Image("images/2.png", "Talanta Logo");
//        logo.setWidth("80%");
//        logo.setMaxWidth("180px");
//        logo.setHeight("auto");
//        logo.addClassName("logo");
//        logo.getStyle().set("margin", "0 auto 30px");
//        logo.getStyle().set("display", "block");
//        logo.getStyle().set("padding", "0 20px");
//
//        sidebar.add(
//                logo,
//                createNavItem("Dashboard", VaadinIcon.DASHBOARD),
//                createNavItem("Users", VaadinIcon.USER),
//                createNavItem("Personality Tests", VaadinIcon.CLIPBOARD_TEXT, true),
//                createNavItem("Courses & Careers", VaadinIcon.BOOK),
//                createNavItem("Institutions", VaadinIcon.ACADEMY_CAP),
//                createNavItem("Payments", VaadinIcon.CREDIT_CARD),
//                createNavItem("Reports", VaadinIcon.CHART),
//                createNavItem("Content", VaadinIcon.FILE_TEXT),
//                createNavItem("Notifications", VaadinIcon.BELL),
//                createNavItem("Settings", VaadinIcon.COG)
//        );
//        return sidebar;
//    }
//
//    private HorizontalLayout createNavItem(String title, VaadinIcon icon) {
//        return createNavItem(title, icon, false);
//    }
//
//    private HorizontalLayout createNavItem(String title, VaadinIcon icon, boolean isActive) {
//        Icon menuIcon = icon.create();
//        menuIcon.setColor(primaryColor);
//        Span label = new Span(title);
//
//        HorizontalLayout nav = new HorizontalLayout(menuIcon, label);
//        nav.setPadding(true);
//        nav.setSpacing(true);
//        nav.setAlignItems(FlexComponent.Alignment.CENTER);
//        nav.addClassName("nav-item");
//
//        if (isActive) {
//            nav.addClassName("active");
//        }
//
//        return nav;
//    }
//
//    private VerticalLayout createMainContent() {
//        VerticalLayout mainContent = new VerticalLayout();
//        mainContent.addClassName("main-content");
//        mainContent.setPadding(false);
//        mainContent.setSpacing(false);
//
//        H2 title = new H2("Personality Tests");
//        title.addClassName("view-title");
//
//        Button addButton = new Button("Add New Question", e -> openAddQuestionDialog());
//        addButton.setIcon(new Icon(VaadinIcon.PLUS));
//        addButton.addClassName("add-button");
//
//        Button previewButton = new Button("Preview Test", e -> {
//        });
//        previewButton.setIcon(new Icon(VaadinIcon.EYE));
//        previewButton.addClassName("preview-button");
//
//        HorizontalLayout topBar = new HorizontalLayout(addButton, previewButton);
//        topBar.addClassName("top-bar");
//        topBar.setWidthFull();
//        topBar.setAlignItems(FlexComponent.Alignment.CENTER);
//        topBar.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
//
//        questions = getSampleYesNoQuestions();
//
//        questionGrid = new Grid<>(Question.class, false);
//        questionGrid.addClassName("question-grid");
//
//        // Configure columns with better styling
//        questionGrid.addColumn(Question::getId)
//                .setHeader("ID")
//                .setWidth("80px")
//                .setFlexGrow(0)
//                .setResizable(true);
//
//        questionGrid.addColumn(Question::getText)
//                .setHeader("Question Text")
//                .setAutoWidth(true)
//                .setFlexGrow(1);
//
//        questionGrid.addColumn(q -> "Yes/No")
//                .setHeader("Type")
//                .setWidth("120px")
//                .setFlexGrow(0);
//
//        questionGrid.addColumn(Question::getTrait)
//                .setHeader("Personality Trait")
//                .setWidth("180px")
//                .setFlexGrow(0);
//
//        questionGrid.addColumn(
//                new ComponentRenderer<>(question -> {
//                    HorizontalLayout actions = new HorizontalLayout();
//                    Button editButton = new Button("Edit", editEvent -> openEditQuestionDialog(question));
//                    editButton.addClassName("edit-button");
//                    Button deleteButton = new Button("Delete", deleteEvent -> deleteQuestion(question));
//                    deleteButton.addClassName("delete-button");
//                    actions.add(editButton, deleteButton);
//                    return actions;
//                }))
//                .setHeader("Actions")
//                .setWidth("200px")
//                .setFlexGrow(0);
//
//        questionGrid.setItems(questions);
//        questionGrid.setHeightFull();
//
//        mainContent.add(title, topBar, questionGrid);
//        mainContent.setFlexGrow(1, questionGrid);
//        mainContent.setSizeFull();
//
//        return mainContent;
//    }
//
//    private void openAddQuestionDialog() {
//        openQuestionDialog(null);
//    }
//
//    private void openEditQuestionDialog(Question question) {
//        openQuestionDialog(question);
//    }
//
//    private void openQuestionDialog(Question editQuestion) {
//        Dialog dialog = new Dialog();
//        dialog.addClassName("question-dialog");
//        dialog.setHeaderTitle(editQuestion == null ? "Add Yes/No Question" : "Edit Yes/No Question");
//        dialog.setCloseOnOutsideClick(false);
//        dialog.setCloseOnEsc(true);
//
//        VerticalLayout dialogLayout = new VerticalLayout();
//        dialogLayout.setPadding(true);
//        dialogLayout.setSpacing(true);
//        dialogLayout.setWidthFull();
//
//        TextField questionText = new TextField("Question Text");
//        questionText.setWidthFull();
//        questionText.getStyle().set("margin-bottom", "15px"); // Add some space below
//
//        ComboBox<String> traitSelect = new ComboBox<>("Personality Trait");
//        traitSelect.setItems("Leadership", "Creativity", "Introversion", "Extroversion", "Teamwork", "Decision Making");
//        traitSelect.setWidthFull();
//        traitSelect.getStyle().set("margin-bottom", "20px"); // Add more space below
//
//        FormLayout formLayout = new FormLayout();
//        formLayout.add(questionText, traitSelect);
//        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));
//        formLayout.setWidthFull();
//
//        dialogLayout.add(formLayout);
//
//        if (editQuestion != null) {
//            questionText.setValue(editQuestion.getText());
//            traitSelect.setValue(editQuestion.getTrait());
//        }
//
//        Button saveButton = new Button("Save", e -> {
//            String text = questionText.getValue();
//            String trait = traitSelect.getValue();
//            if (text != null && !text.isEmpty() && trait != null && !trait.isEmpty()) {
//                if (editQuestion != null) {
//                    editQuestion.setText(text);
//                    editQuestion.setTrait(trait);
//                } else {
//                    questions.add(new Question(questions.size() + 1, text, List.of("Yes", "No"), trait, "Yes/No"));
//                }
//                refreshGrid();
//                dialog.close();
//            } else {
//                // Optionally show a validation message
//            }
//        });
//        saveButton.addClassName("save");
//        saveButton.getStyle().set("background-color", primaryColor);
//        saveButton.setIcon(new Icon(VaadinIcon.CHECK));
//        saveButton.getElement().getStyle().set("color", "white");
//
//        Button cancelButton = new Button("Cancel", e -> dialog.close());
//        cancelButton.addClassName("cancel");
//        cancelButton.setIcon(new Icon(VaadinIcon.CLOSE));
//
//        HorizontalLayout dialogActions = new HorizontalLayout(saveButton, cancelButton);
//        dialogActions.addClassName("dialog-actions");
//        dialogActions.setWidthFull();
//        dialogActions.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
//        dialogLayout.add(dialogActions);
//
//        dialog.add(dialogLayout);
//        dialog.open();
//    }
//
//    private void deleteQuestion(Question questionToDelete) {
//        questions.removeIf(question -> question.getId() == questionToDelete.getId());
//        refreshGrid();
//    }
//
//    private void refreshGrid() {
//        questionGrid.setItems(questions);
//    }
//
//    private List<Question> getSampleYesNoQuestions() {
//        List<Question> list = new ArrayList<>();
//        list.add(new Question(1, "Do you often take initiative?", List.of("Yes", "No"), "Leadership", "Yes/No"));
//        list.add(new Question(2, "Do you enjoy trying new things?", List.of("Yes", "No"), "Creativity", "Yes/No"));
//        list.add(new Question(3, "Do you prefer working alone?", List.of("Yes", "No"), "Introversion", "Yes/No"));
//        list.add(new Question(4, "Do you feel energized in social gatherings?", List.of("Yes", "No"), "Extroversion", "Yes/No"));
//        return list;
//    }
//
//    public static class Question {
//        private int id;
//        private String text;
//        private List<String> options;
//        private String trait;
//        private String type;
//
//        public Question(int id, String text, List<String> options, String trait, String type) {
//            this.id = id;
//            this.text = text;
//            this.options = options;
//            this.trait = trait;
//            this.type = type;
//        }
//
//        public int getId() {
//            return id;
//        }
//
//        public String getText() {
//            return text;
//        }
//
//        public List<String> getOptions() {
//            return options;
//        }
//
//        public String getTrait() {
//            return trait;
//        }
//
//        public String getType() {
//            return type;
//        }
//
//        public void setText(String text) {
//            this.text = text;
//        }
//
//        public void setTrait(String trait) {
//            this.trait = trait;
//        }
//
//        public void setOptions(List<String> options) {
//            this.options = options;
//        }
//
//        public void setType(String type) {
//            this.type = type;
//        }
//    }
//
//    protected void onAttach(com.vaadin.flow.component.AttachEvent attachEvent) {
//    }
//}
//

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
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;

import java.util.*;

@Route("personality-tests")
public class PersonalityTestView extends VerticalLayout {

    public PersonalityTestView() {
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
        
        chartsLayout.add(
            createPersonalityTypeChart(),
            createCareerMatchChart()
        );
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

    private VerticalLayout createPersonalityTypeChart() {
        Chart chart = new Chart(ChartType.PIE);
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Personality Type Distribution");

        DataSeries series = new DataSeries();
        series.add(new DataSeriesItem("Analytical", 35));
        series.add(new DataSeriesItem("Social", 25));
        series.add(new DataSeriesItem("Creative", 20));
        series.add(new DataSeriesItem("Practical", 15));
        series.add(new DataSeriesItem("Leadership", 5));

        conf.addSeries(series);
        chart.setWidthFull();
        
        VerticalLayout chartContainer = new VerticalLayout();
        chartContainer.getStyle()
           .set("background-color", "white")
           .set("padding", "20px")
           .set("border-radius", "10px")
           .set("box-shadow", "0 2px 5px rgba(0,0,0,0.05)")
           .set("width", "48%");
        chartContainer.add(chart);
        
        return chartContainer;
    }

    private VerticalLayout createCareerMatchChart() {
        Chart chart = new Chart(ChartType.COLUMN);
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Top Career Matches");

        List<String> categories = Arrays.asList(
            "Software Engineer", 
            "Teacher", 
            "Doctor", 
            "Artist", 
            "Business Manager"
        );
        conf.getxAxis().setCategories(categories.toArray(new String[0]));

        Number[] matches = {120, 95, 80, 65, 50};
        DataSeries series = new DataSeries("Matches");
        series.setData(matches);

        conf.addSeries(series);
        chart.setWidthFull();
        
        VerticalLayout chartContainer = new VerticalLayout();
        chartContainer.getStyle()
           .set("background-color", "white")
           .set("padding", "20px")
           .set("border-radius", "10px")
           .set("box-shadow", "0 2px 5px rgba(0,0,0,0.05)")
           .set("width", "48%");
        chartContainer.add(chart);
        
        return chartContainer;
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
           .set("box-shadow", "0 2px 5px rgba(0,0,0,0.05)");
        
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
           .set("box-shadow", "0 2px 5px rgba(0,0,0,0.05)");
        
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
           .set("box-shadow", "0 2px 5px rgba(0,0,0,0.05)");
        
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
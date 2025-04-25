package com.example.talanta.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.router.Route;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@JsModule("https://cdn.jsdelivr.net/npm/chart.js")
@Route("reports")
public class ReportsView extends VerticalLayout {

    public ReportsView() {
        setSizeFull();
        setPadding(false);
        setSpacing(false);

        HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();
        layout.setSpacing(false);
        
        // Reuse admin sidebar
        VerticalLayout sidebar = new AdminDashboardView().createSidebar();
        sidebar.setWidth("240px");
        sidebar.getStyle()
                .set("background", "#F4F4F4")
                .set("border-right", "1px solid #E0E0E0");

        // Main content area with scroll
        VerticalLayout mainContent = createMainContent();
        mainContent.getStyle().set("background", "#FAFAFA");
        
        // Wrap main content in a scroller
        Scroller scroller = new Scroller(mainContent);
        scroller.setSizeFull();
        scroller.setScrollDirection(Scroller.ScrollDirection.VERTICAL);

        layout.add(sidebar, scroller);
        add(layout);
    }

    private VerticalLayout createMainContent() {
        VerticalLayout content = new VerticalLayout();
        content.setPadding(false);
        content.setSpacing(false);
        content.setWidthFull();
        content.setHeight(null);

        // Header section
        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.getStyle()
                .set("background", "white")
                .set("padding", "1rem")
                .set("border-bottom", "1px solid #E0E0E0");

        H2 title = new H2("Reports Dashboard");
        title.getStyle()
                .set("margin", "0")
                .set("color", "#E65100");

        header.add(title);
        header.expand(title);

        // Report type tabs
        Tabs reportTabs = new Tabs();
        reportTabs.setWidthFull();
        reportTabs.add(
                createTab("User Reports"),
                createTab("Payment Reports"),
                createTab("Course Engagement"),
                createTab("Personality Tests"),
                createTab("Institution Reports")
        );
        reportTabs.setSelectedIndex(0);

        // Date range filter
        HorizontalLayout filters = new HorizontalLayout();
        filters.setWidthFull();
        filters.setPadding(true);
        filters.getStyle().set("background", "white");

        ComboBox<String> quickRange = new ComboBox<>("Quick Range");
        quickRange.setItems("Today", "This Week", "This Month", "This Year", "Custom");
        quickRange.setValue("This Month");
        quickRange.setWidth("150px");

        DatePicker startDate = new DatePicker("From");
        startDate.setValue(LocalDate.now().minusDays(30));
        startDate.setWidth("150px");

        DatePicker endDate = new DatePicker("To");
        endDate.setValue(LocalDate.now());
        endDate.setWidth("150px");

        Button applyFilter = new Button("Apply", new Icon(VaadinIcon.CHECK));
        applyFilter.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        applyFilter.getStyle().set("background-color", "#E65100");

        Button exportBtn = new Button("Export", new Icon(VaadinIcon.DOWNLOAD));
        exportBtn.addClickListener(e -> showExportOptions());

        filters.add(quickRange, startDate, endDate, applyFilter, exportBtn);
        filters.setAlignItems(FlexComponent.Alignment.END);

        // Report content area
        VerticalLayout reportContent = new VerticalLayout();
        reportContent.setPadding(true);
        reportContent.setSpacing(true);
        reportContent.setWidthFull();
        reportContent.setHeight(null);
        
        // Initial content
        reportContent.add(createUserReportsContent());

        // Tab selection listener
        reportTabs.addSelectedChangeListener(event -> {
            reportContent.removeAll();
            switch (reportTabs.getSelectedIndex()) {
                case 0: reportContent.add(createUserReportsContent()); break;
                case 1: reportContent.add(createPaymentReportsContent()); break;
                case 2: reportContent.add(createCourseEngagementContent()); break;
                case 3: reportContent.add(createPersonalityTestContent()); break;
                case 4: reportContent.add(createInstitutionReportsContent()); break;
            }
        });

        content.add(header, reportTabs, filters, reportContent);
        return content;
    }

    private Tab createTab(String label) {
        Tab tab = new Tab();
        Span labelSpan = new Span(label);
        labelSpan.getStyle()
                .set("font-weight", "600")
                .set("padding", "0.5rem");
        tab.add(labelSpan);
        return tab;
    }

    private VerticalLayout createUserReportsContent() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setPadding(false);

        // Summary cards
        HorizontalLayout summaryCards = new HorizontalLayout();
        summaryCards.setWidthFull();
        summaryCards.setSpacing(true);

        summaryCards.add(
                createSummaryCard("Total Users", "1,245", VaadinIcon.USERS, "#E65100"),
                createSummaryCard("New Signups (30d)", "87", VaadinIcon.USER_CHECK, "#4CAF50"),
                createSummaryCard("Active Users", "892", VaadinIcon.USER_STAR, "#2196F3"),
                createSummaryCard("Inactive Users", "353", VaadinIcon.USER_CLOCK, "#9E9E9E")
        );

        // Charts
        HorizontalLayout chartsRow1 = new HorizontalLayout();
        chartsRow1.setWidthFull();
        chartsRow1.setSpacing(true);

        chartsRow1.add(
                createPieChart("Users by Category", 
                        new String[]{"Students", "Graduates", "Institutions", "Others"},
                        new Number[]{650, 320, 150, 125}),
                createBarChart("Signups Over Time", 
                        new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun"},
                        new Number[]{85, 76, 98, 110, 95, 87})
        );

        HorizontalLayout chartsRow2 = new HorizontalLayout();
        chartsRow2.setWidthFull();
        chartsRow2.setSpacing(true);

        chartsRow2.add(
                createBarChart("Users by Region", 
                        new String[]{"Nairobi", "Mombasa", "Kisumu", "Nakuru", "Eldoret"},
                        new Number[]{520, 210, 180, 150, 85}),
                createUsersGrid()
        );

        layout.add(summaryCards, chartsRow1, chartsRow2);
        return layout;
    }

    private VerticalLayout createPaymentReportsContent() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setPadding(false);

        HorizontalLayout summaryCards = new HorizontalLayout();
        summaryCards.setWidthFull();
        summaryCards.setSpacing(true);

        summaryCards.add(
                createSummaryCard("Total Revenue", "Ksh 245,800", VaadinIcon.MONEY, "#E65100"),
                createSummaryCard("Avg. Revenue/Day", "Ksh 8,193", VaadinIcon.TRENDING_UP, "#4CAF50"),
                createSummaryCard("Pending Payments", "12", VaadinIcon.TIMER, "#FFC107"),
                createSummaryCard("Failed Transactions", "5", VaadinIcon.WARNING, "#F44336")
        );

        HorizontalLayout chartsRow1 = new HorizontalLayout();
        chartsRow1.setWidthFull();
        chartsRow1.setSpacing(true);

        chartsRow1.add(
                createLineChart("Revenue Over Time", 
                        new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun"},
                        new Number[]{35000, 42000, 38000, 45000, 52000, 48000}),
                createPieChart("Revenue by Plan", 
                        new String[]{"Basic", "Premium", "Institution", "Other"},
                        new Number[]{85000, 120000, 35000, 5800})
        );

        layout.add(summaryCards, chartsRow1, createPaymentsGrid());
        return layout;
    }

    private VerticalLayout createCourseEngagementContent() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setPadding(false);

        HorizontalLayout chartsRow = new HorizontalLayout();
        chartsRow.setWidthFull();
        chartsRow.setSpacing(true);

        chartsRow.add(
                createBarChart("Top Courses by Views", 
                        new String[]{"Computer Science", "Business", "Medicine", "Engineering", "Arts"},
                        new Number[]{1250, 980, 870, 760, 540}),
                createBarChart("Top Purchased Courses", 
                        new String[]{"Computer Science", "Business", "Medicine", "Engineering", "Arts"},
                        new Number[]{320, 280, 190, 150, 90})
        );

        Grid<CourseEngagement> engagementGrid = new Grid<>();
        engagementGrid.addColumn(CourseEngagement::getCourse).setHeader("Course");
        engagementGrid.addColumn(CourseEngagement::getViews).setHeader("Views");
        engagementGrid.addColumn(CourseEngagement::getPurchases).setHeader("Purchases");
        engagementGrid.addColumn(CourseEngagement::getAvgTimeSpent).setHeader("Avg Time");
        engagementGrid.addColumn(CourseEngagement::getCompletionRate).setHeader("Completion");
        engagementGrid.addColumn(CourseEngagement::getAvgRating).setHeader("Rating");

        engagementGrid.setItems(Arrays.asList(
            new CourseEngagement("Computer Science", 1250, 320, "45 min", "78%", 4.5),
            new CourseEngagement("Business", 980, 280, "38 min", "72%", 4.2),
            new CourseEngagement("Medicine", 870, 190, "52 min", "65%", 4.7),
            new CourseEngagement("Engineering", 760, 150, "41 min", "68%", 4.3),
            new CourseEngagement("Arts", 540, 90, "35 min", "60%", 3.9)
        ));

        engagementGrid.getStyle()
            .set("background", "white")
            .set("border-radius", "4px")
            .set("box-shadow", "0 2px 4px rgba(0,0,0,0.1)");
        engagementGrid.setHeight("300px");

        layout.add(chartsRow, engagementGrid);
        return layout;
    }

    private VerticalLayout createPersonalityTestContent() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setPadding(false);

        HorizontalLayout chartsRow = new HorizontalLayout();
        chartsRow.setWidthFull();
        chartsRow.setSpacing(true);

        chartsRow.add(
                createPieChart("Personality Types", 
                        new String[]{"Analyst", "Diplomat", "Sentinel", "Explorer"},
                        new Number[]{35, 28, 22, 15}),
                createBarChart("Test Completion Rate", 
                        new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun"},
                        new Number[]{65, 72, 80, 85, 78, 82})
        );

        Grid<PersonalityTestResult> testGrid = new Grid<>();
        testGrid.addColumn(PersonalityTestResult::getType).setHeader("Type");
        testGrid.addColumn(PersonalityTestResult::getUsers).setHeader("Users");
        testGrid.addColumn(PersonalityTestResult::getTopCareerMatch).setHeader("Top Career");
        testGrid.addColumn(PersonalityTestResult::getTopCourseMatch).setHeader("Top Course");
        testGrid.addColumn(PersonalityTestResult::getAvgScore).setHeader("Avg Score");

        testGrid.setItems(Arrays.asList(
            new PersonalityTestResult("Analyst", 35, "Software Engineer", "Computer Science", 82),
            new PersonalityTestResult("Diplomat", 28, "HR Manager", "Psychology", 78),
            new PersonalityTestResult("Sentinel", 22, "Accountant", "Business", 75),
            new PersonalityTestResult("Explorer", 15, "Graphic Designer", "Arts", 70)
        ));

        testGrid.getStyle()
            .set("background", "white")
            .set("border-radius", "4px")
            .set("box-shadow", "0 2px 4px rgba(0,0,0,0.1)");
        testGrid.setHeight("300px");

        layout.add(chartsRow, testGrid);
        return layout;
    }

    private VerticalLayout createInstitutionReportsContent() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setPadding(false);

        HorizontalLayout chartsRow = new HorizontalLayout();
        chartsRow.setWidthFull();
        chartsRow.setSpacing(true);

        chartsRow.add(
                createBarChart("Institutions with Most Courses", 
                        new String[]{"UoN", "KU", "JKUAT", "MMUST", "Egerton"},
                        new Number[]{45, 38, 32, 25, 18}),
                createLineChart("Enrollment Traffic", 
                        new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun"},
                        new Number[]{120, 150, 180, 210, 190, 220})
        );

        Grid<InstitutionPerformance> institutionGrid = new Grid<>();
        institutionGrid.addColumn(InstitutionPerformance::getInstitution).setHeader("Institution");
        institutionGrid.addColumn(InstitutionPerformance::getCourses).setHeader("Courses");
        institutionGrid.addColumn(InstitutionPerformance::getStudents).setHeader("Students");
        institutionGrid.addColumn(InstitutionPerformance::getRevenue).setHeader("Revenue (Ksh)");
        institutionGrid.addColumn(InstitutionPerformance::getAvgRating).setHeader("Rating");

        institutionGrid.setItems(Arrays.asList(
            new InstitutionPerformance("University of Nairobi", 45, 320, 450000, 4.2),
            new InstitutionPerformance("Kenyatta University", 38, 280, 380000, 4.0),
            new InstitutionPerformance("JKUAT", 32, 210, 320000, 4.1),
            new InstitutionPerformance("MMUST", 25, 150, 250000, 3.8),
            new InstitutionPerformance("Egerton University", 18, 120, 180000, 3.9)
        ));

        institutionGrid.getStyle()
            .set("background", "white")
            .set("border-radius", "4px")
            .set("box-shadow", "0 2px 4px rgba(0,0,0,0.1)");
        institutionGrid.setHeight("300px");

        layout.add(chartsRow, institutionGrid);
        return layout;
    }

    private Div createSummaryCard(String title, String value, VaadinIcon icon, String color) {
        Div card = new Div();
        card.getStyle()
                .set("background", "white")
                .set("border-radius", "4px")
                .set("padding", "1rem")
                .set("box-shadow", "0 2px 4px rgba(0,0,0,0.1)")
                .set("flex", "1");

        HorizontalLayout header = new HorizontalLayout();
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setSpacing(true);

        Icon cardIcon = new Icon(icon);
        cardIcon.setSize("24px");
        cardIcon.setColor(color);

        Span titleSpan = new Span(title);
        titleSpan.getStyle()
                .set("font-size", "0.875rem")
                .set("color", "#666");

        header.add(cardIcon, titleSpan);

        Span valueSpan = new Span(value);
        valueSpan.getStyle()
                .set("font-size", "1.5rem")
                .set("font-weight", "600")
                .set("margin-top", "0.5rem")
                .set("display", "block");

        card.add(header, valueSpan);
        return card;
    }

    // Chart.js Implementation Methods
    private VerticalLayout createPieChart(String title, String[] labels, Number[] data) {
        return createChart(title, "pie", labels, data, 
            "['rgba(230, 81, 0, 0.8)', 'rgba(255, 152, 0, 0.8)', " +
            "'rgba(255, 193, 7, 0.8)', 'rgba(139, 195, 74, 0.8)', " +
            "'rgba(67, 160, 71, 0.8)']");
    }

    private VerticalLayout createBarChart(String title, String[] labels, Number[] data) {
        return createChart(title, "bar", labels, data, "'rgba(230, 81, 0, 0.8)'");
    }

    private VerticalLayout createLineChart(String title, String[] labels, Number[] data) {
        return createChart(title, "line", labels, data, "'rgba(230, 81, 0, 0.8)'");
    }

   private VerticalLayout createChart(String title, String type, String[] labels, Number[] data, String colors) {
    VerticalLayout container = new VerticalLayout();
    container.getStyle()
            .set("background", "white")
            .set("border-radius", "4px")
            .set("padding", "1rem")
            .set("box-shadow", "0 2px 4px rgba(0,0,0,0.1)")
            .set("flex", "1");

    H3 chartTitle = new H3(title);
    chartTitle.getStyle().set("margin", "0 0 1rem 0");
    container.add(chartTitle);

    String canvasId = "chart-" + System.currentTimeMillis();
    Element canvas = new Element("canvas");
    canvas.setAttribute("id", canvasId);
    canvas.getStyle()
            .set("width", "100%")
            .set("height", "300px");

    Div chartDiv = new Div();
    chartDiv.getElement().appendChild(canvas);
    chartDiv.setWidthFull();

    chartDiv.addAttachListener(event -> {
        // Properly format labels with quotes
        String jsLabels = Arrays.stream(labels)
                .map(label -> "\"" + label + "\"")
                .collect(Collectors.joining(", ", "[", "]"));

        String jsData = Arrays.toString(data);

        String js = String.format(
            "var ctx = document.getElementById('%s').getContext('2d');" +
            "new Chart(ctx, {" +
            "  type: '%s'," +
            "  data: {" +
            "    labels: %s," +  // Use properly formatted labels
            "    datasets: [{" +
            "      label: 'Data'," +
            "      data: %s," +
            "      backgroundColor: %s," +
            "      borderColor: 'white'," +
            "      borderWidth: 2" +
            "    }]" +
            "  }," +
            "  options: {" +
            "    responsive: true," +
            "    maintainAspectRatio: false," +
            "    plugins: {" +
            "      legend: { position: 'right' }" +
            "    }," +
            "    scales: {" +
            "      x: { grid: { display: false } }," +
            "      y: { beginAtZero: true }" +
            "    }" +
            "  }" +
            "});",
            canvasId, type, jsLabels, jsData, colors
        );

        UI.getCurrent().getPage().executeJs(js);
    });

    container.add(chartDiv);
    return container;
}

    // Data Grids
    private Grid<UserReport> createUsersGrid() {
        Grid<UserReport> grid = new Grid<>();
        grid.addColumn(UserReport::getRegion).setHeader("Region");
        grid.addColumn(UserReport::getStudents).setHeader("Students");
        grid.addColumn(UserReport::getGraduates).setHeader("Graduates");
        grid.addColumn(UserReport::getInstitutions).setHeader("Institutions");
        grid.addColumn(UserReport::getTotal).setHeader("Total");

        grid.setItems(Arrays.asList(
                new UserReport("Nairobi", 320, 120, 80, 520),
                new UserReport("Mombasa", 130, 50, 30, 210),
                new UserReport("Kisumu", 110, 40, 30, 180),
                new UserReport("Nakuru", 90, 35, 25, 150),
                new UserReport("Eldoret", 50, 20, 15, 85)
        ));

        grid.getStyle()
                .set("background", "white")
                .set("border-radius", "4px")
                .set("box-shadow", "0 2px 4px rgba(0,0,0,0.1)");
        grid.setHeight("300px");

        return grid;
    }

    private Grid<PaymentReport> createPaymentsGrid() {
        Grid<PaymentReport> grid = new Grid<>();
        grid.addColumn(PaymentReport::getPlan).setHeader("Plan");
        grid.addColumn(PaymentReport::getTransactions).setHeader("Transactions");
        grid.addColumn(PaymentReport::getRevenue).setHeader("Revenue (Ksh)");
        grid.addColumn(PaymentReport::getAvgValue).setHeader("Avg Value");

        grid.setItems(Arrays.asList(
                new PaymentReport("Basic", 85, 85000, 1000),
                new PaymentReport("Premium", 60, 120000, 2000),
                new PaymentReport("Institution", 7, 35000, 5000),
                new PaymentReport("Other", 12, 5800, 483)
        ));

        grid.getStyle()
                .set("background", "white")
                .set("border-radius", "4px")
                .set("box-shadow", "0 2px 4px rgba(0,0,0,0.1)");
        grid.setHeight("300px");

        return grid;
    }

    private void showExportOptions() {
        Dialog dialog = new Dialog();
        dialog.setWidth("400px");

        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(false);
        layout.setSpacing(true);

        H3 header = new H3("Export Report");
        header.getStyle().set("margin-top", "0");

        ComboBox<String> format = new ComboBox<>("Format");
        format.setItems("PDF", "Excel", "CSV");
        format.setValue("PDF");

        ComboBox<String> range = new ComboBox<>("Date Range");
        range.setItems("Current View", "Last 30 Days", "Last Year", "All Time");
        range.setValue("Current View");

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        buttons.setSpacing(true);

        Button cancel = new Button("Cancel", e -> dialog.close());
        Button export = new Button("Export", e -> {
            Notification.show("Exporting report...");
            dialog.close();
        });
        export.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        export.getStyle().set("background-color", "#E65100");

        buttons.add(cancel, export);
        layout.add(header, format, range, buttons);
        dialog.add(layout);
        dialog.open();
    }

    // Data model classes
    public static class UserReport {
        private String region;
        private int students;
        private int graduates;
        private int institutions;
        private int total;

        public UserReport(String region, int students, int graduates, int institutions, int total) {
            this.region = region;
            this.students = students;
            this.graduates = graduates;
            this.institutions = institutions;
            this.total = total;
        }

        public String getRegion() { return region; }
        public int getStudents() { return students; }
        public int getGraduates() { return graduates; }
        public int getInstitutions() { return institutions; }
        public int getTotal() { return total; }
    }

    public static class PaymentReport {
        private String plan;
        private int transactions;
        private int revenue;
        private int avgValue;

        public PaymentReport(String plan, int transactions, int revenue, int avgValue) {
            this.plan = plan;
            this.transactions = transactions;
            this.revenue = revenue;
            this.avgValue = avgValue;
        }

        public String getPlan() { return plan; }
        public int getTransactions() { return transactions; }
        public int getRevenue() { return revenue; }
        public int getAvgValue() { return avgValue; }
    }

    public static class CourseEngagement {
        private String course;
        private int views;
        private int purchases;
        private String avgTimeSpent;
        private String completionRate;
        private double avgRating;

        public CourseEngagement(String course, int views, int purchases, 
                              String avgTimeSpent, String completionRate, double avgRating) {
            this.course = course;
            this.views = views;
            this.purchases = purchases;
            this.avgTimeSpent = avgTimeSpent;
            this.completionRate = completionRate;
            this.avgRating = avgRating;
        }

        public String getCourse() { return course; }
        public int getViews() { return views; }
        public int getPurchases() { return purchases; }
        public String getAvgTimeSpent() { return avgTimeSpent; }
        public String getCompletionRate() { return completionRate; }
        public double getAvgRating() { return avgRating; }
    }

    public static class PersonalityTestResult {
        private String type;
        private int users;
        private String topCareerMatch;
        private String topCourseMatch;
        private int avgScore;

        public PersonalityTestResult(String type, int users, String topCareerMatch, 
                                   String topCourseMatch, int avgScore) {
            this.type = type;
            this.users = users;
            this.topCareerMatch = topCareerMatch;
            this.topCourseMatch = topCourseMatch;
            this.avgScore = avgScore;
        }

        public String getType() { return type; }
        public int getUsers() { return users; }
        public String getTopCareerMatch() { return topCareerMatch; }
        public String getTopCourseMatch() { return topCourseMatch; }
        public int getAvgScore() { return avgScore; }
    }

    public static class InstitutionPerformance {
        private String institution;
        private int courses;
        private int students;
        private int revenue;
        private double avgRating;

        public InstitutionPerformance(String institution, int courses, 
                                    int students, int revenue, double avgRating) {
            this.institution = institution;
            this.courses = courses;
            this.students = students;
            this.revenue = revenue;
            this.avgRating = avgRating;
        }

        public String getInstitution() { return institution; }
        public int getCourses() { return courses; }
        public int getStudents() { return students; }
        public int getRevenue() { return revenue; }
        public double getAvgRating() { return avgRating; }
    }
}
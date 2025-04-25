package com.example.talanta.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.dom.Element; // Add this line
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.orderedlayout.FlexComponent;

import java.util.List;
import java.util.Arrays;

@JsModule("https://cdn.jsdelivr.net/npm/chart.js")
@Route("Admindashboard")
public class AdminDashboardView extends VerticalLayout {

    private HorizontalLayout activeNavItem = null;

    public AdminDashboardView() {
        setSizeFull(); // Ensure this fills the viewport
        getStyle().set("overflow", "auto");
        HorizontalLayout layout = new HorizontalLayout();
        VerticalLayout sidebar = createSidebar();
        VerticalLayout mainContent = createMainContent();

        layout.setSizeFull();
        sidebar.setWidth("240px");
        sidebar.getStyle().set("height", "880px");
        sidebar.getStyle()
                .set("background", "#F4F4F4")
                .set("box-shadow", "2px 0 5px rgba(0,0,0,0.1)");
        layout.add(sidebar, mainContent);
        add(layout);
    }

    VerticalLayout createSidebar() {
        VerticalLayout sidebar = new VerticalLayout();
        sidebar.getStyle()
                .set("padding-top", "20px")
                .set("height", "100%");

        Image logo = new Image("images/2.png", "Talanta Logo");
        logo.setWidth("150px");
        logo.setHeight("auto");
        logo.getStyle()
                .set("margin-bottom", "30px")
                .set("margin-left", "20px");

        // Create nav items
        HorizontalLayout dashboardNav = createNavItem("Dashboard", VaadinIcon.DASHBOARD, "Admindashboard");
        HorizontalLayout usersNav = createNavItem("Users", VaadinIcon.USER, "users");
        HorizontalLayout testsNav = createNavItem("Personality Tests", VaadinIcon.CLIPBOARD_TEXT, "personality-tests");
        HorizontalLayout coursesNav = createNavItem("Courses", VaadinIcon.BOOK, "courses-careers");
        HorizontalLayout FieldNav = createNavItem("Fields", VaadinIcon.BOOK, "fieldView");
        HorizontalLayout institutionsNav = createNavItem("Institutions", VaadinIcon.ACADEMY_CAP, "institutions");
        HorizontalLayout paymentsNav = createNavItem("Payments", VaadinIcon.CREDIT_CARD, "payments");
        HorizontalLayout reportsNav = createNavItem("Reports", VaadinIcon.CHART, "reports");
        HorizontalLayout pricingNav = createNavItem("Pricing", VaadinIcon.FILE_TEXT, "pricing");
        HorizontalLayout notificationsNav = createNavItem("Notifications", VaadinIcon.BELL, "notifications");
        HorizontalLayout settingsNav = createNavItem("Settings", VaadinIcon.COG, "settings");

        // Add all items to sidebar
        sidebar.add(
                logo,
                dashboardNav,
                usersNav,
                testsNav,
                coursesNav,
                FieldNav,
                institutionsNav,
                paymentsNav,
                reportsNav,
                pricingNav,
                notificationsNav,
                settingsNav
        );

        // Set dashboard as active by default
        setActiveNavItem(dashboardNav);

        return sidebar;
    }

    private void setActiveNavItem(HorizontalLayout navItem) {
        if (activeNavItem != null) {
            // Reset previous active item
            activeNavItem.getStyle()
                    .set("background-color", "transparent")
                    .set("border-left", "3px solid transparent");
            ((Icon) activeNavItem.getComponentAt(0)).getElement().getStyle()
                    .set("color", "#666666");
        }

        // Set new active item
        navItem.getStyle()
                .set("background-color", "rgba(230, 81, 0, 0.1)")
                .set("border-left", "3px solid #E65100");
        ((Icon) navItem.getComponentAt(0)).getElement().getStyle()
                .set("color", "#E65100");

        activeNavItem = navItem;
    }

    private HorizontalLayout createNavItem(String title, VaadinIcon icon, String route) {
        Icon menuIcon = icon.create();
        menuIcon.getStyle().set("color", "#666666");
        Span label = new Span(title);
        label.getStyle()
                .set("font-size", "14px")
                .set("color", "#333333");

        HorizontalLayout nav = new HorizontalLayout(menuIcon, label);
        nav.setPadding(true);
        nav.setSpacing(true);
        nav.setAlignItems(FlexComponent.Alignment.CENTER);

        nav.getStyle()
                .set("padding", "12px 20px")
                .set("cursor", "pointer")
                .set("border-radius", "0 5px 5px 0")
                .set("transition", "all 0.2s ease")
                .set("margin-left", "-3px")
                .set("width", "100%")
                .set("box-sizing", "border-box")
                .set("border-left", "3px solid transparent");

        // Mouse enter event
        nav.getElement().addEventListener("mouseenter", event -> {
            if (nav != activeNavItem) {
                nav.getStyle().set("background-color", "rgba(230, 81, 0, 0.05)");
            }
        });

        // Mouse leave event
        nav.getElement().addEventListener("mouseleave", event -> {
            if (nav != activeNavItem) {
                nav.getStyle().set("background-color", "transparent");
            }
        });

        nav.addClickListener(e -> {
            UI.getCurrent().navigate(route);
            setActiveNavItem(nav);
        });

        return nav;
    }

    private VerticalLayout createMainContent() {
        VerticalLayout main = new VerticalLayout();
        main.getStyle()
                .set("padding", "20px")
                .set("background", "#f9f9f9")
                .set("height", "auto")
                .set("flex-grow", "1");

        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.setWidthFull();
        headerLayout.setJustifyContentMode(FlexLayout.JustifyContentMode.BETWEEN);

        VerticalLayout headingDateLayout = new VerticalLayout();
        com.vaadin.flow.component.html.H1 heading = new com.vaadin.flow.component.html.H1("Welcome, Admin");
        heading.getStyle()
                .set("color", "#E65100")
                .set("margin", "0");
        Paragraph date = new Paragraph("Tuesday, April 8, 2025");
        date.getStyle()
                .set("color", "#666666")
                .set("margin", "0");
        headingDateLayout.getStyle().set("padding", "0");
        headingDateLayout.add(heading, date);

        headerLayout.add(headingDateLayout);
        main.add(headerLayout);

        HorizontalLayout cardsLayout = new HorizontalLayout();
        cardsLayout.setWidthFull();
        cardsLayout.setJustifyContentMode(FlexLayout.JustifyContentMode.BETWEEN);
        cardsLayout.getStyle().set("margin-top", "20px");

        cardsLayout.add(
                createStatCard("1,234", "Total Users"),
                createStatCard("567", "Total Tests Taken"),
                createStatCard("$12,340", "Total Payments"),
                createStatCard("25", "Total Courses")
        );
        main.add(cardsLayout);

        HorizontalLayout reportsLayout = new HorizontalLayout();
        reportsLayout.setWidthFull();
        reportsLayout.setSpacing(true);
        reportsLayout.getStyle().set("margin-top", "20px");

        reportsLayout.add(
                createBoxWithGraph(),
                createActivityBox()
        );

        main.add(reportsLayout);
//         createBoxWithGraph();
        return main;

    }

    private VerticalLayout createStatCard(String value, String label) {
        VerticalLayout card = new VerticalLayout();

        card.getStyle()
                .set("background-color", "white")
                .set("padding", "20px")
                .set("border-radius", "10px")
                .set("box-shadow", "0 2px 5px rgba(0,0,0,0.05)")
                .set("width", "24%")
                .set("border-top", "3px solid #E65100");

        com.vaadin.flow.component.html.H1 valueLabel = new com.vaadin.flow.component.html.H1(value);
        valueLabel.getStyle()
                .set("margin", "0")
                .set("color", "#E65100")
                .set("font-size", "24px");

        Paragraph description = new Paragraph(label);
        description.getStyle()
                .set("margin", "5px 0 0 0")
                .set("color", "#666666")
                .set("font-size", "14px");
        card.add(valueLabel, description);
        return card;
    }

    private VerticalLayout createBoxWithGraph() {
        VerticalLayout box = new VerticalLayout();
        box.getStyle()
                .set("background-color", "white")
                .set("padding", "20px")
                .set("border-radius", "10px")
                .set("box-shadow", "0 2px 5px rgba(0,0,0,0.05)")
                .set("width", "48%");

        com.vaadin.flow.component.html.H1 title = new com.vaadin.flow.component.html.H1("User Activity");
        title.getStyle()
                .set("margin", "0 0 15px 0")
                .set("color", "#333333")
                .set("font-size", "18px");

        // Create a canvas element
        Element canvas = new Element("canvas");
        canvas.setAttribute("id", "userActivityChart");
        canvas.getStyle().set("width", "100%");
        canvas.getStyle().set("height", "300px");

        Div chartContainer = new Div();
        chartContainer.getElement().appendChild(canvas); // Attach canvas to div
        chartContainer.setWidth("100%");
        chartContainer.getStyle().set("height", "300px");

        // Initialize chart when container attaches
        chartContainer.addAttachListener(event -> {
            UI.getCurrent().getPage().executeJs(
                    "var ctx = document.getElementById($0).getContext('2d');"
                    + "new Chart(ctx, "
                    + "{ type: 'line', data: { labels: ['May','Jun','Jul','Aug','Sep','Oct'], "
                    + "datasets: [{ label: 'Users', data: [200,500,400,600,550,800], "
                    + "borderColor: '#E65100', borderWidth: 2, tension: 0.4, "
                    + "backgroundColor: 'rgba(230, 81, 0, 0.05)', fill: true, "
                    + "pointBackgroundColor: '#E65100', pointBorderColor: 'white', pointBorderWidth: 2 }] }, "
                    + "options: { responsive: true, maintainAspectRatio: false, "
                    + "plugins: { legend: { display: false } }, scales: { x: { grid: { display: false } }, "
                    + "y: { beginAtZero: true, grid: { color: 'rgba(0,0,0,0.05)' } } } } });",
                    "userActivityChart" // ID matches canvas element
            );
        });

        box.add(title, chartContainer);
        return box;
    }

    private VerticalLayout createActivityBox() {
        VerticalLayout box = new VerticalLayout();

        box.getStyle()
                .set("background-color", "white")
                .set("padding", "20px")
                .set("border-radius", "10px")
                .set("box-shadow", "0 2px 5px rgba(0,0,0,0.05)")
                .set("width", "48%");

        com.vaadin.flow.component.html.H1 title = new com.vaadin.flow.component.html.H1("Recent Activity");
        title.getStyle()
                .set("margin", "0 0 15px 0")
                .set("color", "#333333")
                .set("font-size", "18px");

        VerticalLayout activityContent = new VerticalLayout();
        activityContent.setPadding(false);
        activityContent.setSpacing(false);
        activityContent.getStyle().set("width", "100%");

        String[] activities = {
            "New user registered at 6:30 PM",
            "Payment of $49.99 received at 5:15 PM",
            "New user registered at 4:00 PM",
            "User profile updated at 2:20 PM",
            "Test completed at 10:45 AM"
        };

        Div container = new Div();
        container.getStyle()
                .set("display", "flex")
                .set("flex-direction", "column")
                .set("gap", "8px");

        for (String activity : activities) {
            Div activityItem = createActivityItem(activity);
            container.add(activityItem);
        }

        box.add(title, container);

        return box;
    }

    private Div createActivityItem(String activity) {
        Div item = new Div();
        item.setText(activity);
        item.getStyle()
                .set("background-color", "#f9f9f9")
                .set("padding", "12px 15px")
                .set("border-radius", "5px")
                .set("box-shadow", "0 1px 2px rgba(0,0,0,0.05)")
                .set("font-size", "14px")
                .set("border-left", "3px solid #E65100");
        return item;
    }
}

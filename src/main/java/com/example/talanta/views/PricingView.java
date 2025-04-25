package com.example.talanta.views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route("pricing")
public class PricingView extends VerticalLayout {

    private Grid<PricingItem> pricingGrid = new Grid<>();
    private List<PricingItem> pricingItems = new ArrayList<>();
    private TextField searchField = new TextField("Search plans");
    private ComboBox<String> statusFilter = new ComboBox<>("Status");
    private ComboBox<String> durationFilter = new ComboBox<>("Duration");

    public PricingView() {
        setSizeFull(); // Ensure this fills the viewport
        getStyle().set("overflow", "auto");
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();

        // Reuse admin sidebar
        VerticalLayout sidebar = new AdminDashboardView().createSidebar();
        sidebar.setWidth("240px");
        sidebar.getStyle()
                .set("background", "#F4F4F4")
                .set("height", "880px")
                .set("border-right", "1px solid #E0E0E0");

        VerticalLayout mainContent = createMainContent();
        mainContent.getStyle().set("background", "#FAFAFA");

        layout.add(sidebar, mainContent);
        add(layout);
    }

    private VerticalLayout createMainContent() {
        VerticalLayout content = new VerticalLayout();
        content.setPadding(false);
        content.setSpacing(false);
        content.setSizeFull();

        // Header section
        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.getStyle()
                .set("background", "white")
                .set("padding", "1rem")
                .set("border-bottom", "1px solid #E0E0E0");

        H2 title = new H2("Pricing Plans Management");
        title.getStyle()
                .set("margin", "0")
                .set("color", "#E65100");

        Button addButton = new Button("Add Pricing Plan", new Icon(VaadinIcon.PLUS));
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addButton.getStyle().set("background-color", "#E65100");
        addButton.addClickListener(e -> openAddEditDialog(null));

        header.add(title, addButton);
        header.expand(title);

        // Filters and controls
        HorizontalLayout filters = new HorizontalLayout();
        filters.setWidthFull();
        filters.setPadding(true);
        filters.getStyle().set("background", "white");

        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setClearButtonVisible(true);
        searchField.setPlaceholder("Search by name or description...");
        searchField.setWidth("300px");

        statusFilter.setItems("All", "Active", "Inactive");
        statusFilter.setValue("All");
        statusFilter.setWidth("150px");

        durationFilter.setItems("All", "7 days", "30 days", "90 days", "Custom");
        durationFilter.setValue("All");
        durationFilter.setWidth("150px");

        Button exportButton = new Button("Export", new Icon(VaadinIcon.DOWNLOAD));
        exportButton.addClickListener(e -> exportData());

        filters.add(searchField, statusFilter, durationFilter, exportButton);
        filters.setAlignItems(FlexComponent.Alignment.END);

        // Pricing plans grid
        configureGrid();

        content.add(header, filters, pricingGrid);
        content.expand(pricingGrid);
        return content;
    }

    private void configureGrid() {
        pricingGrid.setSizeFull();
        pricingGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        pricingGrid.setSelectionMode(Grid.SelectionMode.NONE);
        pricingGrid.setHeight("350px");

        // Columns configuration
        pricingGrid.addColumn(new ComponentRenderer<>(item -> {
            Span status = new Span(item.isActive() ? "Active" : "Inactive");
            status.getElement().getThemeList().add(item.isActive() ? "badge success" : "badge error");
            return status;
        })).setHeader("Status").setAutoWidth(true);

        pricingGrid.addColumn(PricingItem::getName).setHeader("Plan Name").setAutoWidth(true);

        pricingGrid.addColumn(new ComponentRenderer<>(item -> {
            Span price = new Span(String.format("Ksh %,.2f", item.getPrice()));
            if (item.getDiscount() > 0) {
                Div div = new Div(price);
                Span original = new Span(String.format("Ksh %,.2f", item.getOriginalPrice()));
                original.getStyle().set("text-decoration", "line-through")
                        .set("color", "#999")
                        .set("font-size", "0.8em")
                        .set("margin-left", "0.5rem");
                div.add(original);
                return div;
            }
            return price;
        })).setHeader("Price").setAutoWidth(true);

        pricingGrid.addColumn(PricingItem::getDuration).setHeader("Duration").setAutoWidth(true);

        pricingGrid.addColumn(new ComponentRenderer<>(item -> {
            return new Span(item.getDescription());
        })).setHeader("Description").setAutoWidth(true);

        pricingGrid.addColumn(new ComponentRenderer<>(item -> {
            HorizontalLayout actions = new HorizontalLayout();

            Button preview = new Button(new Icon(VaadinIcon.EYE));
            preview.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            preview.addClickListener(e -> previewPlan(item));
            preview.getElement().setAttribute("title", "Preview");

            Button edit = new Button(new Icon(VaadinIcon.EDIT));
            edit.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            edit.addClickListener(e -> openAddEditDialog(item));
            edit.getElement().setAttribute("title", "Edit");

            Button toggle = new Button(new Icon(item.isActive() ? VaadinIcon.BAN : VaadinIcon.CHECK));
            toggle.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            toggle.addClickListener(e -> togglePlanStatus(item));
            toggle.getElement().setAttribute("title", item.isActive() ? "Deactivate" : "Activate");

            Button delete = new Button(new Icon(VaadinIcon.TRASH));
            delete.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_ERROR);
            delete.addClickListener(e -> confirmDelete(item));
            delete.getElement().setAttribute("title", "Delete");

            actions.add(preview, edit, toggle, delete);
            return actions;
        })).setHeader("Actions").setAutoWidth(true);

        loadSampleData();
        pricingGrid.setItems(pricingItems);
    }

    private void openAddEditDialog(PricingItem item) {
        Dialog dialog = new Dialog();
        dialog.setWidth("600px");
        dialog.setCloseOnEsc(true);
        dialog.setCloseOnOutsideClick(false);

        boolean isNew = item == null;
        H3 title = new H3(isNew ? "Add New Pricing Plan" : "Edit Pricing Plan");
        title.getStyle().set("margin-top", "0");

        // Form fields
        TextField nameField = new TextField("Plan Name");
        nameField.setWidthFull();

        TextArea descriptionField = new TextArea("Description");
        descriptionField.setWidthFull();

        NumberField priceField = new NumberField("Price (Ksh)");
        priceField.setWidthFull();

        NumberField originalPriceField = new NumberField("Original Price (Ksh)");
        originalPriceField.setWidthFull();
        originalPriceField.setHelperText("Leave empty if no discount");

        NumberField discountField = new NumberField("Discount (%)");
        discountField.setWidthFull();
        discountField.setMax(100);
        discountField.setMin(0);

        TextField durationField = new TextField("Access Duration");
        durationField.setWidthFull();
        durationField.setHelperText("e.g. '30 days', '6 months', '1 year'");

        Checkbox activeCheckbox = new Checkbox("Active Plan");
        activeCheckbox.setValue(true);

        // Populate fields if editing
        if (!isNew) {
            nameField.setValue(item.getName());
            descriptionField.setValue(item.getDescription());
            priceField.setValue(item.getPrice());
            originalPriceField.setValue(item.getOriginalPrice());
            discountField.setValue(item.getDiscount());
            durationField.setValue(item.getDuration());
            activeCheckbox.setValue(item.isActive());
        }

        // Form layout
        VerticalLayout form = new VerticalLayout();
        form.setPadding(false);
        form.setSpacing(true);
        form.add(
                title,
                nameField,
                descriptionField,
                new HorizontalLayout(priceField, originalPriceField, discountField),
                durationField,
                activeCheckbox
        );

        // Dialog buttons
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        buttons.setSpacing(true);

        Button cancel = new Button("Cancel", e -> dialog.close());
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        Button save = new Button(isNew ? "Add Plan" : "Save Changes");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.getStyle().set("background-color", "#E65100");
        save.addClickListener(e -> {
            // Save logic here
            dialog.close();
        });

        buttons.add(cancel, save);

        dialog.add(form, buttons);
        dialog.open();
    }

    private void previewPlan(PricingItem item) {
        Dialog previewDialog = new Dialog();
        previewDialog.setWidth("500px");

        VerticalLayout previewContent = new VerticalLayout();
        previewContent.setSpacing(true);
        previewContent.setPadding(false);

        H3 name = new H3(item.getName());
        name.getStyle().set("color", "#E65100");

        Span price = new Span(String.format("Ksh %,.2f", item.getPrice()));
        price.getStyle().set("font-size", "1.5rem").set("font-weight", "bold");

        if (item.getDiscount() > 0) {
            Span original = new Span(String.format("Ksh %,.2f", item.getOriginalPrice()));
            original.getStyle().set("text-decoration", "line-through").set("color", "#999");
            HorizontalLayout priceLayout = new HorizontalLayout(price, original);
            priceLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
            previewContent.add(priceLayout);
        } else {
            previewContent.add(price);
        }

        Div description = new Div(new Text(item.getDescription()));
        description.getStyle().set("margin", "1rem 0");

        Div duration = new Div(new Icon(VaadinIcon.CLOCK));
        duration.add(new Span(" " + item.getDuration()));
        duration.getStyle().set("color", "#666");

        previewContent.add(name, description, duration);
        previewDialog.add(previewContent);
        previewDialog.open();
    }

    private void togglePlanStatus(PricingItem item) {
        item.setActive(!item.isActive());
        // Update in backend
        pricingGrid.getDataProvider().refreshItem(item);
    }

    private void confirmDelete(PricingItem item) {
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Delete Pricing Plan");
        dialog.setText("Are you sure you want to delete '" + item.getName() + "'? This action cannot be undone.");

        dialog.setCancelable(true);
        dialog.setConfirmText("Delete");
        dialog.setConfirmButtonTheme("error primary");
        dialog.addConfirmListener(e -> {
            // Delete logic here
            pricingItems.remove(item);
            pricingGrid.setItems(pricingItems);
        });

        dialog.open();
    }

    private void exportData() {
        // Export logic here
        Notification.show("Exporting pricing data...", 3000, Notification.Position.MIDDLE);
    }

    private void loadSampleData() {
        pricingItems.add(new PricingItem("Basic Career Match", 300, 300, 0, "7 days",
                "Basic access to career matching tools and limited course information", true));

        pricingItems.add(new PricingItem("Premium Career Package", 1200, 1500, 20, "30 days",
                "Full access to all career tools, detailed course information, and expert advice", true));

        pricingItems.add(new PricingItem("Institution Sponsorship", 5000, 6000, 16.67, "90 days",
                "Special pricing for educational institutions with extended access", true));

        pricingItems.add(new PricingItem("Legacy Plan", 400, 400, 0, "15 days",
                "Older pricing plan being phased out", false));
    }

    public static class PricingItem {

        private String name;
        private double price;
        private double originalPrice;
        private double discount;
        private String duration;
        private String description;
        private boolean active;

        public PricingItem(String name, double price, double originalPrice, double discount,
                String duration, String description, boolean active) {
            this.name = name;
            this.price = price;
            this.originalPrice = originalPrice;
            this.discount = discount;
            this.duration = duration;
            this.description = description;
            this.active = active;
        }

        // Getters and setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(double originalPrice) {
            this.originalPrice = originalPrice;
        }

        public double getDiscount() {
            return discount;
        }

        public void setDiscount(double discount) {
            this.discount = discount;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }
    }
}

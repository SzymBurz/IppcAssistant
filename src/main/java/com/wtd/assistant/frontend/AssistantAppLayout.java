package com.wtd.assistant.frontend;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;


//Nie wiem co z tym zrobić
@Route("app-layout")
public class AssistantAppLayout extends AppLayout {

    public AssistantAppLayout() {
        DrawerToggle toggle = new DrawerToggle();

        H1 title = new H1("IPPC-Assistant");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        Tabs tabs = getTabs();

        addToDrawer(tabs);
        addToNavbar(toggle, title);

    }

    private Tabs getTabs() {
        Tabs tabs = new Tabs();
        tabs.add(createTab(VaadinIcon.FACTORY, "IPPC Codes"),
                createTab(VaadinIcon.EYE, "Audits"),
                createTab(VaadinIcon.CAR, "Trips"),
                createTab(VaadinIcon.CALENDAR, "Calendar"));
                //createTab(VaadinIcon.RECORDS, "Documents"),
                //createTab(VaadinIcon.LIST, "Tasks"),
                //createTab(VaadinIcon.CHART, "Analytics"));
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        return tabs;
    }

    private Tab createTab(VaadinIcon viewIcon, String viewName) {
        Icon icon = viewIcon.create();
        icon.getStyle().set("box-sizing", "border-box")
                .set("margin-inline-end", "var(--lumo-space-m)")
                .set("margin-inline-start", "var(--lumo-space-xs)")
                .set("padding", "var(--lumo-space-xs)");

        RouterLink link = new RouterLink();
        link.add(icon, new Span(viewName));
        // Demo has no routes
        link.setRoute(EnterprisesView.class);
        link.setTabIndex(-1);

        return new Tab(link);
    }
}



package com.wtd.assistant.frontend.views;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.wtd.assistant.frontend.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;

@Route("app-layout")
public class AssistantAppLayout extends AppLayout {

    private SecurityService securityService;

    @Autowired
    public AssistantAppLayout(SecurityService securityService) {
        this.securityService = securityService;

        DrawerToggle toggle = new DrawerToggle();

        H1 title = new H1("IPPC-Assistant");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        Tabs tabs = getTabs();

        addToDrawer(tabs);
        addToNavbar(toggle, title);

        if (securityService.getAuthenticatedUser() != null) {

            Button logout = new Button("Logout", click ->
                    securityService.logout());
            VerticalLayout vertLay = new VerticalLayout(logout);
            vertLay.setAlignItems(FlexComponent.Alignment.END);
            addToNavbar(vertLay);
        }

    }

    private Tabs getTabs() {
        Tabs tabs = new Tabs();
        tabs.add( createTab(VaadinIcon.HOME, "Home", HomePage.class),
                createTab(VaadinIcon.FACTORY, "IPPC Codes", EnterprisesView.class),
                createTab(VaadinIcon.EYE, "Audits", AuditView.class),
                createTab(VaadinIcon.CAR, "Trips", TripsView.class),
                createTab(VaadinIcon.CALENDAR, "Calendar", CalendarView.class),
                createTab(VaadinIcon.COG_O, "Settings", SettingsView.class));
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        return tabs;
    }

    private Tab createTab(VaadinIcon viewIcon, String viewName, Class routing) {
        Icon icon = viewIcon.create();
        icon.getStyle().set("box-sizing", "border-box")
                .set("margin-inline-end", "var(--lumo-space-m)")
                .set("margin-inline-start", "var(--lumo-space-xs)")
                .set("padding", "var(--lumo-space-xs)");

        RouterLink link = new RouterLink();
        link.add(icon, new Span(viewName));
        link.setRoute(routing);

        link.setTabIndex(-1);

        return new Tab(link);
    }
}



package com.wtd.assistant.frontend.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("login")
@PageTitle("Login | Vaadin CRM")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final LoginForm login = new LoginForm();

    public LoginView(){
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        login.setAction("login");

        VerticalLayout header = new VerticalLayout();
        header.add(new H1("Auditor assistant"), new Span("Username: user"), new Span("Password: userpass"));
        header.setAlignItems(Alignment.CENTER);

        add(header, login);

        login.addLoginListener(e -> {
            // Check login credentials (you might have your own logic here)
            boolean isAuthenticated = authenticateUser(e.getUsername(), e.getPassword());

            if (isAuthenticated) {
                // If authentication is successful, navigate to another view
                getUI().ifPresent(ui -> ui.navigate("")); // "dashboard" is the target view
            } else {
                // If authentication fails, show an error message or handle accordingly
                login.setError(true);
            }
        });


    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // inform the user about an authentication error
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
    }


    private boolean authenticateUser(String username, String password) {
        // Implement your own authentication logic (check credentials, etc.)
        // Return true if authentication is successful, false otherwise
        return "user".equals(username) && "userpass".equals(password);
    }

}

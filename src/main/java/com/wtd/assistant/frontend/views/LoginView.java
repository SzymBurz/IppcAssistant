package com.wtd.assistant.frontend.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.wtd.assistant.frontend.service.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Route("login")
@PageTitle("Login | Vaadin CRM")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final LoginForm login = new LoginForm();
    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    public LoginView(){
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        login.setAction("login");

        VerticalLayout header = new VerticalLayout();
        header.add(new H1("Auditor assistant"));
        header.setAlignItems(Alignment.CENTER);
        RouterLink registerLink = new RouterLink("Register", RegisterView.class);
        add(header, login, registerLink);

        login.addLoginListener(e -> {
            boolean isAuthenticated = authenticateUser(e.getUsername(), e.getPassword());

            if (isAuthenticated) {
                getUI().ifPresent(ui -> ui.navigate(""));
            } else {
                login.setError(true);
            }
        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
    }

    private boolean authenticateUser(String username, String password) {

        Authentication result = customAuthenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(result);

        return result.isAuthenticated();

    }

}

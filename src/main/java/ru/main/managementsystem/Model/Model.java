package ru.main.managementsystem.Model;

import ru.main.managementsystem.Views.*;
import ru.main.managementsystem.admin.entity.User;

public class Model {

    private static Model model;
    private final AdminViewFactory adminViewFactory;
    private final UserViewFactory userViewFactory;
    private final ViewFactory viewFactory;
    private static User currentUser;

    private Model(){
        this.adminViewFactory = new AdminViewFactory();
        this.userViewFactory = new UserViewFactory();
        this.viewFactory = new ViewFactory();
    }

    public static synchronized Model getInstance(){
        if (model == null){
            model = new Model();
        }
        return model;
    }

    public AdminViewFactory getAdminViewFactory(){
        return adminViewFactory;
    }

    public UserViewFactory getUserViewFactory(){
        return userViewFactory;
    }

    public ViewFactory getViewFactory(){
        return viewFactory;
    }

    public void setCurrentUser(User authenticatedUser) {
        currentUser = authenticatedUser;
    }
}

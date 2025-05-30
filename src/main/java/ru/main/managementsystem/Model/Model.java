package ru.main.managementsystem.Model;

import ru.main.managementsystem.Views.ViewFactory;
import ru.main.managementsystem.admin.entity.User;

public class Model {

    private static Model model;
    private final ViewFactory viewFactory;
    private static User currentUser;

    private Model(){
        this.viewFactory = new ViewFactory();
    }

    public static synchronized Model getInstance(){
        if (model == null){
            model = new Model();
        }
        return model;
    }

    public ViewFactory getViewFactory(){
        return viewFactory;
    }

    public void setCurrentUser(User authenticatedUser) {
        currentUser = authenticatedUser;
    }
}

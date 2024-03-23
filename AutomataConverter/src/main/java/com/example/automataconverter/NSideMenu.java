package com.example.automataconverter;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;

public class NSideMenu {

    private Label fLabel ;
    private Label rLabel ;
    private Label nLabel ;

    private Label tLabel;
    private ContextMenu menu;
    NSideMenu(){
        this.menu = new ContextMenu();
        createMenu();

    }
    private void createMenu(){

        fLabel = new Label("Final");
        rLabel = new Label("Remove");
        nLabel = new Label("Normal");
        tLabel = new Label("Transition");
        menu.getItems().add(new CustomMenuItem(fLabel));
        menu.getItems().add(new CustomMenuItem(nLabel));
        menu.getItems().add(new CustomMenuItem(rLabel));
        menu.getItems().add(new CustomMenuItem(tLabel));
    }

    public Label getfLabel() {
        return fLabel;
    }

    public Label getrLabel() {
        return rLabel;
    }

    public ContextMenu getMenu() {
        return menu;
    }

    public Label getnLabel() {
        return nLabel;
    }

    public Label gettLabel() {
        return tLabel;
    }
}

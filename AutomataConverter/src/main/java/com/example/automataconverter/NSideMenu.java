package com.example.automataconverter;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;

public class NSideMenu {

    private Label fLabel  = new Label("Final");
    private Label rLabel  = new Label("Remove");
    private Label nLabel  = new Label("Normal");
    private Label sLabel  = new Label("Start");
    private Label tLabel  = new Label("Transition");
    private CustomMenuItem fItem= new CustomMenuItem(fLabel);
    private CustomMenuItem nItem= new CustomMenuItem(nLabel);
    private CustomMenuItem rItem= new CustomMenuItem(rLabel);
    private CustomMenuItem sItem= new CustomMenuItem(sLabel);
    private CustomMenuItem tItem= new CustomMenuItem(tLabel);
    private ContextMenu menu;
    NSideMenu(){
        this.menu = new ContextMenu();
        createMenu();

    }
    private void createMenu(){
        menu.getItems().add(rItem);
        menu.getItems().add(tItem);
        menu.getItems().add(fItem);
        menu.getItems().add(nItem);
        menu.getItems().add(sItem);

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
    public Label getsLabel() {
        return sLabel;
    }

    public CustomMenuItem getfItem() {
        return fItem;
    }

    public CustomMenuItem getnItem() {
        return nItem;
    }

    public CustomMenuItem getsItem() {
        return sItem;
    }

    //remove label Functions
    public void removefLabel(){
        menu.getItems().removeAll(fItem);
    }
    public void removenLabel(){
        menu.getItems().removeAll(nItem);
    }
    public void removesLabel(){
        menu.getItems().removeAll(sItem);
    }
    //add label Functions
    public void addfLabel(){
        menu.getItems().add(fItem);
    }
    public void addnLabel(){
        menu.getItems().add(nItem);
    }
    public void addsLabel(){
        menu.getItems().add(sItem);
    }

}

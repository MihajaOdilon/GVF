package com.sales_management_javafx.controller.account;

import com.sales_management_javafx.SalesApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.sales_management.entity.AccountEntity;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AccountLayoutController implements Initializable {
    @FXML
    private BorderPane account_layout;

    public AccountLayoutController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.account_layout.setCenter(this.getTable());
        this.account_layout.setBottom(this.getToolbar());
    }
    public GridPane getToolbar(){
        FXMLLoader toolbarLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/account/accountToolbar.fxml"));
        GridPane toolbar;
        try {
            toolbar = toolbarLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return toolbar;
    }
    public BorderPane getTable(){
        FXMLLoader tableLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/account/accountTable.fxml"));
        BorderPane table;
        try {
            table = tableLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return table;
    }
}
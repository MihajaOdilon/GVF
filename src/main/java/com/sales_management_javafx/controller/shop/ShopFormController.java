package com.sales_management_javafx.controller.shop;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.NumberTextField;
import com.sales_management_javafx.composent.ShopGridPane;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.ShopEntity;
import org.sales_management.service.ShopService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ShopFormController implements Initializable {
    @FXML
    private VBox shopFormVBox;
    @FXML
    private TextField shopNameTextfield;
    @FXML
    private TextField shopAddressTextfield;
    @FXML
    private TextField shopContactTextfield;
    @FXML
    private TextField shopEmailTextfield;
    @FXML
    private Button createShopButton;
    @FXML
    private Button cancelShopButton;
    private final ShopService shopService;

    public ShopFormController() {
        this.shopService = new ShopService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.formValidation();
        this.onCreateShop();
        this.onCancelCreateShop();
        NumberTextField.requireNumber(shopContactTextfield);
    }
    private void formValidation(){
        if (this.shopNameTextfield.getText().isEmpty()){
            createShopButton.setDisable(true);
        }
        shopNameTextfield.textProperty().addListener((observableValue, s, t1) -> createShopButton.setDisable(this.shopNameTextfield.getText().isEmpty()));
    }
    private void onCreateShop(){
        createShopButton.setOnAction(actionEvent -> {
            if (!this.shopNameTextfield.getText().isEmpty()){
                ShopEntity shop = new ShopEntity();
                shop.setName(this.shopNameTextfield.getText());
                if (!this.shopContactTextfield.getText().isEmpty()){
                    shop.setContact(Long.valueOf(this.shopContactTextfield.getText()));
                }
                shop.setAddress(this.shopAddressTextfield.getText());
                shop.setEmail(this.shopEmailTextfield.getText());
                if (this.shopService.create(shop)!=null){
                    BorderPane dashboardLayout = (BorderPane) this.shopFormVBox.getParent();
                    ScrollPane dashboardLayoutScrollpane = (ScrollPane) dashboardLayout.lookup("#dashboardLayoutScrollpane");
                    GridPane shopGridPane = new ShopGridPane().getGridPane(new ShopService().getAll(),4);
                    dashboardLayoutScrollpane.setContent(shopGridPane);
                    dashboardLayout.setBottom(this.getDashboardToolbar());
                }
            }
        });
    }
    private void onCancelCreateShop(){
        this.cancelShopButton.setOnAction(actionEvent -> {
            BorderPane dashboardLayout = (BorderPane) this.shopFormVBox.getParent();
            dashboardLayout.setBottom(this.getDashboardToolbar());
        });
    }
    private StackPane getDashboardToolbar(){
        FXMLLoader dashboardToolbarLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/dashboard/dashboardToolbar.fxml"));
        StackPane dashboardToolbar;
        try {
            dashboardToolbar = dashboardToolbarLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dashboardToolbar;
    }
}

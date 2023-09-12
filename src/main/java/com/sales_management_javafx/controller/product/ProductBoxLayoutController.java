package com.sales_management_javafx.controller.product;

import com.sales_management_javafx.composent.ProductGridPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import org.sales_management.service.ProductService;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductBoxLayoutController implements Initializable {
    @FXML
    private ScrollPane productBox;
    private final ProductService productService;
    private final ProductGridPane productGridPane;

    public ProductBoxLayoutController() {
        this.productService = new ProductService();
        this.productGridPane = new ProductGridPane();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setProducts();
    }
    private void setProducts(){
        productBox.setContent(this.productGridPane.getGridPane(this.productService.getAll(), 4));
        productBox.setFitToWidth(true);
    }
}

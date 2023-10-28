package com.sales_management_javafx.composent;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.controller.product_type.ProductTypeBoxController;
import com.sales_management_javafx.controller.seller.SellerPriceVariationBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.ArticleEntity;
import org.sales_management.entity.ProductTypeEntity;

import java.io.IOException;
import java.util.Collection;

public class ProductTypeGridPane {
    private final GridPane gridPane;

    public ProductTypeGridPane() {
        this.gridPane = new GridPane();
    }

    public GridPane getGridPane(Collection<ProductTypeEntity> productTypes , int colSize) {
        for (int i = 0 ; i < colSize ; i++){
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setHgrow(Priority.ALWAYS);
            constraints.setFillWidth(true);
            constraints.setPercentWidth((double) 100 /colSize);
            gridPane.getColumnConstraints().add(constraints);
        }
        int col = 0;
        int row = 0;
        for (ProductTypeEntity productType : productTypes) {
            gridPane.add(this.getProductTypeBox(productType), col, row);
            col++;
            if (col == colSize) {
                col = 0;
                row++;
            }
        }
        gridPane.getStyleClass().add("gridpane");
        gridPane.setId("product-type-grid-pane");
        return gridPane;
    }
    private StackPane getProductTypeBox(ProductTypeEntity productType){
        FXMLLoader productTypeBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/product_type/productTypeBox.fxml"));
        StackPane productTypeBox;
        try {
            productTypeBox = productTypeBoxLoader.load();
            ProductTypeBoxController productTypeBoxController = productTypeBoxLoader.getController();
            productTypeBoxController.initialize(productType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return productTypeBox;
    }
}

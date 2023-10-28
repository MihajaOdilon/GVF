package com.sales_management_javafx.composent;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.controller.product.ProductBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;
import org.sales_management.entity.ProductEntity;

import java.io.IOException;
import java.util.Collection;

public class ProductGridPane{
    private final GridPane gridPane;

    public ProductGridPane() {
        this.gridPane = new GridPane();
    }
    public GridPane getGridPane(Collection<ProductEntity> products, int colSize, boolean isShowCreateBox){
        for (int i = 0 ; i < colSize ; i++){
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setHgrow(Priority.ALWAYS);
            constraints.setFillWidth(true);
            constraints.setPercentWidth((double) 100 /colSize);
            gridPane.getColumnConstraints().add(constraints);
        }
        int col = 1;
        int row = 0;
        if (isShowCreateBox){
//            gridPane.add(this.getProductCreateFormBox(), 0, row);
        }
        else col = 0;
        for (ProductEntity product : products) {
//            if (!ProductFile.readProductsFromFile().contains(product)){
                gridPane.add(this.getProductBox(product), col, row);
                col++;
                if (col == colSize) {
                    col = 0;
                    row++;
                }
//            }
        }
        gridPane.getStyleClass().add("gridpane");
        gridPane.setId("productGridPane");
        return gridPane;
    }
    private StackPane getProductBox(ProductEntity product){
        FXMLLoader productBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/product/productBox.fxml"));
        StackPane productBoxStackpane;
        try {
            productBoxStackpane = productBoxLoader.load();
            ProductBoxController productBoxController = productBoxLoader.getController();
            productBoxController.initialize(product);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return productBoxStackpane;
    }
}

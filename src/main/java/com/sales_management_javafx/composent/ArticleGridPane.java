package com.sales_management_javafx.composent;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.controller.article_type.ArticleBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.ArticleTypeEntity;

import java.io.IOException;
import java.util.Collection;

public class ArticleGridPane {
    private final GridPane gridPane;

    public ArticleGridPane() {
        this.gridPane = new GridPane();
    }

    public GridPane getGridPane(Collection<ArticleTypeEntity> priceVariations, int colSize, boolean show){
        for (int i = 0 ; i < colSize ; i++){
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setHgrow(Priority.ALWAYS);
            constraints.setFillWidth(true);
            constraints.setPercentWidth((double) 100 /colSize);
            gridPane.getColumnConstraints().add(constraints);
        }
        int col = 0;
        int row = 0;
        if (show){
            gridPane.add(this.getCreatePriceBox(),col,row);
            col++;
        }
        for (ArticleTypeEntity priceVariation : priceVariations) {
            try {
                gridPane.add(this.getArticleBox(priceVariation), col, row);
                col++;
                if (col == colSize) {
                    col = 0;
                    row++;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());;
            }
        }
        gridPane.getStyleClass().add("gridpane");
        return gridPane;
    }
    private StackPane getArticleBox(ArticleTypeEntity article){
        FXMLLoader articleBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/article/articleBox.fxml"));
        StackPane productBoxStackpane;
        try {
            productBoxStackpane = articleBoxLoader.load();
            ArticleBoxController articleBoxController = articleBoxLoader.getController();
            articleBoxController.initialize(article);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return productBoxStackpane;
    }
    private StackPane getCreatePriceBox(){
        FXMLLoader createPriceBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/article/articleCreate.fxml"));
        StackPane priceVariationCreateStackPane;
        try {
            priceVariationCreateStackPane = createPriceBoxLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return priceVariationCreateStackPane;
    }
    private StackPane getToolbar(){
        FXMLLoader createPriceBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/product_variation/productCategoryToolbar.fxml"));
        StackPane priceVariationToolbar;
        try {
            priceVariationToolbar = createPriceBoxLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return priceVariationToolbar;
    }
}

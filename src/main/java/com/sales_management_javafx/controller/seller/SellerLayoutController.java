package com.sales_management_javafx.controller.seller;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.composent.SellerArticleGridPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.ArticleTypeEntity;
import org.sales_management.entity.UserEntity;
import org.sales_management.service.ArticleTypeService;
import org.sales_management.session.SessionManager;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

public class SellerLayoutController implements Initializable {
    @FXML private StackPane sellerLayout;
    @FXML private BorderPane sellerLayoutBorderpane;
    @FXML private Label username;
    private final UserEntity user;

    @FXML
    private ScrollPane sellerArticleScrollpane;

    @FXML private TextField searchTextfield;
    private final ArticleTypeService articleTypeService;
    public SellerLayoutController() {
        this.user = SessionManager.getSession().getCurrentUser();
        this.articleTypeService = new ArticleTypeService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.sellerLayoutBorderpane.setBottom(this.getToolbar());
        this.setProducts();
        this.setSearchTextfield();
        if (user != null){
            username.setText(user.getAccount().getUsername());
        }
    }

    private void setProducts(){
        GridPane gridPane = new SellerArticleGridPane().getGridPane(articleTypeService.getAll(),4);
        sellerArticleScrollpane.setContent(gridPane);
    }
    private void setSearchTextfield(){
        searchTextfield.textProperty().addListener(event->{
            if (!searchTextfield.getText().isEmpty()){
                Collection<ArticleTypeEntity> articles = articleTypeService.searchArticleByByCode(searchTextfield.getText());
                GridPane sellerArticleGridPane = new SellerArticleGridPane().getGridPane(articles,4);
                sellerArticleScrollpane.setContent(sellerArticleGridPane);
            }
            else {
                GridPane sellerArticleGridPane = new SellerArticleGridPane().getGridPane(articleTypeService.getAll(),4);
                sellerArticleScrollpane.setContent(sellerArticleGridPane);
            }
        });
    }
    private GridPane getToolbar(){
        FXMLLoader pannierToolbarLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/seller/sellerToolbar.fxml"));
        GridPane pannierToolbar;
        try {
            pannierToolbar = pannierToolbarLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return pannierToolbar;
    }
}

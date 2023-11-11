package com.sales_management_javafx.controller.product;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.MenuIcon;
import com.sales_management_javafx.composent.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.ProductEntity;
import org.sales_management.service.ArticleService;
import org.sales_management.service.ProductCategoryService;
import org.sales_management.service.ProductService;
import org.sales_management.service.ProductTypeService;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

public class ProductBoxLayoutController implements Initializable {
    @FXML private StackPane productBoxLayout;
    @FXML private BorderPane product;
    @FXML private BorderPane modal;
    @FXML private ScrollPane productBoxLayoutScrollpane;
    @FXML private BorderPane productBoxLayoutBorderpane;
    @FXML private TextField searchProductTextfield;
    @FXML private Label categories;
    @FXML private Label products;
    @FXML private Label types;
    @FXML private Label articles;
    @FXML private ImageView articleIcon;
    @FXML private Button exit;
    private final ProductService productService;
    private final ProductCategoryService productCategoryService;
    private final ProductTypeService productTypeService;
    private final ArticleService articleService;
    private final ProductGridPane productGridPane;
    private final MenuGridPane menuGridPane;
    private final MenuIcon menuIcon;

    public ProductBoxLayoutController() {
        this.productTypeService = new ProductTypeService();
        this.articleService = new ArticleService();
        this.menuIcon = new MenuIcon();
        this.menuGridPane = new MenuGridPane();
        this.productCategoryService = new ProductCategoryService();
        this.productService = new ProductService();
        this.productGridPane = new ProductGridPane();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.initializeSearchTextField();
        this.setCategories();
        this.product.setVisible(true);
        this.modal.setVisible(false);
        this.putIcons();
        this.setCategories();
        this.setProducts();
        this.setTypes();
        this.setArticles();
        this.exit.setOnAction(event->{
            this.setExit();
        });
    }
    private void initializeSearchTextField(){
        this.searchProductTextfield.setPromptText("Recherche");
        searchProductTextfield.textProperty().addListener((observableValue, s, t1) -> {
            if (!searchProductTextfield.getText().isEmpty()){
                Collection<ProductEntity> products = this.productService.searchProductsByName(searchProductTextfield.getText());
                this.productBoxLayoutScrollpane.setContent(new ProductGridPane().getGridPane(products,4,false));
                System.out.println(productBoxLayoutScrollpane);
            }
            else {
                this.productBoxLayoutScrollpane.setContent(new ProductGridPane().getGridPane(this.productService.getAll(), 4,false));
            }
        });
    }
    private void setCategories(){
        categories.setOnMouseClicked(event->{
            this.productBoxLayoutScrollpane.setContent(new ProductCategoryGridPane().getGridPane(productCategoryService.getAll(),4));
        });
    }
    private void setProducts(){
        products.setOnMouseClicked(event->{
            this.productBoxLayoutScrollpane.setContent(new ProductGridPane().getGridPane(productService.getAll(),4,false));
        });
    }
    private void setTypes(){
        types.setOnMouseClicked(event->{
            this.productBoxLayoutScrollpane.setContent(new ProductTypeGridPane().getGridPane(productTypeService.getAll(),4));
        });
    }
    private void setArticles(){
        articles.setOnMouseClicked(event->{
            this.productBoxLayoutScrollpane.setContent(new ArticleGridPane().getGridPane(articleService.getAll(),4,false));
        });
    }
    public void setExit(){
        productBoxLayout.getParent().setVisible(false);
        BorderPane stockistLayout = (BorderPane) productBoxLayout.getParent().getParent().getParent();
        stockistLayout.setBottom(getToolbar());
    }
    private void putIcons(){
        this.articleIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/ArticleIcon.png"))));
    }

    private StackPane getToolbar(){
        FXMLLoader toolbarLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/stockist/stockistToolbar.fxml"));
        StackPane toolbar;
        try {
            toolbar = toolbarLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return toolbar;
    }
}

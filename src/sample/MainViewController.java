package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;
import javafx.util.Callback;
import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Submission;
import net.dean.jraw.pagination.DefaultPaginator;
import sample.service.RedditService;

public class MainViewController {

   @FXML
   private AnchorPane menu;

   @FXML
   private JFXButton btnHome;

   @FXML
   private JFXButton btnLogin;

   @FXML
   private JFXButton btnPopular;

   @FXML
   private MaterialIconView labelRefresh;

   @FXML
   private JFXListView<?> listSubreddits;

   @FXML
   private AnchorPane list;

   @FXML
   private MaterialIconView labelInbox;

   @FXML
   private MaterialIconView labelUnread;

   @FXML
   private MaterialIconView labelAll;

   @FXML
   private JFXListView<Submission> listArticles;

   @FXML
   private MaterialIconView labelMarkAllRead;

   @FXML
   private AnchorPane content;

   @FXML
   private MaterialIconView labelMarkUnread;

   @FXML
   private MaterialIconView labelShare;

   @FXML
   private MaterialIconView labelComments;

   @FXML
   private MaterialIconView labelFullView;

   @FXML
   private Label labelTitle;

   @FXML
   private WebView webview;

   @FXML
   private Label labelDate;

   @FXML
   private Label labelAuthor;

   @FXML
   public void initialize() {
      RedditService rs = new RedditService();

      rs.login(null,null);
      btnLogin.setText(rs.getAccountName());

      listArticles.setCellFactory(new Callback<ListView<Submission>, ListCell<Submission>>() {
         @Override
         public ListCell<Submission> call(ListView<Submission> param) {
            return new JFXListCell<Submission>(){
               @Override
               public void updateItem(Submission item, boolean empty) {
                  super.updateItem(item, empty);
                  if(item!=null && !empty){
                     VBox container = new VBox();
                     container.setMaxWidth(listArticles.getWidth() - 42);
                     container.setMouseTransparent(true);
                     container.setStyle("-fx-background-color:#C1354F");

                     Label lblArticleSubredit = new Label(item.getSubreddit());
                     lblArticleSubredit.setTextFill(Color.WHITESMOKE);
                     lblArticleSubredit.setFont(Font.font(11));
                     container.getChildren().add(lblArticleSubredit);

                     Label lblArticleAuthor = new Label(item.getAuthor());
                     lblArticleAuthor.setTextFill(Color.WHITESMOKE);
                     lblArticleAuthor.setFont(Font.font(11));
                     container.getChildren().add(lblArticleAuthor);

                     Label lblArticleTitle = new Label(item.getTitle());
                     lblArticleTitle.setWrapText(true);
                     lblArticleTitle.setTextFill(Color.WHITE);
                     lblArticleTitle.setFont(Font.font(13));
                     container.getChildren().add(lblArticleTitle);
                     setText("");
                     setGraphic(container);
                  }
               }
            };
         }
      });
      DefaultPaginator<Submission> paginator = rs.getTopSubmissions();
      Listing<Submission> submissions = paginator.next();

      listArticles.setItems(FXCollections.observableList(submissions));
      listArticles.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
         @Override public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            showArticleContent();
         }
      });
      listArticles.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
         showArticleContent();
      });

   }

   private void showArticleContent() {
      Submission selected = listArticles.getSelectionModel().getSelectedItem();
      labelTitle.setText(selected.getTitle());
      labelDate.setText(selected.getCreated() + "");
      labelAuthor.setText(selected.getAuthor());
      if (!selected.isSelfPost() && selected.getUrl().contains("i.imgur.com")) {
         webview.getEngine().load(selected.getUrl());
      } else {
         Platform.runLater(() -> {
            webview.getEngine().load(selected.getUrl());
         });
      }
   }
}


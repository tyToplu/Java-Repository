import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;

public class SeatsStage extends Application{ //TODO Static olmasına dikkat et.
    private ComboBox<String> users = new ComboBox<String>(FXCollections.observableArrayList(User.getUsernames()));
    private VBox vbox = new VBox(10);
    private Text heading = new Text((String.format("%s (%d minutes) Hall: %s", FilmChoosingStage.getCurrentFilm().getMovieName(), FilmChoosingStage.getCurrentFilm().getDuration(), FilmTrailerStage.getCurrentHall().getHallName())));
    private Text infoText = new Text();
    private Text lastText = new Text();
    private Button back = new Button("< BACK");
    private HBox backBox = new HBox(back);
    private BorderPane bp = new BorderPane();
    public void start(Stage stage){
        users.setValue(LoginStage.getCurrentUser().getUsername());
        Image image = new Image(new File("icons\\empty_seat.png").toURI().toString());
        Image image2 = new Image(new File("icons\\reserved_seat.png").toURI().toString());
        vbox.getChildren().add(heading);
        // Vbox created
        for(int row = 0; row < FilmTrailerStage.getCurrentHall().getRow(); row++) {
            HBox hbox = new HBox(10);
            for (int column = 0; column < FilmTrailerStage.getCurrentHall().getColumn(); column++) {
                Button button = new Button();
                Seat currentSeat = FilmTrailerStage.getCurrentHall().getSeats().get(row).get(column);
                ImageView iv;
                if(currentSeat.getOwner().equals("null")){
                    iv = new ImageView(image);
                    iv.setFitHeight(50);
                    iv.setFitWidth(50);
                    button.setGraphic(iv);
                }else{
                    iv = new ImageView(image2);
                    iv.setFitHeight(50);
                    iv.setFitWidth(50);
                    button.setGraphic(iv);
                }
                if(!LoginStage.getCurrentUser().isAdmin() && !currentSeat.getOwner().equals(LoginStage.getCurrentUser().getUsername()) && iv.getImage() == image2)
                    button.setDisable(true);
                else {
                    if(LoginStage.getCurrentUser().isAdmin()) {
                        button.setOnMouseEntered(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                if (currentSeat.getOwner().equals("null"))
                                    infoText.setText("Not bought yet!");
                                else {
                                    infoText.setText(String.format("Bought by %s for %d TL!", currentSeat.getOwner(), currentSeat.getPrice()));
                                }
                            }
                        });
                        button.setOnMouseExited(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                infoText.setText("");
                            }
                        });
                    }
                    button.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {


                            Ticket ticket;
                            if (iv.getImage().equals(image)){
                                int price;
                                iv.setImage(image2);
                                if (User.getUsers().get(users.getValue()).isMember()) {
                                    price = (int)((double)FilmTrailerStage.getCurrentHall().getPricePerSeat() * ((double)(100-User.getMemberDiscount())/(double)100));
                                }
                                else {
                                    price = FilmTrailerStage.getCurrentHall().getPricePerSeat();
                                }
                                ticket = new Ticket(users.getValue(), currentSeat.getMovie(), FilmTrailerStage.getCurrentHall().getHallName(), price, currentSeat.getRowIndex() + 1, currentSeat.getColumnIndex() + 1, User.getUsers().get(users.getValue()).isMember());
                                User.getUsers().get(users.getValue()).getTickets().add(ticket);
                                Ticket.getTickets().add(ticket);
                                currentSeat.setTicket(ticket);
                                currentSeat.setPrice(price);
                                currentSeat.setOwner(users.getValue());
                                if(LoginStage.getCurrentUser().isAdmin())
                                    lastText.setText(String.format("Seat at %d-%d is bought for %s for %d TL successfully", currentSeat.getRowIndex() + 1, currentSeat.getColumnIndex() + 1, users.getValue(), price));
                                else{
                                    lastText.setText(String.format("Seat at %d-%d is bought for %d TL successfully", currentSeat.getRowIndex() + 1, currentSeat.getColumnIndex() + 1, price));

                                }
                            }else{
                                iv.setImage(image);
                                User.getUsers().get(currentSeat.getOwner()).getTickets().remove(currentSeat.getTicket());
                                Ticket.getTickets().remove(currentSeat.getTicket());
                                if(LoginStage.getCurrentUser().isAdmin()) {
                                    lastText.setText(String.format("Seat at %d-%d is refunded for %s successfully", currentSeat.getRowIndex() + 1, currentSeat.getColumnIndex() + 1, currentSeat.getOwner()));
                                }else
                                    lastText.setText(String.format("Seat at %d-%d is refunded successfully", currentSeat.getRowIndex() + 1, currentSeat.getColumnIndex() + 1));
                                currentSeat.setOwner("null");
                                currentSeat.setPrice(0);
                                currentSeat.setTicket(null);
                            }
                        }
                    });
                }
                hbox.getChildren().add(button);
            }
            hbox.setAlignment(Pos.CENTER);
            vbox.getChildren().add(hbox);
        }




        back.setOnAction(e -> {
            FilmTrailerStage fts = new FilmTrailerStage();
            fts.start(stage);
        });



        if(LoginStage.getCurrentUser().isAdmin())
            vbox.getChildren().addAll(users, infoText, lastText);
        else
            vbox.getChildren().addAll(lastText);
        vbox.setAlignment(Pos.CENTER);


        backBox.setPadding(new Insets(10,0,20,20));


        bp.setCenter(vbox);
        bp.setBottom(backBox);
        bp.setPadding(new Insets(20));



        vbox.setPadding(new Insets(20,20,0,20));


        Scene scene = new Scene(bp);
        stage.setResizable(false);
        stage.setMinWidth(500);
        stage.setScene(scene);
        stage.show();
    }
}

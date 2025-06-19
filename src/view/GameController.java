package view;

import java.util.ArrayList;

import model.Colour;
import model.card.Card;
import model.card.standard.Suit;
import model.player.Marble;
import model.player.Player;
import engine.Game;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.stage.Modality;
import model.card.standard.King;



public class GameController {
	@FXML
	TextField username;
	ImageView btn1;
	Game game;
	GameView gameview;
	ArrayList<CardView> hand;
	HBox curr;
	HBox nextr;
	public void login(ActionEvent e){
		try{
			curr = new HBox();
			nextr = new HBox();
			curr.setMaxSize(70, 70);
			curr.setMinSize(70, 70);
			nextr.setMaxSize(70, 70);
			nextr.setMinSize(70, 70);
			String name = username.getText();
			game = new Game(name);
			Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			gameview = new GameView(game,name);
			Timeline t = new Timeline(new KeyFrame(Duration.millis(500), e1 -> {
				if(game.checkWin()!=null){
					Color c;
					Colour c1 = game.checkWin();
					String text;
					if(c1 == Colour.RED){
						c = Color.RED;
						text = "Red has won";
					}else if(c1 == Colour.BLUE){
						c = Color.BLUE;
						text = "Blue has won";
					}else if(c1 == Colour.GREEN){
						c = Color.GREEN;
						text = "Green has won";
					}else{
						c = Color.YELLOW;
						text = "Yellow has won";
					}
					Stage popupStage = new Stage();
				    popupStage.initModality(Modality.APPLICATION_MODAL);
				    popupStage.setTitle("Win");
				    Label message = new Label(text);
				    message.setTextFill(c);
				    Button closeButton = new Button("Close");
				    closeButton.setOnAction(ev -> popupStage.close());

				    VBox layout = new VBox(10, message, closeButton);
				    layout.setStyle("-fx-alignment: center; -fx-padding: 20;");
				    Scene popupScene = new Scene(layout, 300, 150);

				    popupStage.setScene(popupScene);
				    popupStage.showAndWait();
				}
			}));
			turn(-80,-600,30,-400);
			Scene scene = new Scene(gameview);
			stage.setScene(scene);
			stage.setFullScreen(true);
			stage.setResizable(false);
			stage.show();
			selectcard(stage);
			selectmarbles(stage);
			player(stage);
		}catch(Exception err){
			err.printStackTrace();
		}
	}
	public void selectcard(Stage primaryStage){
		hand = gameview.getHandview().getCardviews();
		for(CardView card:hand){
			card.setOnMouseClicked(e-> {
				try{
				 	boolean selected = !card.isSelected();
			        card.setSelected(selected);
			        Card cardm = card.getcard();
			        game.selectCard(cardm);
			        gameview.enablebtn(selected);}
				catch(Exception fe){
					this.showPopup(primaryStage, fe.getMessage());
			        }
			});
		}
	}
	public void selectmarbles(Stage primaryStage){
		if(game.canPlayTurn()){
		ArrayList<CellView> trackcells = gameview.getBoardview().getTrackcells();
		ArrayList<CellView>[] safezones = gameview.getBoardview().getSafezones();
		for(CellView cellview:trackcells){
			cellview.setOnMouseClicked(e->{
				try{
					boolean selected = !cellview.isSelected();
			        cellview.setSelected(selected);
			        if(selected){
			        	cellview.setStyle("-fx-background-color: green");
			        }else{
			        	cellview.setStyle("-fx- background-color: !important;");
			        }
			        Marble marble = cellview.getMarbleview().getMarble();
			        game.selectMarble(marble);
				}catch(Exception fe){
					this.showPopup(primaryStage, fe.getMessage());
				}
			});
		}
		for(int i =0;i<4;i++){
			for(CellView cellview:safezones[i]){
				cellview.setOnMouseClicked(e->{
					try{
						boolean selected = !cellview.isSelected();
				        cellview.setSelected(selected);
				        Marble marble = cellview.getMarbleview().getMarble();
				        game.selectMarble(marble);
				        gameview.enablebtn(selected);
					}catch(Exception fe){
						this.showPopup(primaryStage, fe.getMessage());
					}
				});
			}
		}
		}else{
			game.endPlayerTurn();
		}
	}
	public void player(Stage primarystage) {
		if(game.canPlayTurn()){
	    gameview.play.setOnMouseClicked(e -> {
	        try {
	            // Player's turn
	            game.playPlayerTurn();
	            game.endPlayerTurn();
	            game.deselectAll();
	            cleanupFirePit();
	            gameview.setgame(game);
	            ArrayList<CellView> trackcells = gameview.getBoardview().getTrackcells();
	            for(CellView cellview:trackcells){
	    			if(cellview.isSelected()){
	    				cellview.setStyle("-fx- background-color: !important;");
	    			}
	    		}

	            // Update camera angle
	            ArrayList<Player> players = game.getPlayers();
	            Colour c = game.getActivePlayerColour();
	            int i = 0;
	            for (i = 0; i < 4; i++) {
	                if (players.get(i).getColour() == c) break;
	            }

	            if (i == 1) {
	                turn(-400, -1800, 600, -920);
	            } else if (i == 2) {
	                turn(-920, -1280, 1800, -890);
	            } else if (i == 3) {
	                turn(-890, -100, 500, -80);
	            } else {
	                turn(-80, -600, 30, -400);
	            }

	            if (checkWin(primarystage)) return;

	            gameview.play.setDisable(true); // Disable button until player selects again
	            // Let CPUs play now
	            playAllCPUs(primarystage);

	            // After CPUs finish (delay), re-enable player interaction

	        } catch (Exception ex) {
	            int count = 0;
	            if(game.getPlayers().get(0).getMarbles().size() == 4 ){
	            	count=0;
	            }else{
	            	count=1;
	            }
	            if (count == 0) {
	                boolean yes = this.showYesNoDialog(primarystage, "Do you want to keep or not?");
	                if (!yes) {
	                    game.endPlayerTurn();
	                    game.deselectAll();
	                    cleanupFirePit();
	                    gameview.setgame(game);
	                    ArrayList<Player> players = game.getPlayers();
	    	            Colour c = game.getActivePlayerColour();
	    	            int i = 0;
	    	            for (i = 0; i < 4; i++) {
	    	                if (players.get(i).getColour() == c) break;
	    	            }

	    	            if (i == 1) {
	    	                turn(-400, -1800, 600, -920);
	    	            } else if (i == 2) {
	    	                turn(-920, -1280, 1800, -890);
	    	            } else if (i == 3) {
	    	                turn(-890, -100, 500, -80);
	    	            } else {
	    	                turn(-80, -600, 30, -400);
	    	            }

	    	            if (checkWin(primarystage)) return;
	    	            gameview.play.setDisable(true); // 
	    	            // Let CPUs play now
	    	            playAllCPUs(primarystage);
	                }else{
	                	 	game.deselectAll();
	                	 	game.endPlayerTurn();
		                    cleanupFirePit();
		                    gameview.setgame(game);
	                	 ArrayList<Player> players = game.getPlayers();
	     	            Colour c = game.getActivePlayerColour();
	     	            int i = 0;
	     	            for (i = 0; i < 4; i++) {
	     	                if (players.get(i).getColour() == c) break;
	     	            }

	     	            if (i == 1) {
	     	                turn(-400, -1800, 600, -920);
	     	            } else if (i == 2) {
	     	                turn(-920, -1280, 1800, -890);
	     	            } else if (i == 3) {
	     	                turn(-890, -100, 500, -80);
	     	            } else {
	     	                turn(-80, -600, 30, -400);
	     	            }

	     	            if (checkWin(primarystage)) return;

	     	            gameview.play.setDisable(true); // Disable button until player selects again
	     	            // Let CPUs play now
	     	            playAllCPUs(primarystage);
	                }
	            } else {
	                this.showPopup(primarystage, ex.getMessage());
	            }
	        }
	    });
		}else{
            game.endPlayerTurn();
            game.deselectAll();
            cleanupFirePit();
            gameview.setgame(game);

            // Update camera angle
            ArrayList<Player> players = game.getPlayers();
            Colour c = game.getActivePlayerColour();
            int i = 0;
            for (i = 0; i < 4; i++) {
                if (players.get(i).getColour() == c) break;
            }

            if (i == 1) {
                turn(-400, -1800, 600, -920);
            } else if (i == 2) {
                turn(-920, -1280, 1800, -890);
            } else if (i == 3) {
                turn(-890, -100, 500, -80);
            } else {
                turn(-80, -600, 30, -400);
            }

            if (checkWin(primarystage)) return;

            gameview.play.setDisable(true); // Disable button until player selects again
            // Let CPUs play now
            playAllCPUs(primarystage);
		}
	}

	public void cleanupFirePit() {
	    ArrayList<Card> firepit = game.getFirePit();
	    firepit.removeIf(c -> c == null);
	}
	public boolean checkWin(Stage primarystage) {
	    Colour winner = game.checkWin();
	    if (winner != null) {
	        Color fxColor;
	        String text;

	        if (winner == Colour.RED) {
	            fxColor = Color.RED;
	            text = "Red has won!";
	        } else if (winner == Colour.BLUE) {
	            fxColor = Color.BLUE;
	            text = "Blue has won!";
	        } else if (winner == Colour.GREEN) {
	            fxColor = Color.GREEN;
	            text = "Green has won!";
	        } else if (winner == Colour.YELLOW) {
	            fxColor = Color.YELLOW;
	            text = "Yellow has won!";
	        } else {
	            fxColor = Color.BLACK;
	            text = "Unknown winner";
	        }

	        showWinPopup(primarystage,text, fxColor);
	        return true;
	    }
	    return false;
	}

	public void showWinPopup(Stage ownerStage, String messageText, Color color) {
	    Popup popup = new Popup();
	    popup.setAutoHide(false); // User must close it manually

	    Label message = new Label(messageText);
	    message.setTextFill(color);
	    message.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

	    Button closeButton = new Button("Close");
	    closeButton.setOnAction(e -> popup.hide());

	    VBox content = new VBox(10, message, closeButton);
	    content.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-padding: 20;");
	    content.setPrefSize(300, 150);
	    content.setAlignment(javafx.geometry.Pos.CENTER);

	    popup.getContent().add(content);

	    popup.show(ownerStage);
	}

	public static void showPopup(Stage ownerStage, String message) {
        Popup popup = new Popup();
        popup.setAutoHide(true); // Optional: auto closes when clicking outside

        Label label = new Label(message);
        Button closeButton = new Button("Close");

        closeButton.setOnAction(e -> popup.hide());

        VBox content = new VBox(10, label, closeButton);
        content.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-padding: 20;");
        content.setPrefSize(300, 150);
        content.setAlignment(javafx.geometry.Pos.CENTER);

        popup.getContent().add(content);

        // Show the popup in the center of the ownerStage
        popup.show(ownerStage);
    }
	public void playAllCPUs(Stage stage) {
	    playCPUWithDelay(1, stage); // Start with CPU at index 1
	}
	private void playCPUWithDelay(int index, Stage stage) {

	    PauseTransition delay = new PauseTransition(Duration.seconds(3));
	    delay.setOnFinished(e -> {
	        if (game.canPlayTurn()) {
	            try {
	                game.playPlayerTurn();
	                game.endPlayerTurn();
	                cleanupFirePit();
	                gameview.setgame(game);

	                // Update board UI and camera angle
	                ArrayList<Player> players = game.getPlayers();
	                Colour c = game.getActivePlayerColour();
	                int i = 0;
	                for (i = 0; i < 4; i++) {
	                    if (players.get(i).getColour() == c) break;
	                }

	                if (i == 1) {
		                turn(-400, -1800, 600, -920);
		            } else if (i == 2) {
		                turn(-920, -1280, 1800, -890);
		            } else if (i == 3) {
		                turn(-890, -100, 500, -80);
		            } else {
		                turn(-80, -600, 30, -400);
		            }

	                if (checkWin(stage)) return;
	                if (index+1 >= 4) {
	    		        // All CPUs have played, now it's the player's turn again
	    		        if (!checkWin(stage)) {
	    		        	gameview.play.setDisable(true); // stay disabled until a card is selected
	    		            selectcard(stage); // enable card selection
	    		            selectmarbles(stage); // enable marble selection
	    		        }
	    		    }
	    	        playCPUWithDelay(index + 1, stage); 
	            } catch (Exception ex) {
	            	ex.printStackTrace();
	            	if ((index+1)>=4 && !checkWin(stage)) {
			        	gameview.play.setDisable(true); // stay disabled until a card is selected
			            selectcard(stage); // enable card selection
			            selectmarbles(stage); // enable marble selection
			        }else if(!checkWin(stage)){
				        playCPUWithDelay(index + 1, stage); 
				    }// Continue to next CPU
	            }
	        }else{
	        	 try {
		                game.endPlayerTurn();
		                cleanupFirePit();
		                gameview.setgame(game);

		                // Update board UI and camera angle
		                ArrayList<Player> players = game.getPlayers();
		                Colour c = game.getActivePlayerColour();
		                int i = 0;
		                for (i = 0; i < 4; i++) {
		                    if (players.get(i).getColour() == c) break;
		                }

		                if (i == 1) {
			                turn(-400, -1800, 600, -920);
			            } else if (i == 2) {
			                turn(-920, -1280, 1800, -890);
			            } else if (i == 3) {
			                turn(-890, -100, 500, -80);
			            } else {
			                turn(-80, -600, 30, -400);
			            }

		                if (checkWin(stage)) return;
		                if (index+1 >= 4) {
		    		        // All CPUs have played, now it's the player's turn again
		    		        if (!checkWin(stage)) {
		    		        	gameview.play.setDisable(true); // stay disabled until a card is selected
		    		            selectcard(stage); // enable card selection
		    		            selectmarbles(stage); // enable marble selection
		    		        }
		    		    }
		    	        playCPUWithDelay(index + 1, stage); 
		            } catch (Exception ex) {
		            	ex.printStackTrace();
		            	if ((index+1)>=4 && !checkWin(stage)) {
				        	gameview.play.setDisable(true); // stay disabled until a card is selected
				            selectcard(stage); // enable card selection
				            selectmarbles(stage); // enable marble selection
				        }else if(!checkWin(stage)){
					        playCPUWithDelay(index + 1, stage); 
					    }// Continue to next CPU
		            }
	        }
	    });

	    delay.play();
	}

	public static boolean showYesNoDialog(Stage ownerStage, String messageText) {
	    final boolean[] result = {false}; // Will store user's choice

	    Stage popupStage = new Stage();
	    popupStage.initOwner(ownerStage); // Bind to the fullscreen window
	    popupStage.initModality(Modality.APPLICATION_MODAL);
	    popupStage.setTitle("Decision");

	    Label message = new Label(messageText);
	    Button yesButton = new Button("Yes");
	    Button noButton = new Button("No");

	    yesButton.setOnAction(e -> {
	        result[0] = true;
	        popupStage.close();
	    });

	    noButton.setOnAction(e -> {
	        result[0] = false;
	        popupStage.close();
	    });

	    VBox buttonBox = new VBox(10, yesButton, noButton);
	    buttonBox.setAlignment(javafx.geometry.Pos.CENTER);

	    VBox layout = new VBox(20, message, buttonBox);
	    layout.setAlignment(javafx.geometry.Pos.CENTER);
	    layout.setStyle("-fx-padding: 20;");
	    layout.setPrefSize(300, 150);

	    Scene scene = new Scene(layout);
	    popupStage.setScene(scene);

	    // Center the popup relative to the owner stage
	    double popupWidth = 300;
	    double popupHeight = 150;

	    double ownerX = ownerStage.getX();
	    double ownerY = ownerStage.getY();
	    double ownerWidth = ownerStage.getWidth();
	    double ownerHeight = ownerStage.getHeight();

	    double centerX = ownerX + (ownerWidth - popupWidth) / 2;
	    double centerY = ownerY + (ownerHeight - popupHeight) / 2;

	    // Ensure popup stays inside the application window
	    centerX = Math.max(centerX, ownerX);
	    centerY = Math.max(centerY, ownerY);
	    centerX = Math.min(centerX, ownerX + ownerWidth - popupWidth);
	    centerY = Math.min(centerY, ownerY + ownerHeight - popupHeight);

	    popupStage.setX(centerX);
	    popupStage.setY(centerY);

	    popupStage.showAndWait(); // This waits for the user

	    return result[0];
	}

	public void turn(int cury,int curx,int nextx, int nexty){
		ArrayList<Player> players = game.getPlayers();
		Colour active = game.getActivePlayerColour();
		Colour next = game.getNextPlayerColour();
		for(int c=0;c<4;c++){
			if(players.get(c).getColour()==active){
				for(int i=0;i<gameview.getChildren().size();i++){
					if(gameview.getChildren().get(i) == curr){
						gameview.getChildren().remove(i);
						gameview.getChildren().remove(i);
					}
				}
				break;
			}
		}
		Label cur = new Label("This turn");
		Label aft = new Label("Next turn");
		curr.getChildren().clear();
		curr.getChildren().add(cur);
		nextr.getChildren().clear();
		nextr.getChildren().add(aft);
		curr.setStyle("-fx-background-color: green;");
		nextr.setStyle("-fx-background-color: black;");
		gameview.getChildren().add(curr);
		gameview.getChildren().add(nextr);
		curr.setAlignment(Pos.CENTER);
		nextr.setAlignment(Pos.CENTER);
		StackPane.setAlignment(curr, Pos.BOTTOM_RIGHT);
		StackPane.setAlignment(nextr, Pos.BOTTOM_LEFT);
		curr.setTranslateX(curx);
		curr.setTranslateY(cury);
		nextr.setTranslateX(nextx);
		nextr.setTranslateY(nexty);
	}
}

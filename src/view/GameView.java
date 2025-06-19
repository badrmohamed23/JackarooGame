package view;

import java.util.ArrayList;

import model.Colour;
import model.card.Card;
import model.player.Player;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import engine.Game;

public class GameView extends StackPane{
	Game game;
	BoardView boardview;
	FirePitView firepitview;
	HandView handview;
	HomeViews homeviews;
	String name;
	Button play;
	HBox curr;
	HBox nextr;
	public GameView(Game game,String name){
		draw(game,name);
	}
	public void draw(Game game,String name){
		this.game = game;
		this.getChildren().clear();
		boardview = new BoardView(game.getBoard());
		ArrayList<Card> firepit = game.getFirePit();
		if(firepit.size()==0){
			firepitview = new FirePitView(null);
		}else{
			firepitview = new FirePitView(firepit.get(firepit.size() - 1));
		}
		handview = new HandView(game.getPlayers());
		homeviews = new HomeViews(game.getPlayers());
		this.getChildren().add(homeviews);
		this.getChildren().add(handview);
		this.getChildren().add(boardview);
		this.getChildren().add(firepitview);
		StackPane.setAlignment(boardview,Pos.CENTER);
		StackPane.setAlignment(firepitview,Pos.CENTER);
		StackPane.setAlignment(handview,Pos.CENTER);
		StackPane.setAlignment(homeviews,Pos.CENTER);
		firepitview.setFitHeight(100);
		firepitview.setFitWidth(100);
		handview.setAlignment(Pos.CENTER);
		handview.setTranslateY(-80);
		this.setStyle("-fx-background-color: lightblue;");
		for(int i =0;i<4;i++){
			Colour colour = game.getPlayers().get(i).getColour();
			Circle circ = new Circle(50);
			if(colour == Colour.RED){
				circ.setFill(Color.RED);
			}else if(colour == Colour.BLUE){
				circ.setFill(Color.BLUE);
			}else if(colour == Colour.GREEN){
				circ.setFill(Color.GREEN);
			}else{
				circ.setFill(Color.YELLOW);
			}
			this.getChildren().add(circ);
			if(i==0){
				StackPane.setAlignment(circ,Pos.BOTTOM_CENTER);
				circ.setTranslateX(-120);
			}
			else if(i==1){
				StackPane.setAlignment(circ,Pos.CENTER_LEFT);
				circ.setTranslateY(-120);
			}
			else if(i==2){
				StackPane.setAlignment(circ,Pos.TOP_CENTER);
				circ.setTranslateX(-120);
			}
			else{
				StackPane.setAlignment(circ,Pos.CENTER_RIGHT);
				circ.setTranslateY(-120);
			}
		}
		this.name = name;
		Label l = new Label(name);
		this.getChildren().add(l);
		l.setStyle("-fx-background-color: yellow;");
		StackPane.setAlignment(l, Pos.BOTTOM_CENTER);
		l.setTranslateX(-280);
		l.setTranslateY(-20);
		l.setStyle("-fx-text-fill: white;");
		l.setStyle("-fx-font-size: 23px;");
		for(int i =1;i<4;i++){
			Label m = new Label("CPU " + i);
			m.setStyle("-fx-background-color:yellow;");
			this.getChildren().add(m);
			m.setStyle("-fx-text-fill: white;");
			m.setStyle("-fx-font-size: 23px;");
			if(i==1){
				StackPane.setAlignment(m,Pos.CENTER_LEFT);
				m.setTranslateY(-300);
			}
			else if(i==2){
				StackPane.setAlignment(m,Pos.TOP_CENTER);
				m.setTranslateX(-300);
			}
			else{
				StackPane.setAlignment(m,Pos.CENTER_RIGHT);
				m.setTranslateY(-300);
			}
		}
		play = new Button("Play");
		play.setDisable(true);
		play.setPrefSize(85, 85);
		this.getChildren().add(play);
		StackPane.setAlignment(play, Pos.BOTTOM_CENTER);
		play.setTranslateX(100);
	}
	public void change(){
		boardview.setboard(game.getBoard());
		ArrayList<Card> firepit = game.getFirePit();
		if(firepit.size()==0){
			firepitview.setcard(null);
		}else{
			firepitview.setcard(firepit.get(firepit.size() - 1));
		}
		handview.sethand(game.getPlayers());
		homeviews.setplayers(game.getPlayers());
	}
	public void setgame(Game game){
		this.game = game;
		this.change();
	}
	public HandView getHandview() {
		return handview;
	}
	public void enablebtn(boolean v){
			play.setDisable(!v);
	}
	public BoardView getBoardview() {
		return boardview;
	}
	
}

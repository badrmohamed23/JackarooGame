package view;

import java.util.ArrayList;

import model.card.Card;
import model.player.Player;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class HandView extends StackPane{
	ArrayList<CardView> cardviews;
	ArrayList<Player> players;
	public HandView(ArrayList<Player> players){
		draw(players);
	}
	public void draw(ArrayList<Player> players){
		this.players = players;
		this.cardviews = new ArrayList<>();
		HBox hb1 = new HBox();
		int c;
		for(c =0;c<players.get(0).getHand().size();c++){
			ArrayList<Card> hand = players.get(0).getHand();
			Card card = hand.get(c);
			CardView cardview = new CardView(card);
			cardviews.add(cardview);
			hb1.getChildren().add(cardview);
		}
		hb1.setMaxSize(70, 70);
		hb1.setMinSize(70, 70);
		this.getChildren().add(hb1);
		StackPane.setAlignment(hb1, Pos.BOTTOM_CENTER);
		hb1.setTranslateX(-150);
		for(int i =1;i<4;i++){
			HBox hb = new HBox();
			Label label = new Label("" + players.get(i).getHand().size());
			hb.getChildren().add(label);
			hb.setAlignment(Pos.CENTER);
			this.getChildren().add(hb);
			hb.setStyle("-fx-background-color: #000000");
			hb.setMaxSize(40, 40);
			hb.setMinSize(40,40);
			label.setStyle("-fx-text-fill: white;");
			if(i==1){
				StackPane.setAlignment(hb,Pos.CENTER_LEFT);
				hb.setTranslateX(-490);
				hb.setTranslateY(-140);
			}
			else if(i==2){
				StackPane.setAlignment(hb,Pos.TOP_CENTER);
				hb.setTranslateX(-220);
				hb.setTranslateY(50);
			}
			else{
				StackPane.setAlignment(hb,Pos.CENTER_RIGHT);
				hb.setTranslateX(490);
				hb.setTranslateY(-140);
			}
		}
		this.setMaxSize(900, 900);
		this.setMinSize(900, 900);
	}
	public void change (){
		this.getChildren().clear();
		HBox hb1 = new HBox();
		int c;
		for(c =0;c<players.get(0).getHand().size();c++){
			ArrayList<Card> hand = players.get(0).getHand();
			Card card = hand.get(c);
			cardviews.get(c).setcard(card);
			hb1.getChildren().add(cardviews.get(c));
		}
		System.out.println(players.get(0).getHand().size());
		hb1.setMaxSize(70, 70);
		hb1.setMinSize(70, 70);
		this.getChildren().add(hb1);
		StackPane.setAlignment(hb1, Pos.BOTTOM_CENTER);
		hb1.setTranslateX(-150);
		for(int i =1;i<4;i++){
			HBox hb = new HBox();
			Label label = new Label("" + players.get(i).getHand().size());
			hb.getChildren().add(label);
			hb.setAlignment(Pos.CENTER);
			this.getChildren().add(hb);
			hb.setStyle("-fx-background-color: #000000");
			hb.setMaxSize(40, 40);
			hb.setMinSize(40,40);
			label.setStyle("-fx-text-fill: white;");
			if(i==1){
				StackPane.setAlignment(hb,Pos.CENTER_LEFT);
				hb.setTranslateX(-490);
				hb.setTranslateY(-140);
			}
			else if(i==2){
				StackPane.setAlignment(hb,Pos.TOP_CENTER);
				hb.setTranslateX(-220);
				hb.setTranslateY(50);
			}
			else{
				StackPane.setAlignment(hb,Pos.CENTER_RIGHT);
				hb.setTranslateX(490);
				hb.setTranslateY(-140);
			}
		}
	}
	public void sethand(ArrayList<Player> players){
		this.players = players;
		this.change();
	}
	public ArrayList<CardView> getCardviews() {
		return cardviews;
	}
	public ArrayList<Player> getPlayers() {
		return players;
	}
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	
}

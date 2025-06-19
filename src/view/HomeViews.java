package view;

import java.util.ArrayList;

import model.player.Player;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;

public class HomeViews extends StackPane{
	ArrayList<HomeView> homeviews;;
	ArrayList<Player> players;
	public HomeViews(ArrayList<Player> players){
		draw(players);
	}
	public void draw(ArrayList<Player> players){
		this.players = players;
		this.homeviews = new ArrayList<>();
		for(int i =0;i<players.size();i++){
			homeviews.add(new HomeView(players.get(i)));
		}for(int i =0;i<players.size();i++){
			this.getChildren().add(homeviews.get(i));
		}
		for(int i =0;i<4;i++){
			if(i==0){
				StackPane.setAlignment(homeviews.get(i),Pos.BOTTOM_CENTER);
			}
			else if(i==1){
				StackPane.setAlignment(homeviews.get(i),Pos.CENTER_LEFT);
				homeviews.get(i).setTranslateX(10);
			}
			else if(i==2){
				StackPane.setAlignment(homeviews.get(i),Pos.TOP_CENTER);
			}
			else{
				StackPane.setAlignment(homeviews.get(i),Pos.CENTER_RIGHT);
				homeviews.get(i).setTranslateX(-10);
			}
		}
	}
	public void change(){
		this.getChildren().clear();
		for(int i =0;i<players.size();i++){
			homeviews.get(i).setplayer(players.get(i));
			this.getChildren().add(homeviews.get(i));
			if(i==0){
				StackPane.setAlignment(homeviews.get(i),Pos.BOTTOM_CENTER);
			}
			else if(i==1){
				StackPane.setAlignment(homeviews.get(i),Pos.CENTER_LEFT);
			}
			else if(i==2){
				StackPane.setAlignment(homeviews.get(i),Pos.TOP_CENTER);
			}
			else{
				StackPane.setAlignment(homeviews.get(i),Pos.CENTER_RIGHT);
			}
		}
	}
	public void setplayers(ArrayList<Player> players){
		this.players = players;
		this.change();
	}
}

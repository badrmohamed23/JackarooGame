package view;

import java.util.ArrayList;

import model.card.Card;
import model.card.standard.Standard;
import model.card.standard.Suit;
import model.card.wild.Burner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class FirePitView extends ImageView{
	Card card;
	public FirePitView(Card card){
		draw(card);
	}
	public void draw(Card card){
		this.card = card;
		String path = getpath(card);
		Image myImage = new Image(getClass().getResourceAsStream(path));
		this.setImage(myImage);
	}
	public String getpath(Card card){
		int rank = 0;
		String path = "";
		if(card == null){
			return "back.png";
		}else if(card instanceof Standard){
			Standard stan = (Standard)card;
			rank = stan.getRank();
			if(stan.getSuit().equals(Suit.CLUB)){
				path = path + "k" + rank+".png";
			}else if(stan.getSuit().equals(Suit.DIAMOND)){
				path = path + "a" + rank+".png";
			}else if(stan.getSuit().equals(Suit.HEART)){
				path = path + "s" + rank+".png";
			}else{
				path = path + "p" + rank+".png";
			}
			return path;
		}else{
			if(card instanceof Burner){
				return "burn.png";
			}else{
				return "Saver.png";
			}
		}
	}
	public void change(){
		if(card==null){
			Image myImage = new Image(getClass().getResourceAsStream("back.png"));
			this.setImage(myImage);
		}
		else{
			String path = getpath(card);
			Image myImage = new Image(getClass().getResourceAsStream(path));
			this.setImage(myImage);
		}
	}
	public void setcard(Card card){
		this.card = card;
		this.change();
	}
}

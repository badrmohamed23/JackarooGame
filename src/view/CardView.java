package view;

import model.card.Card;
import model.card.standard.Standard;
import model.card.standard.Suit;
import model.card.wild.Burner;
import model.card.wild.Wild;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CardView extends ImageView{
	Card card;
	boolean selected;
	
	public CardView(Card card){
		draw(card);
	}
	public void draw(Card card){
		this.card = card;
		String path = getpath(card);
		Image myImage = new Image(getClass().getResourceAsStream(path));
		this.setImage(myImage);
		this.setFitHeight(100);
		this.setFitWidth(100);
	}
	public String getpath(Card card){
		int rank = 0;
		String path = "";
		if(card instanceof Standard){
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
		}else if(card==null){
			return "back.png";
		}
		else{
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
			this.setFitHeight(100);
			this.setFitWidth(100);
		}
		else{
			String path = getpath(card);
			Image myImage = new Image(getClass().getResourceAsStream(path));
			this.setImage(myImage);
			this.setFitHeight(100);
			this.setFitWidth(100);
		}
	}
	public void setcard(Card card){
		this.card = card;
		this.change();
	}
	public Card getcard(){
		return card; 
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
}

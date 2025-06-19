package view;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.Colour;
import model.player.Marble;

public class MarbleView extends StackPane{
	Marble marble;
	Circle circ;
	public MarbleView(Marble marble){
		this.marble = marble;
		this.draw();
	}
	public void draw(){
		this.circ = new Circle();
		if(marble != null){
			circ.setRadius(10);
			if(marble.getColour() == Colour.RED){
				circ.setFill(Color.RED);
			}else if(marble.getColour() == Colour.BLUE){
				circ.setFill(Color.BLUE);
			}else if(marble.getColour() == Colour.GREEN){
				circ.setFill(Color.GREEN);
			}else{
				circ.setFill(Color.YELLOW);
			}
			this.getChildren().add(circ);
		}else{
			circ.setOpacity(0);
			this.getChildren().add(circ);
		}
	}
	public void change(){
		if(marble == null){
			circ.setOpacity(0);
		}
		else{
			circ.setOpacity(1);
			if(marble.getColour() == Colour.RED){
				circ.setFill(Color.RED);
			}else if(marble.getColour() == Colour.BLUE){
				circ.setFill(Color.BLUE);
			}else if(marble.getColour() == Colour.GREEN){
				circ.setFill(Color.GREEN);
			}else{
				circ.setFill(Color.YELLOW);
			}
			circ.setRadius(10);
		}
	}
	public Marble getMarble() {
		return marble;
	}
	public void setMarble(Marble marble) {
		this.marble = marble;
		this.change();
	}
	public Circle getCirc() {
		return circ;
	}
	public void setCirc(Circle circ) {
		this.circ = circ;
	}
		
}

package view;

import engine.board.Cell;
import engine.board.CellType;
import model.player.Marble;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CellView extends StackPane{
	Circle circ;
	Cell cell;
	MarbleView marbleview;
	boolean selected;
	public CellView(Cell cell){
		this.cell = cell;
		draw();
	}
	public void draw(){
		this.marbleview = new MarbleView(cell.getMarble());
		this.circ = new Circle();
		circ.setRadius(10);
		circ.setFill(Color.BLACK);
		this.getChildren().addAll(circ,marbleview);
		selected = false;
	}
	public void change(){
		marbleview.setMarble(cell.getMarble());
	}
	public void setcell (Cell cell){
		this.cell = cell;
		this.change();
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public Cell getCell() {
		return cell;
	}
	public MarbleView getMarbleview() {
		return marbleview;
	}
	public void setMarbleview(MarbleView marbleview) {
		this.marbleview = marbleview;
	}
	
}

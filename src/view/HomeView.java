package view;

import java.util.ArrayList;

import engine.board.Cell;
import engine.board.CellType;
import model.player.Marble;
import model.player.Player;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class HomeView extends HBox{
	ArrayList<CellView> cellviews;
	ArrayList<Cell> cells;
	Player player;
	public HomeView(Player player){
		draw(player);
	}
	public void draw(Player player){
		this.player = player;
		this.cells = new ArrayList<>();
		this.cellviews = new ArrayList<>();
		ArrayList<Marble> marbles = player.getMarbles();
		for(int i =0;i<4;i++){
			cells.add(new Cell(CellType.ENTRY));
		}
		int i;
		for(i =0;i<marbles.size();i++){
			cells.get(i).setMarble(marbles.get(i));
			cellviews.add(new CellView(cells.get(i)));
			this.getChildren().add(cellviews.get(i));
		}
		while(i<4){
			cellviews.add(new CellView(cells.get(i)));
			this.getChildren().add(cellviews.get(i));
			i++;
		}
		this.setAlignment(Pos.CENTER);
		this.setMaxSize(100, 100);
		this.setMinSize(100, 100);
	}
	public void change(){
		this.getChildren().clear();
		ArrayList<Marble> marbles = player.getMarbles();
		int i;
		for(i=0;i<marbles.size();i++){
			cells.get(i).setMarble(marbles.get(i));
			cellviews.get(i).setcell(cells.get(i));
			this.getChildren().add(cellviews.get(i));
		}
		while(i<4){
			cells.get(i).setMarble(null);
			cellviews.get(i).setcell(cells.get(i));
			this.getChildren().add(cellviews.get(i));
			i++;
		}
		this.setAlignment(Pos.CENTER);
		this.setMaxSize(100, 100);
		this.setMinSize(100, 100);
	}
	public void setplayer(Player player){
		this.player = player;
		this.change();
	}
}

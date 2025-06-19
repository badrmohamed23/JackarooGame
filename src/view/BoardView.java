package view;

import java.util.ArrayList;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import engine.board.Board;
import engine.board.Cell;
import engine.board.CellType;

public class BoardView extends GridPane{
	Board board;
	ArrayList<CellView> trackcells;
	ArrayList<CellView>[] safezones;
	public BoardView(Board board){
		this.board = board;
		draw();
	}
	public void draw(){
		this.setStyle("-fx-background-color: #f5deb3;");
		this.setPrefSize(50, 50);
		ArrayList<Cell> cells = board.getTrack();
		trackcells = new ArrayList<CellView>();
		safezones = new ArrayList[4];
		for(int i =0;i<4;i++){
			safezones[i] = new ArrayList<CellView>();
		}
		for(int i =0;i<100;i++){
			Cell cell = cells.get(i);
			if(cell.getCellType() == CellType.ENTRY){
				if(i==98){
					ArrayList<Cell> cells1 = board.getSafeZones().get(0).getCells();
					for(int j =0;j<4;j++){
						Cell cell1 = cells1.get(j);
						CellView cellview = new CellView(cell1);
						safezones[0].add(cellview);
					}
				}else if(i==23){
					ArrayList<Cell> cells1 = board.getSafeZones().get(1).getCells();
					for(int j =0;j<4;j++){
						Cell cell1 = cells1.get(j);
						CellView cellview = new CellView(cell1);
						safezones[1].add(cellview);
					}
				}else if(i==48){
					ArrayList<Cell> cells1 = board.getSafeZones().get(2).getCells();
					for(int j =0;j<4;j++){
						Cell cell1 = cells1.get(j);
						CellView cellview = new CellView(cell1);
						safezones[2].add(cellview);
					}
				}else{
					ArrayList<Cell> cells1 = board.getSafeZones().get(3).getCells();
					for(int j =0;j<4;j++){
						Cell cell1 = cells1.get(j);
						CellView cellview = new CellView(cell1);
						safezones[3].add(cellview);
					}
				}
			}
			MarbleView m = new MarbleView(cell.getMarble());
			CellView cellview = new CellView(cell);
			trackcells.add(cellview);
		}
		int c = 0;
		int curi = 50;
		int curj = 50;
		int curidx = 0;
		for(int i =0;i<25;i++){
			CellView cellview = trackcells.get(curidx);
			curidx++;
			if(i==23){
				int j = curj+1;
				for(int k =0;k<4;k++){
					CellView cellview1 = safezones[1].get(k);
					this.add(cellview1,j,curi);
					j++;
				}
			}
			this.add(cellview, curj, curi);
			curi--;
		}
		for(int i =0;i<25;i++){
			CellView cellview = trackcells.get(curidx);
			curidx++;
			if(i==23){
				int i1 = curi+1;
				for(int k =0;k<4;k++){
					CellView cellview1 = safezones[2].get(k);
					this.add(cellview1,curj,i1);
					i1++;
				}
			}
			this.add(cellview, curj, curi);
			curj++;
		}
		for(int i =0;i<25;i++){
			CellView cellview = trackcells.get(curidx);
			curidx++;
			if(i==23){
				int i1 = curj-1;
				for(int k =0;k<4;k++){
					CellView cellview1 = safezones[3].get(k);
					this.add(cellview1,i1,curi);
					i1--;
				}
			}
			this.add(cellview, curj, curi);
			curi++;
		}
		for(int i =0;i<25;i++){
			CellView cellview = trackcells.get(curidx);
			curidx++;
			if(i==23){
				int j = curi-1;
				for(int k =0;k<4;k++){
					CellView cellview1 = safezones[0].get(k);
					this.add(cellview1,curj,j);
					j--;
				}
			}
			this.add(cellview, curj, curi);
			curj--;
		}
		this.setMaxSize(525, 525);
		this.setMinSize(525, 525);
	}
	public void change(){
		for(int i =0;i<100;i++){
			Cell c = board.getTrack().get(i);
			trackcells.get(i).setcell(c);
		}
		for(int i =0;i<4;i++){
			ArrayList<CellView> c = safezones[i];
			for(int j =0;j<4;j++){
				Cell c1 = board.getSafeZones().get(i).getCells().get(j);
				c.get(j).setcell(c1);
			}
		}
	}
	public void setboard (Board board){
		this.board = board;
		this.change();
	}
	public ArrayList<CellView> getTrackcells() {
		return trackcells;
	}
	public void setTrackcells(ArrayList<CellView> trackcells) {
		this.trackcells = trackcells;
	}
	public ArrayList<CellView>[] getSafezones() {
		return safezones;
	}
	public void setSafezones(ArrayList<CellView>[] safezones) {
		this.safezones = safezones;
	}
	
}

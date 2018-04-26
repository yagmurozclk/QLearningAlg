import java.util.ArrayList;
import java.util.List;

public class Node {
	
	private int id;
	private boolean isDeparture=false;
	private boolean isArrival=false;
	private int width,height;
	private int x,y;
	private int i,j;
	private Node rightNeighbour;
	private Node leftNeighbour;
	private Node upNeighbour;
	private Node downNeighbour;
	private boolean isSelected=false;
	
	
	Node(int i,int j,int x,int y,int width,int height,int id){
		this.x=x;
		this.y=y;
		this.i=i;
		this.j=j;
		this.width=width;
		this.height=height;
		this.id=id;
	}
	
	
	
	
	public int getI() {
		return i;
	}




	public void setI(int i) {
		this.i = i;
	}




	public int getJ() {
		return j;
	}




	public void setJ(int j) {
		this.j = j;
	}




	public boolean isSelected() {
		return isSelected;
	}




	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}




	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public boolean isDeparture() {
		return isDeparture;
	}



	public void setDeparture(boolean isDeparture) {
		this.isDeparture = isDeparture;
	}



	public boolean isArrival() {
		return isArrival;
	}



	public void setArrival(boolean isArrival) {
		this.isArrival = isArrival;
	}



	public int getWidth() {
		return width;
	}



	public void setWidth(int width) {
		this.width = width;
	}



	public int getHeight() {
		return height;
	}



	public void setHeight(int height) {
		this.height = height;
	}



	public Node getRightNeighbour() {
		return rightNeighbour;
	}


	public void setRightNeighbour(Node rightNeighbour) {
		this.rightNeighbour = rightNeighbour;
	}


	public Node getLeftNeighbour() {
		return leftNeighbour;
	}


	public void setLeftNeighbour(Node leftNeighbour) {
		this.leftNeighbour = leftNeighbour;
	}


	public Node getUpNeighbour() {
		return upNeighbour;
	}


	public void setUpNeighbour(Node upNeighbour) {
		this.upNeighbour = upNeighbour;
	}


	public Node getDownNeighbour() {
		return downNeighbour;
	}


	public void setDownNeighbour(Node downNeighbour) {
		this.downNeighbour = downNeighbour;
	}


	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}
	
	public String toString() {
        return String.valueOf(this.getId());
    }
	
	
	
	
}

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MazeCanvas extends Canvas {

	  private int width, height;

	  int rows;
	  int cols;
	  int nodeWidth,nodeHeight;
	  int margin=10;
	  
	  Node[][] nodeMatrix;
	  List<Node> path = new ArrayList<Node>();

	  
	  QLearning qlearning;
	  
	 
	  File inputFile;
	  boolean isCleared=false;
	  boolean isDrawPath=false;

	  MazeCanvas(int width, int height, int rows, int cols) {
		  this.width = width;
		  this.height = height;
		  //setSize(width,height);
		  setBounds(10, 10, width, height);
		  this.rows = rows;
		  this.cols = cols;
		  this.nodeWidth=(width-margin)/rows;
		  this.nodeHeight=(height-margin)/cols;
		  
		  this.nodeMatrix = new Node[rows][cols];
		  
		  this.nodeMatrix = fillMatrix(rows,cols);
		 

	  }
	  
	  
	  
	  
	  public boolean isDrawPath() {
		return isDrawPath;
	}




	public void setDrawPath(boolean isDrawPath) {
		this.isDrawPath = isDrawPath;
	}




	public int getRows() {
		return rows;
	}




	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}




	public Node[][] getNodeMatrix() {
		return nodeMatrix;
	}




	public void setNodeMatrix(Node[][] nodeMatrix) {
		this.nodeMatrix = nodeMatrix;
	}






	public Node[] getNodes(){
		Node[] nodes = new Node[rows*cols];
		int count=0;
		for(int i=0;i<rows;i++){			  
			  for(int j=0;j<cols;j++){
				  
				  nodes[count]=nodeMatrix[i][j];  
				  count++;
			  }			  
		  }
		
		return nodes;
	}


	MazeCanvas(int width, int height,File inputFile) {
		  
		  this.inputFile = inputFile;
		  this.height = height;
		  //setSize(width,height);	
		  setBounds(10, 10, width, height);
		  this.rows=this.cols= (int) Math.sqrt(getFileRowCount(inputFile));
		  this.nodeWidth=(width-margin)/rows;
		  this.nodeHeight=(height-margin)/cols;
		  this.nodeMatrix = new Node[rows][cols];
		  this.nodeMatrix = fillMatrix(rows,cols);
		  setNeighbourhood(inputFile);
		  repaint();
		  
		  qlearning = new QLearning(this);

		  //addMouseListener(this);
	  }
	
	public void fillRAndQMatrix() throws IOException{
		path.clear();
		qlearning.initializeMatrix();
		qlearning.fillRMatrix(this.getNodeMatrix(),this.getRows(),this.getCols());
		qlearning.printMatrix(qlearning.getR(), this.getRows() * this.getCols(), this.getRows() * this.getCols());
		qlearning.qLearning();
		qlearning.printMatrix(qlearning.getQ(), this.getRows() * this.getCols(), this.getRows() * this.getCols());
		
	}
	
	public void fillQMatrix(){
		//qlearning.fillQMatrix(this.getNodeMatrix(),this.getRows(),this.getCols(),this.endNode);
		//qlearning.printMatrix(qlearning.getQ(), this.getRows() * this.getCols(), this.getRows() * this.getCols());
		
	}
	  
	  private int getFileRowCount(File file){
		  try {
			    Scanner sc=new Scanner(file);
			    int i = 1;
		        while (sc.hasNextLine()) {		            
		            sc.nextLine();		           
		            i++;
		        }
		        sc.close();
		        
		        return i;
		        
		  } 
		  catch (IOException e) {
			    System.err.println(e);
		  }
		  
		  return 0;
	  }
	  
	  public Node getNode(int id){
		  
		  for(int i=0;i<rows;i++){			  
			  for(int j=0;j<cols;j++){
				  if(nodeMatrix[i][j].getId()==id){
					return nodeMatrix[i][j];  
				  }
			  }			  
		  }
		  
		  return null;
	  }
	  
	  private void setNeighbourhood(Node node,Node neighbourNode){
		    boolean isRight=false;
			boolean isLeft=false;
			boolean isDown=false;
			boolean isUp=false;
			
			if(node!=null && node.getJ()+1<this.cols && nodeMatrix[node.getI()][node.getJ()+1].getId()==neighbourNode.getId()){
				
				isRight=true;
			}
			if(node!=null && node.getJ()-1>=0 && nodeMatrix[node.getI()][node.getJ()-1].getId()==neighbourNode.getId()){
					
				isLeft=true;
			}
			if(node!=null && node.getI()+1<this.rows && nodeMatrix[node.getI()+1][node.getJ()].getId()==neighbourNode.getId()){
				
				isDown=true;
			}
			if(node!=null && node.getI()-1>=0 && nodeMatrix[node.getI()-1][node.getJ()].getId()==neighbourNode.getId()){
				
				isUp=true;
			}
			
			if(node!=null && neighbourNode!=null){
				if(isRight){
					node.setRightNeighbour(neighbourNode);					
				}
				if(isLeft){
					node.setLeftNeighbour(neighbourNode);
					
				}
				if(isUp){
					node.setUpNeighbour(neighbourNode);
					
				}
				if(isDown){
					node.setDownNeighbour(neighbourNode);
					
				}
			}
			repaint();
	  }
	  
	  public void setNeighbourhood(File file){
		  try {
			    Scanner sc=new Scanner(file);
			    int i = 0;
		        while (sc.hasNextLine()) {
		            Node node = getNode(i);
		            String line = sc.nextLine();
		            String[] lineVariables = line.split(",");
		            for(String id : lineVariables){
		            	setNeighbourhood(node,getNode(Integer.valueOf(id)));
		            }
		            i++;
		        }
		        sc.close();
		        
			} catch (IOException e) {
			    System.err.println(e);
			}
	  }
	  
	  
	  private Node[][] fillMatrix(int rows,int cols){		  
		  Node[][] nodeMatrix = new Node[rows][cols];
		  int x=0,y=0;
		  int id=0;
		  for(int i=0;i<rows;i++){
			  x=0;
			  for(int j=0;j<cols;j++){
				  nodeMatrix[i][j]=createNode(i,j,x,y,this.nodeWidth,this.nodeHeight,id);
				  id++;
				  x=x+this.nodeWidth;
			  }
			  y=y+this.nodeHeight;
		  }
		  
		  
		  return nodeMatrix;
	  }
	  
	  
	  
	  
	  private Node createNode(int i,int j,int x,int y,int width,int height,int id){
		  
		  Node node = new Node(i,j,x,y,width,height,id);
		  
		  return node;
	  }
	  
	  private void drawNode(Node node,Graphics g){
		  g.setColor(Color.BLUE);
		  g.drawString(String.valueOf(node.getId()), node.getX() + node.getWidth()/2, node.getY() + node.getHeight()/2);
		  
		  for(int j=0;j<15;j++){
			  //g.setColor(Color.RED);		  
			  if(node.getLeftNeighbour()==null){
				  g.drawLine(node.getX() +j , node.getY()+j, node.getX()+j, node.getY() + node.getHeight()+j);
				  
			  }
			 
			  //g.setColor(Color.BLACK);
			  if(node.getRightNeighbour()==null){
				  g.drawLine(node.getX()+node.getWidth()+j, node.getY()+j, node.getX() + node.getWidth()+j, node.getY()+ node.getHeight()+j);
			  }
			  
			  //g.setColor(Color.GREEN);
			  if(node.getUpNeighbour()==null){
				  g.drawLine(node.getX()+j, node.getY()+j, node.getX() + node.getWidth()+j, node.getY()+j);
			  }
			  
			  //g.setColor(Color.BLUE);
			  if(node.getDownNeighbour()==null){
				  g.drawLine(node.getX()+j, node.getY() + node.getHeight()+j, node.getX() + node.getWidth()+j , node.getY() + node.getHeight()+j);
			  }
		  }
		  
		  
		  
	  }

	  public void paint(Graphics g) {
	    /*
		  if(isCleared()){
			  g.clearRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
			  isCleared=false;
		  }*/
		  for(int i=0;i<rows;i++){			 
			  for(int j=0;j<cols;j++){
				  drawNode(this.nodeMatrix[i][j],g);
			  }			 
		  }
		  
		  if(isDrawPath){
			  drawShortestPath(g);
		  }
		  
		  
	  
	  }
	  
	public void drawShortestPath(Graphics g){
		g.setColor(Color.RED);
		for(int j=0;j<25;j++){
		for (int i = 0; i < path.size() - 1; i++) {
			g.drawLine((path.get(i).getX() + path.get(i).getX() + path.get(i)
					.getWidth() + j) / 2,
					(path.get(i).getY() + path.get(i).getY() + path.get(i)
							.getHeight() + j) / 2, 
							(path.get(i + 1).getX()
							+ path.get(i + 1).getX() + path.get(i + 1)
							.getWidth() + j) / 2, 
							(path.get(i + 1).getY()
							+ path.get(i + 1).getY() + path.get(i + 1)
							.getHeight() + j) / 2
					);

		}
		}
		g.setColor(Color.BLUE);
	}
	  
	private Node setNode(int x,int y){
		
		for(int i=0;i<rows;i++){			 
			  for(int j=0;j<cols;j++){
				  if(x>nodeMatrix[i][j].getX() && 
					x<nodeMatrix[i][j].getX()+nodeMatrix[i][j].getWidth() &&
					y>nodeMatrix[i][j].getY() && 
					y<nodeMatrix[i][j].getY()+nodeMatrix[i][j].getHeight()
				    ){
					  
					  return nodeMatrix[i][j];
				  }
			  }			 
		  }
		
		return null;
	}

		
	public boolean isCleared() {
		return isCleared;
	}




	public void setCleared(boolean isCleared) {
		this.isCleared = isCleared;
	}

	



	public QLearning getQlearning() {
		return qlearning;
	}
	  
	
	  
	  
}

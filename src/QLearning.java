import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class QLearning {

	private Integer[][] R;/*
							 * = { {-1,0,-1,0,-1,-1}, {0,-1,100,-1,0,-1},
							 * {-1,-1,0,-1,-1,-1}, {0,-1,-1,-1,0,-1},
							 * {-1,0,-1,0,-1,0}, {-1,-1,100,-1,0,-1}
							 * 
							 * };
							 */

	private Double[][] Q;/*
							 * = { {0.0, 0.0, 0.0, 0.0, 0.0, 0.0}, {0.0, 0.0,
							 * 0.0, 0.0, 0.0, 0.0}, {0.0, 0.0, 0.0, 0.0, 0.0,
							 * 0.0}, {0.0, 0.0, 0.0, 0.0, 0.0, 0.0}, {0.0, 0.0,
							 * 0.0, 0.0, 0.0, 0.0}, {0.0, 0.0, 0.0, 0.0, 0.0,
							 * 0.0}
							 * 
							 * };
							 */

	private int rowNumber;
	private int columnNumber;

	private final double multiplier = 0.8;

	private Node endNode;
	private Node startNode;
	private Integer iterationNumber;
	private MazeCanvas mazeCanvas;

	QLearning(MazeCanvas mazeCanvas) {

		this.rowNumber = mazeCanvas.getRows() * mazeCanvas.getCols();
		this.columnNumber = mazeCanvas.getRows() * mazeCanvas.getCols();

		R = new Integer[rowNumber][columnNumber];
		Q = new Double[rowNumber][columnNumber];
		initializeMatrix();

		this.mazeCanvas = mazeCanvas;
		// printMatrix(R,rowNumber,columnNumber);

	}

	public void initializeMatrix() {
		for (int i = 0; i < this.rowNumber; i++) {
			for (int j = 0; j < this.columnNumber; j++) {
				R[i][j] = -1;
				Q[i][j] = 0.0;
			}
		}
		
		

	}

	public void printMatrix(Integer[][] matrix, int rowNumber, int columnNumber) throws IOException {
		File file = new File("outR.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter("outR.txt");
        PrintWriter pw =new PrintWriter(fw);
		for (int i = 0; i < rowNumber; i++) {
			for (int j = 0; j < columnNumber; j++) {
				

				System.out.printf("%7d", matrix[i][j]);
				pw.printf("%4d",matrix[i][j]);
			}
			System.out.println();
			pw.println();
		}

		System.out.println();
		pw.println();
		pw.close();
	}

	public void printMatrix(Double[][] matrix, int rowNumber, int columnNumber) throws IOException  {
		File file = new File("outQ.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter("outQ.txt");
        PrintWriter pw =new PrintWriter(fw);
		for (int i = 0; i < rowNumber; i++) {
			for (int j = 0; j < columnNumber; j++) {

				System.out.printf("%7.02f", matrix[i][j]);
				pw.printf("%7.02f",matrix[i][j]);
			}
			System.out.println();
			pw.println();
			
		}

		System.out.println();
		pw.println();
		pw.close();
       
		 
	}

	public void fillRMatrix(Node[][] nodeMatrix, int rowNumber, int columnNumber) {

		for (int i = 0; i < rowNumber; i++) {
			for (int j = 0; j < columnNumber; j++) {

				if (nodeMatrix[i][j].getId() == endNode.getId()) {
					R[nodeMatrix[i][j].getId()][nodeMatrix[i][j].getId()] = 100;
				}

				if (nodeMatrix[i][j].getLeftNeighbour() != null) {
					if (nodeMatrix[i][j].getLeftNeighbour().getId() == endNode.getId()) {
						R[nodeMatrix[i][j].getId()][nodeMatrix[i][j].getLeftNeighbour().getId()] = 100;
					} else {
						R[nodeMatrix[i][j].getId()][nodeMatrix[i][j].getLeftNeighbour().getId()] = 0;
					}
				}
				if (nodeMatrix[i][j].getRightNeighbour() != null) {
					if (nodeMatrix[i][j].getRightNeighbour().getId() == endNode.getId()) {
						R[nodeMatrix[i][j].getId()][nodeMatrix[i][j].getRightNeighbour().getId()] = 100;
					} else {
						R[nodeMatrix[i][j].getId()][nodeMatrix[i][j].getRightNeighbour().getId()] = 0;
					}
				}
				if (nodeMatrix[i][j].getDownNeighbour() != null) {
					if (nodeMatrix[i][j].getDownNeighbour().getId() == endNode.getId()) {
						R[nodeMatrix[i][j].getId()][nodeMatrix[i][j].getDownNeighbour().getId()] = 100;
					} else {
						R[nodeMatrix[i][j].getId()][nodeMatrix[i][j].getDownNeighbour().getId()] = 0;
					}
				}
				if (nodeMatrix[i][j].getUpNeighbour() != null) {
					if (nodeMatrix[i][j].getUpNeighbour().getId() == endNode.getId()) {
						R[nodeMatrix[i][j].getId()][nodeMatrix[i][j].getUpNeighbour().getId()] = 100;
					} else {
						R[nodeMatrix[i][j].getId()][nodeMatrix[i][j].getUpNeighbour().getId()] = 0;
					}
				}

			}
			System.out.println();
		}

	}
	

	public Integer[][] getR() {
		return R;
	}

	public Double[][] getQ() {
		return Q;
	}

	public Node getEndNode() {
		return endNode;
	}

	public void setEndNode(Node endNode) {
		this.endNode = endNode;
	}

	public Node getStartNode() {
		return startNode;
	}

	public void setStartNode(Node startNode) {
		this.startNode = startNode;
	}

	public Integer getIterationNumber() {
		return iterationNumber;
	}

	public void setIterationNumber(Integer iterationNumber) {
		this.iterationNumber = iterationNumber;
	}

	
	public void qLearning() throws IOException{
		
		Random rnd = new Random();
		
		for(int y = 0; y < iterationNumber; ++y){ // iterasyon dÃ¶ngÃ¼sÃ¼
			
			int durum = rnd.nextInt(rowNumber); // her iterasyon basÄ±nda durum rastgele set ediliyor
			
			do{ // durum hedefe eÅŸit olana kadar dÃ¶nÃ¼yor (yani 1 iterasyonu tamamlÄ±yor)
				
				Vector<Integer> vectorChoice = new Vector<Integer>();
				for(int x = 0; x < rowNumber; ++x){	// R matrisinde durum satÄ±rÄ±ndaki sÃ¼tunlarÄ± geziyoruz.	
					if(R[durum][x] != -1) // komsusu olan odalarÄ±(aksiyonlarÄ±) vectore ekledik
						vectorChoice.add(x);
				}
				
				int aksiyon = randomChoice(vectorChoice);
				
				//System.out.println("durum : "+durum+" - aksiyon: "+aksiyon);
				
				Vector<Double> vectorMax = new Vector<Double>();
				for(int x = 0; x < rowNumber; ++x){
					if(R[aksiyon][x] != -1)	
						vectorMax.add(Q[aksiyon][x]);
				}
				
				
				double qMax = vectorMax(aksiyon, vectorMax);
				//System.out.println("\n qMax: " + qMax);
			
				// Q(durum,aksiyon) = R(durum,aksiyon)+Î³Ã—Max{Q(sonrakidurumlar,tumaksiyonlar)}
				Q[durum][aksiyon] = (double)R[durum][aksiyon] + multiplier * qMax ;
				//printMatrix(Q, rowNumber, columnNumber);
				durum = aksiyon;
		    }while (durum != endNode.getId());
			//System.out.println(y + ". Ä°TERASYON BÄ°TMÄ°Å�TÄ°R..........!!!!!!!!!!!"); 
		}
		
		Node node;
		Node startNode=this.startNode;
		do{
			this.mazeCanvas.path.add(startNode);
			node = createPath(startNode);
			startNode = node;
			
		}while(startNode.getId()!=endNode.getId());
		this.mazeCanvas.path.add(endNode);
		//System.out.println("Yol BulunmuÅŸtur"); 
		
		File file = new File("outPath.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter("outPath.txt");
        PrintWriter pw =new PrintWriter(fw);
        pw.printf("printing path : \n");
        
        for(int k = 0; k < this.mazeCanvas.path.size(); ++k){
        	pw.printf(this.mazeCanvas.path.get(k) + " ");
        }
		
        pw.close();
	}
	
	private Node createPath(Node node){
		
		int nodeNumber=node.getId();
		int nextI=0;
		Double gratest=Q[nodeNumber][0];
		for(int i=1;i<columnNumber;i++){
			if(Q[nodeNumber][i]>gratest){
				gratest=Q[nodeNumber][i];
				nextI=i;
			}
		}
		
		return this.mazeCanvas.getNode(nextI);
		
	}
	
	
	private double vectorMax(int aksiyon, Vector<Double> vec){ // Max{Q(sonrakidurumlar,tumaksiyonlar)} bunu yapÄ±yor
		double qMax = vec.get(0);
		
		if(vec.size() == 1)
			return qMax;
		
		for(int k = 1; k < vec.size(); ++k)
			if(qMax < vec.get(k))
				qMax = vec.get(k);
		
		return qMax;
	}
	
	private int randomChoice(Vector<Integer> vec){ // random olarak seÃ§ilecek aksiyonu buluyor 
		Random rnd = new Random();
		int size = vec.size();
		int index = rnd.nextInt(size);
		return vec.get(index);
	}
	
}

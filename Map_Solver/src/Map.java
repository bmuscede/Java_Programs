import java.io.*;
import java.util.Iterator;
import java.util.Stack;

public class Map {
	private int mapWidth;
	private int mapLength;
	private int mapToll;
	private Graph roadMap;
	private int startVert;
	private int endVert;
	private Stack<Node> path;

	private final String disc = "discovery";
	private final String back = "back";
	
	public Map(String inputFile) throws MapException{
		BufferedReader readFile = null;
		
		try {
			readFile = new BufferedReader(new FileReader(inputFile));
		} catch (FileNotFoundException e) {
			throw new MapException("File does not exist.");
		}
		
		try {
			readFile.readLine();
			mapWidth = Integer.parseInt(readFile.readLine());
			mapLength = Integer.parseInt(readFile.readLine());
			mapToll = Integer.parseInt(readFile.readLine());
		} catch (NumberFormatException e) {
			throw new MapException("Error reading file.");
		} catch (IOException e) {
			throw new MapException("Error reading file.");
		}
		
		//Gets the number of nodes that this program uses.
		roadMap = new Graph(mapLength * mapWidth);
		
		String currentLine;
		int evenLine = 0;
		boolean reading = true;
		
		for (int i = 0; reading; i++){
			try {
				currentLine = readFile.readLine();
			} catch (IOException e) {
				throw new MapException("Error reading file.");
			}
			
			if (currentLine == null){
				reading = false;
				continue;
			}
			
			if (i % 2 == 1){
				evenLine++;
			}
			
			loadLine(currentLine, evenLine, i);
		}
	}
	
	public Iterator<Node> findPath() {
		//Creates a new stack to hold the path for the car.
		Node start = null;
		Node end = null;
		path = new Stack<Node>();
		Iterator<Node> mapRoute = null;
		
		try {
			//Gets the nodes of the start and end vertices.
			start = roadMap.getNode(startVert);
			end = roadMap.getNode(endVert);
			
			//Calls the recursive DFS function.
			mapRoute = searchDFS(roadMap, start, end, mapToll);
		} catch (GraphException e) {
			System.out.println(e);
		}
		
		//Returns the iterator of the stack.
		return mapRoute;
	}
	
	public Graph getGraph() throws MapException{
		if (roadMap == null) throw new MapException("Map not defined.");
		return roadMap;
	}
	
	private void loadLine(String line, int lnNumber, int iVal){
		if (iVal % 2 == 1){
			oddAdd(line, lnNumber);
		} else {
			evenAdd(line, lnNumber);
		}
	}
	
	private void oddAdd(String line, int lnNumber){
		//Indicates the node position.
		int pos = lnNumber * mapWidth;
		String type;
		
		//Goes through all of the nodes on this level and looks for links.
		for (int i = 0; i < line.length() - 1; i++){
			if ((i % 2) == 1){
				pos++;
				continue;
			}
			
			//Connects the nodes together.
			if (line.charAt(i) == '|'){
				type = "free";
			} else if (line.charAt(i) == 'v'){
				type = "toll";
			} else {
				continue;
			}
			
			//Adds the edge into the graph.
			try {
				Node temp1 = roadMap.getNode(pos - mapWidth);
				Node temp2 = roadMap.getNode(pos);
				roadMap.insertEdge(temp1, temp2, type);
			} catch (GraphException e) {
				System.out.println(e);
			}
		}
	}
	
	private void evenAdd(String line, int lnNumber){
		//Indicates the node position.
		int pos = lnNumber * mapWidth;
		String type;
		
		//Goes through all of the nodes on this level and looks for links.
		for (int i = 0; i < line.length() - 1; i++){
			if ((i % 2) == 1){
				pos++;
				continue;
			}
			//Checks if there is a starting or ending point here.
			if (line.charAt(i) == 's'){
				startVert = pos;
			} else if (line.charAt(i) == 'e'){
				endVert = pos;
			}
			
			//Connects the nodes together.
			if (line.charAt(i + 1) == '-'){
				type = "free";
			} else if (line.charAt(i + 1) == 'h'){
				type = "toll";
			} else {
				continue;
			}
			
			//Adds the edge into the graph.
			try {
				Node temp1 = roadMap.getNode(pos);
				Node temp2 = roadMap.getNode(pos + 1);
				roadMap.insertEdge(temp1, temp2, type);
			} catch (GraphException e) {
				System.out.println(e);
			}
		}
	}
	
	private Iterator<Node> searchDFS(Graph map, Node initial, Node end, int toll) 
			throws GraphException{
		//Marks the initial node.
		initial.setMark(true);
		path.push(initial);
		
		//Sees if the destination has been found.
		if (initial.equals(end)){
			return path.iterator();
		}
		
		Edge temp;
		Node newNode;
		
		//Gets all the incident edges off the node.
		Iterator<Edge> incidentEdge = map.incidentEdges(initial);
		while (incidentEdge.hasNext() == true){
			temp = incidentEdge.next();
			
			if (temp.firstEndpoint().equals(initial)){
				newNode = temp.secondEndpoint();
			} else {
				newNode = temp.firstEndpoint();
			}
			
			//Sees if that node has been visited before.
			if (newNode.getMark() == false){
				//Now checks the edge that it's about to take.
				if ((temp.getType() == "toll") && (toll > 0)){
					toll--;
				} else if ((temp.getType() == "toll") && (toll <= 0)){
					//There is a toll and we can't pay for it. Go to the next edge.
					continue;
				}
				
				temp.setLabel(disc);
				
				Iterator<Node> result = searchDFS(map, newNode, end, toll);
				if (result != null){
					return path.iterator();
				} else if (temp.getType() == "toll"){
					toll++;
				}
			} else {
				temp.setLabel(back);
			}
		}
		
		path.pop();
		return null;
	}
}

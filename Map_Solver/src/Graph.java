import java.util.Iterator;
import java.util.LinkedList;

public class Graph implements GraphADT {
	private int size;
	private int edgeElm;
	private Node[] nodes;
	private Edge[] edges;
	
	public Graph(int n){
		size = n;
		edgeElm = 0;
		nodes = new Node[size];
		edges = new Edge[size];
		
		for (int i = 0; i < size; i++){
			nodes[i] = new Node(i);
		}
	}
	
	public void insertEdge(Node nodeu, Node nodev, String type)
			throws GraphException {
		if (nodeExists(nodeu) == false) throw new GraphException("Node Does Not Exist.");
		if (nodeExists(nodev) == false) throw new GraphException("Node Does Not Exist.");
		
		Edge newEdge = new Edge(nodeu, nodev, type);
		if (edgeElm >= edges.length){
			expandEdge();
		}
		edges[edgeElm] = newEdge;
		edgeElm++;
	}

	public Node getNode(int name) throws GraphException {
		Node temp = findNode(name);
		if (temp == null) throw new GraphException("Node Does Not Exist.");
		
		return temp;
	}

	public Iterator<Edge> incidentEdges(Node u) throws GraphException {
		if (nodeExists(u) == false) throw new GraphException("Node Does Not Exist.");
		
		LinkedList<Edge> incidentEdge = new LinkedList<Edge>();
		for (int i = 0; i < edgeElm; i++){
			if ((u.equals(edges[i].firstEndpoint())) ||
					(u.equals(edges[i].secondEndpoint()))){
				incidentEdge.add(edges[i]);
			}
		}
		
		Iterator<Edge> incident = incidentEdge.iterator();
		return incident;
	}

	public Edge getEdge(Node u, Node v) throws GraphException {
		if (nodeExists(u) == false) throw new GraphException("Node Does Not Exist.");
		if (nodeExists(v) == false) throw new GraphException("Node Does Not Exist.");
		
		Edge temp = findEdge(u, v);
		if (temp == null) throw new GraphException("Edge Does Not Exist.");
		
		return temp;
	}

	public boolean areAdjacent(Node u, Node v) throws GraphException {
		if (nodeExists(u) == false) throw new GraphException("Node Does Not Exist.");
		if (nodeExists(v) == false) throw new GraphException("Node Does Not Exist.");
		
		Node first;
		Node second;
		
		for (int i = 0; i < edgeElm; i++){
			first = edges[i].firstEndpoint();
			second = edges[i].secondEndpoint();
			
			if (first.equals(u)){
				if (second.equals(v)){
					return true;
				}
			} else if (first.equals(v)){
				if (second.equals(u)){
					return true;
				}
			}
		}
		
		return false;
	}

	private boolean nodeExists(Node find){
		for (int i = 0; i < size; i++){
			if (nodes[i].equals(find)){
				return true;
			}
		}
		
		return false;
	}
	
	private Node findNode(int name){
		for (int i = 0; i < size; i++){
			if (nodes[i].getName() == name){
				return nodes[i];
			}
		}
		
		return null;
	}
	
	private Edge findEdge(Node u, Node v){
		Edge tempEdge;
		
		for (int i = 0; i < edgeElm; i++){
			tempEdge = edges[i];
			
			if (tempEdge.firstEndpoint().equals(u)){
				if (tempEdge.secondEndpoint().equals(v)){
					return tempEdge;
				}
			} else if (tempEdge.secondEndpoint().equals(u)){
				if (tempEdge.firstEndpoint().equals(u)){
					return tempEdge;
				}
			}
		}
		
		return null;
	}
	
	private void expandEdge(){
		Edge[] newArray = new Edge[edgeElm * 2];
		System.arraycopy(edges, 0, newArray, 0, edgeElm);
		
		edges = newArray;
	}
}

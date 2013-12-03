/**
 * This class contains all the data for each edge node.
 * It will contain the pointer to each of the node objects
 * and information regarding them.
 * @author Bryan J. Muscedere
 *
 */
public class Edge {
	//The first endpoint of the edge.
	private Node firstEnd;
	//The second endpoint of the edge.
	private Node secondEnd;
	//The type of the edge that is connected. (Toll or Free).
	private String type;
	//A label that can be used for labeling specific details.
	private String label;
	
	/**
	 * The constructor class that initializes the edge object
	 * to hold two adjacent nodes and the edge's type.
	 * @param u The first adjacent node.
	 * @param v The second adjacent node.
	 * @param type The type of the edge that is connected.
	 */
	public Edge(Node u, Node v, String type){
		//The two adjacent nodes and type are being initalized.
		firstEnd = u;
		secondEnd = v;
		this.type = type;
		label = "";
	}
	
	/**
	 * Class that returns the first endpoint of the edge.
	 * @return The first endpoint.
	 */
	public Node firstEndpoint(){
		return firstEnd;
	}
	/**
	 * Class that returns the second enpoint of the edge.
	 * @return The second endpoint.
	 */
	public Node secondEndpoint(){
		return secondEnd;
	}
	
	/**
	 * Gets the type of the edge.
	 * @return Either 'toll' or 'free' depending on the node.
	 */
	public String getType(){
		return type;
	}
	
	/**
	 * Sets the label of the node.
	 * @param label The new label that is being set.
	 */
	public void setLabel(String label){
		this.label = label;
	}
	
	/**
	 * Gets the label of the current edge.
	 * @return The current label of the edge.
	 */
	public String getLabel(){
		return label;
	}
}

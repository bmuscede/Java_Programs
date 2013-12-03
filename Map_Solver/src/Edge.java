
public class Edge {
	private Node firstEnd;
	private Node secondEnd;
	private String type;
	private String label;
	
	public Edge(Node u, Node v, String type){
		firstEnd = u;
		secondEnd = v;
		this.type = type;
		label = "";
	}
	
	public Node firstEndpoint(){
		return firstEnd;
	}
	public Node secondEndpoint(){
		return secondEnd;
	}
	
	public String getType(){
		return type;
	}
	
	public void setLabel(String label){
		this.label = label;
	}
	
	public String getLabel(){
		return label;
	}
}

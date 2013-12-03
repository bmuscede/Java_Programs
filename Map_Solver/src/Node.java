
public class Node {
	private int vertex;
	private boolean mark;
	
	public Node(int name){
		vertex = name;
		mark = false;
	}
	
	public int getName(){
		return vertex;
	}
	
	public boolean getMark(){
		return mark;
	}
	
	public void setMark(boolean mark){
		this.mark = mark;
	}
}

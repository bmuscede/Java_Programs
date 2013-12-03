/**
 * This class codes for a single node in the binary tree. It contains a spot for
 * its element, left and right children and parent. Each node is linked to another
 * node.
 * @author Bryan J. Muscedere
 */
public class Node<T> {
	//The element that is held at that node.
	private T element;
	//The left child.
	private Node<T> left;
	//The right child.
	private Node<T> right;
	//The parent of this node.
	private Node<T> parent;
 	
	/**
	 * Constructor class that sets up the node. This class only sets the object.
	 * This means that the left, right, and parent Nodes will be null.
	 * @param obj The object that will be stored.
	 */
	public Node(T obj){
		element = obj;
	}
	
	/**
	 * Sets the element of the node to a new element.
	 * @param element The new element that is passed.
	 */
	public void setElement(T element){
		this.element = element;
	}
	/**
	 * Gets the element that is stored in the node.
	 * @return The element that is stored.
	 */
	public T getElement(){
		return element;
	}
	
	/**
	 * Sets the parent of the current node to one that is specified.
	 * @param parent The node of the parent.
	 */
	public void setParent(Node<T> parent){
		this.parent = parent;
	}
	/**
	 * This method gets the parent node of the current node.
	 * @return The parent node.
	 */
	public Node<T> getParent(){
		return parent;
	}
	
	/**
	 * Sets the left child of the current node.
	 * @param temp The new left child.
	 */
	public void setLeft(Node<T> temp){
		left = temp;
	}
	/**
	 * Gets the left child of the current node.
	 * @return The left child of the node.
	 */
	public Node<T> getLeft(){
		return left;
	}
	
	/**
	 * Sets the right child of the current node.
	 * @param temp The new right child.
	 */
	public void setRight(Node<T> temp){
		right = temp;
	}
	/**
	 * Gets the right child of the current node.
	 * @return The right child of the node.
	 */
	public Node<T> getRight(){
		return right;
	}	
}
/**
 * Class that represents the dictionary in a binary tree format. Contains functions to
 * find words, types and get parents. All the public classes are at the top while the
 * private classes are at the bottom.
 * @author Bryan J. Muscedere
 */
public class OrderedDictionary implements OrderedDictionaryADT{
	//The root of the tree.
	private Node<DictEntry> root;

	/**
	 * Constructor class that initalizes the dictionary. Sets the root to null.
	 */
	public OrderedDictionary(){
		root = null;
	}
	
	/**
	 * Finds the node in the tree with the word passed and returns its definition.
	 * Returns the empty string if the tree is empty.
	 * @param word The word in the dictionary.
	 * @return The definition of the word searched for.
	 */
	public String findWord(String word) {
		//Sees if the tree is empty. 
		if (root == null) return "";
		
		//Employs the recursive find on the tree. Returns a node.
		Node<DictEntry> temp = recursiveFind(root, word);
		
		//Sees if the recursive find could not find the element.
		if (temp.getElement() == null){
			return "";
		}
		
		//Returns the definition.
		return temp.getElement().definition();
	}
	
	/**
	 * Finds the type of the corresponding word. Returns -1 if not found, otherwise
	 * a 1, 2 or 3 is returned.
	 * @param word The desired word that is being searched for.
	 * @return The type of the corresponding word searched for. Can be -1, 1, 2 or 3.
	 */
	public int findType(String word) {
		//Sees if the tree is empty.
		if (root == null) return -1;
		
		//Employs recursive search on the tree.
		Node<DictEntry> temp = recursiveFind(root, word);
		
		//If the node that is returned is null, the element wasn't found.
		if (temp.getElement() == null){
			return -1;
		}
		
		//Otherwise, returns the type.
		return temp.getElement().type();
	}

	/**
	 * Inserts a word, definition and type into the tree. Places them in a DictEntry object
	 * and then finds the correct place to place the node.
	 * @param word The word that is being inserted into the tree.
	 * @param definition The corresponding definition of the word.
	 * @param type The corresponding type of the word.
	 */
	public void insert(String word, String definition, int type)
			throws DictionaryException {
		
		//Looks if the word is already present. If it is, throws an exception.
		if (findWord(word) != "") throw new DictionaryException("already present");
		
		//Creates a new DictEntryElement for the desired object.
		DictEntry element = new DictEntry(word, definition, type);
		
		//Sees if the tree is empty.
		if (root == null){
			//If it is, creates a new node at the root element.
			root = new Node<DictEntry>(element);
			
			//Sets the left and right children to empty nodes.
			root.setLeft(new Node<DictEntry>(null));
			root.setRight(new Node<DictEntry>(null));
			
			//Sets the parent to null.
			root.setParent(null);
		} else {
			//Calls the recursive function tree insert.
			Node<DictEntry> tempNode = recursiveFind(root, element.word());
			
			//Sets the element of this empty node to that of the DictEntry object.
			tempNode.setElement(element);
			
			//Sets the left and right node to the empty node.
			tempNode.setLeft(new Node<DictEntry>(null));
			tempNode.setRight(new Node<DictEntry>(null));
			
			//Stores the parent of the node, and the parent of its two children.
			tempNode.setParent(getParent(tempNode));
			tempNode.getLeft().setParent(tempNode);
			tempNode.getRight().setParent(tempNode);
		}
	}
	
	/**
	 * This class removes an element in the array. If the element is not in the array
	 * a DictionaryException will be thrown. Otherwise, this method determines how an
	 * element should be removed and removes it.
	 * @param word The word that is desired to be removed.
	 */
	public void remove(String word) throws DictionaryException {
		//Creates a new empty node and sees if it's present in the dictionary.
		Node<DictEntry> temp = recursiveFind(root, word);
		
		//If it isn't, throws an exception.
		if (temp.getElement() == null) throw new DictionaryException("is not present.");
		
		//Determines the way to remove the element
		if ((temp.getLeft().getElement() == null) || (temp.getRight().getElement() == null)){
			//This node has at least one leaf node. We can remove at external.
			removeAtExternal(temp);
		} else {
			//This node is an internal node. We must remove through remove at internal.
			removeAtInternal(temp);
		}
	}
	
	/**
	 * Returns the successor to a desired prefix or word.
	 * @param The word or prefix that will be used to find a successor.
	 * @return The successor to this word or prefix.
	 */
	public String successor(String word) {
		//Finds the position of where the current word is in the array. If a prefix an empty node is found.
		Node<DictEntry> successorNode = recursiveFind(root, word);
		Node<DictEntry> tempNode = null;
		
		//Sees if it is either a node with two empty child nodes or an empty right child.
		if ((hasLeaf(successorNode) == 2) || (hasLeaf(successorNode) == 3)){
			//If it is, it gets the parent node.
			tempNode = successorNode.getParent();
			
			//Moves up the parent node until the correct successor element is found.
			while (tempNode.getParent() != null){
				//Sees if the parent node is greater than the current node.
				if (word.compareTo(tempNode.getElement().word()) <= 0){
					//Exits the loop if it is.
					break;
				}
			
				//Otherwise, gets the next parent.
				tempNode = tempNode.getParent();
			}
			
			//Sees if it is the greatest element in the tree.
			if (word.compareTo(tempNode.getElement().word()) > 0){
				tempNode = new Node<DictEntry>(null);
			}
		} else {
			//Gets the right child.
			tempNode = successorNode.getRight();
			//Now uses a method to get the smallest element in the right subtree.
			tempNode = getSmallest(tempNode);
		}
		
		//Sees if the tempnode is null.
		if (tempNode.getElement() == null){
			//This means there is no successor to the word.
			return "";
		}
		
		//Returns the successor.
		return tempNode.getElement().word();
	}

	/**
	 * Finds the predecessor of the current word or prefix that is passed to the function.
	 * This method can accept both a prefix or a function and it returns the predecessor.
	 * @param word The word/prefix that will be used to find a predecessor.
	 * @return The predecessor of the word or prefix.
	 */
	public String predecessor(String word) {
		//Finds the position of the current word/prefix in the tree.
		Node<DictEntry> predecessorNode = recursiveFind(root, word);
		Node<DictEntry> tempNode = null;

		//Sees if the left child is empty.
		if ((hasLeaf(predecessorNode) == 1) || (hasLeaf(predecessorNode) == 3)){
			//It is empty. Therefore gets the parent.
			tempNode = predecessorNode.getParent();
			
			//Loops until either the parent node is null or a smaller element than the word is found.
			while (tempNode.getParent() != null){
				//Sees if the element is smaller than the word being searched for.
				if (word.compareTo(tempNode.getElement().word()) >= 0){
					//Breaks from the loop. 
					break;
				}
				
				//Sets the temp node to be the parent node.
				tempNode = tempNode.getParent();
			}
			
			//Sees if the current node is the smallest in the tree.
			if (word.compareTo(tempNode.getElement().word()) < 0){
				//Sets it to null if it is.
				tempNode = new Node<DictEntry>(null);
			}
		} else {
			//The left child is available.
			tempNode = predecessorNode.getLeft();
			//Gets the largest element in the left subtree.
			tempNode = getLargest(tempNode);
		}
		
		//Sees if the element is null.
		if (tempNode.getElement() == null){
			//Therefore there is no predecessor.
			return "";
		}
		
		//Returns the element.
		return tempNode.getElement().word();
	}

	/**
	 * Helper method that removes an element that has one or fewer children. 
	 * @param nodeDel The node to be deleted.
	 */
	private void removeAtExternal(Node<DictEntry> nodeDel){
		Node<DictEntry> nonLeaf = null;
		
		//Gets the non-empty child attached to this node (if there is one).
		if (nodeDel.getLeft().getElement() == null){
			//Gets the right child.
			nonLeaf = nodeDel.getRight();
		} else {
			//Gets the left child.
			nonLeaf = nodeDel.getLeft();
		}
		
		//Sees if the node to delete is the root.
		if (nodeDel == root){
			//Therefore, clears the entire tree.
			root = nonLeaf;
			nonLeaf.setParent(null);
			return;
		}
		
		//Otherwise, gets the parent and sees if the node to be deleted is on the left or right.
		Node<DictEntry> parent = nodeDel.getParent();
		nonLeaf.setParent(parent);
		
		if (parent.getLeft() == nodeDel){
			//If it's on the left, sets the left to the node to be deleted's child.
			parent.setLeft(nonLeaf);
		} else {
			//If it's on the right, sets the right to the node to be deleted's child.
			parent.setRight(nonLeaf);
		}
	}
	
	/**
	 * Method to remove a node that has two children. Calls upon the removeAtExternal.
	 * @param nodeDel The node to be deleted
	 */
	private void removeAtInternal(Node<DictEntry> nodeDel){
		Node<DictEntry> copyNode = null;
		
		//First, gets either the smallest element of the node's children. Decides based on the size of subtree.
		if (size(nodeDel.getLeft()) > size(nodeDel.getRight())){
			copyNode = getLargest(nodeDel.getRight());
		} else {
			copyNode = getSmallest(nodeDel.getRight());
		}
		
		//Sets either the smallest or largest element as the element in the deletion node's element.
		nodeDel.setElement(copyNode.getElement());
		
		//Removes the duplicate node with its format.
		removeAtExternal(copyNode);
	}
	
	/**
	 * Recursive method that calculates the size of a tree/subtree.
	 * @param subTree The node that is the top of the tree/subtree.
	 * @return The number of nodes in the tree or subtree. Not counting empty nodes.
	 */
	private int size(Node<DictEntry> subTree){
		//Sets the sum to 0.
		int sum = 0;
		
		//Base case. If the current element is empty, returns 0.
		if (subTree.getElement() == null){
			return 0;
		}
		
		//First, gets the sum of all the left children.
		if (subTree.getLeft().getElement() != null){
			sum += size(subTree.getLeft());
		}
		//Next gets the sum of all the right children.
		if (subTree.getRight().getElement() != null){
			sum += size(subTree.getRight());
		}
		
		//Returns the sum plus the current node.
		return sum + 1;
	}
	
	/**
	 * Iterative method to get the parent of the current node. Useful only in the insertion method to
	 * initially determine the parent.
	 * @param child The child node.
	 * @return The parent of the child node passed.
	 */
	private Node<DictEntry> getParent(Node<DictEntry> child){
		//Sets up all the parent and child node counters.
		Node<DictEntry> tempParent = null;
		Node<DictEntry> tempChild = root;
		
		//Gets the word of the child.
		String word = child.getElement().word();
		int compare = 0;
		
		//Loops while the tempChild node does not equal the child node and while the tempChild element is not null.
		while((!(tempChild.equals(child))) && (tempChild.getElement() != null)){
			compare = word.compareTo(tempChild.getElement().word());
			
			//Compares the word of the child to the word of the tempChild.
			if ((compare < 0) || (compare == 0)){
				//If its smaller or equal, get the left child as the new tempChild.
				tempParent = tempChild;
				tempChild = tempChild.getLeft();
			} else if (compare > 0) {
				//If it is greater, get the right child as the new tempChild.
				tempParent = tempChild;
				tempChild = tempChild.getRight();
			}
		}
		
		//Sees if the element is equal to null.
		if (tempChild.getElement() == null){
			//If so, returns null.
			return null;
		}
		
		//Otherwise, returns the parent node.
		return tempParent;
	}
	
	/**
	 * Determines whether or not a certain node contains a left, right or no children.
	 * @param current The current node to be analyzed.
	 * @return A numerical value determining the number of empty/leaf nodes. 3 means both left and right are leaf, 2 means right is leaf,
	 * 1 means left is leaf and 0 means it is an internal node.
	 */
	private int hasLeaf(Node<DictEntry> current){
		//Sees if the current node is an empty node.
		if ((current.getLeft() == null)){
			//We have a leaf node!
			return 3;
		}
		
		//Checks to see if both the left and the right contain empty nodes. Then if the right has an empty node. Then if the left has an empty node.
		if ((current.getLeft().getElement() == null) && (current.getRight().getElement() == null)){
			return 3;
		} else if (current.getRight().getElement() == null){
			return 2;
		} else if (current.getLeft().getElement() == null){
			return 1;
		}
		
		//Otherwise, it returns 0.
		return 0;
	}
	
	/**
	 * Gets the smallest element in a specified tree/subtree.
	 * @param tree The subtree or tree that needs to be analyzed.
	 * @return The node containing the smallest element in that subtree.
	 */
	private Node<DictEntry> getSmallest(Node<DictEntry> tree){
		//Gets the left element of the subtree.
		Node<DictEntry> next = tree.getLeft();
		//Sees if it contains null.
		if (next == null){
			return null;
		}
		
		//Goes through the tree, keeping to the left, until an empty node is reached.
		while (next.getElement() != null){
			//Stores the next node in the previous node.
			tree = next;
			//Gets the new next node.
			next = next.getLeft();
		}
		
		//Returns the previous node.
		return tree;
	}
	
	/**
	 * Gets the largest element in a specified tree/subtree.
	 * @param tree The subtree or tree that needs to be analyzed.
	 * @return The node containing the largest element in that subtree.
	 */
	private Node<DictEntry> getLargest(Node<DictEntry> tree){
		//Gets the right element of the subtree.
		Node<DictEntry> next = tree.getRight();
		//Sees if it contains null.
		if (next == null){
			return null;
		}
		
		//Goes through the tree, keeping to the right, until an empty node is reached.
		while (next.getElement() != null){
			//Stores the next node in the previous node.
			tree = next;
			//Gets the new next node.
			next = next.getRight();
		}
		
		//Returns the previous node.
		return tree;
	}
	
	/**
	 * The helper method that recursively finds an element in the tree. If not in the tree, it finds the node where that element
	 * should be placed.
	 * @param current The current node where the search takes place.
	 * @param word The word that is being searched for.
	 * @return The node containing the position of the word.
	 */
	private Node<DictEntry> recursiveFind(Node<DictEntry> current, String word){
		//If the current element is null, returns this node.
		if (current.getElement() == null) return current;
		
		//Compares the value of the word with the current node's word.
		int compare = word.compareTo(current.getElement().word());
		if (compare < 0){
			//If smaller, go to the left with the new current element the left child. 
			return recursiveFind(current.getLeft(), word);
		} else if (compare > 0) {
			//If larger, go to the right with the new current element the right child.
			return recursiveFind(current.getRight(), word);
		}
		
		//Otherwise, if equal, returns the current node.
		return current;
	}
}
import java.io.PrintStream;
import java.util.Scanner;

public class QuestionTree {

	private QuestionNode root;
	private Scanner input;
	private PrintStream output;
	private int totGames;
	private int gamesWon;
	
	/**
	 * Constructor to initialize the new question tree. 
	 * @param input User interface for input.
	 * @param output User interface for output.
	 */
	public QuestionTree(Scanner input, PrintStream output) {
		//Throws an IllegalArgumentException if the passed
		//parameter is null.
		if(input == null || output == null)
			throw new IllegalArgumentException();
		this.input = input;
		this.output = output;
		root = new QuestionNode("A:Jedi");
	}
	
	/**
	 * Plays one complete guessing game with the user.
	 */
	public void play() {
		root = play(root);
	}
	
	/**
	 * Private helper method to use recursion for play.
	 * @param node The node to be returned.
	 * @return the changed QuestionNode.
	 */
	private QuestionNode play(QuestionNode node) {
		
		//Tests to see if the node is an answer node.
		if(node.question.startsWith("A:")) {
			//Prints out the answer node data in a formatted manner to the desired PrintStream.
			output.print("Would your object happen to be " + node.question.substring(2) + "? ");
			//If the guess was correct, prints out an appropriate message and increments gamesWon.
			if(UserInterface.nextAnswer(input)) {
				output.println("I win!");
				gamesWon++;
			}
			//If the guess was incorrect, prompts the user for more information to alter the tree.
			else {
				output.print("I lose. What is your object? ");
				String object = "A:" + input.nextLine();
				output.print("Type a yes/no question to distinguish your item from " + node.question.substring(2) + ": ");
				String newQuestion = "Q:" + input.nextLine();
				output.print("And what is the answer for your object? ");
				//Appropriately adjusts the tree based on if the additional object should be added to the left
				//or right of the QuestionNode.
				if(UserInterface.nextAnswer(input))					
					node = new QuestionNode(newQuestion, new QuestionNode(object), node);
				else
					node = new QuestionNode(newQuestion, node, new QuestionNode(object));
			}
			//Increments the total games played regardless of whether the computer wins.
			totGames++;
		}
		//If the node is a question node, then prints out the question and recursively calls
		//the method based on if the user enters yes (traverse down left tree) or no (traverse
		//down right tree).
		else {
			output.print(node.question.substring(2) + " ");
			String answer = input.nextLine();
			if(answer.startsWith("y"))
				node.left = play(node.left);
			else
				node.right = play(node.right);				
				
		}
		//Returns the node.
		return node;
	}
	
	/**
	 * Saves the current tree in an output file.
	 * @param output A PrintStream object representing the output file.
	 */
	public void save(PrintStream output) {
		//If the passed parameter is null, throws an exception.
		if(output == null)
			throw new IllegalArgumentException();
		//Calls on the private helper method to traverse the tree.
		save(output, root);		
	}
	/**
	 * A private helper method to traverse through the tree while saving
	 * the information into an output file.
	 * @param output The PrintStream object representing the output file.
	 * @param node The current node to be examined.
	 */
	private void save(PrintStream output, QuestionNode node) {
		//Implicit base case to do nothing if the node is null.
		if(node != null) {
			//Writes the question in the node to the file (preorder).
			output.println(node.question);
			//Traverse down the left side.
			save(output, node.left);
			//Traverse down the right side.
			save(output, node.right);
		}
	}
	
	/**
	 * Replaces the current tree by reading another tree from a file.
	 * Precondition: The file exists and is in proper standard format.
	 * @param input The Scanner that reads from a file.
	 */
	public void load(Scanner input) {
		//If the passed parameter is null, then throws an exception.
		if(input == null)
			throw new IllegalArgumentException();
		//If the file is empty, then does not load in any information.
		if(input.hasNextLine()) {
			//Initializes the root equal to the first line in the file.
			root = new QuestionNode(input.nextLine());
			root.left = add(root.left, input);
			root.right = add(root.right, input);
		}
	}
	/**
	 * Private helper method to add the nodes in the appropriate position.
	 * @param node The node to be examined and returned.
	 * @param input The user interface for input.
	 * @return the changed QuestionNode
	 */
	private QuestionNode add(QuestionNode node, Scanner input) {
		//Returns null if the Scanner does not data left.
		if(!input.hasNext())
			node = null;
		else {
			//Stores the question in a variable for use later on.
			String question = input.nextLine();
			//If the passed node is null, then creates a new QuestionNode.
			if(node == null) {
				node = new QuestionNode(question);	
				//If the line is a question, then calls on the method for both
				//the left and right side to build the tree.
				if(question.startsWith("Q")) {
					node.left = add(node.left, input);
					node.right = add(node.right, input);
				}
			}		
		}
		return node;
	}
	
	//Returns the total games played so far.
	public int totalGames() {
		return totGames;
	}
	
	//Returns the games won by the computer so far.
	public int gamesWon() {
		return gamesWon;
	}
}

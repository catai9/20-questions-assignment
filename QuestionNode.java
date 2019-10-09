
public class QuestionNode {

	//Question stored in this node.
	public String question;
	//Reference to left subtree (if answer is yes).
	public QuestionNode left;
	//Reference to right subtree (if answer is no).
	public QuestionNode right;
	
	//Constructs a leaf node with the given question.
	public QuestionNode(String question) {
		this(question, null, null);
	}
	
	//Constructs a branch node with the given data and links.
	public QuestionNode(String question, QuestionNode left,  QuestionNode right) {
		this.question = question;
		this.left = left;
		this.right = right;
	}
	
	
}

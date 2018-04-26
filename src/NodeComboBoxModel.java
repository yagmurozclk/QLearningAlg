import javax.swing.DefaultComboBoxModel;

public class NodeComboBoxModel extends DefaultComboBoxModel<Node> {

	public NodeComboBoxModel(Node[] items) {
		super(items);
	}

	@Override
	public Node getSelectedItem() {
		Node selectedNode = (Node) super.getSelectedItem();

		// do something with this job before returning...

		return selectedNode;
	}

}

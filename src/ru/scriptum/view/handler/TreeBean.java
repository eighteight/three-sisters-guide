package ru.scriptum.view.handler;

import java.util.List;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;

import org.apache.myfaces.custom.tree2.HtmlTree;
import org.apache.myfaces.custom.tree2.TreeModel;
import org.apache.myfaces.custom.tree2.TreeModelBase;
import org.apache.myfaces.custom.tree2.TreeNode;
import org.apache.myfaces.custom.tree2.TreeNodeBase;
import org.apache.myfaces.custom.tree2.TreeState;
import org.apache.myfaces.custom.tree2.TreeStateBase;

import ru.scriptum.model.data.IElement;
import ru.scriptum.model.data.ITemplateElement;
import ru.scriptum.model.domaindata.User;
import ru.scriptum.view.servicelocator.IServiceLocator;
import ru.scriptum.view.util.FacesUtils;

/**
 * Backer bean for use in example. Basically makes a TreeNode available.
 * 
 * @author Sean Schofield
 * @version $Revision: 1.8 $ $Date: 2006/06/23 16:15:39 $
 */
public class TreeBean extends BaseBean {
	/**
	 * serial id for serialisation versioning
	 */
	private static final long serialVersionUID = 1L;

	private String _nodePath;

	private List<IElement> rootList;

	private TreeNode rootNode;

	private HtmlTree tree;

	private TreeState treeState;

	private int allElementsCnt = 0;

	private int currentElementCnt = 0;

	public TreeBean() {
		super();
		treeState = new TreeStateBase();
		treeState.setTransient(true);
		init();
	}

	public void checkPath(FacesContext context, UIComponent component,
			java.lang.Object value) {
		// make sure path is valid (leaves cannot be expanded or renderer will
		// complain)
		FacesMessage message = null;

		String[] path = tree.getPathInformation(value.toString());

		for (int i = 0; i < path.length; i++) {
			String nodeId = path[i];
			try {
				tree.setNodeId(nodeId);
			} catch (Exception e) {
				throw new ValidatorException(message, e);
			}

			if (tree.getNode().isLeaf()) {
				message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Invalid node path (cannot expand a leaf): " + nodeId,
						"Invalid node path (cannot expand a leaf): " + nodeId);
				throw new ValidatorException(message);
			}
		}
	}

	public String expandAll() {
		tree.expandAll();
		return null;
	}

	// public void processAction(ActionEvent event) throws
	// AbortProcessingException {
	// UIComponent component = (UIComponent)event.getSource();
	// while (!(component != null && component instanceof HtmlTree)) {
	// component = component.getParent();
	// }
	// if (component != null) {
	// HtmlTree tree = (HtmlTree)component;
	// TreeNodeBase node = (TreeNodeBase)tree.getNode();
	// tree.setNodeSelected(event);
	// setAuthorBio(((ExtendedTreeNode)node).getLongDescription());
	// }
	// }
	
	public void setServiceLocator(IServiceLocator bean){
		super.setServiceLocator(bean);
	}

	public void expandPath(ActionEvent event) {
		tree.expandPath(tree.getPathInformation(_nodePath));
	}

	/**
	 * NOTE: This is just to show an alternative way of supplying tree data. You
	 * can supply either a TreeModel or TreeNode.
	 * 
	 * @return TreeModel
	 */
	public TreeModel getExpandedTreeData() {
		return new TreeModelBase(getTreeData());
	}

	public String getNodePath() {
		return _nodePath;
	}

	public HtmlTree getTree() {
		return tree;
	}

	public TreeNode getTreeData() {
		currentElementCnt = 0;
		rootNode = new TemplateTreeNodeBase("node", "All", "0","rootTemplate",false);
		for (IElement element : rootList) {
			if (!(element instanceof ITemplateElement)) continue;
			TreeNodeBase base = new TemplateTreeNodeBase("node", element
					.getName(), element.getId().toString(),
					((ITemplateElement) element).getTemplateType(), false);
			// element.getChildren().size() > 0);
			setTreeData(base, element);
			rootNode.getChildren().add(base);
		}
		return rootNode;
	}

	public TreeModel getTreeModel() {
		init();
		TreeModel treeModel = new TreeModelBase(getTreeData());
		treeModel.setTreeState(treeState);
		return treeModel;
	}

	protected void init() {
		try {
			User user = FacesUtils.getLoggedInUser();
			this.rootList = FacesUtils.getServiceLocatorBean().getBeanService().getAllRoots(user);
			this.allElementsCnt = FacesUtils.getServiceLocatorBean().getBeanService().getAll().size();
		} catch (Exception e) {
			String msg = "Could not initialize ListHandler";
			this.logger.error("Could not initialize ProductListBean", e);
			throw new FacesException(msg, e);
		}
	}

	public void processAction(ActionEvent event)
			throws AbortProcessingException {
		UIComponent component = (UIComponent) event.getSource();
		while (!(component != null && component instanceof HtmlTree)) {
			component = component.getParent().getParent().getParent();
		}
		if (component != null && component instanceof HtmlTree) {
			HtmlTree tree = (HtmlTree) component;
			// TreeNodeBase node = (TreeNodeBase)tree.getNode();
			tree.setNodeSelected(event);
			// setAuthorBio(((ExtendedTreeNode)node).getLongDescription());
		}
	}

	public void setNodePath(String nodePath) {
		_nodePath = nodePath;
	}

	public void setTree(HtmlTree tree) {
		this.tree = tree;
	}

	private void setTreeData(TreeNode parent, IElement root) {
		if (currentElementCnt > 2*allElementsCnt) return;
		for (IElement child : root.getChildren()) {
			if (child == null) continue;
			TreeNode childBase = new TemplateTreeNodeBase("node", child
					.getName(), child.getId().toString(),
					((ITemplateElement) child).getTemplateType(), false);
			// child.getChildren().size() > 0);
			parent.getChildren().add(childBase);
			currentElementCnt ++;
			setTreeData(childBase, child);
		}
	}
}
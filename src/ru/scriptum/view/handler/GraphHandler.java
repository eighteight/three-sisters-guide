/*
 * Created on May 20, 2007
 *
 * boba
 */
package ru.scriptum.view.handler;

import ru.scriptum.model.data.IElement;
import ru.scriptum.model.data.ITemplateElement;
import ru.scriptum.model.domaindata.ImageElement;
import ru.scriptum.view.util.FacesUtils;

public class GraphHandler extends TemplateListHandler {
	private static final long serialVersionUID = 1L;
	private Long contentId;
	private int currentGraphDepth = 0;
	private int fontSize = 8;
	private int graphDepth = 1;
	private Long id;
	private boolean fontIsSetByClient;
	
    public GraphHandler(){
		super();
	}

    private String computeEdgeLength() {
		return (100-5*graphDepth)+"";
	}
	private String computeFontSize() {
		return (fontIsSetByClient? getFontSize():(20 - graphDepth))+"px";
	}
	
	private String getAllChildrenXml() {
		String ret = "";
		for (IElement element : ServiceLocatorBean.getInstance().getBeanService().getAllRoots(FacesUtils.getLoggedInUser())) {

			if (element instanceof ITemplateElement) {
				ret = getElementXml(element);
			}
		}
		
		if ("".equals(ret)){
			ret = "<root root=\"true\" edgeLength=\"40\" fixed=\"true\" color=\"#cccccc\" text=\""
				+"no data" + "\""+">";
			ret += "</root>";
		}
		return ret;
	}
	public int getFontSize() {
		return fontSize;
	}

	public int getCurrentGraphDepth() {
		return currentGraphDepth;
	}

	public void setCurrentGraphDepth(int currentGraphDepth) {
		this.currentGraphDepth = currentGraphDepth;
	}

	private String getChildrenXml(IElement parent) {
		this.currentGraphDepth ++;
		String ret = "";
		for (IElement element : parent.getChildren()) {
			if (element == null) continue;
			ret += "<node color=\"#aaaaaa\" text=\"" + element.getName()
					+ "\" name=\"" + element.getId() + "\" fontSize=\""+computeFontSize()+"\"";
			String templateType = ((ITemplateElement) element).getTemplateType();
			if ("imageTemplate".equals(templateType)) {
				ret += " image=\"" + ((ImageElement) element).getPath() + "\"";
			}
			if ("videoTemplate".equals(templateType)) {
				ImageElement image = (ImageElement) element;
				ret += " video=\"" + image.getPath() + "\" mimeType=\""+image.getProperties()[2].getValue()+"\"";
				ret +=  " fileSize=\""+image.getProperties()[3].getValue()+"\"";
			}
			if (element.hasChildren()) {
				ret += " target=\"" + element.getId() + "\"";
			}
			ret += ">";
			if (currentGraphDepth<graphDepth) ret += getChildrenXml(element);
			ret += "</node>";
		}
		return ret;
	}
	
	private ITemplateElement getContentElement() {
		return contentId==null?null:(ITemplateElement) ServiceLocatorBean.getInstance().getBeanService().getPersistable(contentId);
	}

	public Long getContentId() {
		return contentId;
	}

	private String getCurrentChildrenXml() {
		String ret = "";
		IElement iElement = (IElement) ServiceLocatorBean.getInstance().getBeanService().getPersistable(id);
		if (iElement instanceof ITemplateElement) {
			ret += getElementXml(iElement);
		}
		return ret;
	}

	private String getElementXml(IElement element) {
		String ret = "<root root=\"true\" fixed=\"true\" edgeLength=\""+computeEdgeLength()+"\" color=\"#cccccc\" text=\""
				+ element.getName() + "\" name=\"" + element.getId() + "\"";
		if (!element.isRoot())
			ret += " target=\"" + element.getParent().getId() + "\"";
		ret += ">";
		ret += getChildrenXml(element);
		ret += "</root>";
		return ret;
	}
	
	public int getGraphDepth() {
		return graphDepth;
	}

	public Long getId() {
		return id;
	}

	public String getTemplateType() {
		ITemplateElement element = getContentElement();
		return element == null? null: element.getTemplateType();
	}

	public String getUrl(){
		if (contentId == null) return "";
		ITemplateElement element = getContentElement();
		String ret = "/blobs/"+((ImageElement)element).getFileName(); 
		return ret;
	}

	public String getXml() {
		String ret = null;
		initId();
		currentGraphDepth = 0;
		ret = id == null ? getAllChildrenXml() : getCurrentChildrenXml();
		System.out.println("XML "+ ret);
		return ret;
	}

	public void initId(){
		String idd = FacesUtils.getRequestParameter("id");
		if (idd != null && idd.length() > 0) {
			id = Long.parseLong(idd);
		}
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

	public void setFontSize(int fontSize) {
		this.fontIsSetByClient = true;
		this.fontSize = fontSize;
	}

	public void setGraphDepth(int graphDepth) {
		this.graphDepth = graphDepth;
	}
	public void setId(Long currentId) {
		this.id = currentId;
	}
}
/*
 * Created on Dec 7, 2005
 *
 * boba
 */
package ru.scriptum.model.properties;


public class HeteroPropertySource implements IPropertySource {

	private IProperty[] properties;

	public HeteroPropertySource() {
	}

	public Object getEditableValue() {
		return null;
	}

	public static void main(String[] args) {
		HeteroPropertySource hps = new HeteroPropertySource();
		hps.getProperties(null);
	}

	public IProperty[] getProperties(String configName) {
		return properties;
	}

	public Object getPropertyValue(Object id) {
		return null;
	}

	public boolean isPropertySet(Object id) {
		// TODO Auto-generated method stub
		return false;
	}

	public void resetPropertyValue(Object id) {
		// TODO Auto-generated method stub

	}

	public void setPropertyValue(Object id, Object value) {
		// TODO Auto-generated method stub

	}

	public void setProperties(IProperty[] propertyDescriptors) {
		this.properties = propertyDescriptors;
	}
}

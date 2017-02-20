/*
 * Created on Dec 13, 2005
 *
 * boba
 */
package ru.scriptum.model.properties;

import java.util.HashMap;
import java.util.Map;

public class Property implements IProperty {

	private Map<String, String> defaultValues;

	private String name;

	private boolean required;

	private boolean unique;

	private String value;

	public Property(IProperty ret) {
		this(ret.getName(), ret.getValue(), ret.isRequired(), ret.isUnique());
		this.setDefaultValues(ret.getDefaultValues());
	}

	public Property(String name, String value, boolean required, boolean unique) {
		this.name = name;
		this.value = value;
		this.required = required;
		this.unique = unique;
	}

	public IProperty clone() {
		return new Property(this);
	}

	public boolean equals(Object object) {
		if (!(object instanceof IProperty))
			return false;
		IProperty prop = (IProperty) object;
		if (this.value.equals(prop.getValue())
				&& this.required == prop.isRequired()
				&& this.unique == prop.isUnique()
				&& this.name.equals(prop.getName()))
			return true;
		return false;
	}

	public String getCategory() {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, String> getDefaultValues() {
		return defaultValues;
	}

	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] getFilterFlags() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getHelpContextIds() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value == null ? "" : value;
	}

	public boolean isCompatibleWith(IProperty anotherProperty) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isRequired() {
		return required;
	}

	public boolean isUnique() {
		return unique;
	}

	public void setDefaultValueProxy(String[] defaultValues) {
		this.defaultValues = new HashMap<String, String>();
		for (String def : defaultValues) {
			this.defaultValues.put(def, def);
		}
	}

	public void setDefaultValues(Map<String, String> values) {
		this.defaultValues = values;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String toString() {
		return name + " " + value + " unique " + unique;
	}
}

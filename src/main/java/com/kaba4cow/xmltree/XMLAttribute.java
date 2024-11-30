package com.kaba4cow.xmltree;

import java.util.Objects;

/**
 * Represents an XML attribute with a name and value.
 * 
 * @see XMLNode
 * @see XMLText
 */
public class XMLAttribute extends XMLObject {

	private String name;
	private Object value;

	XMLAttribute(XMLNode parent) {
		super(parent);
		this.name = null;
		this.value = null;
	}

	/**
	 * Retrieves the name of the attribute.
	 *
	 * @return the attribute name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the attribute.
	 *
	 * @param name the new name for the attribute
	 * 
	 * @return a reference to this object
	 * 
	 * @throws IllegalArgumentException if an attribute with the same name already exists in the parent node
	 */
	public XMLAttribute setName(String name) {
		if (getParent().containsAttributeName(name))
			throw new IllegalArgumentException(String.format("Attribute with name \"%s\" already exists", name));
		this.name = name;
		return this;
	}

	/**
	 * Retrieves the value of the attribute.
	 *
	 * @return the attribute value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Sets the value of the attribute.
	 *
	 * @param value the new value for the attribute
	 * 
	 * @return a reference to this object
	 */
	public XMLAttribute setValue(Object value) {
		this.value = value;
		return this;
	}

	/**
	 * Converts the attribute to its XML string representation.
	 *
	 * @return the XML string representation of the attribute
	 */
	@Override
	public String toXMLString() {
		return String.format("%s=\"%s\"", escapeString(name), escapeString(Objects.toString(value)));
	}

	@Override
	public String toString() {
		return String.format("XMLAttribute [name=%s, value=%s]", name, value);
	}

}

package com.kaba4cow.xmltree;

import java.util.Optional;

import com.kaba4cow.stringview.StringView;

/**
 * Represents an XML attribute with a name and value.
 * 
 * @see XMLNode
 * @see XMLText
 */
public class XMLAttribute extends XMLObject {

	private String name;
	private String value;

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
	 * Retrieves the optional name.
	 * 
	 * @return an {@link Optional} containing the name
	 */
	public Optional<String> optName() {
		return Optional.ofNullable(name);
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
	 * Creates a {@link StringView} for the name.
	 * 
	 * @return a new {@link StringView} for the name
	 */
	public StringView viewName() {
		return new StringView(name);
	}

	/**
	 * Retrieves the value of the attribute.
	 *
	 * @return the attribute value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Retrieves the optional value.
	 * 
	 * @return an {@link Optional} containing the value
	 */
	public Optional<String> optValue() {
		return Optional.ofNullable(value);
	}

	/**
	 * Sets the value of the attribute.
	 *
	 * @param value the new value for the attribute
	 * 
	 * @return a reference to this object
	 */
	public XMLAttribute setValue(String value) {
		this.value = value;
		return this;
	}

	/**
	 * Creates a {@link StringView} for the value.
	 * 
	 * @return a new {@link StringView} for the value
	 */
	public StringView viewValue() {
		return new StringView(value);
	}

	/**
	 * Converts the attribute to its XML string representation.
	 *
	 * @return the XML string representation of the attribute
	 */
	@Override
	public String toXMLString() {
		return String.format("%s=\"%s\"", escapeString(name), escapeString(value));
	}

	@Override
	public String toString() {
		return String.format("XMLAttribute [name=%s, value=%s]", name, value);
	}

}

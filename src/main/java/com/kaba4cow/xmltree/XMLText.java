package com.kaba4cow.xmltree;

import java.util.Optional;

import com.kaba4cow.stringview.StringView;

/**
 * Represents an XML node text content.
 * 
 * @see XMLNode
 * @see XMLAttribute
 */
public class XMLText extends XMLObject {

	private String text;

	XMLText(XMLNode parent) {
		super(parent);
		this.text = null;
	}

	/**
	 * Retrieves the text content.
	 *
	 * @return the text content
	 */
	public String getText() {
		return text;
	}

	/**
	 * Retrieves the optional text content.
	 * 
	 * @return an {@link Optional} containing the text
	 */
	public Optional<String> optText() {
		return Optional.ofNullable(text);
	}

	/**
	 * Sets the text content.
	 *
	 * @param text the new text content
	 * 
	 * @return a reference to this object
	 */
	public XMLText setText(String text) {
		this.text = text;
		return this;
	}

	/**
	 * Creates a {@link StringView} for the text.
	 * 
	 * @return a new {@link StringView} for the text
	 */
	public StringView viewText() {
		return new StringView(text);
	}

	/**
	 * Converts the text to its XML string representation.
	 *
	 * @return the XML string representation of the text
	 */
	@Override
	public String toXMLString() {
		return escapeString(text);
	}

	@Override
	public String toString() {
		return String.format("XMLText [text=%s]", text);
	}

}

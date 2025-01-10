package com.kaba4cow.xmltree;

import java.util.Objects;

/**
 * Represents an base class for XML-related objects in the XML tree. Provides common functionality for XML objects.
 */
abstract class XMLObject {

	private final XMLNode parent;

	/**
	 * Creates an XML object with a potential parent node.
	 *
	 * @param parent the parent {@link XMLNode} of this object, can be {@code null}
	 */
	protected XMLObject(XMLNode parent) {
		this.parent = parent;
	}

	/**
	 * Escapes special XML characters in a string to make it safe for XML output.
	 *
	 * @param string the input string to escape
	 * 
	 * @return the escaped string with XML special characters replaced, or {@code null} if input is {@code null}
	 */
	protected static String escapeString(String string) {
		return Objects.isNull(string)//
				? null//
				: string//
						.replace("&", "&amp;")//
						.replace("<", "&lt;")//
						.replace(">", "&gt;")//
						.replace("\"", "&quot;")//
						.replace("'", "&apos;");
	}

	/**
	 * Converts the XML object to its XML string representation.
	 *
	 * @return the XML string representation of the object
	 */
	abstract String toXMLString();

	/**
	 * Checks if the object has a parent node.
	 *
	 * @return {@code true} if the object has a parent, {@code false} otherwise
	 */
	boolean hasParent() {
		return Objects.nonNull(parent);
	}

	/**
	 * Retrieves the parent node of this XML object.
	 *
	 * @return the parent {@link XMLNode}
	 */
	XMLNode getParent() {
		return parent;
	}

}

package com.kaba4cow.xmltree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.kaba4cow.stringview.StringView;

/**
 * Represents a node in an XML document, capable of containing child nodes, attributes, and text.
 * 
 * @see XMLAttribute
 * @see XMLText
 */
public class XMLNode extends XMLObject {

	private final List<XMLNode> nodes;
	private final List<XMLAttribute> attributes;
	private final XMLText text;

	private String tag;

	/**
	 * Creates a new XMLNode with no parent.
	 */
	public XMLNode() {
		this((XMLNode) null);
	}

	/**
	 * Creates a new XMLNode by parsing an XML source string.
	 *
	 * @param source the XML source string to parse
	 */
	public XMLNode(String source) {
		this();
		XMLParser.parseXML(this, source);
	}

	XMLNode(XMLNode parent) {
		super(parent);
		this.nodes = new ArrayList<>();
		this.attributes = new ArrayList<>();
		this.text = new XMLText(this);
		this.tag = null;
	}

	/**
	 * Retrieves the tag name of the node.
	 *
	 * @return the tag name
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * Sets the tag name of the node.
	 *
	 * @param tag the new tag name
	 * 
	 * @return a reference to this object
	 */
	public XMLNode setTag(String tag) {
		this.tag = tag;
		return this;
	}

	/**
	 * Retrieves a copy of all child nodes.
	 *
	 * @return a list of child nodes
	 */
	public List<XMLNode> getNodes() {
		return new ArrayList<>(nodes);
	}

	/**
	 * Retrieves a child node by index.
	 *
	 * @param index the index of the child node
	 * 
	 * @return the child {@link XMLNode} at the specified index
	 * 
	 * @throws IndexOutOfBoundsException if the index is out of range
	 */
	public XMLNode getNode(int index) {
		return nodes.get(index);
	}

	/**
	 * Retrieves the first child node with a matching tag.
	 *
	 * @param tag the tag to search for
	 * 
	 * @return the first matching {@link XMLNode}, or {@code null} if no match is found
	 */
	public XMLNode getNode(String tag) {
		for (XMLNode node : nodes)
			if (Objects.equals(node.getTag(), tag))
				return node;
		return null;
	}

	/**
	 * Retrieves an optional child node with a matching tag.
	 *
	 * @param tag the tag to search for
	 * 
	 * @return an {@link Optional} containing the first matching node
	 */
	public Optional<XMLNode> optNode(String tag) {
		return Optional.ofNullable(getNode(tag));
	}

	/**
	 * Retrieves child nodes matching a given predicate.
	 *
	 * @param predicate the condition to filter nodes
	 * 
	 * @return a list of nodes matching the predicate
	 */
	public List<XMLNode> getNodes(Predicate<XMLNode> predicate) {
		return nodes.stream().filter(predicate).collect(Collectors.toList());
	}

	/**
	 * Adds a new child node with the specified tag.
	 *
	 * @param tag the tag for the new node
	 * 
	 * @return the newly created node
	 */
	public XMLNode addNode(String tag) {
		XMLNode node = new XMLNode(this);
		node.setTag(tag);
		nodes.add(node);
		return node;
	}

	/**
	 * Retrieves an existing child node with the specified tag or creates a new one if it doesn't exist.
	 *
	 * @param tag the tag to find or create
	 * 
	 * @return an existing or newly created node
	 */
	public XMLNode getOrAddNode(String tag) {
		return optNode(tag).orElseGet(() -> addNode(tag));
	}

	/**
	 * Removes and returns a child node by index.
	 *
	 * @param index the index of the node to remove
	 * 
	 * @return the removed {@link XMLNode}
	 * 
	 * @throws IndexOutOfBoundsException if the index is out of range
	 */
	public XMLNode removeNode(int index) {
		return nodes.remove(index);
	}

	/**
	 * Removes and returns a child node with the specified tag.
	 *
	 * @param tag the tag of the node to remove
	 * 
	 * @return the removed {@link XMLNode}, or {@code null} if no matching node is found
	 */
	public XMLNode removeNode(String tag) {
		XMLNode node = getNode(tag);
		nodes.remove(node);
		return node;
	}

	/**
	 * Removes child nodes matching a given predicate.
	 *
	 * @param predicate the condition for node removal
	 * 
	 * @return a reference to this object
	 */
	public XMLNode removeNodes(Predicate<XMLNode> predicate) {
		nodes.removeIf(predicate);
		return this;
	}

	/**
	 * Sorts child nodes using the provided comparator.
	 *
	 * @param comparator the {@link Comparator} to use for sorting
	 * 
	 * @return a reference to this object
	 */
	public XMLNode sortNodes(Comparator<XMLNode> comparator) {
		Collections.sort(nodes, comparator);
		return this;
	}

	/**
	 * Removes all child nodes.
	 *
	 * @return a reference to this object
	 */
	public XMLNode clearNodes() {
		nodes.clear();
		return this;
	}

	/**
	 * Checks if a child node with the specified tag exists.
	 *
	 * @param tag the tag to search for
	 * 
	 * @return {@code true} if a node with the tag exists, {@code false} otherwise
	 */
	public boolean containsNodeTag(String tag) {
		for (XMLNode node : nodes)
			if (Objects.equals(node.getTag(), tag))
				return true;
		return false;
	}

	/**
	 * Gets the number of child nodes.
	 *
	 * @return the count of child nodes
	 */
	public int getNodeCount() {
		return nodes.size();
	}

	/**
	 * Checks if the node has any child nodes.
	 *
	 * @return {@code true} if there are child nodes, {@code false} otherwise
	 */
	public boolean hasNodes() {
		return !nodes.isEmpty();
	}

	/**
	 * Retrieves a copy of all attributes.
	 *
	 * @return a list of {@link XMLAttribute}s
	 */
	public List<XMLAttribute> getAttributes() {
		return new ArrayList<>(attributes);
	}

	/**
	 * Retrieves an attribute by index.
	 *
	 * @param index the index of the attribute
	 * 
	 * @return the {@link XMLAttribute} at the specified index
	 * 
	 * @throws IndexOutOfBoundsException if the index is out of range
	 */
	public XMLAttribute getAttribute(int index) {
		return attributes.get(index);
	}

	/**
	 * Retrieves the first attribute with a matching name.
	 *
	 * @param name the name to search for
	 * 
	 * @return the first matching {@link XMLAttribute}, or {@code null} if no match is found
	 */
	public XMLAttribute getAttribute(String name) {
		for (XMLAttribute attribute : attributes)
			if (Objects.equals(attribute.getName(), name))
				return attribute;
		return null;
	}

	/**
	 * Retrieves an optional attribute with a matching name.
	 *
	 * @param name the name to search for
	 * 
	 * @return an {@link Optional} containing the first matching {@link XMLAttribute}
	 */
	public Optional<XMLAttribute> optAttribute(String name) {
		return Optional.ofNullable(getAttribute(name));
	}

	/**
	 * Creates an optional {@link StringView} for the value of an attribute with a matching name.
	 *
	 * @param name the name to search for
	 * 
	 * @return an {@link Optional} containing {@link StringViewer} for the value of the first matching {@link XMLAttribute}
	 */
	public Optional<StringView> viewAttributeValue(String name) {
		return optAttribute(name).map(XMLAttribute::viewValue);
	}

	/**
	 * Retrieves attributes matching a given predicate.
	 *
	 * @param predicate the condition to filter attributes
	 * 
	 * @return a list of {@link XMLAttribute}s matching the predicate
	 */
	public List<XMLAttribute> getAttributes(Predicate<XMLAttribute> predicate) {
		return attributes.stream().filter(predicate).collect(Collectors.toList());
	}

	/**
	 * Adds a new attribute with the specified name.
	 *
	 * @param name the name for the new attribute
	 * 
	 * @return the newly created {@link XMLAttribute}
	 */
	public XMLAttribute addAttribute(String name) {
		XMLAttribute attribute = new XMLAttribute(this);
		attribute.setName(name);
		attributes.add(attribute);
		return attribute;
	}

	/**
	 * Retrieves an existing attribute with the specified name or creates a new one if it doesn't exist.
	 *
	 * @param name the name to find or create
	 * 
	 * @return an existing or newly created {@link XMLAttribute}
	 */
	public XMLAttribute getOrAddAttribute(String name) {
		return optAttribute(name).orElseGet(() -> addAttribute(name));
	}

	/**
	 * Removes and returns an attribute by index.
	 *
	 * @param index the index of the attribute to remove
	 * 
	 * @return the removed {@link XMLAttribute}
	 * 
	 * @throws IndexOutOfBoundsException if the index is out of range
	 */
	public XMLAttribute removeAttribute(int index) {
		return attributes.remove(index);
	}

	/**
	 * Removes and returns an attribute with the specified name.
	 *
	 * @param name the name of the attribute to remove
	 * 
	 * @return the removed {@link XMLAttribute}, or {@code null} if no matching attribute is found
	 */
	public XMLAttribute removeAttribute(String name) {
		XMLAttribute attribute = getAttribute(name);
		attributes.remove(attribute);
		return attribute;
	}

	/**
	 * Removes attributes matching a given predicate.
	 *
	 * @param predicate the condition for attribute removal
	 * 
	 * @return a reference to this object
	 */
	public XMLNode removeAttributes(Predicate<XMLAttribute> predicate) {
		attributes.removeIf(predicate);
		return this;
	}

	/**
	 * Sorts attributes using the provided comparator.
	 *
	 * @param comparator the {@link Comparator} to use for sorting
	 * 
	 * @return a reference to this object
	 */
	public XMLNode sortAttributes(Comparator<XMLAttribute> comparator) {
		Collections.sort(attributes, comparator);
		return this;
	}

	/**
	 * Removes all attributes.
	 *
	 * @return a reference to this object
	 */
	public XMLNode clearAttributes() {
		attributes.clear();
		return this;
	}

	/**
	 * Checks if an attribute with the specified name exists.
	 *
	 * @param name the name to search for
	 * 
	 * @return {@code true} if an attribute with the name exists, {@code false} otherwise
	 */
	public boolean containsAttributeName(String name) {
		for (XMLAttribute attribute : attributes)
			if (Objects.equals(attribute.getName(), name))
				return true;
		return false;
	}

	/**
	 * Gets the number of attributes.
	 *
	 * @return the count of attributes
	 */
	public int getAttributeCount() {
		return attributes.size();
	}

	/**
	 * Checks if the node has any attributes.
	 *
	 * @return {@code true} if there are attributes, {@code false} otherwise
	 */
	public boolean hasAttributes() {
		return !attributes.isEmpty();
	}

	/**
	 * Retrieves the text content of the node.
	 *
	 * @return the {@link XMLText} associated with this node
	 */
	public XMLText getText() {
		return text;
	}

	/**
	 * Checks if the node has non-empty text content.
	 *
	 * @return {@code true} if text content exists and is not just whitespace, {@code false} otherwise
	 */
	public boolean hasText() {
		return Objects.nonNull(text.getText()) && !text.getText().trim().isEmpty();
	}

	/**
	 * Checks if this is a root node (has no parent).
	 *
	 * @return {@code true} if the node has no parent, {@code false} otherwise
	 */
	public boolean isRoot() {
		return !hasParent();
	}

	/**
	 * Converts the node to its XML string representation with default tab indentation.
	 *
	 * @return the XML string representation of the node
	 */
	@Override
	public String toXMLString() {
		return toXMLString('\t');
	}

	/**
	 * Converts the node to its XML string representation with specified indentation.
	 *
	 * @param indent the character to use for indentation
	 * 
	 * @return the XML string representation of the node
	 */
	public String toXMLString(char indent) {
		return toXMLString(indent, 0);
	}

	private String toXMLString(char indent, int level) {
		String indentString = indentString(indent, level);
		StringBuilder builder = new StringBuilder();
		builder.append(indentString).append("<").append(tag);
		String nodeString = null;
		String textString = null;
		if (hasAttributes())
			builder.append(" ").append(attributes.stream().map(XMLAttribute::toXMLString).collect(Collectors.joining(" ")));
		if (hasNodes())
			nodeString = nodes.stream().map(node -> node.toXMLString(indent, level + 1)).collect(Collectors.joining("\n"));
		else if (hasText())
			textString = text.toXMLString();
		if (Objects.isNull(nodeString) && Objects.isNull(textString))
			builder.append("/>");
		else {
			builder.append(">");
			if (Objects.nonNull(nodeString))
				builder.append("\n").append(nodeString).append("\n").append(indentString);
			else if (Objects.nonNull(textString))
				builder.append(textString);
			builder.append("</").append(tag).append(">");
		}
		return builder.toString();
	}

	private String indentString(char indent, int level) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < level; i++)
			builder.append(indent);
		return builder.toString();
	}

	@Override
	public String toString() {
		return String.format("XMLNode [tag=%s, attributes=%s, nodes=%s, text=%s]", tag, attributes, nodes, text);
	}

}

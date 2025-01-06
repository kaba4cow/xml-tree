package com.kaba4cow.xmltree;

import java.util.Objects;
import java.util.function.Predicate;

public class XMLPredicates {

	private XMLPredicates() {}

	public static Predicate<XMLNode> nodeTagEquals(String tag) {
		return node -> Objects.equals(node.getTag(), tag);
	}

	public static Predicate<XMLNode> nodeTextEquals(String text) {
		return node -> Objects.equals(node.getText(), text);
	}

	public static Predicate<XMLNode> nodeHasAttribute(String name) {
		return node -> node.containsAttributeName(name);
	}

	public static Predicate<XMLNode> nodeHasAttribute(String name, String value) {
		return node -> node.containsAttributeName(name) && Objects.equals(node.getAttribute(name).getValue(), value);
	}

	public static Predicate<XMLAttribute> attributeNameEquals(String name) {
		return attribute -> Objects.equals(attribute.getName(), name);
	}

	public static Predicate<XMLAttribute> attributeValueEquals(String value) {
		return attribute -> Objects.equals(attribute.getValue(), value);
	}

}

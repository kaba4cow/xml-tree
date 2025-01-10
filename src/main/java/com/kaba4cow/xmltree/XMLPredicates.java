package com.kaba4cow.xmltree;

import java.util.Objects;
import java.util.function.Predicate;

public class XMLPredicates {

	private XMLPredicates() {}

	public static Predicate<XMLNode> withTag(String tag) {
		return node -> Objects.equals(node.getTag(), tag);
	}

	public static Predicate<XMLNode> withText(String text) {
		return node -> Objects.equals(node.getText(), text);
	}

	public static Predicate<XMLNode> withAttribute(String name) {
		return node -> node.containsAttributeName(name);
	}

	public static Predicate<XMLNode> withAttribute(String name, String value) {
		return node -> node.containsAttributeName(name) && Objects.equals(node.getAttribute(name).getValue(), value);
	}

	public static Predicate<XMLNode> withTagAndAttribute(String tag, String name, String value) {
		return node -> Objects.equals(node.getTag(), tag) && node.containsAttributeName(name)
				&& Objects.equals(node.getAttribute(name).getValue(), value);
	}

	public static Predicate<XMLAttribute> withName(String name) {
		return attribute -> Objects.equals(attribute.getName(), name);
	}

	public static Predicate<XMLAttribute> withValue(String value) {
		return attribute -> Objects.equals(attribute.getValue(), value);
	}

	public static Predicate<XMLAttribute> withNameAndValue(String name, String value) {
		return attribute -> Objects.equals(attribute.getName(), name) && Objects.equals(attribute.getValue(), value);
	}

}

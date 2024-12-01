package com.kaba4cow.xmltree;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

class XMLParser {

	private XMLParserNode root;

	public XMLParser() {
		this.root = null;
	}

	public void to(XMLNode output) {
		root.copy(output);
	}

	public XMLParser parse(String input) {
		Stack<XMLParserNode> stack = new Stack<>();
		StringBuilder builder = new StringBuilder();
		boolean inside = false;
		char[] chars = input.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (c == '<') {
				if (builder.length() > 0 && !stack.isEmpty()) {
					stack.peek().setText(builder.toString().trim());
					builder.setLength(0);
				}
				inside = true;
				if (i < chars.length - 1 && chars[i + 1] == '?') {
					i = skipUntil(chars, i, "?>");
					inside = false;
				} else if (i < chars.length - 3 && chars[i + 1] == '!' && chars[i + 2] == '-' && chars[i + 3] == '-') {
					i = skipUntil(chars, i, "-->");
					inside = false;
				}
			} else if (c == '>') {
				if (inside) {
					String tag = builder.toString().trim();
					builder.setLength(0);
					inside = false;
					if (tag.startsWith("/"))
						stack.pop();
					else {
						boolean selfClosing = tag.endsWith("/");
						if (selfClosing)
							tag = tag.substring(0, tag.length() - 1).trim();
						XMLParserNode node = parseTag(tag);
						if (stack.isEmpty())
							root = node;
						else
							stack.peek().addNode(node);
						if (!selfClosing)
							stack.push(node);
					}
				}
			} else
				builder.append(c);
		}
		return this;
	}

	private int skipUntil(char[] chars, int start, String end) {
		for (int i = start; i < chars.length - end.length() + 1; i++) {
			boolean match = true;
			for (int j = 0; j < end.length(); j++)
				if (chars[i + j] != end.charAt(j)) {
					match = false;
					break;
				}
			if (match)
				return i + end.length() - 1;
		}
		return chars.length - 1;
	}

	private XMLParserNode parseTag(String tag) {
		String[] parts = tag.split("\\s+", 2);
		XMLParserNode node = new XMLParserNode().setTag(parts[0]);
		if (parts.length > 1) {
			String[] attributes = parts[1].split("\\s+");
			for (String attribute : attributes) {
				int index = attribute.indexOf('=');
				if (index != -1) {
					String name = attribute.substring(0, index);
					String value = attribute.substring(index + 1).replace("\"", "");
					node.addAttribute(name, value);
				}
			}
		}
		return node;
	}

	private class XMLParserNode {

		private String tag;
		private String text;
		private final List<XMLParserNode> nodes;
		private final Map<String, String> attributes;

		private XMLParserNode() {
			this.tag = null;
			this.text = null;
			this.nodes = new ArrayList<>();
			this.attributes = new LinkedHashMap<>();
		}

		private void copy(XMLNode output) {
			output.setTag(tag);
			output.setText(text);
			for (Map.Entry<String, String> attribute : attributes.entrySet())
				output.addAttribute(attribute.getKey()).setValue(attribute.getValue());
			for (XMLParserNode node : nodes)
				node.copy(output.addNode(null));
		}

		private XMLParserNode setTag(String tag) {
			this.tag = tag;
			return this;
		}

		private XMLParserNode setText(String text) {
			this.text = text;
			return this;
		}

		private XMLParserNode addNode(XMLParserNode node) {
			this.nodes.add(node);
			return this;
		}

		private XMLParserNode addAttribute(String name, String value) {
			this.attributes.put(name, value);
			return this;
		}

	}

}

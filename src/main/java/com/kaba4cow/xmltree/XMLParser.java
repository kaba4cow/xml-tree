package com.kaba4cow.xmltree;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class XMLParser {

	private static final Pattern DECLARATION_PATTERN = Pattern.compile("<\\?xml.*?\\?>");
	private static final Pattern COMMENT_PATTERN = Pattern.compile("<!--.*?-->");
	private static final Pattern TAG_PATTERN = Pattern.compile("<([^\\s>/]+)([^>]*)>(.*?)</\\1>", Pattern.DOTALL);
	private static final Pattern SELF_CLOSING_TAG_PATTERN = Pattern.compile("<([^\\s>/]+)([^>]*?)/?>", Pattern.DOTALL);
	private static final Pattern NAMESPACE_PATTERN = Pattern.compile("xmlns:(\\w+)\\s*=\\s*\"([^\"]+)\"");
	private static final Pattern ATTRIBUTE_PATTERN = Pattern.compile("(\\w+)(?::?\\w*)?\\s*=\\s*\"([^\"]*)\"");

	private XMLParser() {}

	public static void parseXML(XMLNode node, String source) {
		source = source.trim();
		source = removeXmlDeclaration(source);
		source = removeComments(source);
		source = source.trim();
		Matcher fullTagMatcher = TAG_PATTERN.matcher(source);
		Matcher selfClosingTagMatcher = SELF_CLOSING_TAG_PATTERN.matcher(source);
		if (fullTagMatcher.matches())
			parseFullTag(node, fullTagMatcher);
		else if (selfClosingTagMatcher.matches())
			parseSelfClosingTag(node, selfClosingTagMatcher);
		else
			throw new IllegalArgumentException("Invalid XML structure");
	}

	private static void parseFullTag(XMLNode node, Matcher matcher) {
		String tagName = matcher.group(1);
		node.setTag(tagName);
		String attributeString = matcher.group(2);
		parseAttributes(node, attributeString);
		String content = matcher.group(3).trim();
		parseContent(node, content);
	}

	private static void parseSelfClosingTag(XMLNode node, Matcher matcher) {
		String tagName = matcher.group(1);
		node.setTag(tagName);
		String attributeString = matcher.group(2);
		parseAttributes(node, attributeString);
	}

	public static String removeXmlDeclaration(String source) {
		return DECLARATION_PATTERN.matcher(source).replaceAll("").trim();
	}

	public static String removeComments(String source) {
		return COMMENT_PATTERN.matcher(source).replaceAll("").trim();
	}

	public static void parseAttributes(XMLNode node, String attributeString) {
		Matcher namespaceMatcher = NAMESPACE_PATTERN.matcher(attributeString);
		while (namespaceMatcher.find()) {
			String prefix = namespaceMatcher.group(1);
			String uri = namespaceMatcher.group(2);
			node.addAttribute("xmlns:" + prefix).setValue(uri);
		}
		Matcher attributeMatcher = ATTRIBUTE_PATTERN.matcher(attributeString);
		while (attributeMatcher.find()) {
			String name = attributeMatcher.group(1);
			String value = attributeMatcher.group(2);
			if (!name.startsWith("xmlns")) {
				XMLAttribute attribute = node.addAttribute(name);
				attribute.setValue(value);
			}
		}
	}

	public static void parseContent(XMLNode node, String content) {
		content = content.trim();
		if (content.isEmpty())
			return;
		if (!content.contains("<")) {
			node.getText().setText(content);
			return;
		}
		Matcher fullTagMatcher = TAG_PATTERN.matcher(content);
		Matcher selfClosingTagMatcher = SELF_CLOSING_TAG_PATTERN.matcher(content);
		int lastIndex = 0;
		while (fullTagMatcher.find(lastIndex) || selfClosingTagMatcher.find(lastIndex)) {
			Matcher currentMatcher = fullTagMatcher.find(lastIndex) ? fullTagMatcher : selfClosingTagMatcher;
			if (currentMatcher.start() > lastIndex) {
				String textBefore = content.substring(lastIndex, currentMatcher.start()).trim();
				if (!textBefore.isEmpty())
					node.getText().setText(textBefore);
			}
			String matchedTag = currentMatcher.group(0);
			XMLNode childNode = node.addNode(null);
			parseXML(childNode, matchedTag);
			lastIndex = currentMatcher.end();
		}
		if (lastIndex < content.length()) {
			String remainingText = content.substring(lastIndex).trim();
			if (!remainingText.isEmpty())
				node.getText().setText(remainingText);
		}
	}

}
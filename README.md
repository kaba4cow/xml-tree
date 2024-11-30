# XML Tree Library

A lightweight **Java** library for parsing, creating, and manipulating **XML** documents. It provides a simple API for working with **XML** data.

## Features

- Create **XML** documents programmatically
- Parse **XML** from strings
- Manipulate **XML** nodes, attributes, and text content
- Flexible node and attribute management
- Easy serialization to **XML** string format
- Support for nested nodes and complex **XML** structures

## Key Classes

### XMLNode

The core class representing an **XML** element with the following capabilities:
- Add, remove, and manage child nodes
- Add, remove, and manipulate attributes
- Set and retrieve text content
- Serialize to **XML** string with customizable indentation

### XMLAttribute

Represents **XML** attributes with:
- Name and value management
- Unique attribute name validation

### XMLText

Handles text content within **XML** nodes with:
- Text setting and retrieval
- **XML**-safe string escaping

## Usage

- Creating an **XML** Document

```java
XMLNode root = new XMLNode();
root.setTag("root");

XMLNode book = root.addNode("foo");
book.addAttribute("bar").setValue(64);
book.getText().setText("The foo");

XMLNode author = book.addNode("baz");
author.getText().setText("The baz");

String xmlString = root.toXMLString();
```

- Parsing an Existing **XML** String

```java
XMLNode parsedDocument = new XMLNode("<root><foo id='105'>The foo</foo></root>");

XMLNode foo = parsedDocument.getNode("foo");
String id = book.getAttribute("id").getValue().toString();
```

- Advanced Node Manipulation

```java
List<XMLNode> filteredNodes = xmlNode.getNodes(node -> 
    node.hasAttribute("repo") && 
    node.getAttribute("repo").getValue().equals("central")
);

xmlNode.sortNodes(Comparator.comparing(node -> 
    node.getAttribute("id").getValue().toString()
));
```

## Key Methods

XMLNode:

- `addNode(String tag)`: Add a new child node
- `getNode(String tag)`: Retrieve a child node by tag
- `removeNode(String tag)`: Remove a specific node
- `getAttributes()`: Get all attributes
- `addAttribute(String name)`: Add a new attribute

XMLAttribute:

- `setName(String name)`: Set attribute name
- `setValue(Object value)`: Set attribute value

XMLText:

- `setText(String text)`: Set node text content

## Error Handling

- Throws `IllegalArgumentException` for duplicate attribute names
- Provides methods to safely access and manipulate **XML** structures
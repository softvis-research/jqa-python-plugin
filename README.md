# jQAssistant Python Plugin

This is a **Python** parser for [jQAssistant](https://jqassistant.org/). 
It enables jQAssistant to scan and to analyze **Python** related artifacts.

## Getting Started

Download the jQAssistant command line tool for your system: [jQAssistant - Get Started](https://jqassistant.org/get-started/).

Next download the latest version from the release tab. Put the `jqa-python-plugin-*.jar` into the plugins folder of the jQAssistant command
 line tool.

```bash
jqassistant.sh scan -f <file or directory path>
```

You can then start a local Neo4j server to start querying the database at [http://localhost:7474](http://localhost:7474):

```bash
jqassistant.sh server
```

## Build
Requirements:
* Java 8 
* Maven
```bash
git clone https://github.com/softvis-research/jqa-python-plugin
cd jqa-python-plugin
mvn clean install
```

## Model

![Neo4j model for the jQAssistant Jira plugin](./documents/model.PNG)

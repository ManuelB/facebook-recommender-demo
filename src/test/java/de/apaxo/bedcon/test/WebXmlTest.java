package de.apaxo.bedcon.test;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.junit.Test;

public class WebXmlTest {
	@Test
	public void testIsWebXmlValid() throws Exception {
		// http://www.edankert.com/validate.html
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		factory.setValidating(true);
		factory.setAttribute(
				"http://java.sun.com/xml/jaxp/properties/schemaLanguage",
				"http://www.w3.org/2001/XMLSchema");
		factory.setNamespaceAware(true);

		DocumentBuilder parser = factory.newDocumentBuilder();
		// rethrow all exception so test fails if web.xml is invalid
		parser.setErrorHandler(new ErrorHandler() {

			@Override
			public void warning(SAXParseException exception)
					throws SAXException {
				throw exception;
			}

			@Override
			public void fatalError(SAXParseException exception)
					throws SAXException {
				throw exception;
			}

			@Override
			public void error(SAXParseException exception) throws SAXException {
				throw exception;
			}
		});
		Document doc = parser.parse("src/main/webapp/WEB-INF/web.xml");

	}
}

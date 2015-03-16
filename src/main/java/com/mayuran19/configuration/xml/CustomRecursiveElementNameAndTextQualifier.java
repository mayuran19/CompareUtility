package com.mayuran19.configuration.xml;

import org.custommonkey.xmlunit.examples.RecursiveElementNameAndTextQualifier;
import org.w3c.dom.Element;

public class CustomRecursiveElementNameAndTextQualifier extends
		RecursiveElementNameAndTextQualifier {

	@Override
	public boolean qualifyForComparison(Element currentControl,
			Element currentTest) {
		return super.qualifyForComparison(currentControl, currentTest);
	}
	
}

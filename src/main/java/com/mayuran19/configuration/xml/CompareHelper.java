package com.mayuran19.configuration.xml;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.XMLTestCase;
import org.custommonkey.xmlunit.XMLUnit;
import org.custommonkey.xmlunit.examples.RecursiveElementNameAndTextQualifier;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.mayuran19.configuration.common.exception.ApplicationException;

public class CompareHelper extends XMLTestCase {
	private static final Logger LOGGER = Logger.getLogger(CompareHelper.class);

	public void isEqual1(File filePathOne, File filePathTwo) {
		try {
			XMLUnit.setNormalize(true);
			XMLUnit.setNormalizeWhitespace(true);
			XMLUnit.setIgnoreComments(true);
			XMLUnit.setIgnoreAttributeOrder(true);
			XMLUnit.setIgnoreWhitespace(false);
			InputSource inputSourceOne = new InputSource(new FileInputStream(
					filePathOne));
			InputSource inputSourceTwo = new InputSource(new FileInputStream(
					filePathTwo));
			try {
				assertXMLEqual(inputSourceOne, inputSourceTwo);
			} catch (SAXException e) {
				LOGGER.error("Error while comparing xmls", e);
			} catch (IOException e) {
				LOGGER.error("Error while comparing xmls", e);
			}
		} catch (FileNotFoundException e) {
			LOGGER.error("Error while comparing xmls", e);
		}
	}

	private DetailedDiff getDifferent(File controlXMLPath, File testXMLPath)
			throws ApplicationException {
		XMLUnit.setIgnoreComments(true);
		XMLUnit.setNormalize(true);
		XMLUnit.setNormalizeWhitespace(true);
		XMLUnit.setIgnoreAttributeOrder(true);
		XMLUnit.setIgnoreWhitespace(true);
		try {
			/*org.apache.xml.security.Init.init();
			Canonicalizer canon = Canonicalizer
					.getInstance(Canonicalizer.ALGO_ID_C14N_OMIT_COMMENTS);
			byte controlByte[] = canon.canonicalize(this
					.readFile(controlXMLPath));
			byte textByte[] = canon.canonicalize(this.readFile(testXMLPath));*/
			
			 Document controlXmlDocument = XMLUnit .buildControlDocument(new
			 InputSource(new FileInputStream( controlXMLPath))); Document
			 testXmlDocument = XMLUnit .buildTestDocument(new InputSource(new
			 FileInputStream( testXMLPath)));
			 
			 Transformer transform = TransformerFactory.newInstance().newTransformer(
							new StreamSource(new File("/home/mayuran/Dropbox/workspace/java/compare/src/main/resources/transformer.xsl")));
			 StreamResult controlStreamResult = new StreamResult(new ByteArrayOutputStream());
			 transform.transform(new DOMSource(controlXmlDocument), controlStreamResult);
			 System.out.println(new String(controlStreamResult.toString()));
			/*Document controlXmlDocument = XMLUnit
					.buildControlDocument(new String(controlByte));
			Document testXmlDocument = XMLUnit.buildTestDocument(new String(
					textByte));*/
			
			/*System.out.println(new String(controlByte));
			System.out.println(new String(textByte));*/

			Diff diff = new Diff(controlXmlDocument, testXmlDocument);
			diff.overrideElementQualifier(new RecursiveElementNameAndTextQualifier());
			diff.overrideDifferenceListener(new CustomDifferentListener());
			DetailedDiff detailedDiff = new DetailedDiff(diff);
			return detailedDiff;
		} catch (SAXException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ApplicationException(e);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ApplicationException(e);
		} /*catch (InvalidCanonicalizerException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ApplicationException(e);
		} catch (CanonicalizationException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ApplicationException(e);
		} catch (ParserConfigurationException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ApplicationException(e);
		}*/ catch (TransformerConfigurationException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ApplicationException(e);
		} catch (TransformerFactoryConfigurationError e) {
			LOGGER.error(e.getMessage(), e);
			throw new ApplicationException(e);
		} catch (TransformerException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ApplicationException(e);
		}
	}

	public void isEqual(File controlXMLPath, File testXMLPath) {
		try {
			DetailedDiff detailedDiff = this.getDifferent(controlXMLPath,
					testXMLPath);
			if (detailedDiff.similar()) {
				LOGGER.debug(controlXMLPath.getAbsoluteFile() + " = "
						+ testXMLPath.getAbsolutePath());
			} else {
				List<Difference> differences = detailedDiff.getAllDifferences();
				LOGGER.info(controlXMLPath.getAbsoluteFile() + " != "
						+ testXMLPath.getAbsolutePath());
				LOGGER.info("**************************");
				for (Difference difference : differences) {
					LOGGER.info(difference.toString());
				}
				LOGGER.info("**************************");
				LOGGER.info(System.lineSeparator());
			}
		} catch (ApplicationException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	public void normalize(Document doc) {
		Element root = doc.getDocumentElement();
		root.normalize();
	}

	public byte[] readFile(File file) {
		byte[] data = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			data = new byte[(int) file.length()];
			fis.read(data);
			fis.close();
		} catch (FileNotFoundException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return data;
	}
}

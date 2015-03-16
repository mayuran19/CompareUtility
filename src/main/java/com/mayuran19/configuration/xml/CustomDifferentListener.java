package com.mayuran19.configuration.xml;

import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.DifferenceConstants;
import org.custommonkey.xmlunit.DifferenceListener;
import org.w3c.dom.Node;

public class CustomDifferentListener implements DifferenceListener {
	private static final int[] IGNORE_VALUES = new int[] {
	/* DifferenceConstants.ATTR_VALUE.getId(), */
	DifferenceConstants.ATTR_VALUE_EXPLICITLY_SPECIFIED.getId(),
			/*DifferenceConstants.TEXT_VALUE.getId(),*/
			DifferenceConstants.ATTR_SEQUENCE.getId(),
			DifferenceConstants.CHILD_NODELIST_SEQUENCE.getId() };

	private boolean isIgnoredDifference(Difference difference) {
		int differenceId = difference.getId();
		for (int i = 0; i < IGNORE_VALUES.length; ++i) {
			if (differenceId == IGNORE_VALUES[i]) {
				return true;
			}
		}
		return false;
	}

	public int differenceFound(Difference difference) {
		if (isIgnoredDifference(difference)) {
			return RETURN_IGNORE_DIFFERENCE_NODES_SIMILAR;
		} else {
			return RETURN_ACCEPT_DIFFERENCE;
		}
	}

	public void skippedComparison(Node control, Node test) {
		System.out.println("");
	}

}

/**
 * 
 */
package com.dhemery.gibberizer.core;

public enum JoinStyle {
	SPACE(" "),
	NEW_LINE("\n"),
	BLANK_LINE("\n\n");
	
	final String delimiter;
	
	JoinStyle(String delimiter) {
		this.delimiter = delimiter;
	}
}
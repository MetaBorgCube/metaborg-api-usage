package org.metaborg.example.externalref.app.syntax;

public class StringLiteral extends Expression {

	protected final String value; 

	public StringLiteral(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}

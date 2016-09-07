package org.metaborg.example.api.analysis.syntax;

public class IntLiteral extends Expression {

	protected final int value; 

	public IntLiteral(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return Integer.toString(value);
	}
}

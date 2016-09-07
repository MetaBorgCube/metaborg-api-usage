package org.metaborg.example.api.oosyntax.syntax;

public class Variable extends Expression {

	protected final String id; 

	public Variable(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return id;
	}
}

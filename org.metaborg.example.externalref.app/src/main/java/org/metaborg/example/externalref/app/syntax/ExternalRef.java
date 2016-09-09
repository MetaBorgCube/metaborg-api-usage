package org.metaborg.example.externalref.app.syntax;

public class ExternalRef extends Expression {

	protected final String id; 

	public ExternalRef(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return id;
	}
}

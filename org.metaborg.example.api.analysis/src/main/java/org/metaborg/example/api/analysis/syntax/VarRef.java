package org.metaborg.example.api.analysis.syntax;

public class VarRef extends Expression {

	protected final String id; 

	public VarRef(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return id;
	}
}

package org.metaborg.example.api.oosyntax.syntax;

public class VarRef extends Expression {

	protected final Variable var; 

	public VarRef(Variable var) {
		this.var = var;
	}

	@Override
	public String toString() {
		return var.toString();
	}
}

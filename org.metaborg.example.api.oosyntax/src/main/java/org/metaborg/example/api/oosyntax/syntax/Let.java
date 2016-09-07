package org.metaborg.example.api.oosyntax.syntax;

public class Let extends Expression {

	protected final Variable var; 
	protected final Expression init; 
	protected final Expression body; 

	public Let(Variable var, Expression init, Expression body) {
		this.var = var;
		this.init = init;
		this.body = body;
	}

	@Override
	public String toString() {
		return "let " + var + " = " + init + " in " + body + " end";
	}
}

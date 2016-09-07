package org.metaborg.example.api.parsing.syntax;

public class Let extends Expression {

	protected final String id; 
	protected final Expression init; 
	protected final Expression body; 

	public Let(String id, Expression init, Expression body) {
		this.id = id;
		this.init = init;
		this.body = body;
	}
	
	@Override
	public String toString() {
		return "let " + id + " = " + init + " in " + body + " end";
	}

}

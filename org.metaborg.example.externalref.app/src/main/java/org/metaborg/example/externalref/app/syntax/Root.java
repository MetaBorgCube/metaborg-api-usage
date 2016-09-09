package org.metaborg.example.externalref.app.syntax;

public class Root {

	protected final Expression exp; 
	
	public Root(Expression exp) {
		this.exp = exp;
	}

	@Override
	public String toString() {
		
		return exp.toString();
	}
}

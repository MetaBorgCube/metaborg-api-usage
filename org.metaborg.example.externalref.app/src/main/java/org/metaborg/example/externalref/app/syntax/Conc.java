package org.metaborg.example.externalref.app.syntax;

public class Conc extends Expression {

	protected final Expression left; 
	protected final Expression right; 

	public Conc(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public String toString() {
		return left + " || " + right;
	}
}

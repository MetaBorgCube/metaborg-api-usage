package org.metaborg.example.externalref.app.syntax;

public class Add extends Expression {

	protected final Expression left; 
	protected final Expression right; 

	public Add(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public String toString() {
		return left + " + " + right;
	}
	
}

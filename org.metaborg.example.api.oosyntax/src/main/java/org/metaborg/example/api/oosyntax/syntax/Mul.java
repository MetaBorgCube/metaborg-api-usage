package org.metaborg.example.api.oosyntax.syntax;

public class Mul extends Expression {

	protected final Expression left; 
	protected final Expression right; 

	public Mul(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public String toString() {
		return parenthesize(left) + " * " + parenthesize(right);
	}

	private String parenthesize(Expression exp) {
		if (exp instanceof Add)
			return "(" + exp + ")";
		else 
			return exp.toString();
	}

}

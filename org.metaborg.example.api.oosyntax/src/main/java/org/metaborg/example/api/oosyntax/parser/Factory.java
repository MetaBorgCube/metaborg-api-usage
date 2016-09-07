package org.metaborg.example.api.oosyntax.parser;

import java.util.HashMap;
import java.util.Map;

import org.metaborg.example.api.oosyntax.SpoofaxUtil;
import org.metaborg.example.api.oosyntax.syntax.Add;
import org.metaborg.example.api.oosyntax.syntax.Expression;
import org.metaborg.example.api.oosyntax.syntax.IntLiteral;
import org.metaborg.example.api.oosyntax.syntax.Let;
import org.metaborg.example.api.oosyntax.syntax.Mul;
import org.metaborg.example.api.oosyntax.syntax.Root;
import org.metaborg.example.api.oosyntax.syntax.VarRef;
import org.metaborg.example.api.oosyntax.syntax.Variable;
import org.spoofax.interpreter.terms.IStrategoAppl;
import org.spoofax.interpreter.terms.IStrategoTerm;

public class Factory {

	private final Map<String, Variable> variables;
	
	public Factory() {
		this.variables = new HashMap<>();
	}

	public Root createRoot(IStrategoTerm term) {
		
		IStrategoAppl appl = SpoofaxUtil.castStrategoAppl(term);
		if ("Root".equals(appl.getConstructor().getName())) {
			Expression exp = createExpression(appl.getSubterm(0));
			return new Root(exp);
		} else throw new IllegalArgumentException(term.toString());
	}

	public Expression createExpression(IStrategoTerm term) {
		
		IStrategoAppl appl = SpoofaxUtil.castStrategoAppl(term);
		
		switch (appl.getConstructor().getName()) {

		case "IntLiteral": {

			String stringValue = SpoofaxUtil.convertStrategoString(appl.getSubterm(0));
			int value = Integer.parseInt(stringValue);
			return new IntLiteral(value);
		}
		case "Add": {

			Expression left = createExpression(appl.getSubterm(0));
			Expression right = createExpression(appl.getSubterm(1));
			return new Add(left, right);
		}
		case "Mul": {

			Expression left = createExpression(appl.getSubterm(0));
			Expression right = createExpression(appl.getSubterm(1));
			return new Mul(left, right);
		}
		case "Let": {

			String id = SpoofaxUtil.convertStrategoString(appl.getSubterm(0).getSubterm(0));
			Variable var = new Variable(id);
			variables.put(id, var);
			Expression init = createExpression(appl.getSubterm(0).getSubterm(1));
			Expression body = createExpression(appl.getSubterm(1));
			return new Let(var, init, body);
		}
		case "VarRef": {

			String id = SpoofaxUtil.convertStrategoString(appl.getSubterm(0));
			Variable var = variables.get(id);
			return new VarRef(var);
		}
		default:
			throw new IllegalArgumentException(term.toString());
		}

	}
}

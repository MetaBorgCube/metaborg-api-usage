package org.metaborg.example.externalref.app.parser;

import org.metaborg.example.externalref.app.SpoofaxUtil;
import org.metaborg.example.externalref.app.syntax.Add;
import org.metaborg.example.externalref.app.syntax.Conc;
import org.metaborg.example.externalref.app.syntax.Expression;
import org.metaborg.example.externalref.app.syntax.ExternalRef;
import org.metaborg.example.externalref.app.syntax.IntLiteral;
import org.metaborg.example.externalref.app.syntax.Mul;
import org.metaborg.example.externalref.app.syntax.Root;
import org.metaborg.example.externalref.app.syntax.StringLiteral;
import org.spoofax.interpreter.terms.IStrategoAppl;
import org.spoofax.interpreter.terms.IStrategoTerm;

public class Factory {

	public Factory() {
		
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
		case "StringLiteral": {

			String stringValue = SpoofaxUtil.convertStrategoString(appl.getSubterm(0));
			String value = stringValue.substring(1, stringValue.length()-1);
			return new StringLiteral(value);
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
		case "Conc": {

			Expression left = createExpression(appl.getSubterm(0));
			Expression right = createExpression(appl.getSubterm(1));
			return new Conc(left, right);
		}
		case "ExternalRef": {

			String id = SpoofaxUtil.convertStrategoString(appl.getSubterm(0));
			return new ExternalRef(id);
		}
		default:
			throw new IllegalArgumentException(term.toString());
		}

	}
}

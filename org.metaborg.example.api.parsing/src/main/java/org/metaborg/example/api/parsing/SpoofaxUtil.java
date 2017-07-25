package org.metaborg.example.api.parsing;


import org.spoofax.interpreter.terms.IStrategoAppl;
import org.spoofax.interpreter.terms.IStrategoString;
import org.spoofax.interpreter.terms.IStrategoTerm;

public final class SpoofaxUtil {

	public static IStrategoAppl castStrategoAppl(IStrategoTerm term) {
		if (term instanceof IStrategoAppl)
			return (IStrategoAppl) term;
		else
			throw new IllegalArgumentException(term.toString());

	}

	public static String convertStrategoString(IStrategoTerm term) {
		if (term instanceof IStrategoString)
			return ((IStrategoString) term).stringValue();
		else
			throw new IllegalArgumentException(term.toString());

	}

}

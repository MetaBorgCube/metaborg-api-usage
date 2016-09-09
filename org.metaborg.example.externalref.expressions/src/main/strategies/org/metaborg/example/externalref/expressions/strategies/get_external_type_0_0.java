package org.metaborg.example.externalref.expressions.strategies;

import java.util.Optional;

import org.metaborg.example.externalref.context.IDeclaration;
import org.metaborg.example.externalref.context.IExpressionContext;
import org.metaborg.example.externalref.context.Type;
import org.spoofax.interpreter.terms.IStrategoString;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.spoofax.interpreter.terms.ITermFactory;
import org.strategoxt.lang.Context;
import org.strategoxt.lang.Strategy;

/**
 * Example Java strategy implementation.
 *
 * This strategy can be used by editor services and can be called in Stratego
 * modules by declaring it as an external strategy as follows:
 *
 * <code>
 *  external get_external_type_0_0(|)
 * </code>
 *
 * @see InteropRegisterer This class registers get_external_type_0_0 for use.
 */
public class get_external_type_0_0 extends Strategy {

	public static get_external_type_0_0 instance = new get_external_type_0_0();

	protected IExpressionContext<Type> context;

	@Override
	public IStrategoTerm invoke(Context strategoContext, IStrategoTerm current) {

		String name = ((IStrategoString) current.getSubterm(0)).stringValue();

		Optional<IDeclaration<Type>> declaration = context.lookupDeclaration(name);

		if (declaration.isPresent()) {
			Type type = declaration.get().getType();
			return createTerm(strategoContext, type);
		}

		return null;
	}

	public void setContext(IExpressionContext<Type> context) {
		this.context = context;
	}

	public static IStrategoTerm createTerm(Context context, Type type) {

		ITermFactory factory = context.getFactory();

		switch (type) {

		case VARCHAR:

			return factory.makeAppl(factory.makeConstructor("StringTy", 0));

		case INT:

			return factory.makeAppl(factory.makeConstructor("IntTy", 0));

		default:
			throw new IllegalArgumentException(type.toString());
		}
	}

}

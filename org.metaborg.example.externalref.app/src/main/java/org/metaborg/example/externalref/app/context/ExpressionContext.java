package org.metaborg.example.externalref.app.context;

import java.util.Hashtable;
import java.util.Map;
import java.util.Optional;

import org.metaborg.example.externalref.context.IDeclaration;
import org.metaborg.example.externalref.context.IExpressionContext;
import org.metaborg.example.externalref.context.Type;

public class ExpressionContext implements IExpressionContext<Type> {

	final private Map<String, Declaration> decls = new Hashtable<>();
	
	public ExpressionContext() {
		decls.put("x", new Declaration(Type.INT));
		decls.put("y", new Declaration(Type.INT));
		decls.put("z", new Declaration(Type.VARCHAR));	
	}
	
	@Override
	public Optional<IDeclaration<Type>> lookupDeclaration(String name) {
		
		IDeclaration<Type> decl = decls.get(name);
		return Optional.of(decl);
	}
}

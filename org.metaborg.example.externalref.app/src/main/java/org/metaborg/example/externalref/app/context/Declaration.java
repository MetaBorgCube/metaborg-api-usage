package org.metaborg.example.externalref.app.context;

import org.metaborg.example.externalref.context.IDeclaration;
import org.metaborg.example.externalref.context.Type;

public class Declaration implements IDeclaration<Type> {

	final private Type type;
	
	public Declaration(Type type) {
		this.type = type;
	}
	
	@Override
	public Type getType() {
  	
		return type;
  }	

}

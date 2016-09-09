package org.metaborg.example.externalref.context;

import java.util.Optional;

public interface IExpressionContext<T> {

   Optional<IDeclaration<T>> lookupDeclaration(String name);
}

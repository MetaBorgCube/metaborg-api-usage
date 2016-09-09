package org.metaborg.example.api.parsing;


import java.util.Set;

import org.apache.commons.vfs2.FileObject;
import org.metaborg.core.MetaborgException;
import org.metaborg.core.language.ILanguageComponent;
import org.metaborg.core.language.ILanguageDiscoveryRequest;
import org.metaborg.core.language.ILanguageImpl;
import org.metaborg.core.language.LanguageUtils;
import org.metaborg.spoofax.core.Spoofax;
import org.spoofax.interpreter.terms.IStrategoAppl;
import org.spoofax.interpreter.terms.IStrategoString;
import org.spoofax.interpreter.terms.IStrategoTerm;

public final class SpoofaxUtil {

	public static ILanguageImpl getImplementation(Spoofax spoofax, String languageResource) throws MetaborgException {
		FileObject location = spoofax.resourceService.resolve("zip:" + languageResource + "!/");

		Iterable<ILanguageDiscoveryRequest> requests = spoofax.languageDiscoveryService.request(location);
		Iterable<ILanguageComponent> components = spoofax.languageDiscoveryService.discover(requests);
		Set<ILanguageImpl> implementations = LanguageUtils.toImpls(components);
		
		return LanguageUtils.active(implementations);
	}

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

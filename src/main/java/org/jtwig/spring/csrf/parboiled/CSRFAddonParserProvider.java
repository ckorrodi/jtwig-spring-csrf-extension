package org.jtwig.spring.csrf.parboiled;

import org.jtwig.parser.addon.AddonParserProvider;
import org.jtwig.parser.parboiled.node.AddonParser;

import java.util.Collection;
import java.util.Collections;

public class CSRFAddonParserProvider implements AddonParserProvider {
    @Override
    public Class<? extends AddonParser> parser() {
        return CSRFNodeParser.class;
    }

    @Override
    public Collection<String> keywords() {
        return Collections.singletonList(CSRFNodeParser.CSRF_TOKEN);
    }
}

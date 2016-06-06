package org.jtwig.spring.csrf.parboiled;

import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.LimitsParser;
import org.jtwig.parser.parboiled.base.PositionTrackerParser;
import org.jtwig.parser.parboiled.base.SpacingParser;
import org.jtwig.parser.parboiled.node.AddonParser;
import org.jtwig.spring.csrf.model.CSRFNode;
import org.parboiled.Rule;

public class CSRFNodeParser extends AddonParser {
    public static final String CSRF_TOKEN = "csrf";

    public CSRFNodeParser(ParserContext context) {
        super(CSRFNodeParser.class, context);
    }

    @Override
    public Rule NodeRule() {
        PositionTrackerParser trackerParser = parserContext().parser(PositionTrackerParser.class);
        LimitsParser limitsParser = parserContext().parser(LimitsParser.class);
        SpacingParser spacingParser = parserContext().parser(SpacingParser.class);
        return Sequence(
                trackerParser.PushPosition(),
                limitsParser.startCode(),
                spacingParser.Spacing(),
                String(CSRF_TOKEN),
                spacingParser.Spacing(),
                limitsParser.endCode(),

                push(new CSRFNode(trackerParser.pop()))
        );
    }
}

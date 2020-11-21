package cz.mg.compiler.tasks.mg.resolver.search.operator;

import cz.mg.collections.text.ReadableText;
import cz.mg.language.entities.mg.runtime.components.types.functions.MgLunaryOperator;
import cz.mg.language.entities.mg.runtime.parts.MgDatatype;
import cz.mg.compiler.tasks.mg.resolver.search.Source;


public class LunaryOperatorSearch<LunaryOperator extends MgLunaryOperator> extends UnaryOperatorSearch<LunaryOperator> {
    public LunaryOperatorSearch(Source source) {
        super(source);
    }

    public LunaryOperatorSearch(Source source, ReadableText requiredName) {
        super(source, requiredName);
    }

    public LunaryOperatorSearch(Source source, ReadableText requiredName, MgDatatype outputDatatype, MgDatatype inputDatatype) {
        super(source, requiredName, outputDatatype, inputDatatype);
    }

    @Override
    protected Class getType() {
        return MgLunaryOperator.class;
    }
}

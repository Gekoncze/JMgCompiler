package cz.mg.compiler.tasks.mg.resolver.search.operator;

import cz.mg.collections.text.ReadableText;
import cz.mg.language.entities.mg.runtime.components.types.functions.MgRunaryOperator;
import cz.mg.language.entities.mg.runtime.parts.MgDatatype;
import cz.mg.compiler.tasks.mg.resolver.search.Source;


public class RunaryOperatorSearch<RunaryOperator extends MgRunaryOperator> extends UnaryOperatorSearch<RunaryOperator> {
    public RunaryOperatorSearch(Source source) {
        super(source);
    }

    public RunaryOperatorSearch(Source source, ReadableText requiredName) {
        super(source, requiredName);
    }

    public RunaryOperatorSearch(Source source, ReadableText requiredName, MgDatatype outputDatatype, MgDatatype inputDatatype) {
        super(source, requiredName, outputDatatype, inputDatatype);
    }

    @Override
    protected Class getType() {
        return MgRunaryOperator.class;
    }
}

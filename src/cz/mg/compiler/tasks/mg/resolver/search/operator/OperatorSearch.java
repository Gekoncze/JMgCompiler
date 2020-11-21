package cz.mg.compiler.tasks.mg.resolver.search.operator;

import cz.mg.collections.text.ReadableText;
import cz.mg.language.entities.mg.runtime.components.types.functions.MgOperator;
import cz.mg.compiler.tasks.mg.resolver.search.ComponentSearch;
import cz.mg.compiler.tasks.mg.resolver.search.Source;


public class OperatorSearch<Operator extends MgOperator> extends ComponentSearch<Operator> {
    public OperatorSearch(Source source) {
        super(source);
    }

    public OperatorSearch(Source source, ReadableText requiredName) {
        super(source, requiredName);
    }

    @Override
    protected Class getType() {
        return MgOperator.class;
    }
}

package cz.mg.compiler.tasks.mg.resolver.search.operator;

import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.annotations.storage.Value;
import cz.mg.collections.text.ReadableText;
import cz.mg.language.entities.mg.runtime.components.types.functions.MgUnaryOperator;
import cz.mg.language.entities.mg.runtime.parts.MgDatatype;
import cz.mg.compiler.tasks.mg.resolver.search.Source;


public abstract class UnaryOperatorSearch<UnaryOperator extends MgUnaryOperator> extends OperatorSearch<UnaryOperator> {
    @Optional @Value
    private final MgDatatype output;

    @Optional @Value
    private final MgDatatype input;

    public UnaryOperatorSearch(
        @Mandatory Source source
    ) {
        this(source, null);
    }

    public UnaryOperatorSearch(
        @Mandatory Source source,
        @Optional ReadableText requiredName
    ) {
        this(source, requiredName, null, null);
    }

    public UnaryOperatorSearch(
        @Mandatory Source source,
        @Optional ReadableText requiredName,
        @Optional MgDatatype output,
        @Optional MgDatatype input
    ) {
        super(source, requiredName);
        this.output = output;
        this.input = input;

        addFilter(this::filterByOutputCount);
        addFilter(this::filterByInputCount);

        if(output != null){
            addFilter(this::filterByOutput);
        }

        if(input != null){
            addFilter(this::filterByInput);
        }
    }

    @Override
    protected Class getType() {
        return MgUnaryOperator.class;
    }

    private boolean filterByOutputCount(UnaryOperator operator){
        return operator.getOutputVariables().count() == 1;
    }

    private boolean filterByInputCount(UnaryOperator operator){
        return operator.getInputVariables().count() == 1;
    }

    private boolean filterByOutput(UnaryOperator operator){
        return MgDatatype.isCompatible(
            output,
            operator.getOutputVariables().getFirst().getDatatype()
        );
    }

    private boolean filterByInput(UnaryOperator operator) {
        return MgDatatype.isCompatible(
            operator.getInputVariables().getFirst().getDatatype(),
            input
        );
    }
}

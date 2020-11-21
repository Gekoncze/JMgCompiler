package cz.mg.compiler.tasks.mg.resolver.search.operator;

import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.annotations.storage.Value;
import cz.mg.collections.text.ReadableText;
import cz.mg.language.entities.mg.runtime.components.types.functions.MgBinaryOperator;
import cz.mg.language.entities.mg.runtime.parts.MgDatatype;
import cz.mg.compiler.tasks.mg.resolver.search.Source;


public class BinaryOperatorSearch<BinaryOperator extends MgBinaryOperator> extends OperatorSearch<BinaryOperator> {
    @Optional @Value
    private final MgDatatype output;

    @Optional @Value
    private final MgDatatype leftInput;

    @Optional @Value
    private final MgDatatype rightInput;


    public BinaryOperatorSearch(
        @Mandatory Source source
    ) {
        this(source, null);
    }

    public BinaryOperatorSearch(
        @Mandatory Source source,
        @Optional ReadableText requiredName
    ) {
        this(source, requiredName, null, null, null);
    }

    public BinaryOperatorSearch(
        @Mandatory Source source,
        @Optional ReadableText requiredName,
        @Optional MgDatatype output,
        @Optional MgDatatype leftInput,
        @Optional MgDatatype rightInput
    ) {
        super(source, requiredName);
        this.output = output;
        this.leftInput = leftInput;
        this.rightInput = rightInput;

        addFilter(this::filterByOutputCount);
        addFilter(this::filterByInputCount);

        if(output != null){
            addFilter(this::filterByOutput);
        }

        if(leftInput != null){
            addFilter(this::filterByLeftInput);
        }

        if(rightInput != null){
            addFilter(this::filterByRightInput);
        }
    }

    @Override
    protected Class getType() {
        return MgBinaryOperator.class;
    }

    private boolean filterByOutputCount(BinaryOperator operator){
        return operator.getOutputVariables().count() == 1;
    }

    private boolean filterByInputCount(BinaryOperator operator){
        return operator.getInputVariables().count() == 2;
    }

    private boolean filterByOutput(BinaryOperator operator){
        return MgDatatype.isCompatible(
            output,
            operator.getOutputVariables().getFirst().getDatatype()
        );
    }

    private boolean filterByLeftInput(BinaryOperator operator) {
        return MgDatatype.isCompatible(
            operator.getInputVariables().getFirst().getDatatype(),
            leftInput
        );
    }

    private boolean filterByRightInput(BinaryOperator operator) {
        return MgDatatype.isCompatible(
            operator.getInputVariables().getFirst().getDatatype(),
            rightInput
        );
    }
}

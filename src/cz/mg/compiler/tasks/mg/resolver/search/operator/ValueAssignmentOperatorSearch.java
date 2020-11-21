package cz.mg.compiler.tasks.mg.resolver.search.operator;

import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.annotations.storage.Value;
import cz.mg.collections.text.ReadableText;
import cz.mg.language.entities.mg.runtime.components.types.functions.MgBinaryOperator;
import cz.mg.language.entities.mg.runtime.parts.MgDatatype;
import cz.mg.compiler.tasks.mg.resolver.search.Source;


public class ValueAssignmentOperatorSearch<ValueAssignmentOperator extends MgBinaryOperator> extends OperatorSearch<ValueAssignmentOperator> {
    @Optional @Value
    private final MgDatatype leftInput;

    @Optional @Value
    private final MgDatatype rightInput;

    public ValueAssignmentOperatorSearch(
        @Mandatory Source source,
        @Optional ReadableText requiredName,
        @Optional MgDatatype leftInput,
        @Optional MgDatatype rightInput
    ) {
        super(source, requiredName);
        this.leftInput = leftInput;
        this.rightInput = rightInput;

        addFilter(this::filterByOutputCount);
        addFilter(this::filterByInputCount);

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

    private boolean filterByOutputCount(ValueAssignmentOperator operator){
        return operator.getOutputVariables().count() == 0;
    }

    private boolean filterByInputCount(ValueAssignmentOperator operator){
        return operator.getInputVariables().count() == 2;
    }

    private boolean filterByLeftInput(ValueAssignmentOperator operator) {
        return MgDatatype.isCompatible(
            operator.getInputVariables().getFirst().getDatatype(),
            leftInput
        );
    }

    private boolean filterByRightInput(ValueAssignmentOperator operator) {
        return MgDatatype.isCompatible(
            operator.getInputVariables().getFirst().getDatatype(),
            rightInput
        );
    }
}

package cz.mg.compiler.tasks.mg.resolver.command.utilities;

import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.text.ReadableText;
import cz.mg.compiler.annotations.Cache;
import cz.mg.language.entities.mg.logical.parts.expressions.MgLogicalOperatorExpression;


public class CachedLogicalOperatorExpression extends MgLogicalOperatorExpression {
    @Mandatory @Cache
    private final OperatorInfo operatorInfo;

    public CachedLogicalOperatorExpression(ReadableText name, OperatorInfo operatorInfo) {
        super(name);
        this.operatorInfo = operatorInfo;
    }

    public OperatorInfo getOperatorInfo() {
        return operatorInfo;
    }
}

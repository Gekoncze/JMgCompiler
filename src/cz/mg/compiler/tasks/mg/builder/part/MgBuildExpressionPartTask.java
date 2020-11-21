package cz.mg.compiler.tasks.mg.builder.part;

import cz.mg.collections.list.List;
import cz.mg.compiler.annotations.Output;
import cz.mg.language.LanguageException;
import cz.mg.language.entities.mg.logical.parts.expressions.*;
import cz.mg.language.entities.mg.logical.parts.expressions.calls.MgLogicalNameCallExpression;
import cz.mg.language.entities.mg.logical.parts.expressions.calls.MgLogicalValueCallExpression;
import cz.mg.language.entities.text.structured.Part;
import cz.mg.language.entities.text.structured.parts.Clump;
import cz.mg.language.entities.text.structured.parts.leaves.Name;
import cz.mg.language.entities.text.structured.parts.leaves.Operator;
import cz.mg.language.entities.text.structured.parts.leaves.Value;


public class MgBuildExpressionPartTask extends MgBuildPartTask {
    @Output
    private MgLogicalClumpExpression expression;

    public MgBuildExpressionPartTask(List<Part> parts) {
        super(parts);
    }

    public MgLogicalClumpExpression getExpression() {
        return expression;
    }

    @Override
    protected void buildParts(List<Part> parts) {
        checkNotEmpty();
        expression = buildClump(parts);
    }

    private static MgLogicalExpression build(Part part){
        if(part instanceof Name){
            return buildName((Name) part);
        }

        if(part instanceof Operator){
            return buildOperator((Operator) part);
        }

        if(part instanceof Value){
            return buildValue((Value) part);
        }

        if(part instanceof Clump){
            return buildClump((Clump) part);
        }

        throw new LanguageException("Unsupported part " + part.getClass().getSimpleName() + " in expression.");
    }

    private static MgLogicalOperatorExpression buildOperator(Operator operator) {
        return new MgLogicalOperatorExpression(operator.getText());
    }

    private static MgLogicalNameCallExpression buildName(Name name){
        return new MgLogicalNameCallExpression(name.getText());
    }

    private static MgLogicalValueCallExpression buildValue(Value value){
        return new MgLogicalValueCallExpression(value.getText());
    }

    private static MgLogicalClumpExpression buildClump(Clump clump){
        return buildClump(clump.getParts());
    }

    private static MgLogicalClumpExpression buildClump(List<Part> parts){
        List<MgLogicalExpression> expressions = new List<>();
        for(Part part : parts){
            expressions.addLast(build(part));
        }
        return new MgLogicalClumpExpression(expressions);
    }
}

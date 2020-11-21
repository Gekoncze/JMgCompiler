package cz.mg.compiler.tasks.mg.builder.part;

import cz.mg.collections.list.List;
import cz.mg.compiler.annotations.Output;
import cz.mg.language.LanguageException;
import cz.mg.language.entities.mg.unresolved.parts.expressions.*;
import cz.mg.language.entities.mg.unresolved.parts.expressions.calls.MgUnresolvedNameCallExpression;
import cz.mg.language.entities.mg.unresolved.parts.expressions.calls.MgUnresolvedValueCallExpression;
import cz.mg.language.entities.text.structured.Part;
import cz.mg.language.entities.text.structured.parts.Clump;
import cz.mg.language.entities.text.structured.parts.leaves.Name;
import cz.mg.language.entities.text.structured.parts.leaves.Operator;
import cz.mg.language.entities.text.structured.parts.leaves.Value;


public class MgBuildExpressionPartTask extends MgBuildPartTask {
    @Output
    private MgUnresolvedClumpExpression expression;

    public MgBuildExpressionPartTask(List<Part> parts) {
        super(parts);
    }

    public MgUnresolvedClumpExpression getExpression() {
        return expression;
    }

    @Override
    protected void buildParts(List<Part> parts) {
        checkNotEmpty();
        expression = buildClump(parts);
    }

    private static MgUnresolvedExpression build(Part part){
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

    private static MgUnresolvedOperatorExpression buildOperator(Operator operator) {
        return new MgUnresolvedOperatorExpression(operator.getText());
    }

    private static MgUnresolvedNameCallExpression buildName(Name name){
        return new MgUnresolvedNameCallExpression(name.getText());
    }

    private static MgUnresolvedValueCallExpression buildValue(Value value){
        return new MgUnresolvedValueCallExpression(value.getText());
    }

    private static MgUnresolvedClumpExpression buildClump(Clump clump){
        return buildClump(clump.getParts());
    }

    private static MgUnresolvedClumpExpression buildClump(List<Part> parts){
        List<MgUnresolvedExpression> expressions = new List<>();
        for(Part part : parts){
            expressions.addLast(build(part));
        }
        return new MgUnresolvedClumpExpression(expressions);
    }
}

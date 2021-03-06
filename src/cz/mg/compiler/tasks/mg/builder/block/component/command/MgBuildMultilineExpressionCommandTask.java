package cz.mg.compiler.tasks.mg.builder.block.component.command;

import cz.mg.collections.Clump;
import cz.mg.collections.list.List;
import cz.mg.compiler.annotations.Cache;
import cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor;
import cz.mg.compiler.tasks.mg.builder.pattern.Count;
import cz.mg.compiler.tasks.mg.builder.pattern.Order;
import cz.mg.compiler.tasks.mg.builder.pattern.Pattern;
import cz.mg.compiler.tasks.mg.builder.pattern.Requirement;
import cz.mg.language.entities.mg.Operators;
import cz.mg.language.entities.mg.unresolved.parts.expressions.MgUnresolvedClumpExpression;
import cz.mg.language.entities.mg.unresolved.parts.expressions.MgUnresolvedExpression;
import cz.mg.language.entities.mg.unresolved.parts.expressions.MgUnresolvedOperatorExpression;
import cz.mg.language.entities.text.structured.Block;


public abstract class MgBuildMultilineExpressionCommandTask extends MgBuildCommandTask {
    private static final Clump<Pattern> PATTERNS = new List<>(
        new Pattern(
            Order.RANDOM,
            Requirement.OPTIONAL,
            Count.MULTIPLE,
            new BlockProcessor<>(
                MgBuildExpressionCommandTask.class,
                MgBuildMultilineExpressionCommandTask.class,
                (source, destination) -> {
                    if(destination.lineExpressions == null) destination.lineExpressions = new List<>();
                    destination.lineExpressions.addLast(source.getCommand().getExpression());
                }
            )
        )
    );

    @Cache
    private List<MgUnresolvedExpression> lineExpressions;

    public MgBuildMultilineExpressionCommandTask(Block block) {
        super(block);
    }

    @Override
    protected void onRun() {
        super.onRun();
        addLineExpressions();
    }

    private void addLineExpressions(){
        if(lineExpressions != null){
            MgUnresolvedClumpExpression expression = getExpression();
            if(expression == null) setExpression(expression = new MgUnresolvedClumpExpression());
            expression.getExpressions().addLast(createListExpression());
        }
    }

    private MgUnresolvedClumpExpression createListExpression(){
        MgUnresolvedClumpExpression expression = new MgUnresolvedClumpExpression();
        for(MgUnresolvedExpression lineExpression : lineExpressions){
            expression.getExpressions().addLast(lineExpression);
            expression.getExpressions().addLast(new MgUnresolvedOperatorExpression(Operators.GROUP));
        }
        expression.getExpressions().removeLast();
        return expression;
    }

    @Override
    protected Clump<Pattern> getPatterns() {
        return PATTERNS;
    }

    public abstract MgUnresolvedClumpExpression getExpression();
    public abstract void setExpression(MgUnresolvedClumpExpression expression);
}

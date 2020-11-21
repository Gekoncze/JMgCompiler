package cz.mg.compiler.tasks.mg.builder.block.root.command;

import cz.mg.collections.Clump;
import cz.mg.collections.list.List;
import cz.mg.compiler.annotations.Cache;
import cz.mg.compiler.tasks.mg.builder.pattern.Order;
import cz.mg.language.entities.mg.Operators;
import cz.mg.language.entities.mg.logical.parts.expressions.MgLogicalClumpExpression;
import cz.mg.language.entities.mg.logical.parts.expressions.MgLogicalExpression;
import cz.mg.language.entities.mg.logical.parts.expressions.MgLogicalOperatorExpression;
import cz.mg.language.entities.text.structured.Block;


public abstract class MgBuildMultilineExpressionCommandTask extends MgBuildCommandTask {
    private static final Clump<cz.mg.compiler.tasks.mg.builder.pattern.Pattern> PATTERNS = new List<>(
        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            Order.RANDOM,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.OPTIONAL,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.MULTIPLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
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
    private List<MgLogicalExpression> lineExpressions;

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
            MgLogicalClumpExpression expression = getExpression();
            if(expression == null) setExpression(expression = new MgLogicalClumpExpression());
            expression.getExpressions().addLast(createListExpression());
        }
    }

    private MgLogicalClumpExpression createListExpression(){
        MgLogicalClumpExpression expression = new MgLogicalClumpExpression();
        for(MgLogicalExpression lineExpression : lineExpressions){
            expression.getExpressions().addLast(lineExpression);
            expression.getExpressions().addLast(new MgLogicalOperatorExpression(Operators.GROUP));
        }
        expression.getExpressions().removeLast();
        return expression;
    }

    @Override
    protected Clump<cz.mg.compiler.tasks.mg.builder.pattern.Pattern> getPatterns() {
        return PATTERNS;
    }

    public abstract MgLogicalClumpExpression getExpression();
    public abstract void setExpression(MgLogicalClumpExpression expression);
}

package cz.mg.compiler.tasks.mg.resolver.command.expression;

import cz.mg.collections.list.List;
import cz.mg.collections.list.ListItem;
import cz.mg.collections.text.ReadableText;
import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext;
import cz.mg.language.LanguageException;
import cz.mg.language.entities.mg.Operators;
import cz.mg.language.entities.mg.unresolved.parts.MgUnresolvedDatatype;
import cz.mg.language.entities.mg.unresolved.parts.expressions.MgUnresolvedClumpExpression;
import cz.mg.language.entities.mg.unresolved.parts.expressions.MgUnresolvedExpression;
import cz.mg.language.entities.mg.unresolved.parts.expressions.MgUnresolvedOperatorExpression;
import cz.mg.language.entities.mg.unresolved.parts.expressions.calls.*;
import cz.mg.language.entities.mg.unresolved.parts.expressions.calls.operator.MgUnresolvedBinaryOperatorCallExpression;
import cz.mg.language.entities.mg.unresolved.parts.expressions.calls.operator.MgUnresolvedEmptyCallExpression;
import cz.mg.language.entities.mg.unresolved.parts.expressions.calls.operator.MgUnresolvedLunaryOperatorCallExpression;
import cz.mg.language.entities.mg.unresolved.parts.expressions.calls.operator.MgUnresolvedRunaryOperatorCallExpression;
import cz.mg.language.entities.mg.runtime.parts.MgDatatype;
import cz.mg.language.entities.mg.runtime.parts.commands.MgBlockCommand;
import cz.mg.compiler.tasks.mg.builder.part.MgBuildDeclarationTask;
import cz.mg.compiler.tasks.mg.resolver.MgResolveTask;
import cz.mg.compiler.tasks.mg.resolver.command.utilities.CachedLogicalOperatorExpression;
import cz.mg.language.entities.mg.runtime.utilities.DeclarationHelper;
import cz.mg.compiler.tasks.mg.resolver.command.utilities.OperatorCache;
import cz.mg.compiler.tasks.mg.resolver.command.utilities.OperatorInfo;
import cz.mg.compiler.tasks.mg.resolver.link.MgResolveVariableDatatypeTask;


public class MgResolveExpressionTreeTask extends MgResolveTask {
    @Input
    private final CommandContext context;

    @Input
    private final MgUnresolvedClumpExpression logicalClumpExpression;

    @Output
    private MgUnresolvedCallExpression logicalCallExpression;

    public MgResolveExpressionTreeTask(CommandContext context, MgUnresolvedClumpExpression logicalClumpExpression) {
        this.context = context;
        this.logicalClumpExpression = logicalClumpExpression;
    }

    public MgUnresolvedCallExpression getLogicalCallExpression() {
        return logicalCallExpression;
    }

    @Override
    protected void onRun() {
        List<MgUnresolvedExpression> expressions = prepareExpressions(logicalClumpExpression);

        resolveDeclarations(expressions);
        resolveFunctionCalls(expressions);
        resolveMemberCalls(expressions);
        resolveOperatorCalls(expressions);
        resolveGroupCalls(expressions);

        if(expressions.count() == 0){
            expressions.addLast(new MgUnresolvedEmptyCallExpression());
        }

        if(expressions.count() != 1) {
            throw new LanguageException("Illegal expression.");
        }

        if(!(expressions.getFirst() instanceof MgUnresolvedCallExpression)) {
            throw new LanguageException("Illegal expression.");
        }

        logicalCallExpression = (MgUnresolvedCallExpression) expressions.getFirst();
    }

    private void resolveDeclarations(List<MgUnresolvedExpression> operators){
        for(
            ListItem<MgUnresolvedExpression> item = operators.getFirstItem();
            item != null;
            item = item.getNextItem()
        ){
            if(isPlainName(item)){
                if(isOperator(item.getNextItem())){
                    if(isPlainName(item.getNextItem().getNextItem())){
                        ReadableText typeName = ((MgUnresolvedNameCallExpression)item.get()).getName();
                        ReadableText operator = ((MgUnresolvedOperatorExpression)item.getNext()).getName();
                        ReadableText name = ((MgUnresolvedNameCallExpression)item.getNextItem().getNext()).getName();
                        MgUnresolvedDatatype logicalDatatype = MgBuildDeclarationTask.createDatatype(typeName, operator);
                        if(logicalDatatype != null){
                            MgResolveVariableDatatypeTask task = new MgResolveVariableDatatypeTask(context, logicalDatatype);
                            task.run();
                            MgDatatype datatype = task.getDatatype();

                            if(context.getCommand() instanceof MgBlockCommand){
                                context.getDeclaredVariables().addLast(
                                    DeclarationHelper.newVariable(name, datatype)
                                );
                            } else {
                                context.getOuterContext().getDeclaredVariables().addLast(
                                    DeclarationHelper.newVariable(name, datatype)
                                );
                            }

                            mergeBinary(
                                item.getNextItem(),
                                (left, right) -> new MgUnresolvedNameCallExpression(name)
                            );
                        }
                    }
                }
            }
        }
    }

    private void resolveFunctionCalls(List<MgUnresolvedExpression> operators){
        for(
            ListItem<MgUnresolvedExpression> item = operators.getFirstItem();
            item != null;
            item = item.getNextItem()
        ){
            if(isPlainName(item)){
                if(isCall(item.getNextItem())){
                    MgUnresolvedNameCallExpression nameCallExpression = (MgUnresolvedNameCallExpression) item.get();
                    mergeLunary(
                        item,
                        expression -> new MgUnresolvedFunctionCallExpression(
                            nameCallExpression.getName(),
                            expression
                        )
                    );
                }
            }
        }
    }

    private void resolveMemberCalls(List<MgUnresolvedExpression> operators){
        for(
            ListItem<MgUnresolvedExpression> item = operators.getFirstItem();
            item != null;
            item = item.getNextItem()
        ){
            if(isMemberAccessOperator(item)){
                mergeBinary(item, MgResolveExpressionTreeTask::createMemberNameCallExpression);
            }
        }
    }

    private void resolveOperatorCalls(List<MgUnresolvedExpression> operators){
        OperatorCache operatorCache = context.getOperatorCache();
        for(int p = operatorCache.getMaxPriority(); p >= operatorCache.getMinPriority(); p--){
            for(
                ListItem<MgUnresolvedExpression> item = operators.getFirstItem();
                item != null;
                item = item.getNextItem()
            ){
                MgUnresolvedExpression expression = item.get();
                if(expression instanceof CachedLogicalOperatorExpression){
                    CachedLogicalOperatorExpression logicalOperatorExpression = (CachedLogicalOperatorExpression) expression;
                    OperatorInfo operatorInfo = logicalOperatorExpression.getOperatorInfo();
                    if(operatorInfo.getPriority() == p){
                        switch(operatorInfo.getPosition()){
                            case MIDDLE:
                                mergeBinary(item, (leftOperand, rightOperand) -> {
                                    return new MgUnresolvedBinaryOperatorCallExpression(
                                        logicalOperatorExpression.getName(),
                                        leftOperand,
                                        rightOperand
                                    );
                                });
                                break;
                            case LEFT:
                                mergeLunary(item, operand -> {
                                    return new MgUnresolvedLunaryOperatorCallExpression(
                                        logicalOperatorExpression.getName(),
                                        operand
                                    );
                                });
                                break;
                            case RIGHT:
                                mergeRunary(item, operand -> {
                                    return new MgUnresolvedRunaryOperatorCallExpression(
                                        logicalOperatorExpression.getName(),
                                        operand
                                    );
                                });
                                break;
                            default: throw new RuntimeException();
                        }
                    }
                }
            }
        }
    }

    private void resolveGroupCalls(List<MgUnresolvedExpression> operators){
        for(
            ListItem<MgUnresolvedExpression> item = operators.getFirstItem();
            item != null;
            item = item.getNextItem()
        ){
            if(isGroupOperator(item)){
                ListItem<MgUnresolvedExpression> leftItem = item.getPreviousItem();
                if(isGroup(leftItem)){
                    mergeBinary(item, (leftExpression, rightExpression) -> {
                        MgUnresolvedGroupCallExpression group = (MgUnresolvedGroupCallExpression) leftExpression;
                        group.getExpressions().addLast(rightExpression);
                        return group;
                    });
                } else {
                    mergeBinary(item, (leftExpression, rightExpression) -> {
                        return new MgUnresolvedGroupCallExpression(new List<>(leftExpression, rightExpression));
                    });
                }
            }
        }
    }

    private boolean isGroup(ListItem<MgUnresolvedExpression> item){
        if(item == null) return false;
        return item.get() instanceof MgUnresolvedGroupCallExpression;
    }

    private boolean isPlainName(ListItem<MgUnresolvedExpression> item){
        if(item == null) return false;
        if(item.get() instanceof MgUnresolvedNameCallExpression){
            // todo - might be redundant, parametrized should not be created yet
            return ((MgUnresolvedNameCallExpression) item.get()).getExpression() == null;
        }
        return false;
    }

    private boolean isCall(ListItem<MgUnresolvedExpression> item){
        if(item == null) return false;
        return item.get() instanceof MgUnresolvedCallExpression;
    }

    private boolean isMemberAccessOperator(ListItem<MgUnresolvedExpression> item){
        return isOperator(item, Operators.MEMBER_ACCESS);
    }

    private boolean isGroupOperator(ListItem<MgUnresolvedExpression> item){
        return isOperator(item, Operators.GROUP);
    }

    private boolean isOperator(ListItem<MgUnresolvedExpression> item, ReadableText name){
        if(item == null) return false;
        if(item.get() instanceof MgUnresolvedOperatorExpression){
            return ((MgUnresolvedOperatorExpression) item.get()).getName().equals(name);
        }
        return false;
    }

    private boolean isOperator(ListItem<MgUnresolvedExpression> item){
        if(item == null) return false;
        if(item.get() instanceof MgUnresolvedOperatorExpression){
            return true;
        }
        return false;
    }

    private void mergeBinary(ListItem<MgUnresolvedExpression> item, LogicalBinaryExpressionCallFactory factory){
        ListItem<MgUnresolvedExpression> leftItem = item.getPreviousItem();
        ListItem<MgUnresolvedExpression> rightItem = item.getNextItem();
        item.setData(factory.create(
            getCall(leftItem, "left"),
            getCall(rightItem, "right")
        ));
        leftItem.remove();
        rightItem.remove();
    }

    private void mergeLunary(ListItem<MgUnresolvedExpression> item, LogicalUnaryExpressionCallFactory factory){
        ListItem<MgUnresolvedExpression> rightItem = item.getNextItem();
        item.setData(factory.create(
            getCall(rightItem, "right")
        ));
        rightItem.remove();
    }

    private void mergeRunary(ListItem<MgUnresolvedExpression> item, LogicalUnaryExpressionCallFactory factory){
        ListItem<MgUnresolvedExpression> leftItem = item.getPreviousItem();
        item.setData(factory.create(
            getCall(leftItem, "left")
        ));
        leftItem.remove();
    }

    private interface LogicalBinaryExpressionCallFactory {
        MgUnresolvedCallExpression create(MgUnresolvedCallExpression leftExpression, MgUnresolvedCallExpression rightExpression);
    }

    private interface LogicalUnaryExpressionCallFactory {
        MgUnresolvedCallExpression create(MgUnresolvedCallExpression expression);
    }

    private MgUnresolvedCallExpression getCall(ListItem<MgUnresolvedExpression> item, String sideLabel){
        if(item != null){
            if(item.get() instanceof MgUnresolvedCallExpression){
                return (MgUnresolvedCallExpression) item.get();
            } else {
                throw new LanguageException("Illegal " + sideLabel + " operand.");
            }
        } else {
            throw new LanguageException("Missing " + sideLabel + " operand.");
        }
    }

    private List<MgUnresolvedExpression> prepareExpressions(MgUnresolvedClumpExpression logicalClumpExpression){
        List<MgUnresolvedExpression> expressions = new List<>();
        for(MgUnresolvedExpression logicalExpression : logicalClumpExpression.getExpressions()){
            expressions.addLast(prepareExpression(resolveNestedGroups(logicalExpression)));
        }
        return expressions;
    }

    private MgUnresolvedExpression prepareExpression(MgUnresolvedExpression logicalExpression){
        if(logicalExpression instanceof MgUnresolvedNameCallExpression){
            return prepareOperatorExpression((MgUnresolvedNameCallExpression) logicalExpression);
        }

        if(logicalExpression instanceof MgUnresolvedOperatorExpression){
            return prepareOperatorExpression((MgUnresolvedOperatorExpression) logicalExpression);
        }

        return logicalExpression;
    }

    private MgUnresolvedExpression prepareOperatorExpression(MgUnresolvedNameCallExpression expression){
        OperatorInfo operatorInfo = findOperator(expression.getName());
        if(operatorInfo != null){
            return new CachedLogicalOperatorExpression(expression.getName(), operatorInfo);
        } else {
            return expression;
        }
    }

    private MgUnresolvedExpression prepareOperatorExpression(MgUnresolvedOperatorExpression expression){
        OperatorInfo operatorInfo = findOperator(expression.getName());
        if(operatorInfo != null){
            return new CachedLogicalOperatorExpression(expression.getName(), operatorInfo);
        } else {
            throw new LanguageException("Could not find operator '" + expression.getName() + "'.");
        }
    }

    private OperatorInfo findOperator(ReadableText name){
        return context.getOperatorCache().findOperator(name);
    }

    private MgUnresolvedExpression resolveNestedGroups(MgUnresolvedExpression logicalExpression){
        if(logicalExpression instanceof MgUnresolvedClumpExpression){
            MgResolveExpressionTreeTask task = new MgResolveExpressionTreeTask(context, (MgUnresolvedClumpExpression) logicalExpression);
            task.run();
            return task.getLogicalCallExpression();
        } else {
            return logicalExpression;
        }
    }

    private static MgUnresolvedMemberNameCallExpression createMemberNameCallExpression(
        MgUnresolvedCallExpression left,
        MgUnresolvedCallExpression right
    ){
        if(right instanceof MgUnresolvedNameCallExpression){
            MgUnresolvedNameCallExpression name = (MgUnresolvedNameCallExpression) right;
            return new MgUnresolvedMemberNameCallExpression(left, name.getName(), name.getExpression());
        } else {
            throw new LanguageException("Member access must be followed by a name.");
        }
    }
}

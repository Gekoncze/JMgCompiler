package cz.mg.compiler.tasks.mg.resolver.command.expression.name.instance;

import cz.mg.compiler.Todo;
import cz.mg.compiler.annotations.Input;
import cz.mg.language.entities.mg.logical.parts.expressions.calls.MgLogicalMemberNameCallExpression;
import cz.mg.compiler.tasks.mg.resolver.command.expression.MgResolveExpressionTask;
import cz.mg.compiler.tasks.mg.resolver.command.utilities.ExpectedParentInput;
import cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext;


public abstract class MgResolveInstanceNameExpressionTask extends MgResolveExpressionTask {
    @Input
    private final MgLogicalMemberNameCallExpression logicalExpression;

    public MgResolveInstanceNameExpressionTask(
        CommandContext context,
        MgLogicalMemberNameCallExpression logicalExpression,
        ExpectedParentInput parent
    ) {
        super(context, parent);
        this.logicalExpression = logicalExpression;
    }

    @Override
    protected void onResolve() {
        new Todo();
//        resolveTargetChild();
//        verifyResolutionOfTargetChild();
//        resolveMemberAccessOptional();
//        resolveRegularChild();
//        resolveMemberAccessMandatory();
    }

//    private void resolveTargetChild(){
//        onResolveChild(logicalTargetExpression);
//    }
//
//    private void verifyResolutionOfTargetChild(){
//        if(getChildren().getFirst().getOutputInterface().getConnectors().count() != 1){
//            throw new LanguageException("Member access requires single output.");
//        }
//    }
//
//    private void resolveMemberAccessOptional(){
//        setNode(createNode(createFilter().findOptional()));
//    }
//
//    private void resolveRegularChild(){
//        if(logicalExpression.getExpression() != null){
//            onResolveChild(logicalExpression.getExpression());
//        }
//    }
//
//    private void resolveMemberAccessMandatory(){
//        setNode(createNode(createFilter().find()));
//    }
//
//    private Node createNode(MgObject object){
//        if(object == null){
//            return null;
//        }
//
//        if(object instanceof MgClassVariable){
//            return new MemberVariableNode((MgClassVariable) object);
//        }
//
//        if(object instanceof MgFunction){
//            return new MemberFunctionNode((MgFunction) object);
//        }
//
//        throw new RuntimeException();
//    }
//
//    private NameExpressionFilter createFilter(){
//        return new NameExpressionFilter(
//            createTargetContext(),
//            logicalExpression.getName(),
//            getParentInputConnectors(),
//            getChildOutputInterface(),
//            getTargetDatatype()
//        );
//    }
//
//    private ClassContext createTargetContext(){
//        ClassContext targetContext = new ClassContext(null);
//        MgDatatype targetDatatype = getTargetDatatype();
//        if(targetDatatype.getType() instanceof MgClass){
//            targetContext.setClazz((MgClass) targetDatatype.getType());
//        } else {
//            throw new LanguageException("Target of member access must be of class type.");
//        }
//        return targetContext;
//    }
//
//    private OutputInterface getChildOutputInterface(){
//        return getChildren().getLast().getOutputInterface();
//    }
//
//    private MgDatatype getTargetDatatype(){
//        return getChildren()
//            .getFirst()
//            .getOutputInterface()
//            .getConnectors()
//            .getFirst()
//            .getRequestedDatatype();
//    }
//
//    @Override
//    public MgExpression onCreateExpression() {
//        if(getNode() instanceof MemberVariableNode){
//            return new MgInstanceVariableExpression(
//                createTargetChildExpression(),
//                ((MemberVariableNode) getNode()).getVariable(),
//                gatherVariableInput(),
//                gatherVariableOutput()
//            );
//        }
//
//        if(getNode() instanceof MemberFunctionNode){
//            return new MgClassFunctionExpression(
//                createTargetChildExpression(),
//                ((MemberFunctionNode) getNode()).getFunction(),
//                createRegularChildExpression(),
//                gatherFunctionInput(),
//                gatherFunctionOutput()
//            );
//        }
//
//        throw new RuntimeException();
//    }
//
//    private MgFunctionVariable gatherVariableInput(){
//        return getChildren()
//            .getFirst()
//            .getOutputInterface()
//            .getConnectors()
//            .getFirst()
//            .getConnection()
//            .getConnectionVariable();
//    }
//
//    private List<MgFunctionVariable> gatherFunctionInput(){
//        List<MgFunctionVariable> input = new List<>();
//        input.addLast(gatherVariableInput());
//        for(InputConnector in : getInputConnectors().getConnectors()){
//            input.addLast(in.getConnection().getConnectionVariable());
//        }
//        return input;
//    }
//
//    private MgFunctionVariable gatherVariableOutput(){
//        return getOutputConnectors()
//            .getConnectors()
//            .getFirst()
//            .getConnection()
//            .getConnectionVariable();
//    }
//
//    private List<MgFunctionVariable> gatherFunctionOutput(){
//        List<MgFunctionVariable> output = new List<>();
//        for(OutputConnector out : getOutputConnectors().getConnectors()){
//            output.addLast(out.getConnection().getConnectionVariable());
//        }
//        return output;
//    }
//
//    private MgExpression createTargetChildExpression(){
//        return getChildren().getFirst().createExpression();
//    }
//
//    private MgExpression createRegularChildExpression(){
//        if(getChildren().count() < 2) return null;
//        return getChildren().getLast().createExpression();
//    }
//
    public static MgResolveInstanceNameExpressionTask create(
        CommandContext context,
        MgLogicalMemberNameCallExpression logicalExpression,
        ExpectedParentInput parent
    ){
        if(logicalExpression.getExpression() == null){
            return new MgResolveInstanceVariableExpressionTask(context, logicalExpression, parent);
        } else {
            return new MgResolveInstanceFunctionExpressionTask(context, logicalExpression, parent);
        }
    }
}

package cz.mg.compiler.tasks.mg.resolver.command.expression.other;

import cz.mg.collections.text.ReadableText;
import cz.mg.compiler.Todo;
import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.language.LanguageException;
import cz.mg.language.entities.mg.unresolved.parts.expressions.calls.MgUnresolvedValueCallExpression;
import cz.mg.language.entities.mg.runtime.components.types.MgType;
import cz.mg.language.entities.mg.runtime.components.types.buildin.MgAtomType;
import cz.mg.language.entities.mg.runtime.instances.buildin.MgAtom;
import cz.mg.language.entities.mg.runtime.parts.expressions.MgExpression;
import cz.mg.language.entities.mg.runtime.parts.expressions.MgValueExpression;
import cz.mg.compiler.tasks.mg.resolver.command.expression.MgResolveExpressionTask;
import cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext;


public class MgResolveValueExpressionTask extends MgResolveExpressionTask {
    @Input
    private final MgUnresolvedValueCallExpression logicalExpression;

    @Output
    private MgValueExpression expression;

    public MgResolveValueExpressionTask(
        CommandContext context,
        MgUnresolvedValueCallExpression logicalExpression
    ) {
        super(context);
        this.logicalExpression = logicalExpression;
    }

    @Override
    protected void onResolve() {
        expression = new MgValueExpression(createValue(getType(), logicalExpression.getValue()));
    }

    @Override
    public MgExpression getExpression() {
        return expression;
    }

    private MgAtomType getType(){
        new Todo();
        return null;
//        if(getParent() != null){
//            for(){
//
//            }
//            ReadableArray<InputConnector> remainingInputConnectors = parentInputInterface.getRemainingConnectors();
//            int count = remainingInputConnectors.count();
//            if(count >= 1){
//                InputConnector inputConnector = remainingInputConnectors.getFirst();
//                return new OutputInterface(new Array<>(new OutputConnector(createDatatype(inputConnector))));
//            } else {
//                throw new LanguageException("Cannot connect expressions. Parent has no free connectors.");
//            }
//        } else {
//            return MgTextType.getInstance();
//        }
    }

    private static MgAtom createValue(MgType type, ReadableText value){
        if(type instanceof MgAtomType){
            return ((MgAtomType) type).create(value);
        } else {
            throw new LanguageException("Expected atom type, given " + type.getName() + ".");
        }
    }
}

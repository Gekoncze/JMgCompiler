package cz.mg.compiler.tasks.mg.resolver.component;

import cz.mg.collections.list.List;
import cz.mg.language.entities.mg.unresolved.components.*;
import cz.mg.language.entities.mg.runtime.components.stamps.MgStamp;
import cz.mg.language.entities.mg.runtime.components.types.functions.*;
import cz.mg.compiler.tasks.mg.resolver.context.Context;


public class MgResolveClassFunctionDefinitionTask extends MgResolveFunctionDefinitionTask {
    public MgResolveClassFunctionDefinitionTask(Context context, MgUnresolvedFunction logicalFunction) {
        super(context, logicalFunction);
    }

    @Override
    protected void onResolveComponent(List<MgStamp> stamps) {
        if(logicalFunction instanceof MgUnresolvedOperator){
            if(logicalFunction instanceof MgUnresolvedBinaryOperator){
                function = new MgBinaryOperator(logicalFunction.getName(), ((MgUnresolvedOperator) logicalFunction).getPriority());
            } else if(logicalFunction instanceof MgUnresolvedLunaryOperator){
                function = new MgLunaryOperator(logicalFunction.getName(), ((MgUnresolvedOperator) logicalFunction).getPriority());
            } else if(logicalFunction instanceof MgUnresolvedRunaryOperator){
                function = new MgRunaryOperator(logicalFunction.getName(), ((MgUnresolvedOperator) logicalFunction).getPriority());
            } else {
                throw new RuntimeException();
            }
        } else {
            switch (getType(stamps, Type.GLOBAL)){
                case GLOBAL:
                    function = new MgGlobalFunction(logicalFunction.getName());
                    break;
                case TYPE:
                    function = new MgTypeFunction(logicalFunction.getName());
                    break;
                case INSTANCE:
                    function = new MgInstanceFunction(logicalFunction.getName());
                    break;
                default:
                    throw new RuntimeException();
            }
            function.getStamps().addCollectionLast(stamps);
        }
        getContext().setFunction(function);

        onResolveComponentChildren();
    }
}

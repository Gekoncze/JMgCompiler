package cz.mg.compiler.tasks.mg.resolver.component;

import cz.mg.collections.list.List;
import cz.mg.language.entities.mg.logical.components.*;
import cz.mg.language.entities.mg.runtime.components.stamps.MgStamp;
import cz.mg.language.entities.mg.runtime.components.types.functions.*;
import cz.mg.compiler.tasks.mg.resolver.context.Context;


public class MgResolveClassFunctionDefinitionTask extends MgResolveFunctionDefinitionTask {
    public MgResolveClassFunctionDefinitionTask(Context context, MgLogicalFunction logicalFunction) {
        super(context, logicalFunction);
    }

    @Override
    protected void onResolveComponent(List<MgStamp> stamps) {
        if(logicalFunction instanceof MgLogicalOperator){
            if(logicalFunction instanceof MgLogicalBinaryOperator){
                function = new MgBinaryOperator(logicalFunction.getName(), ((MgLogicalOperator) logicalFunction).getPriority());
            } else if(logicalFunction instanceof MgLogicalLunaryOperator){
                function = new MgLunaryOperator(logicalFunction.getName(), ((MgLogicalOperator) logicalFunction).getPriority());
            } else if(logicalFunction instanceof MgLogicalRunaryOperator){
                function = new MgRunaryOperator(logicalFunction.getName(), ((MgLogicalOperator) logicalFunction).getPriority());
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

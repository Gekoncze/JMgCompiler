package cz.mg.compiler.tasks.mg.resolver.component;

import cz.mg.collections.list.List;
import cz.mg.language.entities.mg.logical.components.*;
import cz.mg.language.entities.mg.runtime.components.stamps.MgStamp;
import cz.mg.language.entities.mg.runtime.components.types.functions.MgBinaryOperator;
import cz.mg.language.entities.mg.runtime.components.types.functions.MgGlobalFunction;
import cz.mg.language.entities.mg.runtime.components.types.functions.MgLunaryOperator;
import cz.mg.language.entities.mg.runtime.components.types.functions.MgRunaryOperator;
import cz.mg.compiler.tasks.mg.resolver.context.Context;


public class MgResolveLocationFunctionDefinitionTask extends MgResolveFunctionDefinitionTask {
    public MgResolveLocationFunctionDefinitionTask(Context context, MgLogicalFunction logicalFunction) {
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
            function = new MgGlobalFunction(logicalFunction.getName());
        }

        function.getStamps().addCollectionLast(globalStampOnly(stamps));
        getContext().setFunction(function);

        onResolveComponentChildren();
    }
}

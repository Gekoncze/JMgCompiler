package cz.mg.compiler.tasks.mg.resolver.component;

import cz.mg.collections.list.List;
import cz.mg.language.entities.mg.unresolved.components.*;
import cz.mg.language.entities.mg.runtime.components.stamps.MgStamp;
import cz.mg.language.entities.mg.runtime.components.types.functions.MgBinaryOperator;
import cz.mg.language.entities.mg.runtime.components.types.functions.MgGlobalFunction;
import cz.mg.language.entities.mg.runtime.components.types.functions.MgLunaryOperator;
import cz.mg.language.entities.mg.runtime.components.types.functions.MgRunaryOperator;
import cz.mg.compiler.tasks.mg.resolver.context.Context;


public class MgResolveLocationFunctionDefinitionTask extends MgResolveFunctionDefinitionTask {
    public MgResolveLocationFunctionDefinitionTask(Context context, MgUnresolvedFunction logicalFunction) {
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
            function = new MgGlobalFunction(logicalFunction.getName());
        }

        function.getStamps().addCollectionLast(globalStampOnly(stamps));
        getContext().setFunction(function);

        onResolveComponentChildren();
    }
}

package cz.mg.compiler.tasks.mg.resolver;

import cz.mg.collections.list.List;
import cz.mg.collections.list.ListItem;
import cz.mg.collections.text.ReadableText;
import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.resolver.command.utilities.Usage;
import cz.mg.compiler.tasks.mg.resolver.context.Context;
import cz.mg.compiler.tasks.mg.resolver.context.architecture.ApplicationContext;
import cz.mg.language.LanguageException;
import cz.mg.language.entities.mg.logical.parts.MgLogicalUsage;
import cz.mg.language.entities.mg.runtime.components.MgComponent;
import cz.mg.language.entities.mg.runtime.components.MgLocation;
import cz.mg.language.entities.mg.runtime.components.stamps.MgStamp;
import cz.mg.language.entities.mg.runtime.components.stamps.buildin.MgInstanceStamp;
import cz.mg.language.entities.mg.runtime.components.stamps.buildin.MgTypeStamp;
import cz.mg.language.entities.mg.runtime.components.types.classes.MgClass;
import cz.mg.language.entities.mg.runtime.components.types.functions.MgFunction;
import cz.mg.language.entities.mg.runtime.components.types.functions.MgOperator;
import cz.mg.language.entities.mg.runtime.components.variables.MgVariable;


public class MgResolveUsageTask extends MgPostponeResolveTask {
    @Input
    private final MgLogicalUsage logicalUsage;

    @Output
    private List<cz.mg.compiler.tasks.mg.resolver.command.utilities.Usage> usages;

    public MgResolveUsageTask(cz.mg.compiler.tasks.mg.resolver.context.Context context, MgLogicalUsage logicalUsage) {
        super(context);
        this.logicalUsage = logicalUsage;
    }

    public List<cz.mg.compiler.tasks.mg.resolver.command.utilities.Usage> getUsages() {
        return usages;
    }

    private cz.mg.compiler.tasks.mg.resolver.context.architecture.ApplicationContext getApplicationContext(){
        Context context = getContext();
        while(context != null){
            if(context instanceof cz.mg.compiler.tasks.mg.resolver.context.architecture.ApplicationContext){
                return (ApplicationContext) context;
            }
            context = context.getOuterContext();
        }
        throw new RuntimeException("Missing application context for usage resolution.");
    }

    @Override
    protected void onRun() {
        usages = new List<>();
        open(
            getApplicationContext().getApplication().getRoot(),
            logicalUsage.getPath().getFirstItem()
        );
    }

    private void open(MgComponent parent, ListItem<ReadableText> childItem){
        if(childItem != null){
            List<MgComponent> children = find(parent, childItem.get());

            if(!childItem.get().toString().equals("*") && children.isEmpty()){
                throw new LanguageException("Could not find '" + childItem.get() + "'.");
            }

            if(!childItem.get().toString().equals("*") && children.count() > 1){
                throw new LanguageException("Ambiguous name '" + childItem.get() + "'.");
            }

            for(MgComponent child : children){
                open(child, childItem.getNextItem());
            }
        } else {
            addUsage(parent);
        }
    }

    private void addUsage(MgComponent component){
        if(accept(component)){
            if(logicalUsage.getFilter() == MgLogicalUsage.Filter.OPERATOR){
                if(logicalUsage.getAlias() == null){
                    usages.addLast(new cz.mg.compiler.tasks.mg.resolver.command.utilities.Usage(component, component.getName()));
                } else {
                    throw new LanguageException("Operator usages cannot have an alias.");
                }
            } else {
                usages.addLast(new Usage(component, logicalUsage.getAlias()));
            }
        }
    }

    private List<MgComponent> find(MgComponent component, ReadableText name){
        if(component instanceof MgLocation){
            return findInLocation((MgLocation) component, name);
        }

        if(component instanceof MgClass){
            return findInClass((MgClass) component, name);
        }

        throw new LanguageException("Cannot search for usage in " + component.getClass().getSimpleName() + ".");
    }

    private List<MgComponent> findInLocation(MgLocation location, ReadableText name){
        List<MgComponent> results = new List<>();
        for(MgComponent component : location.getComponents()){
            if(acceptName(component, name)){
                results.addLast(component);
            }
        }
        return results;
    }

    private List<MgComponent> findInClass(MgClass clazz, ReadableText name){
        List<MgComponent> results = new List<>();
        for(MgVariable variable : clazz.getVariableDefinitions()){
            if(acceptName(variable, name)){
                results.addLast(variable);
            }
        }
        for(MgFunction function : clazz.getFunctionDefinitions()){
            if(acceptName(function, name)){
                results.addLast(function);
            }
        }
        return results;
    }

    private boolean accept(MgComponent component){
        if(acceptType(component)) {
            if(isGlobal(component)) {
                return true;
            }
        }
        return false;
    }

    private boolean acceptName(MgComponent component, ReadableText name){
        if(name.toString().equals("*")){
            return true;
        } else {
            return component.getName().equals(name);
        }
    }

    private boolean acceptType(MgComponent component){
        switch (logicalUsage.getFilter()){
            case ALL: return true;
            case CLASS: return component instanceof MgClass;
            case FUNCTION: return component instanceof MgFunction;
            case OPERATOR: return component instanceof MgOperator;
            case VARIABLE: return component instanceof MgVariable;
            default: throw new UnsupportedOperationException();
        }
    }

    private boolean isGlobal(MgComponent component){
        for(MgStamp stamp : component.getStamps()){
            if(stamp == MgInstanceStamp.getInstance()){
                return false;
            }

            if(stamp == MgTypeStamp.getInstance()){
                return false;
            }
        }
        return true;
    }
}

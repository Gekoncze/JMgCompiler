package cz.mg.compiler.tasks.mg.resolver.component;

import cz.mg.collections.list.List;
import cz.mg.collections.text.ReadableText;
import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.tasks.mg.resolver.context.component.ComponentContext;
import cz.mg.language.LanguageException;
import cz.mg.language.entities.mg.unresolved.components.MgUnresolvedComponent;
import cz.mg.language.entities.mg.unresolved.parts.MgUnresolvedUsage;
import cz.mg.language.entities.mg.runtime.components.stamps.MgStamp;
import cz.mg.language.entities.mg.runtime.components.stamps.buildin.MgGlobalStamp;
import cz.mg.language.entities.mg.runtime.components.stamps.buildin.MgInstanceStamp;
import cz.mg.language.entities.mg.runtime.components.stamps.buildin.MgTypeStamp;
import cz.mg.compiler.tasks.mg.resolver.MgResolveUsageTask;
import cz.mg.compiler.tasks.mg.resolver.MgPostponeResolveTask;
import cz.mg.compiler.tasks.mg.resolver.link.MgResolveComponentStampTask;


public abstract class MgResolveComponentDefinitionTask extends MgPostponeResolveTask {
    @Input
    private final MgUnresolvedComponent logicalComponent;

    public MgResolveComponentDefinitionTask(ComponentContext context, MgUnresolvedComponent logicalComponent) {
        super(context);
        this.logicalComponent = logicalComponent;
    }

    @Override
    protected ComponentContext getContext() {
        return (ComponentContext) super.getContext();
    }

    @Override
    protected final void onRun() {
        resolveUsages();
        onResolveComponent(resolveStamps());
    }

    private void resolveUsages(){
        for(MgUnresolvedUsage logicalUsage : logicalComponent.getWorkspace().getUsages()){
            postpone(MgResolveUsageTask.class, () -> {
                MgResolveUsageTask task = new MgResolveUsageTask(getContext(), logicalUsage);
                task.run();
                getContext().getUsages().addCollectionLast(task.getUsages());
            });
        }
    }

    private List<MgStamp> resolveStamps(){
        List<MgStamp> stamps = new List<>();
        for(ReadableText logicalStamp : logicalComponent.getStamps()){
            postpone(MgResolveComponentStampTask.class, () -> {
                MgResolveComponentStampTask task = new MgResolveComponentStampTask(getContext(), logicalStamp);
                task.run();
                stamps.addLast(task.getStamp());
            });
        }
        return stamps;
    }

    protected abstract void onResolveComponent(List<MgStamp> stamps);

    protected List<MgStamp> globalStampOnly(List<MgStamp> stamps){
        if(getType(stamps, Type.GLOBAL) == Type.GLOBAL){
            return stamps;
        } else {
            throw new LanguageException("Component must have global stamp (default).");
        }
    }

    protected List<MgStamp> typeStampOnly(List<MgStamp> stamps){
        if(getType(stamps, Type.TYPE) == Type.TYPE){
            return stamps;
        } else {
            throw new LanguageException("Component must have type stamp (default).");
        }
    }

    protected List<MgStamp> instanceStampOnly(List<MgStamp> stamps){
        if(getType(stamps, Type.INSTANCE) == Type.INSTANCE){
            return stamps;
        } else {
            throw new LanguageException("Component must have instance stamp (default).");
        }
    }

    protected Type getType(List<MgStamp> stamps, Type defaultType){
        final int eFlag = 0b0000000000000000;
        final int gFlag = 0b0000000000000001;
        final int tFlag = 0b0000000000000010;
        final int iFlag = 0b0000000000000100;
        int flags = eFlag;
        for(MgStamp stamp : stamps){
            if(stamp == MgGlobalStamp.getInstance()) flags |= gFlag;
            if(stamp == MgTypeStamp.getInstance()) flags |= tFlag;
            if(stamp == MgInstanceStamp.getInstance()) flags |= iFlag;
        }
        switch (flags){
            case eFlag: return defaultType;
            case gFlag: return Type.GLOBAL;
            case tFlag: return Type.TYPE;
            case iFlag: return Type.INSTANCE;
            default: throw new LanguageException("Illegal combination of stamps.");
        }
    }

    protected enum Type {
        GLOBAL,
        TYPE,
        INSTANCE
    }
}

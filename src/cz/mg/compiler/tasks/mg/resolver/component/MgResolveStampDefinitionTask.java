package cz.mg.compiler.tasks.mg.resolver.component;

import cz.mg.collections.list.List;
import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.resolver.context.component.StampContext;
import cz.mg.language.entities.mg.unresolved.components.MgUnresolvedStamp;
import cz.mg.language.entities.mg.runtime.components.stamps.MgStamp;
import cz.mg.compiler.tasks.mg.resolver.context.Context;


public class MgResolveStampDefinitionTask extends MgResolveComponentDefinitionTask {
    @Input
    private final MgUnresolvedStamp logicalStamp;

    @Output
    private MgStamp stamp;

    public MgResolveStampDefinitionTask(Context context, MgUnresolvedStamp logicalStamp) {
        super(new StampContext(context), logicalStamp);
        this.logicalStamp = logicalStamp;
    }

    @Override
    protected StampContext getContext() {
        return (StampContext) super.getContext();
    }

    public MgStamp getStamp() {
        return stamp;
    }

    @Override
    protected void onResolveComponent(List<MgStamp> stamps) {
        stamp = new MgStamp(logicalStamp.getName());
        stamp.getStamps().addCollectionLast(stamps);
        getContext().setStamp(stamp);
    }
}

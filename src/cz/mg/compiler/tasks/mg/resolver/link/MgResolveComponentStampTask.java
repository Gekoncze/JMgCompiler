package cz.mg.compiler.tasks.mg.resolver.link;

import cz.mg.collections.text.ReadableText;
import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.resolver.context.Context;
import cz.mg.compiler.tasks.mg.resolver.search.StampSearch;
import cz.mg.language.entities.mg.runtime.components.stamps.MgStamp;
import cz.mg.compiler.tasks.mg.resolver.MgPostponeResolveTask;


public class MgResolveComponentStampTask extends MgPostponeResolveTask {
    @Input
    private final ReadableText logicalStamp;

    @Output
    private MgStamp stamp;

    public MgResolveComponentStampTask(Context context, ReadableText logicalStamp) {
        super(context);
        this.logicalStamp = logicalStamp;
    }

    public MgStamp getStamp() {
        return stamp;
    }

    @Override
    protected void onRun() {
        stamp = new StampSearch(getContext().getGlobalSource(), logicalStamp).find();
    }
}

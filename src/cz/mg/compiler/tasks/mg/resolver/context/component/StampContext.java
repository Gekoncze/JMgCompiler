package cz.mg.compiler.tasks.mg.resolver.context.component;

import cz.mg.annotations.requirement.Optional;
import cz.mg.annotations.storage.Link;
import cz.mg.language.entities.mg.runtime.components.MgComponent;
import cz.mg.language.entities.mg.runtime.components.stamps.MgStamp;
import cz.mg.compiler.tasks.mg.resolver.context.Context;


public class StampContext extends ComponentContext {
    @Optional @Link
    private MgStamp stamp;

    public StampContext(Context outerContext) {
        super(outerContext);
    }

    @Override
    public MgComponent getComponent() {
        return stamp;
    }

    public MgStamp getStamp() {
        return stamp;
    }

    public void setStamp(MgStamp stamp) {
        this.stamp = stamp;
    }
}

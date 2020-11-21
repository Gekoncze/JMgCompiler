package cz.mg.compiler.tasks.mg.resolver.context.architecture;

import cz.mg.annotations.requirement.Optional;
import cz.mg.annotations.storage.Link;
import cz.mg.language.entities.mg.runtime.components.MgLocation;
import cz.mg.compiler.tasks.mg.resolver.context.Context;


public class LocationContext extends Context {
    @Optional @Link
    private MgLocation location;

    public LocationContext(Context outerContext) {
        super(outerContext);
    }

    public MgLocation getLocation() {
        return location;
    }

    public void setLocation(MgLocation location) {
        this.location = location;
    }
}

package cz.mg.compiler.tasks.mg.resolver.command.utilities;

import cz.mg.collections.text.ReadableText;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.annotations.storage.Link;
import cz.mg.annotations.storage.Value;
import cz.mg.language.entities.mg.runtime.components.MgComponent;


public class Usage {
    @Mandatory @Link
    private final MgComponent component;

    @Mandatory @Value
    private final ReadableText localName;

    public Usage(@Mandatory @Link MgComponent component) {
        this(component, null);
    }

    public Usage(@Mandatory @Link MgComponent component, @Optional @Value ReadableText localName) {
        this.component = component;
        this.localName = localName != null ? localName : component.getName();
    }

    public MgComponent getComponent() {
        return component;
    }

    public ReadableText getLocalName() {
        return localName;
    }
}

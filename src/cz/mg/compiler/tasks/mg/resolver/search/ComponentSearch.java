package cz.mg.compiler.tasks.mg.resolver.search;

import cz.mg.annotations.storage.Part;
import cz.mg.collections.Clump;
import cz.mg.collections.list.List;
import cz.mg.collections.list.ReadableList;
import cz.mg.collections.text.ReadableText;
import cz.mg.compiler.tasks.mg.resolver.command.utilities.Usage;
import cz.mg.language.LanguageException;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.annotations.storage.Link;
import cz.mg.language.entities.mg.runtime.components.MgComponent;
import cz.mg.language.entities.mg.runtime.components.stamps.MgStamp;


public class ComponentSearch<Component extends MgComponent> {
    @Optional @Link
    private final Source source;

    @Optional @Link
    private final ReadableText name;

    @Mandatory @Part
    private final List<@Mandatory @Part Filter<Component>> filters = new List<>();

    public ComponentSearch(
        @Mandatory Source source
    ) {
        this(source, null);
    }

    public ComponentSearch(
        @Mandatory Source source,
        @Optional ReadableText name
    ) {
        this.source = source;
        this.name = name;
    }

    protected Class getType(){
        return MgComponent.class;
    }

    protected final void addFilter(@Mandatory Filter<Component> filter){
        this.filters.addLast(filter);
    }

    public @Optional Component find(boolean optional){
        if(optional){
            return findOptional();
        } else {
            return find();
        }
    }

    public @Mandatory Component find(){
        ReadableList<Component> components = findAll();
        if(components.count() <= 0){
            throw new LanguageException(notFoundMessage());
        } else if(components.count() == 1){
            return components.getFirst();
        } else {
            throw new LanguageException(ambiguousMessage());
        }
    }

    public @Optional Component findOptional(){
        ReadableList<Component> components = findAll();
        if(components.count() <= 0){
            return null;
        } else if(components.count() == 1){
            return components.getFirst();
        } else {
            return null;
        }
    }

    public @Mandatory ReadableList<Component> findAll(){
        Clump<cz.mg.compiler.tasks.mg.resolver.command.utilities.Usage> availableComponents = source.getComponents();
        List<Component> acceptedComponents = new List<>();
        for(Usage usage : availableComponents){
            if(getType().isAssignableFrom(usage.getComponent().getClass())){
                Component component = (Component) usage.getComponent();
                if(name != null){
                    if(name.equals(usage.getLocalName())){
                        if(isAcceptable(component)){
                            acceptedComponents.addLast(component);
                        }
                    }
                } else {
                    if(isAcceptable(component)){
                        acceptedComponents.addLast(component);
                    }
                }
            }
        }
        return acceptedComponents;
    }

    private boolean isAcceptable(@Mandatory Component component){
        for(Filter<Component> filter : filters){
            if(filter.filter(component)){
                return false;
            }
        }
        return true;
    }

    public ComponentSearch<Component> addStampFilter(MgStamp stamp){
        addFilter(new StampFilter(stamp));
        return this;
    }

    private String notFoundMessage(){
        return "Component"
            + nameMessage()
            + typeMessage()
            + " was not found.";
    }

    private String ambiguousMessage(){
        return "Component "
            + nameMessage()
            + typeMessage()
            + " is ambiguous.";
    }

    private String nameMessage(){
        if(name != null){
            return " " + name;
        } else {
            return "";
        }
    }

    private String typeMessage(){
        if(getType() != null){
            return " of type " + getType().getSimpleName();
        } else {
            return "";
        }
    }

    protected interface Filter<T extends MgComponent> {
        public boolean filter(@Mandatory T component);
    }

    private class StampFilter implements Filter<Component> {
        @Mandatory @Link
        private final MgStamp stamp;

        public StampFilter(MgStamp stamp) {
            this.stamp = stamp;
        }

        @Override
        public boolean filter(@Mandatory Component component) {
            return component.getStamps().contains(stamp);
        }
    }
}

package cz.mg.compiler.tasks.mg.resolver.search;

import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.collections.list.ReadableList;
import cz.mg.collections.text.ReadableText;
import cz.mg.language.entities.mg.runtime.components.stamps.MgStamp;


public class StampSearch<Stamp extends MgStamp> extends ComponentSearch<Stamp> {
    public StampSearch(
        @Mandatory Source source
    ) {
        super(source);
    }

    public StampSearch(
        @Mandatory Source source,
        @Optional ReadableText name
    ) {
        super(source, name);
    }

    @Override
    protected Class getType() {
        return MgStamp.class;
    }

    // this is required for some reason ...
    @Override
    public Stamp find(boolean optional) {
        return super.find(optional);
    }

    // this is required for some reason ...
    @Override
    public Stamp find() {
        return super.find();
    }

    // this is required for some reason ...
    @Override
    public Stamp findOptional() {
        return super.findOptional();
    }

    // this is required for some reason ...
    @Override
    public @Mandatory ReadableList<Stamp> findAll() {
        return super.findAll();
    }
}

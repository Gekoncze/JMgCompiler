package cz.mg.compiler.tasks.mg.resolver.search;

import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.collections.list.ReadableList;
import cz.mg.collections.text.ReadableText;
import cz.mg.language.entities.mg.runtime.components.types.classes.MgClass;


public class ClassSearch<Clazz extends MgClass> extends TypeSearch<Clazz> {
    public ClassSearch(
        @Mandatory Source source
    ) {
        super(source);
    }

    public ClassSearch(
        @Mandatory Source source,
        @Optional ReadableText name
    ) {
        super(source, name);
    }

    @Override
    protected Class getType() {
        return MgClass.class;
    }

    // this is required for some reason ...
    @Override
    public Clazz find(boolean optional) {
        return super.find(optional);
    }

    // this is required for some reason ...
    @Override
    public Clazz find() {
        return super.find();
    }

    // this is required for some reason ...
    @Override
    public Clazz findOptional() {
        return super.findOptional();
    }

    // this is required for some reason ...
    @Override
    public @Mandatory ReadableList<Clazz> findAll() {
        return super.findAll();
    }
}

package cz.mg.compiler.tasks.mg.resolver.search;

import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.collections.list.ReadableList;
import cz.mg.collections.text.ReadableText;
import cz.mg.language.entities.mg.runtime.components.types.MgType;


public class TypeSearch<Type extends MgType> extends ComponentSearch<Type> {
    public TypeSearch(
        @Mandatory Source source
    ) {
        super(source);
    }

    public TypeSearch(
        @Mandatory Source source,
        @Optional ReadableText name
    ) {
        super(source, name);
    }

    @Override
    protected Class getType() {
        return MgType.class;
    }

    // this is required for some reason ...
    @Override
    public Type find(boolean optional) {
        return super.find(optional);
    }

    // this is required for some reason ...
    @Override
    public Type find() {
        return super.find();
    }

    // this is required for some reason ...
    @Override
    public Type findOptional() {
        return super.findOptional();
    }

    // this is required for some reason ...
    @Override
    public @Mandatory ReadableList<Type> findAll() {
        return super.findAll();
    }
}

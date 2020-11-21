package cz.mg.compiler.tasks.mg.resolver.search;

import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.annotations.storage.Value;
import cz.mg.collections.list.ReadableList;
import cz.mg.collections.text.ReadableText;
import cz.mg.language.entities.mg.runtime.components.variables.MgVariable;
import cz.mg.language.entities.mg.runtime.parts.MgDatatype;


public class VariableSearch<Variable extends MgVariable> extends ComponentSearch<Variable> {
    @Optional @Value
    private final MgDatatype output;

    @Optional @Value
    private final MgDatatype input;

    public VariableSearch(
        @Mandatory Source source
    ) {
        this(source, null);
    }

    public VariableSearch(
        @Mandatory Source source,
        @Optional ReadableText name
    ) {
        this(source, name, null, null);
    }

    public VariableSearch(
        @Mandatory Source source,
        @Optional ReadableText name,
        @Optional MgDatatype output,
        @Optional MgDatatype input
    ) {
        super(source, name);
        this.output = output;
        this.input = input;

        if(output != null){
            addFilter(this::filterByOutput);
        }

        if(input != null){
            addFilter(this::filterByInput);
        }
    }

    @Override
    protected Class getType() {
        return MgVariable.class;
    }

    // this is required for some reason ...
    @Override
    public Variable find(boolean optional) {
        return super.find(optional);
    }

    // this is required for some reason ...
    @Override
    public Variable find() {
        return super.find();
    }

    // this is required for some reason ...
    @Override
    public Variable findOptional() {
        return super.findOptional();
    }

    // this is required for some reason ...
    @Override
    public @Mandatory ReadableList<Variable> findAll() {
        return super.findAll();
    }

    private boolean filterByOutput(Variable variable) {
        return MgDatatype.isCompatible(
            output,
            variable.getDatatype()
        );
    }

    private boolean filterByInput(Variable variable) {
        return MgDatatype.isCompatible(
            variable.getDatatype(),
            input
        );
    }
}

package cz.mg.compiler.tasks.mg.resolver.search;

import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.annotations.storage.Part;
import cz.mg.annotations.storage.Value;
import cz.mg.collections.array.Array;
import cz.mg.collections.list.ReadableList;
import cz.mg.collections.text.ReadableText;
import cz.mg.language.entities.mg.runtime.components.types.functions.MgFunction;
import cz.mg.language.entities.mg.runtime.parts.MgDatatype;


public class FunctionSearch<Function extends MgFunction> extends ComponentSearch<Function> {
    @Optional @Part
    private final Array<@Optional @Value MgDatatype> input;

    @Optional @Part
    private final Array<@Optional @Value MgDatatype> output;

    public FunctionSearch(
        @Mandatory Source source
    ) {
        this(source, null);
    }

    public FunctionSearch(
        @Mandatory Source source,
        @Optional ReadableText name
    ) {
        this(source, name, null, null);
    }

    public FunctionSearch(
        @Mandatory Source source,
        @Optional ReadableText name,
        @Optional Array<@Optional MgDatatype> input,
        @Optional Array<@Optional MgDatatype> output
    ) {
        super(source, name);
        this.input = input;
        this.output = output;

        if(input != null){
            addFilter(this::filterByInputCount);
            for(int i = 0; i < input.count(); i++){
                if(input.get(i) != null){
                    addFilter(new InputFilter(i));
                }
            }
        }

        if(output != null){
            addFilter(this::filterByOutputCount);
            for(int i = 0; i < output.count(); i++){
                if(output.get(i) != null){
                    addFilter(new OutputFilter(i));
                }
            }
        }
    }

    @Override
    protected Class getType() {
        return MgFunction.class;
    }

    // this is required for some reason ...
    @Override
    public Function find(boolean optional) {
        return super.find(optional);
    }

    // this is required for some reason ...
    @Override
    public Function find() {
        return super.find();
    }

    // this is required for some reason ...
    @Override
    public Function findOptional() {
        return super.findOptional();
    }

    // this is required for some reason ...
    @Override
    public @Mandatory ReadableList<Function> findAll() {
        return super.findAll();
    }

    private boolean filterByInputCount(Function function) {
        return function.getInputVariables().count() == input.count();
    }

    private boolean filterByOutputCount(Function function) {
        return function.getOutputVariables().count() == output.count();
    }

    private class InputFilter implements Filter<Function> {
        @Mandatory @Value
        private final int i;

        private InputFilter(int i) {
            this.i = i;
        }

        @Override
        public boolean filter(@Mandatory Function function) {
            return MgDatatype.isCompatible(
                function.getInputVariables().get(i).getDatatype(),
                input.get(i)
            );
        }
    }

    private class OutputFilter implements Filter<Function> {
        @Mandatory @Value
        private final int i;

        private OutputFilter(int i) {
            this.i = i;
        }

        @Override
        public boolean filter(@Mandatory Function function) {
            return MgDatatype.isCompatible(
                output.get(i),
                function.getInputVariables().get(i).getDatatype()
            );
        }
    }
}

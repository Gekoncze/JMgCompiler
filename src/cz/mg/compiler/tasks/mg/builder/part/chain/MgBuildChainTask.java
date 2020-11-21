package cz.mg.compiler.tasks.mg.builder.part.chain;

import cz.mg.collections.list.List;
import cz.mg.collections.text.ReadableText;
import cz.mg.compiler.annotations.Input;
import cz.mg.language.entities.text.structured.Part;
import cz.mg.language.entities.text.structured.parts.leaves.Operator;
import cz.mg.compiler.tasks.mg.builder.part.MgBuildPartTask;


public abstract class MgBuildChainTask extends MgBuildPartTask {
    @Input
    private final ReadableText separator;

    public MgBuildChainTask(List<Part> parts, ReadableText separator) {
        super(parts);
        this.separator = separator;
    }

    @Override
    protected void onRun() {
        List<Part> parts = new List<>();
        for(Part part : getParts()){
            if(isSeparator(part)){
                buildParts(parts);
                parts = new List<>();
            } else {
                parts.addLast(part);
            }
        }
        buildParts(parts);
    }

    private boolean isSeparator(Part part) {
        if(part instanceof Operator){
            return ((Operator) part).getText().equals(separator);
        }
        return false;
    }
}

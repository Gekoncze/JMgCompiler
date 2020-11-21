package cz.mg.compiler.tasks.mg.builder.part;

import cz.mg.collections.list.List;
import cz.mg.collections.text.ReadableText;
import cz.mg.compiler.annotations.Output;
import cz.mg.language.LanguageException;
import cz.mg.language.entities.text.structured.Part;
import cz.mg.language.entities.text.structured.parts.Leaf;
import cz.mg.language.entities.text.structured.parts.leaves.Operator;
import cz.mg.language.entities.text.structured.parts.leaves.names.ObjectName;


public abstract class MgBuildOperatorNameTask extends MgBuildPartTask {
    @Output
    private ReadableText name;

    public MgBuildOperatorNameTask(List<Part> parts) {
        super(parts);
    }

    public ReadableText getName() {
        return name;
    }

    @Override
    protected void buildParts(List<Part> parts) {
        checkCount(1);
        Leaf leaf = get(Leaf.class, 0);
        if(leaf instanceof ObjectName || leaf instanceof Operator){
            name = leaf.getText();
        } else {
            throw new LanguageException("Expected name or signs, but got " + leaf.getClass().getSimpleName() + ".");
        }
    }
}

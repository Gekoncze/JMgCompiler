package cz.mg.compiler.tasks.mg.builder.part;

import cz.mg.collections.list.List;
import cz.mg.compiler.annotations.Output;
import cz.mg.language.LanguageException;
import cz.mg.language.entities.mg.Operators;
import cz.mg.language.entities.mg.unresolved.parts.path.MgUnresolvedAbsolutePath;
import cz.mg.language.entities.mg.unresolved.parts.path.MgUnresolvedPath;
import cz.mg.language.entities.mg.unresolved.parts.path.MgUnresolvedRelativePath;
import cz.mg.language.entities.text.structured.Part;
import cz.mg.language.entities.text.structured.parts.Leaf;
import cz.mg.language.entities.text.structured.parts.leaves.Name;
import cz.mg.language.entities.text.structured.parts.leaves.Operator;


public class MgBuildUsagePathTask extends MgBuildPartTask {
    @Output
    private MgUnresolvedPath path;

    public MgBuildUsagePathTask(List<Part> parts) {
        super(parts);
    }

    public MgUnresolvedPath getPath() {
        return path;
    }

    @Override
    protected void buildParts(List<Part> originalParts) {
        List<Part> parts = new List<>(originalParts);
        path = processBegin(parts);
        processEnd(parts);

        boolean expectedName = true;
        for(Part part : parts){
            if(expectedName){
                if(isName(part) || isAny(part) || isParent(part)){
                    Leaf leaf = (Leaf) part;
                    path.getNodes().addLast(leaf.getText());
                } else {
                    throw new LanguageException("Expected name.");
                }
            } else {
                if(isPath(part)){
                    // nothing to do with the delimiter
                } else {
                    throw new LanguageException("Expected path delimiter.");
                }
            }
            expectedName = !expectedName;
        }
    }

    private MgUnresolvedPath processBegin(List<Part> parts){
        if(parts.count() <= 0) throw new LanguageException("Missing part for path.");
        if(isPath(parts.getFirst())){
            parts.removeFirst();
            return new MgUnresolvedAbsolutePath();
        } else {
            return new MgUnresolvedRelativePath();
        }
    }

    private void processEnd(List<Part> parts){
        if(parts.count() <= 0) throw new LanguageException("Missing part for path.");
        if(isPath(parts.getLast())) throw new LanguageException("Unexpected delimiter at the end of path.");
    }

    private boolean isName(Part part){
        if(part instanceof Name){
            return true;
        }
        return false;
    }

    private boolean isAny(Part part){
        if(part instanceof Operator){
            Operator operator = (Operator) part;
            if(operator.getText().equals(Operators.PATH_ANY)){
                return true;
            }
        }
        return false;
    }

    private boolean isParent(Part part){
        if(part instanceof Operator){
            Operator operator = (Operator) part;
            if(operator.getText().equals(Operators.PATH_PARENT)){
                return true;
            }
        }
        return false;
    }

    private boolean isPath(Part part){
        if(part instanceof Operator){
            Operator operator = (Operator) part;
            if(operator.getText().equals(Operators.PATH)){
                return true;
            }
        }
        return false;
    }
}

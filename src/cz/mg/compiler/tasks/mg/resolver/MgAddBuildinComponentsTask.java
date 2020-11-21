package cz.mg.compiler.tasks.mg.resolver;

import cz.mg.collections.text.ReadonlyText;
import cz.mg.compiler.annotations.Input;
import cz.mg.language.entities.mg.runtime.components.MgComponent;
import cz.mg.language.entities.mg.runtime.components.MgLocation;
import cz.mg.language.entities.mg.runtime.components.stamps.buildin.MgBuildinStamp;
import cz.mg.language.entities.mg.runtime.components.types.buildin.MgBuildinType;
import cz.mg.language.entities.mg.runtime.components.types.buildin.operators.MgBuildinOperator;
import cz.mg.language.entities.mg.runtime.components.variables.buildin.MgBuildinVariable;


public class MgAddBuildinComponentsTask extends MgResolveTask {
    @Input
    private final MgLocation root;

    public MgAddBuildinComponentsTask(MgLocation root) {
        this.root = root;
    }

    @Override
    protected void onRun() {
        open(root, "cz", "mg").getComponents().addCollectionLast(MgBuildinVariable.ALL);
        open(root, "cz", "mg", "types").getComponents().addCollectionLast(MgBuildinType.ALL);
        open(root, "cz", "mg", "types").getComponents().addCollectionLast(MgBuildinOperator.ALL);
        open(root, "cz", "mg", "stamps").getComponents().addCollectionLast(MgBuildinStamp.ALL);
    }

    private static MgLocation open(MgLocation root, String... path){
        MgLocation cwd = root;
        for(String name : path){
            cwd = open(cwd, name);
        }
        return cwd;
    }

    private static MgLocation open(MgLocation parent, String name){
        for(MgComponent component : parent.getComponents()){
            if(component instanceof MgLocation){
                MgLocation location = (MgLocation) component;
                if(location.getName().toString().equals(name)){
                    return location;
                }
            }
        }
        MgLocation newLocation = new MgLocation(new ReadonlyText(name));
        parent.getComponents().addLast(newLocation);
        return newLocation;
    }
}

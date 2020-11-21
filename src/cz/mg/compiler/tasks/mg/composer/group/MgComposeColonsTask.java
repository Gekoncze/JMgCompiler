package cz.mg.compiler.tasks.mg.composer.group;

import cz.mg.collections.list.List;
import cz.mg.collections.list.ListItem;
import cz.mg.language.entities.text.structured.Part;
import cz.mg.language.entities.text.structured.parts.groups.Colon;
import cz.mg.language.entities.text.structured.parts.leaves.Bracket;


public class MgComposeColonsTask extends MgComposeGroupsTask {
    public MgComposeColonsTask(List<List<Part>> groups) {
        super(groups);
    }

    @Override
    protected void onRun(ListItem<Part> partItem) {
        Part part = partItem.get();
        if(isColon(part)){
            Colon colon = new Colon();
            while(partItem.hasNext()){
                colon.getParts().addLast(partItem.removeNext());
            }
            partItem.setData(colon);
            groups.addLast(colon.getParts());
        }
    }

    private boolean isColon(Part part){
        if(part instanceof Bracket){
            Bracket bracket = (Bracket) part;
            if(bracket.getText().equals(":")){
                return true;
            }
        }
        return false;
    }
}

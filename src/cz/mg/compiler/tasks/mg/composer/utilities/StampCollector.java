package cz.mg.compiler.tasks.mg.composer.utilities;

import cz.mg.collections.list.List;
import cz.mg.collections.map.Map;
import cz.mg.collections.text.ReadableText;
import cz.mg.language.entities.text.plain.tokens.StampToken;
import cz.mg.language.entities.text.structured.Block;


public class StampCollector {
    private final Map<Block, List<ReadableText>> map = new Map<>();

    public StampCollector() {
    }

    public void add(Block block, StampToken stamp){
        List<ReadableText> stamps = map.get(block);
        if(stamps == null) stamps = new List<>();
        stamps.addLast(stamp.getText());
        map.set(block, stamps);
    }

    public List<ReadableText> take(Block block){
        List<ReadableText> stamps = map.remove(block);
        if(stamps != null){
            return stamps;
        } else {
            return new List<>();
        }
    }

    public int count(){
        return map.count();
    }
}

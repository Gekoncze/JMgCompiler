package cz.mg.compiler.tasks.mg.composer.utilities;

import cz.mg.collections.list.List;
import cz.mg.collections.map.Map;
import cz.mg.collections.text.ReadableText;
import cz.mg.language.entities.text.plain.tokens.KeywordToken;
import cz.mg.language.entities.text.structured.Block;


public class KeywordCollector {
    private final Map<Block, List<ReadableText>> map = new Map<>();

    public KeywordCollector() {
    }

    public void add(Block block, KeywordToken keyword){
        List<ReadableText> keywords = map.get(block);
        if(keywords == null) keywords = new List<>();
        keywords.addLast(keyword.getText());
        map.set(block, keywords);
    }

    public List<ReadableText> take(Block block){
        List<ReadableText> keywords = map.remove(block);
        if(keywords != null){
            return keywords;
        } else {
            return new List<>();
        }
    }

    public int count(){
        return map.count();
    }
}

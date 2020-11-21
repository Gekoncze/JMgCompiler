package cz.mg.compiler.tasks.mg.composer.group;

import cz.mg.collections.list.List;
import cz.mg.collections.list.ListItem;
import cz.mg.collections.text.ReadableText;
import cz.mg.language.LanguageException;
import cz.mg.language.entities.text.structured.Part;
import cz.mg.language.entities.text.structured.parts.groups.brackets.Brackets;
import cz.mg.language.entities.text.structured.parts.groups.brackets.CurlyBrackets;
import cz.mg.language.entities.text.structured.parts.groups.brackets.RoundBrackets;
import cz.mg.language.entities.text.structured.parts.groups.brackets.SquareBrackets;
import cz.mg.language.entities.text.structured.parts.leaves.Bracket;


public class MgComposeBracketsTask extends MgComposeGroupsTask {
    public MgComposeBracketsTask(List<List<Part>> groups) {
        super(groups);
    }

    @Override
    protected void onRun(ListItem<Part> partItem) {
        Part part = partItem.get();
        Info info = Info.create(part);
        if(info != null) {
            if(info.type == Type.OPENING){
                build(info.style, partItem);
            } else {
                throw new LanguageException("Missing opening " + info.style + " bracket.");
            }
        }
    }

    private void build(Style style, ListItem<Part> partItem){
        Brackets brackets = create(style);
        groups.addFirst(brackets.getParts());
        partItem.setData(brackets);

        while(partItem.hasNext()){
            ListItem<Part> nextPartItem = partItem.getNextItem();
            Info nextInfo = Info.create(nextPartItem.get());
            if(nextInfo != null){
                if(nextInfo.type == Type.OPENING){
                    build(nextInfo.style, nextPartItem);
                } else {
                    if(style == nextInfo.style){
                        nextPartItem.remove();
                        return;
                    } else {
                        throw new LanguageException("Missing opening " + nextInfo.style.name().toLowerCase() + " bracket.");
                    }
                }
            }
            brackets.getParts().addLast(nextPartItem.remove());
        }

        throw new LanguageException("Missing closing " + style.name().toLowerCase() + " bracket.");
    }

    private Brackets create(Style style){
        switch (style){
            case ROUND: return new RoundBrackets();
            case SQUARE: return new SquareBrackets();
            case CURLY: return new CurlyBrackets();
            default: throw new RuntimeException("Unknown bracket style " + this + ".");
        }
    }

    private enum Style {
        ROUND,
        SQUARE,
        CURLY
    }

    private enum Type {
        OPENING,
        CLOSING
    }

    private static class Info {
        public final Style style;
        public final Type type;

        private Info(Style style, Type type) {
            this.style = style;
            this.type = type;
        }

        public static Info create(Part part){
            if(part instanceof Bracket){
                ReadableText text = ((Bracket) part).getText();
                if(text.equals("(")) return new Info(Style.ROUND, Type.OPENING);
                if(text.equals(")")) return new Info(Style.ROUND, Type.CLOSING);
                if(text.equals("[")) return new Info(Style.SQUARE, Type.OPENING);
                if(text.equals("]")) return new Info(Style.SQUARE, Type.CLOSING);
                if(text.equals("{")) return new Info(Style.CURLY, Type.OPENING);
                if(text.equals("}")) return new Info(Style.CURLY, Type.CLOSING);
            }
            return null;
        }
    }
}

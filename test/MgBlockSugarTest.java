import cz.mg.collections.text.ReadonlyText;
import cz.mg.language.entities.text.plain.Line;
import cz.mg.language.entities.text.plain.Page;
import cz.mg.language.entities.text.plain.tokens.*;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.compiler.tasks.mg.composer.MgComposeBlocksTask;


public class MgBlockSugarTest {
    public static void main(String[] args) {
        Page page = createPage();

        System.out.println("### INPUT ###");
        System.out.println(page.getLines().getFirst().getTokens().toText("", token -> new ReadonlyText(token.getText())));
        System.out.println();

        MgComposeBlocksTask parseBlocksTask = new MgComposeBlocksTask(page);
        parseBlocksTask.run();
        Block block = parseBlocksTask.getRoot();

        System.out.println("### OUTPUT ###");
        printBlock(block, -1);
    }

    private static void printBlock(Block block, int level){
        if(level != -1){
            System.out.print(new ReadonlyText("    ").repeat(level));
            System.out.print(block.getStamps().toText(" "));
            if(block.getStamps().count() > 0) System.out.print(" ");
            System.out.print(block.getKeywords().toText(" "));
            if(block.getKeywords().count() > 0) System.out.print(" ");
            System.out.print(block.getParts().toText(" ", o -> new ReadonlyText(o.getClass().getSimpleName())));
            System.out.println();
        }

        for(Block subBlock : block.getBlocks()){
            printBlock(subBlock, level + 1);
        }
    }

    private static Page createPage(){
        Page page = new Page();

        Line line = new Line(
            new KeywordToken(new ReadonlyText("FUNCTION")),
            new SpaceToken(new ReadonlyText(" ")),
            new ObjectNameToken(new ReadonlyText("name")),
            new SpaceToken(new ReadonlyText(" ")),
            new KeywordToken(new ReadonlyText("INPUT")),
            new SpaceToken(new ReadonlyText(" ")),
            new ObjectNameToken(new ReadonlyText("foo")),
            new SpaceToken(new ReadonlyText(" ")),
            new KeywordToken(new ReadonlyText("OUTPUT")),
            new SpaceToken(new ReadonlyText(" ")),
            new ObjectNameToken(new ReadonlyText("bar")),
            new SpaceToken(new ReadonlyText(" ")),
            new KeywordToken(new ReadonlyText("LEFT")),
            new SpaceToken(new ReadonlyText(" ")),
            new KeywordToken(new ReadonlyText("OPERATOR")),
            new SpaceToken(new ReadonlyText(" ")),
            new ObjectNameToken(new ReadonlyText("not")),
            new SpaceToken(new ReadonlyText(" ")),
            new KeywordToken(new ReadonlyText("PRIORITY")),
            new SpaceToken(new ReadonlyText(" ")),
            new ValueToken(new ReadonlyText("1"))
        );

        page.getLines().addLast(line);
        return page;
    }
}

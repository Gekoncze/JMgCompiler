//import cz.mg.collections.text.ReadonlyText;
//import cz.mg.language.entities.c.logical.CSourceCode;
//import cz.mg.language.entities.c.logical.commands.CForCommand;
//import cz.mg.language.entities.c.logical.elements.directives.CInclude;
//import cz.mg.language.entities.c.logical.elements.statements.declarations.CFunctionForwardDeclaration;
//import cz.mg.language.entities.c.logical.elements.statements.declarations.CStructureForwardDeclaration;
//import cz.mg.language.entities.c.logical.elements.statements.definitions.CFunctionDefinition;
//import cz.mg.language.entities.c.logical.elements.statements.definitions.CStructureDefinition;
//import cz.mg.language.entities.c.logical.parts.*;
//import cz.mg.language.entities.c.logical.commands.CBreakCommand;
//import cz.mg.language.entities.c.logical.commands.CContinueCommand;
//import cz.mg.language.entities.c.logical.commands.CWhileCommand;
//import cz.mg.language.entities.c.logical.parts.expressions.CDeclaration;
//import cz.mg.language.entities.c.logical.parts.expressions.CExpression;
//import cz.mg.language.entities.c.logical.parts.expressions.calls.CBinaryOperatorCall;
//import cz.mg.language.entities.c.logical.parts.expressions.calls.CFunctionCall;
//import cz.mg.language.entities.c.logical.parts.expressions.calls.CUnaryLeftOperatorCall;
//import cz.mg.language.entities.c.logical.parts.expressions.calls.CUnaryRightOperatorCall;
//import cz.mg.language.entities.c.logical.parts.expressions.values.CLiteral;
//import cz.mg.language.entities.c.logical.parts.expressions.values.CName;
//import cz.mg.language.entities.text.linear.Page;
//import cz.mg.compiler.tasks.writers.c.CSourceCodeWriterTask;
//
//
//public class MgToCTest {
//    public static void main(String[] args) {
//        CSourceCode sourceCode = new CSourceCode();
//
//        CInclude include = new CInclude(false, new ReadonlyText("stdio.h"));
//        sourceCode.getIncludes().addLast(include);
//
//        sourceCode.getFunctionForwardDeclarations().addLast(getFunctionDeclaration());
//        sourceCode.getFunctionDefinitions().addLast(getFunctionDefinition());
//        sourceCode.getStructureForwardDeclarations().addLast(getStructureDeclaration());
//        sourceCode.getStructureDefinitions().addLast(getStructureDefinition());
//
//        CSourceCodeWriterTask fileWriterTask = new CSourceCodeWriterTask(sourceCode);
//        fileWriterTask.run();
//
//        Page page = new Page(fileWriterTask.getLines());
//        System.out.println(page.toText().toString());
//    }
//
//    private static CFunctionForwardDeclaration getFunctionDeclaration() {
//        CFunctionForwardDeclaration functionDeclaration = new CFunctionForwardDeclaration(
//                new ReadonlyText("myFunction"),
//                getFunctionType()
//        );
//        return functionDeclaration;
//    }
//
//    private static CFunctionType getFunctionType() {
//        CFunctionType type = new CFunctionType();
//        type.getInput().addLast(getVariable());
//        type.getInput().addLast(new CVariable(new CType(new ReadonlyText("double")), new ReadonlyText("b")));
//        type.getInput().addLast(new CVariable(new CType(new ReadonlyText("float")), new ReadonlyText("c")));
//        return type;
//    }
//
//    private static CFunctionDefinition getFunctionDefinition(){
//        CFunctionDefinition functionDefinition = new CFunctionDefinition(getFunctionDeclaration());
//        functionDefinition.getCommands().addLast(getWhileCommand());
//        functionDefinition.getCommands().addLast(getForCommand());
//        functionDefinition.getCommands().addLast(new CBreakCommand());
//        functionDefinition.getCommands().addLast(new CContinueCommand());
//        return functionDefinition;
//    }
//
//    private static CWhileCommand getWhileCommand(){
//        return new CWhileCommand(
//                getExpression(),
//                new CContinueCommand(),
//                new CBreakCommand()
//        );
//    }
//
//    private static CForCommand getForCommand(){
//        CType type = new CType(new ReadonlyText("int"));
//        CName name = new CName(new ReadonlyText("i"));
//        CDeclaration declaration = new CDeclaration(new CVariable(type, name.getName()));
//        return new CForCommand(
//                new CBinaryOperatorCall(
//                        new ReadonlyText("="),
//                        declaration,
//                        new CLiteral(new ReadonlyText("0"))
//                ),
//                new CBinaryOperatorCall(
//                        new ReadonlyText("<"),
//                        name,
//                        new CLiteral(new ReadonlyText("10"))
//                ),
//                new CUnaryRightOperatorCall(
//                    new ReadonlyText("++"),
//                        name
//                )
//        );
//    }
//
//    private static CExpression getExpression(){
//        return new CBinaryOperatorCall(
//                new ReadonlyText("*"),
//                new CFunctionCall(
//                        new ReadonlyText("foobar"),
//                        new CLiteral(new ReadonlyText("9")),
//                        new CName(new ReadonlyText("b"))
//                ),
//                new CUnaryLeftOperatorCall(
//                        new ReadonlyText("-"),
//                        new CName(new ReadonlyText("a"))
//                )
//        );
//    }
//
//    private static CStructureForwardDeclaration getStructureDeclaration(){
//        return new CStructureForwardDeclaration(new ReadonlyText("MyStructure"));
//    }
//
//    private static CStructureDefinition getStructureDefinition(){
//        CStructureDefinition structureDefinition = new CStructureDefinition(new ReadonlyText("MyStructure"));
//        structureDefinition.getModifiers().addLast(CModifier.PACKED);
//        structureDefinition.getVariables().addLast(getVariable());
//        structureDefinition.getVariables().addLast(new CVariable(new CType(new ReadonlyText("float")), new ReadonlyText("b")));
//        return structureDefinition;
//    }
//
//    private static CVariable getVariable(){
//        CType type = new CType(new ReadonlyText("int"));
//        type.getPointers().addLast(new CPointer());
//        type.getPointers().addLast(new CPointer());
//        return new CVariable(type, new ReadonlyText("a"));
//    }
//}

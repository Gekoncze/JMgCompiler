import cz.mg.collections.Clump;
import cz.mg.collections.list.List;
import cz.mg.collections.text.ReadableText;
import cz.mg.collections.text.ReadonlyText;
import cz.mg.language.Named;
import cz.mg.annotations.storage.Link;
import cz.mg.annotations.storage.Part;
import cz.mg.annotations.storage.Shared;
import cz.mg.annotations.storage.Value;
import cz.mg.language.entities.mg.unresolved.MgUnresolvedEntity;
import cz.mg.language.entities.mg.unresolved.components.MgUnresolvedLocation;
import cz.mg.compiler.tasks.mg.compiler.MgSimpleLogicCompilerTask;


class MgCompileTest {
    private static final ReadonlyText INDENT = new ReadonlyText("    ");

    public static void main(String[] args) {
        ReadableText path = new ReadonlyText("/home/me/Desktop/Dev/Java/JMgLanguage/test/src");
        MgSimpleLogicCompilerTask simpleLogicCompilerTask = new MgSimpleLogicCompilerTask(path);
        simpleLogicCompilerTask.run();

        MgUnresolvedLocation root = simpleLogicCompilerTask.getRoot();
        print(root, 0);
    }

    private static void print(Object object, int level) {
        printHeader(object, level);
        if(object instanceof MgUnresolvedEntity) printFields(object, level + 1);
    }

    private static void printHeader(Object object, int level){
        if(object == null) return;
        System.out.print(INDENT.repeat(level) + object.getClass().getSimpleName());
        if(object instanceof Named) System.out.print(" " + ((Named) object).getName());
        if(object instanceof Integer) System.out.print(" " + object);
        if(object instanceof String) System.out.print(" " + object);
        if(object instanceof ReadableText) System.out.print(" " + object);
        System.out.println();
    }

    private static void printFields(Object object, int level){
        if(object == null) return;
        for(Field field : getFields(object)){
            switch (field.ownership){
                case VALUE:
                case PART:
                    print(field.getObject(), level);
                    break;
                case SHARED:
                case LINK:
                    printHeader(field.getObject(), level);
                default:
                    break;
            }
        }
    }

    private static List<Field> getFields(Object object){
        try {
            List<Field> fields = new List<>();
            Class clazz = object.getClass();
            while(clazz != null){
                for(java.lang.reflect.Field field : clazz.getDeclaredFields()){
                    field.setAccessible(true);
                    fields.addCollectionLast(flatten(field.get(object), getOwnership(field)));
                }
                clazz = clazz.getSuperclass();
            }
            return fields;
        } catch (ReflectiveOperationException e){
            throw new RuntimeException(e);
        }
    }

    private static Ownership getOwnership(java.lang.reflect.Field field){
        Ownership ownership = Ownership.OTHER;
        if(field.isAnnotationPresent(Value.class)){
            ownership = Ownership.VALUE;
        } else if(field.isAnnotationPresent(Part.class)){
            ownership = Ownership.PART;
        } else if(field.isAnnotationPresent(Shared.class)){
            ownership = Ownership.SHARED;
        } else if(field.isAnnotationPresent(Link.class)){
            ownership = Ownership.LINK;
        }
        return ownership;
    }

    private static List<Field> flatten(Object object, Ownership ownership){
        List<Field> fields = new List<>();
        if(object instanceof Clump && !(object instanceof ReadableText)){
            for(Object child : ((Clump)object)){
                fields.addLast(new Field(child, ownership));
            }
        } else {
            fields.addLast(new Field(object, ownership));
        }
        return fields;
    }

    private static class Field {
        private final Object object;
        private final Ownership ownership;

        public Field(Object object, Ownership ownership) {
            this.object = object;
            this.ownership = ownership;
        }

        public Object getObject() {
            return object;
        }

        public Ownership getOwnership() {
            return ownership;
        }
    }

    private static enum Ownership {
        VALUE,
        PART,
        SHARED,
        LINK,
        OTHER
    }
}
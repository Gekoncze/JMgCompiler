package cz.mg.compiler.tasks;

public interface ErrorLogger {
    public static ErrorLogger errorLogger = new ErrorLogger() {
        @Override
        public void log(Exception e) {
            System.out.println("ERROR: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    };

    void log(Exception e);
}

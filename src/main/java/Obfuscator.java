public class Obfuscator {

    private static String source = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static String target = "Qz5xAc8vZbpWoniSumy0XtErDCe6wRFaqVsT9dGfBgYh4jHNkU3Jl2MI1KO7LP";


    public static String obfuscate(String s) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int index = source.indexOf(c);
            result.append(target.charAt(index));
        }
        return result.toString();
    }

    public static String unobfuscate(String s) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int index = target.indexOf(c);
            result.append(source.charAt(index));
        }
        return result.toString();
    }
}

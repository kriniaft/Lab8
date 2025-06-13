package gui;


import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageBundle {
    private static final String BASE_NAME = "gui.resources.messages";
    private static ResourceBundle resourceBundle;
    private static boolean initialized = false;

    public static void initialize() {
        if (!initialized) {
            try {
                // Default to English (New Zealand)
                setLocale(new Locale("en", "NZ"));
                initialized = true;
            } catch (Exception e) {
                System.err.println("Failed to initialize LanguageBundle: " + e.getMessage());
                // Fallback to system default locale
                setLocale(Locale.getDefault());
            }
        }
    }

    public static void setLocale(Locale locale) {
        try {
            resourceBundle = ResourceBundle.getBundle(BASE_NAME, locale, 
                Thread.currentThread().getContextClassLoader());
        } catch (Exception e) {
            System.err.println("Failed to set locale: " + e.getMessage());
            // Fallback to English if the requested locale fails
            if (!locale.equals(new Locale("en", "NZ"))) {
                setLocale(new Locale("en", "NZ"));
            }
        }
    }

    // Convenience methods for language switching
    public static void setEnglish() {
        setLocale(new Locale("en", "NZ"));
    }

    public static void setRussian() {
        setLocale(new Locale("ru", "RU"));
    }

    public static void setSlovak() {
        setLocale(new Locale("sk", "SK"));
    }

    public static void setHungarian() {
        setLocale(new Locale("hu", "HU"));
    }

    public static String getString(String key) {
        if (!initialized) {
            initialize();
        }
        try {
            return resourceBundle.getString(key);
        } catch (Exception e) {
            System.err.println("Failed to get string for key: " + key);
            return key;
        }
    }

} 
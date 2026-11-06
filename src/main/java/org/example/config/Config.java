
package org.example.config;

import io.github.cdimascio.dotenv.Dotenv;
public final class Config {

    private static final Dotenv DOTENV =
            Dotenv.configure().ignoreIfMissing().load();

    private Config() {
    }

    public static String baseUrl() {
        return required("BASE_URL");
    }

    public static String standardUser() {
        return required("SAUCE_USERNAME");
    }

    public static String lockedOutUser() {
        return required("LOCKED_OUT_USER");
    }

    public static String password() {
        return required("PASSWORD");
    }

    public static String firstName() {
        return required("FIRST_NAME");
    }

    public static String lastName() {
        return required("LAST_NAME");
    }

    public static String postalCode() {
        return required("POSTAL_CODE");
    }

    // Retrieves a required configuration value
    private static String required(String key) {
        String value = DOTENV.get(key);

        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Missing configuration '" + key
                            + "'. Set it in the .env file or as an environment variable."
            );
        }

        return value.trim();
    }

}

package org.booking.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utility class providing cryptographic helpers for password hashing and verification.
 */
public class UserServiceUtil {

    /**
     * Generates a BCrypt hash with a randomized salt for a plain-text password.
     *
     * @param plainPassword Raw password entered by the user.
     * @return Hashed password string.
     */
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    /**
     * Validates whether a candidate plain-text password matches a stored BCrypt hash.
     *
     * @param plainPassword  Raw password to verify.
     * @param hashedPassword Salted BCrypt hash from database.
     * @return true if password matches the hash, false otherwise.
     */
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}

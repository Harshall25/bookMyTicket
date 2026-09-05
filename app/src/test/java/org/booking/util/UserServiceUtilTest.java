package org.booking.util;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class UserServiceUtilTest {

    @Test(testName = "BCrypt Salted Hash Generation", description = "Verifies that hashPassword generates non-null and uniquely salted hashes for identical plain passwords")
    public void testHashPasswordGeneratesNonNullAndDifferentHash() {
        String rawPassword = "mySecurePassword123";
        String hash1 = UserServiceUtil.hashPassword(rawPassword);
        String hash2 = UserServiceUtil.hashPassword(rawPassword);

        assertNotNull(hash1, "Hashed password should not be null");
        assertNotEquals(rawPassword, hash1, "Hashed password should not equal raw password");
        // Due to different salts, two hashes of the same password should not be identical
        assertNotEquals(hash1, hash2, "BCrypt hashes should have distinct salts");
    }

    @Test(testName = "BCrypt Password Validation Success", description = "Verifies that checkPassword returns true when raw password matches the hashed password")
    public void testCheckPasswordSuccess() {
        String rawPassword = "correctPassword";
        String hashedPassword = UserServiceUtil.hashPassword(rawPassword);

        boolean isMatch = UserServiceUtil.checkPassword(rawPassword, hashedPassword);
        assertTrue(isMatch, "checkPassword should return true for correct password");
    }

    @Test(testName = "BCrypt Password Validation Failure", description = "Verifies that checkPassword returns false when raw password does not match the hashed password")
    public void testCheckPasswordFailureWithWrongPassword() {
        String rawPassword = "correctPassword";
        String wrongPassword = "incorrectPassword";
        String hashedPassword = UserServiceUtil.hashPassword(rawPassword);

        boolean isMatch = UserServiceUtil.checkPassword(wrongPassword, hashedPassword);
        assertFalse(isMatch, "checkPassword should return false for incorrect password");
    }
}

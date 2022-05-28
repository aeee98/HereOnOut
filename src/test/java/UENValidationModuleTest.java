import static org.junit.jupiter.api.Assertions.*;

class UENValidationModuleTest {
    @org.junit.jupiter.api.Test
    void validUEN() {
        UENValidationModule module = new UENValidationModule();
        //9 Digit Valid
        assertTrue(module.validUEN("12345678X"));
        assertTrue(module.validUEN("31313131A"));
        //9 Digit Invalid
        assertFalse(module.validUEN("S12345678"));
        assertFalse(module.validUEN("12345F678"));

        //10 Digit Old Format Valid
        assertTrue(module.validUEN("199612345A"));
        assertTrue(module.validUEN("202212345A"));
        //10 Digit Old Format Invalid
        assertFalse(module.validUEN("205012345A"));
        assertFalse(module.validUEN("2003123456"));
        assertFalse(module.validUEN("200S00000A"));

        //10 Digit New Format Valid
        assertTrue(module.validUEN("R99FC1234A"));
        assertTrue(module.validUEN("S65NB3333B"));
        assertTrue(module.validUEN("T00CD7777J"));
        assertTrue(module.validUEN("T20GA0001G"));
        //10 Digit New Format Invalid
        assertFalse(module.validUEN("R98AA1234A"));
        assertFalse(module.validUEN("R98SS12345"));
        assertFalse(module.validUEN("T99TU4444D"));
        assertFalse(module.validUEN("U00CD1234J"));
    }
}
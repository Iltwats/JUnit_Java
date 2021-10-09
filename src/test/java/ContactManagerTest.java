import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;


import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContactManagerTest {

    ContactManager contactManager;

    @BeforeAll
    public void setUpAll() {
        System.out.println("Executed before anything");
    }

    @BeforeEach
    public void setUpClasses() {
        contactManager = new ContactManager();
    }

    @Test
    @DisplayName("Add Contact Test")
    public void shouldCreateContact() {
        contactManager.addContact("Atul", "Sharma", "09383934549");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
        Assertions.assertTrue(contactManager.getAllContacts().stream()
                .anyMatch(contact ->
                        contact.getFirstName().equals("Atul") &&
                                contact.getLastName().equals("Sharma") &&
                                contact.getPhoneNumber().equals("09383934549")));
    }

    @Test
    @DisplayName("Should not create contact when first name is NULL")
    public void shouldThrowRuntimeExceptionIfFirstNameIsNull() {
        Assertions.assertThrows(RuntimeException.class, () ->
                contactManager.addContact(null, "sharma", "01123456789"));
    }

    @Test
    @DisplayName("Should not create contact when last name is NULL")
    public void shouldThrowRuntimeExceptionIfLastNameIsNull() {
        Assertions.assertThrows(RuntimeException.class, () ->
                contactManager.addContact("Atul", null, "01123456789"));
    }

    @Test
    @DisplayName("Should not create contact when contact is NULL")
    public void shouldThrowRuntimeExceptionIfContactIsNull() {
        Assertions.assertThrows(RuntimeException.class, () ->
                contactManager.addContact("Atul", "sharma", null));
    }



    @Test
    @EnabledOnOs(value = OS.MAC, disabledReason = "Works Only on MAC")
    public void MacTest() {
        contactManager.addContact("Atul", "Sharma", "09383934549");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
    }

    @Test
    @DisabledOnOs(value = OS.WINDOWS, disabledReason = "Works only on WINDOWS")
    public void WinTest() {
        contactManager.addContact("Atul", "Sharma", "09383934549");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
    }

    @DisplayName("Parametrized test with Value Source for different phone numbers")
    @ParameterizedTest
    @ValueSource(strings = {"09123456789","09123456789","09123450679","09123456780"})
    public void addPhoneNumberToContactUsingValueSource(String phoneNumber) {
        contactManager.addContact("Atul", "Sharma", phoneNumber);
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
    }

    @DisplayName("Parametrized test with Method Source for different phone numbers")
    @ParameterizedTest
    @MethodSource("phoneNumbers")
    public void addPhoneNumberToContactUsingMethodSource(String phoneNumber) {
        contactManager.addContact("Atul", "Sharma", phoneNumber);
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
    }

    private static List<String> phoneNumbers(){
        return Arrays.asList("09123456789","09123456789","09412345679","09123456783");
    }

    @DisplayName("Parametrized test with CSV Source for different phone numbers")
    @ParameterizedTest
    @CsvSource({"09123456789","09123456789","09412345679","09123456783"})
    public void addPhoneNumberToContactUsingCsvSource(String phoneNumber) {
        contactManager.addContact("Atul", "Sharma", phoneNumber);
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
    }

    @DisplayName("Parametrized test with CSV File as Source for different phone numbers")
    @ParameterizedTest
    @CsvFileSource(resources = "./data.csv")
    public void addPhoneNumberToContactUsingCsvFileSource(String phoneNumber) {
        contactManager.addContact("Atul", "Sharma", phoneNumber);
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
    }

    @Nested
    @DisplayName("Nested Test for Repetition")
    class RepeatedNestedTest {
        @DisplayName("Repeated Add contacts test")
        @RepeatedTest(value = 5,
                name = "Repeating contact creation test {currentRepetition} of {totalRepetitions}")
        public void shouldCreateContactRepeatedly() {
            contactManager.addContact("Atul", "Sharma", "09383934549");
            Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
            Assertions.assertEquals(1, contactManager.getAllContacts().size());
            Assertions.assertTrue(contactManager.getAllContacts().stream()
                    .anyMatch(contact ->
                            contact.getFirstName().equals("Atul") &&
                                    contact.getLastName().equals("Sharma") &&
                                    contact.getPhoneNumber().equals("09383934549")));
        }
    }

    @Test
    @DisplayName("Test Should Be Disabled")
    @Disabled
    public void shouldBeDisabled() {
        throw new RuntimeException("Test Should Not be executed");
    }
    @AfterEach
    public void removeEveryMethod() {
        System.out.println("After Each method called");
    }

    @AfterAll
    public void removeAll() {
        System.out.println("Remove Every method");
    }


}
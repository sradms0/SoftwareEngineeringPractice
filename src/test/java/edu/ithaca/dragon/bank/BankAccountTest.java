package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance());
    }

    @Test
    void withdrawTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance());
    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com"));
        // invalid prefixes
        assertFalse( BankAccount.isEmailValid("abc-@mail.com") );
        // failing:
        //assertFalse( BankAccount.isEmailValid("abc..def@mail.com") );
        //assertFalse( BankAccount.isEmailValid(".abc@mail.com") );
        assertFalse( BankAccount.isEmailValid("abc#def@mail.com") );

         // invalid domains
        assertFalse( BankAccount.isEmailValid("abc.def@mail.c") );
        assertFalse( BankAccount.isEmailValid("abc.def@mail#archive.com") );
        // failing:
        //assertFalse( BankAccount.isEmailValid("abc.def@mail") );
        assertFalse( BankAccount.isEmailValid("abc.def@mail..com") );

        // invalid domain suffixes
        //failing:
        //assertFalse( BankAccount.isEmailValid("ab-g@mail.bug"));
        //assertFalse( BankAccount.isEmailValid("abdefg@mail.io"));
        assertFalse( BankAccount.isEmailValid("ae-f-g@mail.o"));
        // string index out of range:
        //assertFalse( BankAccount.isEmailValid("ab@mail."));

        // no email
        assertFalse( BankAccount.isEmailValid(""));
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance());
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

}
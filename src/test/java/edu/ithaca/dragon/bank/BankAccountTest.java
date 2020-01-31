package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        // positive balance
        try {
            assertEquals(200, bankAccount.getBalance());
            bankAccount.withdraw(.5);
            assertEquals(199.5, bankAccount.getBalance());
        } catch(InsufficientFundsException ie) {
            fail("Error thrown..");
        }

        // zero balance
        try {
            bankAccount.withdraw(199.5);
            assertEquals(0, bankAccount.getBalance());
        } catch(InsufficientFundsException ie) {
            fail("Error thrown..");
        }
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        // positive
        bankAccount.withdraw(100);
        assertEquals(100, bankAccount.getBalance());
        bankAccount.withdraw(1);
        assertEquals(99, bankAccount.getBalance());
        bankAccount.withdraw(50.5);
        assertEquals(48.5, bankAccount.getBalance());
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.withdraw(100.999));
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.withdraw(1.001));

        // zero
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.withdraw(0));

        // negative
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.withdraw(-100));
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.withdraw(-1));
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.withdraw(-100.999));
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.withdraw(-1.001));

        // exceeding
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
    }

    @Test
    void isEmailValidTest(){
        // PREFIX:
        // starts with alpha char
        assertFalse( BankAccount.isEmailValid(".abc@mail.com") );
        // every non-alpha-num char (-._) has an alpha char after
        assertFalse( BankAccount.isEmailValid("abc-@mail.com") );
        assertFalse( BankAccount.isEmailValid("abc..def@mail.com") );
        // contains optional valid non-alpha-num char (_-.)
        assertTrue( BankAccount.isEmailValid("abc-def@mail.com") );
        assertTrue( BankAccount.isEmailValid("abc.def@mail.com") );
        assertTrue( BankAccount.isEmailValid("abc_def@mail.com") );
        // border value: contains @
        assertTrue(BankAccount.isEmailValid( "a@b.com"));
        assertFalse( BankAccount.isEmailValid("amail.com") );

        // DOMAIN NAME:
        // contains alpha char with optional numbers and/or dashes
        assertFalse( BankAccount.isEmailValid("abc.def@mail+.com") );
        assertFalse( BankAccount.isEmailValid("abc.def@mail#1archive.com") );

        // DOMAIN EXTENSION:
        // starts with period and ends with two characters
        assertFalse( BankAccount.isEmailValid("ae-f-g@mail.o"));
        assertFalse( BankAccount.isEmailValid("ab@mail."));

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
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("b@c.com", -100));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("b@c.com", -1));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("b@c.com", 100.999));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("b@c.com", 1.001));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("b@c.com", -100.999));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("b@c.com", -1.001));
    }

    @Test
    void isAmountValidTest() {
        // negative int
        assertFalse(BankAccount.isAmountValid(-100));
        assertFalse(BankAccount.isAmountValid(-1));
        // zero
        assertFalse(BankAccount.isAmountValid(0));
        // positive int
        assertTrue(BankAccount.isAmountValid(1));
        assertTrue(BankAccount.isAmountValid(100));
        // negative double
        assertFalse(BankAccount.isAmountValid(-0.99));
        assertFalse(BankAccount.isAmountValid(-0.01));
        // positive decimal equal to two
        assertTrue(BankAccount.isAmountValid(0.01));
        assertTrue(BankAccount.isAmountValid(0.99));
        // decimal less than two
        assertTrue(BankAccount.isAmountValid(0.9));
        assertTrue(BankAccount.isAmountValid(0.1));
        // positive decimal greater than two
        assertFalse(BankAccount.isAmountValid(0.001));
        assertFalse(BankAccount.isAmountValid(0.111));
    }

    @Test
    public void depositTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        // positive
        bankAccount.deposit(100);
        assertEquals(300, bankAccount.getBalance());
        bankAccount.deposit(1);
        assertEquals(301, bankAccount.getBalance());
        bankAccount.deposit(50.5);
        assertEquals(351.5, bankAccount.getBalance());
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.deposit(100.999));
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.deposit(1.001));

        // zero
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.deposit(0));

        // negative
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.deposit(-100));
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.deposit(-1));
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.deposit(-100.999));
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.deposit(-1.001));
    }

    @Test
    public void transferTest() {
        BankAccount bankAccountA = new BankAccount("a@b.com", 500),
                    bankAccountB = new BankAccount("b@c.com", 200);

        // positive int from account A to B
        bankAccountA.transfer(100, bankAccountB);
        assertEquals(300, bankAccountB.getBalance());
        assertEquals(200, bankAccountA.getBalance());

        bankAccountA.transfer(1, bankAccountB);
        assertEquals(301, bankAccountB.getBalance());
        assertEquals(199, bankAccountA.getBalance());

        bankAccountA.transfer(50.5, bankAccountB);
        assertEquals(351.5, bankAccountB.getBalance());
        assertEquals(148.5, bankAccountA.getBalance());

        // positive decimal greater than two from A to B
        assertThrows(IllegalArgumentException.class, ()-> bankAccountA.transfer(100.999, bankAccountB));
        assertThrows(IllegalArgumentException.class, ()-> bankAccountA.transfer(1.001, bankAccountB));

        // zero from A to B
        assertThrows(IllegalArgumentException.class, ()-> bankAccountA.transfer(0, bankAccountB));

        // negative from A to B
        assertThrows(IllegalArgumentException.class, ()-> bankAccountA.transfer(-100, bankAccountB));
        assertThrows(IllegalArgumentException.class, ()-> bankAccountA.transfer(-1, bankAccountB));
        assertThrows(IllegalArgumentException.class, ()-> bankAccountA.transfer(-100.999, bankAccountB));
        assertThrows(IllegalArgumentException.class, ()-> bankAccountA.transfer(-1.001, bankAccountB));

        // entire balance from A to B
        assertThrows(InsufficientFundsException.class, ()-> bankAccountA.transfer(bankAccountA.getBalance(), bankAccountB));
        // more than balance from A to B
        assertThrows(InsufficientFundsException.class, ()-> bankAccountA.transfer(bankAccountA.getBalance()+1, bankAccountB));
        // transfer from A to A
        assertThrows(IllegalArgumentException.class, ()-> bankAccountA.transfer(bankAccountA.getBalance(), bankAccountA));
        // transfer from A to non-existing account
        assertThrows(IllegalArgumentException.class, ()-> bankAccountA.transfer(1, null));

    }
}
package edu.ithaca.dragon.bank;

import java.util.Arrays;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (!isAmountValid(startingBalance))
            throw new IllegalArgumentException("A positive balance with no more than two decimal places is required");
        if (isEmailValid(email)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     * @throws InsufficientFundsException if amount exceeds balance
     */
    public void withdraw (double amount)  throws InsufficientFundsException{
        if (!isAmountValid(amount))
            throw new IllegalArgumentException(
                    "Only a positive amount of money with no more than two decimal places is allowed"
            );

        if (amount <= balance && amount >= 0){
                balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }

    }


    public static boolean isEmailValid(String email){
        if (email.indexOf('@') == -1 || email.indexOf('.') == -1){ //checking if it contains @ and ., or if it has no prefix
            return false;
        }
        else{
            email = email.toLowerCase();
            String[] legalValues ={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","0","1","2","3","4","5","6","7","8","9"};
            String[] charArray= {"-","_","."};
            String prefix = email.substring(0, email.indexOf("@"));
            String suffix = email.substring(email.indexOf("@") + 1);

            String firstChar = String.valueOf(email.charAt(0));
            if(Arrays.asList(charArray).contains(firstChar) || firstChar.equals("@")){
                return false;
            }
            for(String str: charArray){

                if(email.indexOf(str) + 1 == email.length()){
                    return false;
                }
                String specialChar = String.valueOf(email.charAt(email.indexOf(str) + 1));
                if(prefix.contains(str) && !Arrays.asList(legalValues).contains(specialChar)){
                    return false; //checks if special characters are at the end of the prefix, not followed by another valid character
                }
                else if(suffix.contains(str) && !Arrays.asList(legalValues).contains(specialChar)){
                    return false; //checks if special characters are at the end of the prefix, not followed by another valid character
                }
            }


            for(int i = 0; i < prefix.length(); i++){
                String currentChar = String.valueOf(prefix.charAt(i));
                    if(!Arrays.asList(legalValues).contains(currentChar) && !Arrays.asList(charArray).contains(currentChar)){
                        return false; //checks if all prefix chars are valid
                }
            }

            for(int i = 0; i < suffix.length(); i++){
                String currentChar = String.valueOf(suffix.charAt(i));
                if(!Arrays.asList(legalValues).contains(currentChar) && !Arrays.asList(charArray).contains(currentChar)|| currentChar.equals("_")){
                    return false; //checks if all suffix values are valid
                }
            }

            int index = suffix.indexOf(".") + 1;
            String domain = suffix.substring(index);

            //checks if domain name is at least 2 characters long, or if it has more than 1 period & that a period exists
            return domain.length() >= 2 && domain.indexOf('.') == -1 && index != 0;
        }
    }

    /**
     * Checks if a money amount is a non-negative amount and has no more than two decimal places.
     * @param amount A money amount
     * @return a boolean to show validity of amount
     */
    public static boolean isAmountValid(double amount) {
        if (amount > 0) {
            String amountStr = ""+amount;
            int decimalLength = amountStr.substring( amountStr.indexOf(".")+1 ).length();

            return decimalLength < 3;
        }
        return false;
    }
}

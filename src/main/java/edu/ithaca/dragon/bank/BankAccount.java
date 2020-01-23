package edu.ithaca.dragon.bank;

import java.util.Arrays;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
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
     */
    public void withdraw (double amount)  {
        balance -= amount;

    }


    public static boolean isEmailValid(String email){
        if (email.indexOf('@') == -1 || email.indexOf('.') == -1 || email.indexOf('@') == 0){ //checking if it contains @ and ., or if it has no prefix
            return false;
        }
        else{
            email = email.toLowerCase();

            String[] charArray= {"-","_","."};
            for(String str: charArray){
                if(email.contains(str) && String.valueOf(email.charAt(email.indexOf(str) + 1 )).equals("@")){
                    return false; //checks if special characters are at the end of the prefix, not followed by another valid character
                }
            }
            String prefix = email.substring(0, email.indexOf("@"));
            String suffix = email.substring(email.indexOf("@") + 1);
            String[] legalValues ={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","0","1","2","3","4","5","6","7","8","9", "-","_","."};

            for(int i = 0; i < prefix.length(); i++){
                String currentChar = String.valueOf(prefix.charAt(i));
                    if(!Arrays.asList(legalValues).contains(currentChar)){
                        return false; //checks if all prefix chars are valid
                }
            }

            for(int i = 0; i < suffix.length(); i++){
                String currentChar = String.valueOf(suffix.charAt(i));
                if(!Arrays.asList(legalValues).contains(currentChar) || currentChar.equals("_")){
                    return false; //checks if all suffix values are valid
                }
            }

            int index = suffix.indexOf(".") + 1;
            String domain = suffix.substring(index);

            //checks if domain name is at least 2 characters long, or if it has more than 1 period
            return domain.length() >= 2 && domain.indexOf('.') == -1;
        }
    }
}

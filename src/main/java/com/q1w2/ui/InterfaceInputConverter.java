package com.q1w2.ui;

import com.q1w2.exceptions.IncorrectInterfaceInputException;

public class InterfaceInputConverter {

    public static int convertStringToNumber(String number) throws IncorrectInterfaceInputException {
        int intNumber = 0;
        try {
            intNumber = Integer.parseInt(number);
            if (intNumber <= 0)
                throw new NumberFormatException();
        } catch (NumberFormatException e) {
            throw new IncorrectInterfaceInputException("***Incorrect input. This must be a number greater than 0.***");
        }
        return intNumber;
    }
}

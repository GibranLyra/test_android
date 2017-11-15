package gibran.com.br.zaptest.helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by gibranlyra on 15/11/17.
 */

public class Validators {

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}

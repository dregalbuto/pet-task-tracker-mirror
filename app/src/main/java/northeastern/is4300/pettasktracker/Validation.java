package northeastern.is4300.pettasktracker;

import java.util.regex.Pattern;
import android.widget.EditText;

/**
 *
 */

public class Validation {

    private static final String REQUIRED_MSG = "Required";

    public static boolean isValid(EditText editText, String regex, String errMsg, boolean required) {

            String text = editText.getText().toString().trim();
            editText.setError(null);

            if(required && !hasText(editText)) {
                return false;
            }

            if (required && !Pattern.matches(regex, text)) {
                editText.setError(errMsg);
                return false;
            };

            return true;
        }

    public static boolean hasText(EditText editText) {

        String text = editText.getText().toString().trim();
        editText.setError(null);

        if (text.length() == 0) {
            editText.setError(REQUIRED_MSG);
            return false;
        }

        return true;
    }
}

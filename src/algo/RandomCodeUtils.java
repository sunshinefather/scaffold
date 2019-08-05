package algo;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class RandomCodeUtils {
    public static final int TYPE_NUM_ONLY = 0;
    public static final int TYPE_LETTER_ONLY = 1;
    public static final int TYPE_ALL_MIXED = 2;
    public static final int TYPE_NUM_UPPER = 3;
    public static final int TYPE_NUM_LOWER = 4;
    public static final int TYPE_UPPER_ONLY = 5;
    public static final int TYPE_LOWER_ONLY = 6;
    
    private static SecureRandom r;
    
    static {
        try {
            r = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            r =  new SecureRandom();
        }
    }
    
    private RandomCodeUtils() {
        throw new AssertionError();
    }
    
    public static String generateTextCode(int type, int length, String exChars) {
        if (length <= 0) {
            return "";
        }
        StringBuffer code = new StringBuffer();
        int i = 0;

        switch (type) {
        case 0:
            while (i < length) {
                int t = r.nextInt(10);
                if ((exChars == null) || (exChars.indexOf(t) < 0)) {
                    code.append(t);
                    i++;
                }
            }
            break;
        case 1:
            while (i < length) {
                int t = r.nextInt(123);
                if (((t < 97) && ((t < 65) || (t > 90)))
                        || ((exChars != null) && (exChars.indexOf((char) t) >= 0))) {
                    continue;
                }
                code.append((char) t);
                i++;
            }

            break;
        case 2:
            while (i < length) {
                int t = r.nextInt(123);
                if (((t < 97) && ((t < 65) || (t > 90)) && ((t < 48) || (t > 57)))
                        || ((exChars != null) && (exChars.indexOf((char) t) >= 0))) {
                    continue;
                }
                code.append((char) t);
                i++;
            }

            break;
        case 3:
            while (i < length) {
                int t = r.nextInt(91);
                if (((t < 65) && ((t < 48) || (t > 57)))
                        || ((exChars != null) && (exChars.indexOf((char) t) >= 0))) {
                    continue;
                }
                code.append((char) t);
                i++;
            }

            break;
        case 4:
            while (i < length) {
                int t = r.nextInt(123);
                if (((t < 97) && ((t < 48) || (t > 57)))
                        || ((exChars != null) && (exChars.indexOf((char) t) >= 0))) {
                    continue;
                }
                code.append((char) t);
                i++;
            }
            break;
        case 5:
            while (i < length) {
                int t = r.nextInt(91);
                if ((t < 65)
                        || ((exChars != null) && (exChars.indexOf((char) t) >= 0))) {
                    continue;
                }
                code.append((char) t);
                i++;
            }

            break;
        case 6:
            while (i < length) {
                int t = r.nextInt(123);
                if ((t < 97)
                        || ((exChars != null) && (exChars.indexOf((char) t) >= 0))) {
                    continue;
                }
                code.append((char) t);
                i++;
            }

        }

        return code.toString();
    }
}

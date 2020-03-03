package com.rngay.common.util;

import java.util.Random;

public class PassUtil {

    private final static String[] LOWER_CASES = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    private final static String[] UPPER_CASES = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private final static String[] NUMS_LIST = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private final static String[] SYMBOLS_ARRAY = {"!","~","^","_","*"};

    /**
     * 生成随机密码
     * @param pwd_len 密码长度
     * @return 密码的字符串
     */
    public static String getRandomPwd(int pwd_len) {
        if ( pwd_len < 6 || pwd_len >20 ) {
            return "";
        }
        int lower = pwd_len / 2 ;

        int upper = (pwd_len - lower) /2;

        int num =  (pwd_len - lower) / 2;

        int symbol = pwd_len - lower - upper - num ;

        StringBuilder pwd = new StringBuilder();
        Random random = new Random();
        int position;
        while((lower + upper + num + symbol) > 0 ){
            if(lower > 0){
                position = random.nextInt(pwd.length() + 1);

                pwd.insert(position,LOWER_CASES[random.nextInt(LOWER_CASES.length)]);
                lower--;
            }
            if(upper > 0){
                position = random.nextInt(pwd.length()  + 1);

                pwd.insert(position,UPPER_CASES[random.nextInt(UPPER_CASES.length)]);
                upper--;
            }
            if(num > 0){
                position = random.nextInt(pwd.length()  + 1);

                pwd.insert(position,NUMS_LIST[random.nextInt(NUMS_LIST.length)]);
                num--;
            }
            if(symbol > 0){
                position = random.nextInt(pwd.length()  + 1);

                pwd.insert(position,SYMBOLS_ARRAY[random.nextInt(SYMBOLS_ARRAY.length)]);
                symbol--;
            }
            System.out.println(pwd.toString());
        }


        return pwd.toString();
    }

}

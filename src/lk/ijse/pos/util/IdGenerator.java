package lk.ijse.pos.util;

import java.util.Random;

public class IdGenerator {
    public static final String RESOURCE="0123456789";
    public static String getId(){
        String id="C";
        int rand=new Random().nextInt(3)+3;
    for (int i = 0; i < rand; i++){
        id=id.concat(String.valueOf(RESOURCE.charAt(new Random().nextInt(9))));
    }
    return id;
    }
}

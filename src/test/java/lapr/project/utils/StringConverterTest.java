package lapr.project.utils;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StringConverterTest {

    @Test
    void convertToStringList() {
        List<Integer> lista=new ArrayList<>();
        lista.add(1);
        lista.add(2);
        lista.add(3);
        lista.add(4);

        List<String> expResult=new ArrayList<>();
        expResult.add("1");
        expResult.add("2");
        expResult.add("3");
        expResult.add("4");

        List<String> result=StringConverter.convertToStringList(lista);

        assertEquals(expResult,result);
    }
}
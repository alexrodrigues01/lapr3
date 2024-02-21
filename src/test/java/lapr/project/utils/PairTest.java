package lapr.project.utils;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class PairTest {

    @Test
    void getKey() {
        String expResult= "expResult";
        Pair<String,Integer> pair=new Pair<>(expResult,3);
        assertEquals(expResult,pair.getKey());
    }

    @Test
    void getValue() {
        Integer expResult= 3;
        Pair<String,Integer> pair=new Pair<>("expResult",expResult);
        assertEquals(expResult,pair.getValue());
    }

    @Test
    void testEquals() {
        Pair<String,Integer> pair=new Pair<>("expResult",3);
        Pair<String,Integer> pair2=new Pair<>("expResult",3);
        assertEquals(pair,pair2);
    }

    @Test
    void testEquals1() {
        Pair<String,Integer> pair=new Pair<>("expResult",2);
        Pair<String,Integer> pair2=new Pair<>("expResult",3);
        assertNotEquals(pair,pair2);
    }
    @Test
    void testEquals0() {
        Pair<String,Integer> pair=new Pair<>("expResult",3);
        Pair<String,Integer> pair2=new Pair<>("expResultt",3);
        assertNotEquals(pair,pair2);
    }
    @Test
    void testEquals2() {
        Pair<String,Integer> pair=new Pair<>("expResult",2);
        Pair<String,Integer> pair2=pair;
        assertEquals(pair,pair2);
    }
    @Test
    void testEquals3() {
        Pair<String,Integer> pair=new Pair<>("expResult",2);
        Pair<String,Integer> pair2=null;
        assertNotEquals(pair,pair2);
    }
    @Test
    void testEquals4() {
        Pair<String,Integer> pair=new Pair<>("expResult",2);
        String pair2="ola";
       assertFalse(pair.equals(pair2));
    }

    @Test
    void testHashCode() {
        Pair<String,Integer> pair=new Pair<>("expResult",3);
        assertEquals(pair.hashCode(), Objects.hash(pair.getKey(), pair.getValue()));
    }
}
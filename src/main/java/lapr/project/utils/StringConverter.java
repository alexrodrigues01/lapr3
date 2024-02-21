package lapr.project.utils;

import java.util.ArrayList;
import java.util.List;

public class StringConverter {

    /**
     * Construtor private para impedir a criação de objetos deste tipo
     */
    private StringConverter() {
    }

    /**
     * Converte uma lista em string
     *
     * @param <E>
     * @param lista
     * @return
     */
    public static <E> List<String> convertToStringList(List<E> lista) {
        List<String> listaReturn = new ArrayList<>();
        for (E objeto : lista) {
            listaReturn.add(objeto.toString());
        }
        return listaReturn;
    }
}

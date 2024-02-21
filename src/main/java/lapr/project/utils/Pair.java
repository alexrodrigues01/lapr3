package lapr.project.utils;

import java.util.Objects;

/**
 *
 * Class baseada no Pair do javafx do JDK8
 *
 * @param <K>
 * @param <V>
 */
public class Pair<K, V> {

    private K element0;
    private V element1;

    /**
     * Construtor do Pair
     *
     * @param element0
     * @param element1
     */
    public Pair(K element0, V element1) {
        this.element0 = element0;
        this.element1 = element1;
    }

    /**
     * get para o primeiro elemento
     *
     * @return key
     */
    public K getKey() {
        return element0;
    }

    /**
     * get para o segundo elemento
     *
     * @return value
     */
    public V getValue() {
        return element1;
    }

    /**
     * equals do Pair
     *
     * @param obj
     * @return boolean se e igual
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pair<?, ?> other = (Pair<?, ?>) obj;
        if (!Objects.equals(this.element0, other.element0)) {
            return false;
        }
        return Objects.equals(this.element1, other.element1);
    }

    /**
     * HashCode do Pair
     *
     * @return hashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(element0, element1);
    }

    /**
     * set para o primeiro elemento
     *
     * @param key
     */
    public void setKey(K key) {
        this.element0 = key;
    }

    /**
     * set para o segundo elemento
     *
     * @param value
     */
    public void setValue(V value) {
        this.element1 = value;
    }

}

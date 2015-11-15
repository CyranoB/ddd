package be.wallonie.ddd.kernel.context;

/**
 * Created by asmolabs on 14/11/15.
 */
public interface KernelContext {

    /**
     * @param key
     * @param value
     */
    void setData(String key, Object value);

    /**
     *
     * @param key
     * @param <T>
     * @return
     */
    <T> T getData(String key);

    /**
     *
     * @param key
     */
    void clearData(String key);
}

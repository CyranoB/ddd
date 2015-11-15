package be.wallonie.ddd.kernel.context;

/**
 * Created by asmolabs on 14/11/15.
 */
public interface KernelContext {

    void setData(String key, Object value);

    <T> T getData(String key);

    void clearData(String key);
}

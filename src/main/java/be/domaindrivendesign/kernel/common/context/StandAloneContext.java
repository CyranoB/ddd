package be.domaindrivendesign.kernel.common.context;

import java.util.HashMap;

/**
 * Created by asmolabs on 14/11/15.
 */
public class StandAloneContext implements KernelContext {

    private final ThreadLocal<HashMap> context = new ThreadLocal<>();

    public StandAloneContext() {
        context.set(new HashMap());
    }

    @Override
    public void clearData(String key) {
        context.get().clear();
    }

    @Override
    public void setData(String key, Object value) {
        //noinspection unchecked
        context.get().put(key, value);
    }

    @Override
    public <T> T getData(String key) {
        //noinspection unchecked
        return context.get() == null ? null : (T) context.get().get(key);
    }
}

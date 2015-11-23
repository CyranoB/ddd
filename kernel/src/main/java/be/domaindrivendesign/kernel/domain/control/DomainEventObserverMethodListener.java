package be.domaindrivendesign.kernel.domain.control;

import be.domaindrivendesign.kernel.domain.interfaces.DomainEventListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by eddie on 23/11/15.
 */
public class DomainEventObserverMethodListener<T> implements DomainEventListener<T> {

    protected String descriptor;
    protected Method method;
    protected Object instance;

    public DomainEventObserverMethodListener(Object instance, Method method) {
        this.instance = instance;
        this.method = method;
        //This descriptor is used in the equals and hashcode method to compare
        //methods between super-classes, subclasses and interface declarations.
        this.descriptor = method.getName() + ':' + makeDescriptor(method);
        method.setAccessible(true);
    }

    public void onEvent(Object event) {
        try {
            method.invoke(instance, event);
        } catch (InvocationTargetException e) {
            //TODO log?
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Object getInstance() {
        return instance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DomainEventObserverMethodListener that = (DomainEventObserverMethodListener) o;

        if (descriptor != null ? !descriptor.equals(that.descriptor) : that.descriptor != null) return false;
        return !(instance != null ? !instance.equals(that.instance) : that.instance != null);

    }

    @Override
    public int hashCode() {
        int result = descriptor != null ? descriptor.hashCode() : 0;
        result = 31 * result + (instance != null ? instance.hashCode() : 0);
        return result;
    }


    /**
     * Makes a descriptor for a given method.
     */
    public String makeDescriptor(Method m) {
        Class[] params = m.getParameterTypes();
        return makeDescriptor(params, m.getReturnType());
    }

    /**
     * Makes a descriptor for a given method.
     *
     * @param params  parameter types.
     * @param retType return type.
     */
    public String makeDescriptor(Class[] params, Class retType) {
        StringBuffer sbuf = new StringBuffer();
        sbuf.append('(');
        for (int i = 0; i < params.length; i++)
            makeDesc(sbuf, params[i]);

        sbuf.append(')');
        if (retType != null)
            makeDesc(sbuf, retType);

        return sbuf.toString();
    }

    private void makeDesc(StringBuffer sbuf, Class type) {
        if (type.isArray()) {
            sbuf.append('[');
            makeDesc(sbuf, type.getComponentType());
        } else if (type.isPrimitive()) {
            if (type == Void.TYPE)
                sbuf.append('V');
            else if (type == Integer.TYPE)
                sbuf.append('I');
            else if (type == Byte.TYPE)
                sbuf.append('B');
            else if (type == Long.TYPE)
                sbuf.append('J');
            else if (type == Double.TYPE)
                sbuf.append('D');
            else if (type == Float.TYPE)
                sbuf.append('F');
            else if (type == Character.TYPE)
                sbuf.append('C');
            else if (type == Short.TYPE)
                sbuf.append('S');
            else if (type == Boolean.TYPE)
                sbuf.append('Z');
            else
                throw new RuntimeException("bad type: " + type.getName());
        } else
            sbuf.append('L').append(type.getName().replace('.', '/'))
                    .append(';');
    }

}

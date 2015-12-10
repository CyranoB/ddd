package be.domaindrivendesign.kernel.common.error;

/**
 * Created by eddie on 18/11/15.
 */
public class KernelException extends RuntimeException {
    public KernelException(String message) {
        super(message);
    }

    public KernelException(String message, Exception innerException) {
        super(message, innerException);
    }

}

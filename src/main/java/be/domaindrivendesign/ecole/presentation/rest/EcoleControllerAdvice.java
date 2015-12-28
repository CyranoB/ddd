package be.domaindrivendesign.ecole.presentation.rest;

import be.domaindrivendesign.kernel.application.error.KernelApplicationException;
import be.domaindrivendesign.kernel.common.error.KernelException;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@ControllerAdvice(annotations = RestController.class)
public class EcoleControllerAdvice {
    private final MediaType vndErrorMediaType = MediaType.parseMediaType("application/vnd.error");

    @ExceptionHandler(KernelException.class)
    ResponseEntity<VndErrors> kernelException(KernelException e) {
        return error(e, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(KernelApplicationException.class)
    ResponseEntity<VndErrors> kernelApplicationException(KernelApplicationException e) {
        return error(e, HttpStatus.OK, e.getMessage());
    }

    private <E extends Exception> ResponseEntity<VndErrors> error(E e, HttpStatus httpStatus, String logref) {
        String msg = Optional.of(e.getMessage()).orElse(e.getClass().getSimpleName());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(this.vndErrorMediaType);
        return new ResponseEntity<>(new VndErrors(logref, msg), httpHeaders, httpStatus);
    }
}

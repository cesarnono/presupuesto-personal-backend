package com.gestion.cuentas.error;

        import com.gestion.cuentas.excepcion.UsuarioPasswordIncorrectoException;
        import org.springframework.http.HttpHeaders;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.ControllerAdvice;
        import org.springframework.web.bind.annotation.ExceptionHandler;
        import org.springframework.web.context.request.WebRequest;
        import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ManejadorGlobalExcepcion extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {UsuarioPasswordIncorrectoException.class})
    protected ResponseEntity<Object> manejarUsuarioPasswordIncorrecto(RuntimeException ex,
                                                                      WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }
}

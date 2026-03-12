package com.luizen.pedido.infra.entrada;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.luizen.pedido.aplicacao.entrada.ApplicationException;
import com.luizen.pedido.dominio.exceptions.DominioException;
import com.luizen.pedido.kernelcompartilhado.MyLogger;
import com.luizen.pedido.kernelcompartilhado.MyLoggerMessage;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<ApiResponse> handleMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException exception, WebRequest request) {
            ApiResponse error = new ApiResponse(
                String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()),
                "Formato de resposta não suportado.",
                null,
                List.of("Formato de resposta não suportado.")
        );

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(error);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiResponse> handleMediaTypeNotSupported(HttpMediaTypeNotSupportedException exception,
            WebRequest request) {
        ApiResponse error = new ApiResponse(
                String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()),
                "Tipo de conteúdo não suportado.",
                null,
                List.of("Tipo de conteúdo não suportado."));

        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(error);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ApiResponse> handleApplicationException(ApplicationException exception, WebRequest request) {
        ApiResponse error = new ApiResponse(
                String.valueOf(HttpStatus.BAD_REQUEST.value()),
                "Bad Request",
                null,
                List.of(exception.getMessage()));

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGenericException(Exception exception, WebRequest request) {
        MyLogger.getInstance(GlobalExceptionHandler.class).error(MyLoggerMessage.of("ERRO", "EXCEPTION_HANDLER", Map.of(
            "exception", exception.getClass().getName(),
            "message", exception.getMessage(),
            "request", request.getDescription(false)
        )));

        ApiResponse error = new ApiResponse(
                String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                "Internal Server Error",
                null,
                List.of("Erro interno inesperado."));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(DominioException.class)
    public ResponseEntity<ApiResponse> handleDomainException(DominioException exception, WebRequest request) {
        ApiResponse error = new ApiResponse(
                String.valueOf(HttpStatus.BAD_REQUEST.value()),
                "Domain Error",
                null,
                exception.getErrors());

        return ResponseEntity.badRequest().body(error);
    }

}

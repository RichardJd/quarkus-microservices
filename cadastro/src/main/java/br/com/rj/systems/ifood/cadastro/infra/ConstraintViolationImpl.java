package br.com.rj.systems.ifood.cadastro.infra;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.ConstraintViolation;
import java.io.Serializable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConstraintViolationImpl implements Serializable {

    @Schema(description = "Path do atributo, ex: dataInicio, pessoa.endereco.numero")
    private final String attribute;

    @Schema(description = "Mensagem descritiva do erro possivelmente associado ao path", required = true)
    private final String message;

    private ConstraintViolationImpl(ConstraintViolation<?> violation) {
        this.message = violation.getMessage();
        this.attribute = Stream.of(violation.getPropertyPath().toString().split("\\.")).skip(2).collect(Collectors.joining("."));
    }

    public ConstraintViolationImpl(String attribute, String message) {
        this.message = message;
        this.attribute = attribute;
    }

    public static ConstraintViolationImpl of(ConstraintViolation<?> violation) {
        return new ConstraintViolationImpl(violation);
    }

    public static ConstraintViolationImpl of(String violation) {
        return new ConstraintViolationImpl(null, violation);
    }

    public String getMessage() {
        return message;
    }

    public String getAttribute() {
        return attribute;
    }
}

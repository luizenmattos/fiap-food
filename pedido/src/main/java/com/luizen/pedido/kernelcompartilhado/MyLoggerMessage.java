package com.luizen.pedido.kernelcompartilhado;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


public record MyLoggerMessage(LocalDateTime horario, String status, String evento, Map<String, String> parametros) {

    public static MyLoggerMessage of(String status, String evento, Map<String, String> parametros) {
        Map<String, String> parametrosMap = null;
        if (parametros != null) {
            parametrosMap = new HashMap<>(parametros);
        }
        
        return new MyLoggerMessage(LocalDateTime.now(), status, evento, parametrosMap);
    }

    public String getStructuredLogging() {
        String log = "horario=" + horario.toString() 
                   + " status=" + status 
                   + " evento=" + evento;

        if (parametros != null) {
            for (Map.Entry<String, String> entry : parametros.entrySet()) {
                log += " " + entry.getKey() + "=" + entry.getValue();
            }
        }

        return log;
    }
}

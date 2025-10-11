package com.familyspences.procesador_utilidades_api.controllers.ranking.response;

import java.util.Map;

@SuppressWarnings("java:S6218")
public record SuccessfulResponse( Map<String, Double> ranking) implements Response {

}

package com.familyspences.procesador_utilidades_api.service.budget;

import java.time.LocalDate;
import java.util.UUID;

public record CreateBudgetRequest (LocalDate period, double budgetAmount, UUID responsibleId){

}

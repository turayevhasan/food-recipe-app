package uz.pdp.food_recipe_app.repository;

import uz.pdp.food_recipe_app.entity.Procedure;
import uz.pdp.food_recipe_app.payload.procedure.res.ProcedureRes;

public interface ProcedureMapper {
    static ProcedureRes entityToRes(Procedure procedure) {
        return ProcedureRes.builder()
                .id(procedure.getId())
                .text(procedure.getText())
                .createdAt(procedure.getCreatedAt())
                .updatedAt(procedure.getUpdatedAt())
                .build();
    }
}

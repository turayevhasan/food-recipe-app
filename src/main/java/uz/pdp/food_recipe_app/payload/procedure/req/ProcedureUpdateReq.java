package uz.pdp.food_recipe_app.payload.procedure.req;

import lombok.Data;

@Data
public class ProcedureUpdateReq {
    private String text;
    private Long targetProcedureId;
}

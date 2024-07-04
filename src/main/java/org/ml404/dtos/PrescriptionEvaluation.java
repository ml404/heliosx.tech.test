package org.ml404.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "PrescriptionEvaluation object whether a medicine may be prescribed")
public class PrescriptionEvaluation {

    @Schema(description = "field denoting whether the prescription can be prescribed or not")
    private boolean canPrescribe;

    public PrescriptionEvaluation(boolean canPrescribe) {
        this.canPrescribe = canPrescribe;
    }

    public boolean isCanPrescribe() {
        return canPrescribe;
    }

    public void setCanPrescribe(boolean canPrescribe) {
        this.canPrescribe = canPrescribe;
    }
}

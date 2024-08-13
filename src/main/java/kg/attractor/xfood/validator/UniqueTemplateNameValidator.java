package kg.attractor.xfood.validator;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.attractor.xfood.service.CheckTypeService;
import kg.attractor.xfood.service.CriteriaService;

public class UniqueTemplateNameValidator implements ConstraintValidator<UniqueTemplateName, String> {

    private final CheckTypeService checkTypeService;

    public UniqueTemplateNameValidator(CheckTypeService checkTypeService) {
        this.checkTypeService = checkTypeService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }
        return !checkTypeService.existsByName(value);
    }
}

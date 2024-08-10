package kg.attractor.xfood.specification;

import kg.attractor.xfood.model.Appeal;
import org.springframework.data.jpa.domain.Specification;

public class AppealSpecification {

    public static Specification<Appeal> isAccepted(Boolean bool) {
        return (root, query, criteriaBuilder) -> {
            if (bool == null) {
                return criteriaBuilder.isNull(root.get("isAccepted"));
            }
            return criteriaBuilder.equal(root.get("isAccepted"), bool);
        };
    }

    public static Specification<Appeal> hasPizzeria(String pizzeriaName) {
        return(r, q, cb) -> {
            if(pizzeriaName.equals("default")) {
                return cb.conjunction();
            }
            return cb.equal(r
                    .get("checkListsCriteria")
                    .get("checklist")
                    .get("workSchedule")
                    .get("pizzeria")
                    .get("name"), pizzeriaName);
        };
    }

    public static Specification<Appeal> hasExpert(String expertEmail) {
        return(r, q, cb) -> {
            if(expertEmail.equals("default")) {
                return cb.conjunction();
            }
            return cb.equal(r
                    .get("checkListsCriteria")
                    .get("checklist")
                    .get("expert")
                    .get("email"), expertEmail);
        };
    }
}

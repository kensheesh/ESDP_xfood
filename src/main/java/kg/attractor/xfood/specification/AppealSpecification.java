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

    public static Specification<Appeal> hasPizzeria(Long pizzeriaId) {
        return(r, q, cb) -> {
            if(pizzeriaId == 0) {
                return cb.conjunction();
            }
            return cb.equal(r
                    .get("checkListsCriteria")
                    .get("checklist")
                    .get("workSchedule")
                    .get("pizzeria")
                    .get("id"), pizzeriaId);
        };
    }

    public static Specification<Appeal> hasExpert(Long expertId) {
        return(r, q, cb) -> {
            if(expertId == 0) {
                return cb.conjunction();
            }
            return cb.equal(r
                    .get("checkListsCriteria")
                    .get("checklist")
                    .get("expert")
                    .get("id"), expertId);
        };
    }
}

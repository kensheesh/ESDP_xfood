package kg.attractor.xfood.specification;

import kg.attractor.xfood.enums.Status;
import kg.attractor.xfood.model.CheckList;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class ChecklistSpecification {

    public static Specification<CheckList> hasStatus(Status status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }

    public static Specification<CheckList> hasExpert(String email) {
        return (root, query, criteriaBuilder) -> {
            if (email == null  || email.isEmpty()){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("expert").get("email"), email);
        };
    }

    public static Specification<CheckList> betweenDate(LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, criteriaBuilder) -> {
            if(startDate == null || endDate == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.between(root.get("endTime"), startDate, endDate);
        };
    }

    public static Specification<CheckList> hasPizzeria(String pizzeriaName) {
        return(r, q, cb) -> {
            if(pizzeriaName == null || pizzeriaName.equals("default")) {
                return cb.conjunction();
            }
            return cb.equal(r.get("workSchedule").get("pizzeria").get("id"), Long.parseLong(pizzeriaName));
        };
    }
}

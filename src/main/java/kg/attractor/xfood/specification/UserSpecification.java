package kg.attractor.xfood.specification;

import jakarta.persistence.criteria.Predicate;
import kg.attractor.xfood.enums.Role;
import kg.attractor.xfood.model.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification {

    public static Specification<User> hasRole(Role role) {
        return (root, query, criteriaBuilder) -> {
            if (role == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("role"), role);
        };
    }

    public static Specification<User> filterByQuery(String query) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (query.equalsIgnoreCase("да") || query.equalsIgnoreCase("д")) {
                predicates.add(criteriaBuilder.equal(root.get("enabled"), true));
            } else if (query.equalsIgnoreCase("нет") || query.equalsIgnoreCase("н")) {
                predicates.add(criteriaBuilder.equal(root.get("enabled"), false));
            }
            if (query.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            String queryUpperCase = query.toUpperCase();

            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("name")), "%" + queryUpperCase + "%"));
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("surname")), "%" + queryUpperCase + "%"));
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("tgLink")), "%" + queryUpperCase + "%"));
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("email")), "%" + queryUpperCase + "%"));

            Role matchingRole = getRoleFromQuery(queryUpperCase);
            if (matchingRole != null) {
                predicates.add(criteriaBuilder.equal(root.get("role"), matchingRole));
            }

            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }

    private static Role getRoleFromQuery(String query) {
        for (Role role : Role.values()) {
            if (role.name().contains(query)) {
                return role;
            }
        }
        return null;
    }
}

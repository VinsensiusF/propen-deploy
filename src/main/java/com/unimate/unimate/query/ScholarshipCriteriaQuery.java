package com.unimate.unimate.query;

import com.unimate.unimate.entity.Scholarship;
import com.unimate.unimate.enums.ScholarshipDegree;
import com.unimate.unimate.enums.ScholarshipType;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Set;

public class ScholarshipCriteriaQuery {

    private EntityManager entityManager;

    public ScholarshipCriteriaQuery(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Scholarship> getScholarshipsByCriteria(ScholarshipType scholarshipType, Set<ScholarshipDegree> scholarshipDegrees, boolean orderByStartedAtAsc) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Scholarship> criteriaQuery = criteriaBuilder.createQuery(Scholarship.class);
        Root<Scholarship> root = criteriaQuery.from(Scholarship.class);

        Predicate typePredicate = criteriaBuilder.equal(root.get("scholarshipType"), scholarshipType);
        Predicate degreesPredicate = root.join("scholarshipDegrees").in(scholarshipDegrees);

        criteriaQuery.where(typePredicate, degreesPredicate);

        if (orderByStartedAtAsc) {
            criteriaQuery.orderBy(criteriaBuilder.asc(root.get("startedAt")));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("startedAt")));
        }

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}

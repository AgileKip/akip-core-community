package org.akip.groovy

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import jakarta.persistence.EntityManager

@Component("hqlApi")
class HqlApi {

    @Autowired
    EntityManager entityManager

    Object findObject(String hql) {
        return entityManager.createQuery(hql).getSingleResult()
    }

    List<?> findList(String hql) {
        return entityManager.createQuery(hql).resultList
    }

}

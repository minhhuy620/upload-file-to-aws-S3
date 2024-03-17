package blog.be.controller;

import blog.be.dtos.ApiResponse;
import blog.be.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/test")
public class TestController {
//    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
//    EntityManager entityManager = entityManagerFactory.createEntityManager();
    @GetMapping(value = "/admin")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getAuthenticatedUser() {
//        String sql = "SELECT host_name FROM v$instance";
//        String sql = "SELECT sys_context('USERENV','SERVER_HOST') server_host FROM dual"; this also works
//        Query query = entityManager.createNativeQuery(sql);
//        return query.getSingleResult().toString();
        return "TEST";
    }
}

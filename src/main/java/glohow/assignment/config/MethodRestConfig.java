package glohow.assignment.config;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;


@Configuration
public class MethodRestConfig implements RepositoryRestConfigurer {
    //private String url = "http://localhost:8080";
    private String url = "*";
    @Autowired
    private EntityManager entityManager;
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        //CORS configuration
        //allow http://localhost:3000 to access backend and allow method get post put delete
        cors.addMapping("/**").allowedOrigins(url).allowedMethods("GET", "POST", "PUT", "DELETE");
//        HttpMethod[] blockMethods = {
//                HttpMethod.POST,
//                HttpMethod.PUT,
//                HttpMethod.PATCH,
//                HttpMethod.DELETE
//        };
//        HttpMethod[] blockMethodsDelete = {
//                HttpMethod.DELETE
//        };
        // expose id for all entity
        //config.exposeIdsFor(entityManager.getMetamodel().getEntities().stream().map(Type::getJavaType).toArray(Class[]::new));
        //expose id for only rights
        //config.exposeIdsFor(Rights.class);

    }
    private void blockHttpMethods(Class c, RepositoryRestConfiguration config, HttpMethod[] methods){
        config.getExposureConfiguration().
                forDomainType(c).
                withItemExposure(((metdata, httpMethods) -> httpMethods.disable(methods))).
                withCollectionExposure(((metdata, httpMethods) -> httpMethods.disable(methods)));
    }
}
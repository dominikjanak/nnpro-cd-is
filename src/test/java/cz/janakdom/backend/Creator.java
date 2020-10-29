package cz.janakdom.backend;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import java.lang.reflect.Field;
import java.sql.Date;
import java.util.List;
import java.util.Map;

@Component
public class Creator {

    static Creator instance;
    Log log = LogFactory.getLog(Creator.class);
    @Autowired
    private ApplicationContext applicationContext;

    public static void saveMore(Object... entities) {
        instance.saveEntities(entities);
    }

    public static <T> T save(T entity) {
        return instance.saveEntity(entity);
    }

    @PostConstruct
    public void postConstruct() {
        instance = this;
    }

    public void saveEntities(Object... entities) {
        for (Object entity : entities) {
            saveEntity(entity);
        }
    }

    public <T> T saveEntity(T entity) {
        return saveEntity(entity, false);
    }

    public <T> T saveEntity(T entity, boolean deleteOthers) {
        try {
            Map props = PropertyUtils.describe(entity);
            List<Field> allFields = FieldUtils.getAllFieldsList(entity.getClass());
            for (Field field : allFields) {
                try {
                    field.setAccessible(true);
                    Object propValue = FieldUtils.readField(field, entity);
                    final boolean notEmptyField = fieldHasAnnotation(field, NotEmpty.class);
                    boolean manyToOne = fieldHasAnnotation(field, ManyToOne.class);
                    boolean id = fieldHasAnnotation(field, Id.class);
                    if (propValue == null && !id) {
                        Class<?> fieldClass = field.getType();
                        if (fieldClass.isAssignableFrom(String.class)) {
                            propValue = "Test " + field.getName();
                        } else {
                            if (Date.class.equals(fieldClass)) {
                                propValue = new Date(System.currentTimeMillis());
                            } else if (Long.class.equals(fieldClass)) {
                                propValue = 1L;
                            } else {
                                propValue = fieldClass.newInstance();
                            }
                        }
                        PropertyUtils.setProperty(entity, field.getName(), propValue);

                    }
                    saveChildEntity(propValue);
                } catch (IllegalAccessException e) {
                    log.info("Skipping " + field.getName() + ", as it is probably private");
                }
            }

            for (Object propName : props.keySet()) {
                Object propValue = props.get(propName);
                saveChildEntity(propValue);
            }

            CrudRepository dao = getDao(entity);
            // if (deleteOthers) {
            //   dao.deleteAllInBatch();
            // }
            dao.save(entity);
        } catch (Exception e) {
            throw new IllegalStateException("Problem", e);
        }
        return entity;

    }

    private boolean fieldHasAnnotation(Field field, Class annotationClass) {
        return field.getDeclaredAnnotationsByType(annotationClass).length > 0;
    }

    private CrudRepository getDao(Object entity) {
        String repoClassName = entity.getClass().getSimpleName();
        String repositoryBeanName = repoClassName.substring(0, 1).toLowerCase() + repoClassName.substring(1) + "Dao";
        return (CrudRepository) applicationContext.getBean(repositoryBeanName);
    }

    private void saveChildEntity(Object propValue) {
        if (propValue != null) {
            Class<?> valueClass = propValue.getClass();
            final boolean isEntity = isEntity(valueClass);
            if ((isEntity)) {

                saveEntity(propValue);
                String className = propValue.getClass().getSimpleName();
                String daoName = className.substring(0, 1).toLowerCase() + className.substring(1) + "Dao";

                CrudRepository repository = applicationContext.getBeansOfType(CrudRepository.class).get(daoName);
                repository.save(propValue);
            }
        }
    }

    private boolean isEntity(Class<?> valueClass) {
        return valueClass.getDeclaredAnnotationsByType(Entity.class).length > 0;
    }
}

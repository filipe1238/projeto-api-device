package com.app.projetoapi.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

@Service
public abstract class ParentService<T> {

    @Autowired
    private FilterService<T, Integer> filterService;

    /**
     * Este método deve ser implementado nas classes filhas para retornar o repositório.
     *
     * @return
     */
    public abstract BaseRepository<T, Integer> getRepository();

    public void validate(T object) {
        // override this method to validate the object
    }

    public void beforeSave(T object) {
        if (object instanceof ParentEntity) {
            ParentEntity entity = (ParentEntity) object;
            if (entity.getId() == null) {
                entity.setCreatedAt(new Date());
            }
        }
    }

    public void beforeUpdate(T object) {
    }

    public Iterable<T> findAll() {
        return getRepository().findAll();
    }

    public Iterable<T> filterBy(String filterStr, String rangeStr, String sortStr) {
        return this.filterBy(filterStr, rangeStr, sortStr, null, null);
    }

    public Iterable<T> filterBy(String filterStr, String rangeStr, String sortStr, String campoPesquisa) {
        return this.filterBy(filterStr, rangeStr, sortStr, campoPesquisa, null);
    }

    public Iterable<T> filterBy(String filterStr, String rangeStr, String sortStr, List<String> searchFields) {
        return this.filterBy(filterStr, rangeStr, sortStr, null, searchFields);
    }

    public T findById(Integer id) {
        return (T) getRepository().findById(id).orElse(null);
    }

    public T saveUpdate(T object) {
        validate(object);
        T savedObject = null;
        if (object instanceof ParentEntity){
            if (((ParentEntity) object).getId() == null) {
                savedObject = save(object);
            } else {
                beforeUpdate(object);
                savedObject = update(object);
            }
        }
        return savedObject;
    }

    private T save(T object) {
        validate(object);
        beforeSave(object);
        return getRepository().save(object);
    }

    private T update(T object) {
        validate(object);
        beforeUpdate(object);
        return getRepository().save(object);
    }

    public Boolean canDelete(T object) {
        return true;
    }

    public void deleteById(Integer id) {
        T object = findById(id);
        if (canDelete(object)) getRepository().deleteById(id);
        else throw new RuntimeException("Cannot delete object with id " + id);
    }

    public List<Integer> deleteByIds(List<Integer> ids) {
        ids.forEach(this::deleteById);
        return ids;
    }


    private Iterable<T> filterBy(String filterStr, String rangeStr, String sortStr, String campoPesquisa, List<String> searchFields) {
        QueryParamWrapper wrapper = QueryParamExtractor.extract(filterStr, rangeStr, sortStr);
        if (searchFields != null && !searchFields.isEmpty()) {
            return filterService.filterBy(wrapper, getRepository(), searchFields);
        }
        if (campoPesquisa != null && !campoPesquisa.isEmpty()) {
            return filterService.filterBy(wrapper, getRepository(), List.of(campoPesquisa));
        }
        // TODO: Implementar a busca por campo automaticamente
//        T obj = null;
//        List<T> all = (List<T>) getRepository().findAll();
//        if (!all.isEmpty()) {
//            obj = getRepository().findAll().iterator().next();
//        }
//        if (obj != null) {
//            String campo = findFirstStringAttribute(obj);
//            return filterService.filterBy(wrapper, getRepository(), List.of(campo));
//        }
        return filterService.filterBy(wrapper, getRepository());
    }

    /**
     * Este método procura o primeiro atributo do tipo String da classe do objeto passado como parâmetro.
     *
     * @param obj
     * @return
     */
    private String findFirstStringAttribute(Object obj) {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getType() == String.class) {
                return (String) field.getName();
            }
        }
        return null;
    }
}

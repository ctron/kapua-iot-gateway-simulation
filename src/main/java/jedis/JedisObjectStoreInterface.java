package jedis;

import java.util.List;
import java.util.Optional;

/**
 * Created by Arthur Deschamps on 01.06.17.
 * Defines JedisObject factories (stores)
 */
public interface JedisObjectStoreInterface<T extends JedisObject> {

    // Retrieve an object by id
    default Optional<T> retrieve(String id) {
        return getStorage().stream().filter(object -> object.getId().equals(id)).findFirst();
    }

    // Retrieve all objects from class and fill store object list
    default List<T> retrieveAll() {
        List<T> allObjects = JedisManager.getInstance().retrieveAllFromClass(getItemClass());
        if (allObjects != null)
            setStorage(allObjects);
        return allObjects;
    }

    // Return stored items class
    Class<T> getItemClass();

    // Fill the Store "storage"
    void setStorage(List<T> allObjects);


    // Return the Store "storage"
    List<T> getStorage();


    // Add object to storage and db
    default void add(T object) {
        if (object != null) {
            if (JedisManager.getInstance().retrieve(object.getId(), object.getClass()) == null) {
                JedisManager.getInstance().save(object);
                getStorage().add(object);
            }
        }
    }

}
package ch.tranchida.sample.quarkus.customer;

import java.util.List;
import java.util.Optional;

/**
 * Interface décrivant un stockage de Customer.
 */
public interface CustomerStorage {

    List<Customer> listAll();

    Optional<Customer> findById(Long id);

    /**
     * Sauvegarde un customer et lui assigne un id.
     * @param customer customer sans id (ou id ignoré)
     * @return customer avec id assigné
     */
    Customer save(Customer customer);

    /**
     * Met à jour le customer existant identifié par id.
     * @param id id du customer à mettre à jour
     * @param customer données à mettre à jour (id du record sera remplacé)
     * @return customer mis à jour
     * @throws java.util.NoSuchElementException si l'id n'existe pas
     */
    Customer update(Long id, Customer customer);

    /**
     * Supprime le customer identifié par id.
     * @param id id du customer
     * @return true si supprimé, false si absent
     */
    boolean delete(Long id);
}


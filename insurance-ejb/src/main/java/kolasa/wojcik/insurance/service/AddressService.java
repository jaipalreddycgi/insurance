package kolasa.wojcik.insurance.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import kolasa.wojcik.insurance.model.Address;
import kolasa.wojcik.insurance.model.Street;

import com.google.common.base.Strings;

@Stateless
public class AddressService {

	@Inject
	private EntityManager em;

	public Address findAddressById(Long addressId) {
		return em
				.createQuery("Select a from Address a where a.id = :id",
						Address.class).setParameter("id", addressId)
				.getSingleResult();
	}

	public Long getAddressId(String province, String city, String street,
			String buildingNo, String apartmentNo) {

		return null;
	}

	public List<Address> findProvinces(String provincePattern) {
		return null;
	}

	public List<Address> findCities(String cityPattern) {
		return null;
	}

	public List<Address> findStreets(String streetPattern) {
		return null;
	}

	public List<Address> findMatches(String searchString) {
		return em
				.createQuery(
						"select a from Address a where a.clity like :city"
								+ "or a.street like :street", Address.class)
				.setParameter("city", "%" + searchString + "%")
				.setParameter("street", "%" + searchString + "%")
				.getResultList();

	}

	public void addAddress(Address address) {
		em.persist(address);
	}

	public List<Street> suggestStreets(String pattern) {
		List<Street> matchingStreets = em
				.createQuery(
						"select s from Street s where lower(s.description) like :description",
						Street.class)
				.setParameter("description", getSearchPattern(pattern))
				.getResultList();
		
		return matchingStreets;
	}

	private String getSearchPattern(String pattern) {
		if (Strings.isNullOrEmpty(pattern)) {
			return "%";
		} else {
			return "%" + pattern.toLowerCase().replace('*', '%') + "%";
		}
	}

	public Street findStreetByDescription(String description) {
		Street street = em
				.createQuery(
						"select s from Street s where lower(s.description) = :description",
						Street.class).setParameter("description", description)
				.getSingleResult();
		return street;
	}
}
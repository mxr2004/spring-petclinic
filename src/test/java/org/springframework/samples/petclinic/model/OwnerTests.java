/*
 * Copyright 2012-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetType;
import org.springframework.samples.petclinic.owner.Visit;

/**
 * Unit tests for the Owner domain object.
 *
 * @author Ken Krebs
 * @author Michael Isvy
 */
class OwnerTests {

	@Test
	void testGetPetByName() {
		Owner owner = new Owner();
		Pet pet = new Pet();
		pet.setName("Fluffy");

		owner.addPet(pet);

		assertThat(owner.getPet("Fluffy")).isEqualTo(pet);
		assertThat(owner.getPet("fluffy")).isEqualTo(pet); // case insensitive
		assertThat(owner.getPet("Unknown")).isNull();
	}

	@Test
	void testGetPetById() {
		Owner owner = new Owner();
		Pet pet1 = new Pet();
		pet1.setId(1);
		pet1.setName("Max");

		Pet pet2 = new Pet();
		pet2.setId(2);
		pet2.setName("Bella");

		// Add saved pets directly to the list
		owner.getPets().add(pet1);
		owner.getPets().add(pet2);

		assertThat(owner.getPet(1)).isEqualTo(pet1);
		assertThat(owner.getPet(2)).isEqualTo(pet2);
		assertThat(owner.getPet(999)).isNull();
	}

	@Test
	void testAddPet() {
		Owner owner = new Owner();
		Pet pet = new Pet();
		pet.setName("Max");

		assertThat(owner.getPets()).isEmpty();

		owner.addPet(pet);

		assertThat(owner.getPets()).hasSize(1);
		assertThat(owner.getPets()).contains(pet);
	}

	@Test
	void testAddPetWithId() {
		Owner owner = new Owner();
		Pet pet = new Pet();
		pet.setId(1);
		pet.setName("Max");

		owner.addPet(pet); // Should not add pets with existing ID

		assertThat(owner.getPets()).isEmpty();
	}

	@Test
	void testAddVisit() {
		Owner owner = new Owner();
		Pet pet = new Pet();
		pet.setId(1);
		pet.setName("Max");
		owner.getPets().add(pet);

		Visit visit = new Visit();
		visit.setDescription("Routine checkup");

		owner.addVisit(1, visit);

		assertThat(pet.getVisits()).hasSize(1);
		assertThat(pet.getVisits()).contains(visit);
	}

	@Test
	void testAddVisitWithInvalidPetId() {
		Owner owner = new Owner();

		Visit visit = new Visit();
		visit.setDescription("Routine checkup");

		assertThatThrownBy(() -> owner.addVisit(999, visit)).isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("Invalid Pet identifier");
	}

	@Test
	void testAddVisitWithNullPetId() {
		Owner owner = new Owner();

		Visit visit = new Visit();
		visit.setDescription("Routine checkup");

		assertThatThrownBy(() -> owner.addVisit(null, visit)).isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("Pet identifier must not be null");
	}

	@Test
	void testAddVisitWithNullVisit() {
		Owner owner = new Owner();
		Pet pet = new Pet();
		pet.setId(1);
		owner.getPets().add(pet);

		assertThatThrownBy(() -> owner.addVisit(1, null)).isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("Visit must not be null");
	}

	@Test
	void testOwnerToString() {
		Owner owner = new Owner();
		owner.setId(1);
		owner.setFirstName("John");
		owner.setLastName("Doe");
		owner.setAddress("123 Main St");
		owner.setCity("Springfield");
		owner.setTelephone("1234567890");

		String result = owner.toString();

		assertThat(result).contains("id = 1");
		assertThat(result).contains("lastName = 'Doe'");
		assertThat(result).contains("firstName = 'John'");
		assertThat(result).contains("address = '123 Main St'");
		assertThat(result).contains("city = 'Springfield'");
		assertThat(result).contains("telephone = '1234567890'");
	}

}

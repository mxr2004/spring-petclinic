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
package org.springframework.samples.petclinic.owner;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * Integration tests for {@link OwnerRepository}.
 */
@DataJpaTest
class OwnerRepositoryTests {

	@Autowired
	private OwnerRepository ownerRepository;

	@Test
	void testFindByLastName() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Owner> owners = ownerRepository.findByLastNameStartingWith("Davis", pageable);

		assertThat(owners).isNotEmpty();
		assertThat(owners.getTotalElements()).isEqualTo(2);
	}

	@Test
	void testFindByLastNamePartial() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Owner> owners = ownerRepository.findByLastNameStartingWith("Fran", pageable);

		assertThat(owners).isNotEmpty();
		assertThat(owners.getTotalElements()).isEqualTo(1);
	}

	@Test
	void testFindByLastNameNotFound() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Owner> owners = ownerRepository.findByLastNameStartingWith("Unknown", pageable);

		assertThat(owners).isEmpty();
	}

	@Test
	void testFindAll() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Owner> owners = ownerRepository.findByLastNameStartingWith("", pageable);

		assertThat(owners).isNotEmpty();
		assertThat(owners.getTotalElements()).isGreaterThan(0);
	}

	@Test
	void testFindById() {
		Optional<Owner> owner = ownerRepository.findById(1);

		assertThat(owner).isPresent();
		assertThat(owner.get().getFirstName()).isEqualTo("George");
		assertThat(owner.get().getLastName()).isEqualTo("Franklin");
	}

	@Test
	void testFindByIdNotFound() {
		Optional<Owner> owner = ownerRepository.findById(999);

		assertThat(owner).isEmpty();
	}

	@Test
	void testSaveNewOwner() {
		Owner owner = new Owner();
		owner.setFirstName("John");
		owner.setLastName("Doe");
		owner.setAddress("123 Main St");
		owner.setCity("Springfield");
		owner.setTelephone("1234567890");

		Owner savedOwner = ownerRepository.save(owner);

		assertThat(savedOwner.getId()).isNotNull();
		assertThat(savedOwner.getFirstName()).isEqualTo("John");
		assertThat(savedOwner.getLastName()).isEqualTo("Doe");
	}

	@Test
	void testUpdateOwner() {
		Optional<Owner> optionalOwner = ownerRepository.findById(1);
		assertThat(optionalOwner).isPresent();

		Owner owner = optionalOwner.get();
		String oldLastName = owner.getLastName();
		String newLastName = oldLastName + "Updated";

		owner.setLastName(newLastName);
		ownerRepository.save(owner);

		Owner updatedOwner = ownerRepository.findById(1).orElseThrow();
		assertThat(updatedOwner.getLastName()).isEqualTo(newLastName);
	}

	@Test
	void testPagination() {
		Pageable firstPage = PageRequest.of(0, 2);
		Page<Owner> page1 = ownerRepository.findByLastNameStartingWith("", firstPage);

		assertThat(page1.getContent()).hasSize(2);
		assertThat(page1.getTotalPages()).isGreaterThan(1);
		assertThat(page1.hasNext()).isTrue();

		Pageable secondPage = PageRequest.of(1, 2);
		Page<Owner> page2 = ownerRepository.findByLastNameStartingWith("", secondPage);

		assertThat(page2.getContent()).isNotEmpty();
		assertThat(page2.getNumber()).isEqualTo(1);
	}

}

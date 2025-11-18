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
package org.springframework.samples.petclinic.vet;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

/**
 * Integration tests for {@link VetRepository}.
 */
@DataJpaTest
class VetRepositoryTests {

	@Autowired
	private VetRepository vetRepository;

	@Test
	void testFindAll() {
		Collection<Vet> vets = vetRepository.findAll();

		assertThat(vets).isNotEmpty();
		assertThat(vets).hasSizeGreaterThanOrEqualTo(6);
	}

	@Test
	void testVetHasSpecialties() {
		Collection<Vet> vets = vetRepository.findAll();

		// Find a vet with specialties (e.g., Helen Leary)
		Vet vetWithSpecialty = vets.stream()
			.filter(vet -> vet.getFirstName().equals("Helen") && vet.getLastName().equals("Leary"))
			.findFirst()
			.orElseThrow();

		assertThat(vetWithSpecialty.getSpecialties()).isNotEmpty();
		assertThat(vetWithSpecialty.getNrOfSpecialties()).isGreaterThan(0);
	}

	@Test
	void testVetWithoutSpecialties() {
		Collection<Vet> vets = vetRepository.findAll();

		// Find a vet without specialties (e.g., James Carter)
		Vet vetWithoutSpecialty = vets.stream()
			.filter(vet -> vet.getFirstName().equals("James") && vet.getLastName().equals("Carter"))
			.findFirst()
			.orElseThrow();

		assertThat(vetWithoutSpecialty.getSpecialties()).isEmpty();
		assertThat(vetWithoutSpecialty.getNrOfSpecialties()).isZero();
	}

}

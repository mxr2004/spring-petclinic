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

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetType;
import org.springframework.samples.petclinic.owner.Visit;

/**
 * Unit tests for the Pet domain object.
 */
class PetTests {

	@Test
	void testAddVisit() {
		Pet pet = new Pet();

		Visit visit = new Visit();
		visit.setDescription("Routine checkup");
		visit.setDate(LocalDate.of(2024, 1, 15));

		pet.addVisit(visit);

		assertThat(pet.getVisits()).hasSize(1);
		assertThat(pet.getVisits()).contains(visit);
	}

	@Test
	void testMultipleVisits() {
		Pet pet = new Pet();

		Visit visit1 = new Visit();
		visit1.setDescription("First visit");
		visit1.setDate(LocalDate.of(2024, 1, 15));

		Visit visit2 = new Visit();
		visit2.setDescription("Second visit");
		visit2.setDate(LocalDate.of(2024, 2, 20));

		pet.addVisit(visit1);
		pet.addVisit(visit2);

		assertThat(pet.getVisits()).hasSize(2);
		assertThat(pet.getVisits()).containsExactly(visit1, visit2);
	}

	@Test
	void testPetProperties() {
		Pet pet = new Pet();
		pet.setName("Max");
		pet.setBirthDate(LocalDate.of(2020, 5, 1));

		PetType type = new PetType();
		type.setName("dog");
		pet.setType(type);

		assertThat(pet.getName()).isEqualTo("Max");
		assertThat(pet.getBirthDate()).isEqualTo(LocalDate.of(2020, 5, 1));
		assertThat(pet.getType()).isEqualTo(type);
		assertThat(pet.getType().getName()).isEqualTo("dog");
	}

}

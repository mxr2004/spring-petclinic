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

import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.vet.Specialty;
import org.springframework.samples.petclinic.vet.Vet;

/**
 * Unit tests for the Vet domain object.
 */
class VetTests {

	@Test
	void testAddSpecialty() {
		Vet vet = new Vet();

		Specialty specialty = new Specialty();
		specialty.setName("radiology");

		vet.addSpecialty(specialty);

		assertThat(vet.getSpecialties()).hasSize(1);
		assertThat(vet.getSpecialties()).contains(specialty);
		assertThat(vet.getNrOfSpecialties()).isEqualTo(1);
	}

	@Test
	void testMultipleSpecialties() {
		Vet vet = new Vet();

		Specialty specialty1 = new Specialty();
		specialty1.setName("surgery");

		Specialty specialty2 = new Specialty();
		specialty2.setName("dentistry");

		vet.addSpecialty(specialty1);
		vet.addSpecialty(specialty2);

		assertThat(vet.getSpecialties()).hasSize(2);
		assertThat(vet.getNrOfSpecialties()).isEqualTo(2);
	}

	@Test
	void testSpecialtiesAreSorted() {
		Vet vet = new Vet();

		Specialty surgery = new Specialty();
		surgery.setName("surgery");

		Specialty dentistry = new Specialty();
		dentistry.setName("dentistry");

		Specialty radiology = new Specialty();
		radiology.setName("radiology");

		// Add in non-alphabetical order
		vet.addSpecialty(surgery);
		vet.addSpecialty(dentistry);
		vet.addSpecialty(radiology);

		// Verify they come back sorted alphabetically
		assertThat(vet.getSpecialties()).hasSize(3);
		assertThat(vet.getSpecialties().get(0).getName()).isEqualTo("dentistry");
		assertThat(vet.getSpecialties().get(1).getName()).isEqualTo("radiology");
		assertThat(vet.getSpecialties().get(2).getName()).isEqualTo("surgery");
	}

	@Test
	void testVetWithNoSpecialties() {
		Vet vet = new Vet();

		assertThat(vet.getSpecialties()).isEmpty();
		assertThat(vet.getNrOfSpecialties()).isZero();
	}

}

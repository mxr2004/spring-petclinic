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

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

/**
 * Integration tests for {@link PetTypeRepository}.
 */
@DataJpaTest
class PetTypeRepositoryTests {

	@Autowired
	private PetTypeRepository petTypeRepository;

	@Test
	void testFindPetTypes() {
		List<PetType> petTypes = petTypeRepository.findPetTypes();

		assertThat(petTypes).isNotEmpty();
		assertThat(petTypes).hasSizeGreaterThanOrEqualTo(6);
	}

	@Test
	void testFindPetTypesAreSorted() {
		List<PetType> petTypes = petTypeRepository.findPetTypes();

		// Verify they are sorted alphabetically
		assertThat(petTypes).isNotEmpty();
		for (int i = 0; i < petTypes.size() - 1; i++) {
			String currentName = petTypes.get(i).getName();
			String nextName = petTypes.get(i + 1).getName();
			assertThat(currentName).isLessThanOrEqualTo(nextName);
		}
	}

	@Test
	void testPetTypeHasNameAndId() {
		List<PetType> petTypes = petTypeRepository.findPetTypes();

		assertThat(petTypes).isNotEmpty();
		PetType firstType = petTypes.get(0);
		assertThat(firstType.getId()).isNotNull();
		assertThat(firstType.getName()).isNotBlank();
	}

}

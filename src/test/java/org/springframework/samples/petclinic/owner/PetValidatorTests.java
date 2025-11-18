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

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

/**
 * Test class for {@link PetValidator}.
 */
class PetValidatorTests {

	private PetValidator validator;

	@BeforeEach
	void setUp() {
		this.validator = new PetValidator();
	}

	@Test
	void testSupports() {
		assertThat(validator.supports(Pet.class)).isTrue();
		assertThat(validator.supports(Object.class)).isFalse();
	}

	@Test
	void testValidPet() {
		Pet pet = new Pet();
		pet.setName("Max");
		pet.setBirthDate(LocalDate.of(2020, 1, 1));

		PetType type = new PetType();
		type.setName("dog");
		pet.setType(type);

		Errors errors = new BeanPropertyBindingResult(pet, "pet");
		validator.validate(pet, errors);

		assertThat(errors.hasErrors()).isFalse();
	}

	@Test
	void testPetWithEmptyName() {
		Pet pet = new Pet();
		pet.setName("");
		pet.setBirthDate(LocalDate.of(2020, 1, 1));

		PetType type = new PetType();
		type.setName("dog");
		pet.setType(type);

		Errors errors = new BeanPropertyBindingResult(pet, "pet");
		validator.validate(pet, errors);

		assertThat(errors.hasErrors()).isTrue();
		assertThat(errors.getFieldError("name")).isNotNull();
		assertThat(errors.getFieldError("name").getCode()).isEqualTo("required");
	}

	@Test
	void testPetWithNullName() {
		Pet pet = new Pet();
		pet.setBirthDate(LocalDate.of(2020, 1, 1));

		PetType type = new PetType();
		type.setName("dog");
		pet.setType(type);

		Errors errors = new BeanPropertyBindingResult(pet, "pet");
		validator.validate(pet, errors);

		assertThat(errors.hasErrors()).isTrue();
		assertThat(errors.getFieldError("name")).isNotNull();
	}

	@Test
	void testNewPetWithNullType() {
		Pet pet = new Pet();
		pet.setName("Max");
		pet.setBirthDate(LocalDate.of(2020, 1, 1));

		Errors errors = new BeanPropertyBindingResult(pet, "pet");
		validator.validate(pet, errors);

		assertThat(errors.hasErrors()).isTrue();
		assertThat(errors.getFieldError("type")).isNotNull();
		assertThat(errors.getFieldError("type").getCode()).isEqualTo("required");
	}

	@Test
	void testExistingPetWithNullType() {
		Pet pet = new Pet();
		pet.setId(1); // Existing pet
		pet.setName("Max");
		pet.setBirthDate(LocalDate.of(2020, 1, 1));

		Errors errors = new BeanPropertyBindingResult(pet, "pet");
		validator.validate(pet, errors);

		// Type is not required for existing pets
		assertThat(errors.getFieldError("type")).isNull();
	}

	@Test
	void testPetWithNullBirthDate() {
		Pet pet = new Pet();
		pet.setName("Max");

		PetType type = new PetType();
		type.setName("dog");
		pet.setType(type);

		Errors errors = new BeanPropertyBindingResult(pet, "pet");
		validator.validate(pet, errors);

		assertThat(errors.hasErrors()).isTrue();
		assertThat(errors.getFieldError("birthDate")).isNotNull();
		assertThat(errors.getFieldError("birthDate").getCode()).isEqualTo("required");
	}

	@Test
	void testPetWithMultipleErrors() {
		Pet pet = new Pet();
		// All required fields are null/empty

		Errors errors = new BeanPropertyBindingResult(pet, "pet");
		validator.validate(pet, errors);

		assertThat(errors.hasErrors()).isTrue();
		assertThat(errors.getErrorCount()).isEqualTo(3);
		assertThat(errors.getFieldError("name")).isNotNull();
		assertThat(errors.getFieldError("type")).isNotNull();
		assertThat(errors.getFieldError("birthDate")).isNotNull();
	}

}

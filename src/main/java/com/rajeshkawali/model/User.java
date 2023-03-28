package com.rajeshkawali.model;

import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rajesh_Kawali
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table("user")
public class User {

	@PrimaryKey
	private UUID id;

	@NotBlank
	@Column("firstname")
	private String firstName;

	@NotBlank
	@Column("lastname")
	private String lastName;

	private Integer age;

	@NotBlank
	private String gender;

	@NotBlank
	private String role;
}

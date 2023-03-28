package com.rajeshkawali.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.rajeshkawali.constant.UserConstants;
import com.rajeshkawali.model.User;
import com.rajeshkawali.service.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Rajesh_Kawali
 *
 */
@Slf4j
@RequestMapping("/api")
@RestController
public class UserController {

	public static final String CLASS_NAME = UserController.class.getName();

	@Autowired
	private UserService userService;

	@GetMapping("/v1/getAllUsers")
	public List<User> getAllUsers() {
		String _function = ".getAllUsers";
		log.info(CLASS_NAME + _function + "::ENTER");
		List<User> usersList = new ArrayList<>();
		usersList = userService.getAllUsers();
		log.info(CLASS_NAME + _function + "::EXIT");
		return usersList;
	}

	@PostMapping("/v1/addUser")
	public ResponseEntity<?> addUser(@Valid @RequestBody User user) {
		String _function = ".addUser";
		log.info(CLASS_NAME + _function + "::ENTER");
		log.debug(CLASS_NAME + _function + "::Request to add a new User into the DB: {} ", user);
		User addedUser = userService.addUser(user);
		log.info(CLASS_NAME + _function + "::User SuccessFully added to the DB: {}", addedUser);
		return ResponseEntity.status(HttpStatus.CREATED).body(addedUser);

	}

	@GetMapping("/v1/user/{id}")
	public ResponseEntity<?> userById(@PathVariable UUID id) {
		String _function = ".userById";
		log.info(CLASS_NAME + _function + "::ENTER");
		log.debug(CLASS_NAME + _function + "::Requested User Id: {} ", id);
		User addedUser = userService.userById(id);
		if (addedUser != null) {
			log.info(CLASS_NAME + _function + "::EXIT");
			return ResponseEntity.status(HttpStatus.OK).body(addedUser);
		} else {
			log.error(CLASS_NAME + _function + "::User not available for given Id: {} ", id);
			throw idNotFound.apply(id);
		}
	}

	@GetMapping("/v1/userName")
	public ResponseEntity<?> getUserByName(@RequestParam("userName") String name) {
		String _function = ".getUserByName";
		log.info(CLASS_NAME + _function + "::ENTER");
		log.debug(CLASS_NAME + _function + "::Requested User name: {} ", name);
		List<User> usersList = new ArrayList<>();
		usersList = userService.getUserByName(name);
		if (CollectionUtils.isEmpty(usersList)) {
			log.error(CLASS_NAME + _function + "::User not available for given name: {} ", name);
			throw nameNotFound.apply(name);
		} else {
			log.info(CLASS_NAME + _function + "::EXIT");
			return ResponseEntity.status(HttpStatus.OK).body(usersList);
		}
	}

	@PutMapping("/v1/user/{id}")
	public ResponseEntity<?> updateUser(@PathVariable UUID id, @RequestBody User user) {
		String _function = ".updateUser";
		log.info(CLASS_NAME + _function + "::ENTER");
		log.debug(CLASS_NAME + _function + "::Requested User Id: {} ", id);
		log.debug(CLASS_NAME + _function + "::Requested User detail: {} ", user);
		User userToUpdate = userService.userById(id);
		if (userToUpdate != null) {
			User updatedUser = userService.addUser(userToUpdate);
			log.info(CLASS_NAME + _function + "::EXIT");
			return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
		} else {
			log.error(CLASS_NAME + _function + "::User not available for given Id: {} ", id);
			throw idNotFound.apply(id);
		}
	}

	@DeleteMapping("/v1/user/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
		String _function = ".deleteUser";
		log.info(CLASS_NAME + _function + "::ENTER");
		log.debug(CLASS_NAME + _function + "::Request to delete the user using Id: {} ", id);
		User user = userService.userById(id);
		if (user != null) {
			userService.deleteUser(id);
			log.info(CLASS_NAME + _function + "::User successfully deleted from the DB");
			return ResponseEntity.status(HttpStatus.OK).body(UserConstants.DELETE_MESSAGE);
		} else {
			log.error(CLASS_NAME + _function + "::User not available for given Id: {} ", id);
			throw idNotFound.apply(id);
		}
	}

	@GetMapping("/v1/user/error")
	public ResponseEntity<?> errorEndpoint() {
		String _function = ".errorEndpoint";
		log.info(CLASS_NAME + _function + "::ENTER");
		throw serverError.get();
	}

	Function<UUID, ResponseStatusException> idNotFound = (id) -> {
		return new ResponseStatusException(HttpStatus.NOT_FOUND, "No User Available with the given Id: " + id);
	};

	Function<String, ResponseStatusException> nameNotFound = (name) -> {
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No User Available for given name: " + name);
	};

	Supplier<ResponseStatusException> serverError = () -> {
		throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "RunTimeException from User Service");
	};

}

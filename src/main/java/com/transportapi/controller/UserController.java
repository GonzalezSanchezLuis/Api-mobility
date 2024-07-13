package com.transportapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.transportapi.exceptions.InvalidUserException;
import com.transportapi.model.entity.User;
import com.transportapi.services.UserInterface;
import com.transportapi.documentation.ApiDocumentationUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/")
@Tag(name = "User", description = "Controlador de usuario")
public class UserController {

   @Autowired
   private UserInterface userServices;

   @PostMapping("signup")
   @Operation(summary = "Registrar un usuario",description = "Registrar un usuario")
    @ApiDocumentationUser.UserApiResponses

   public ResponseEntity<?> createUser(@RequestBody User user) {
       try {
           User savedUser = userServices.saveUser(user);
           return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
       } catch (InvalidUserException e) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
       }
   }

   @Operation( summary = "Obtiene un usuario por su ID", description = "Busca un usuario por su ID, en la base de datos")
   @ApiDocumentationUser.UserApiResponses
   @GetMapping("user/{id}")
   public ResponseEntity<?> getUser(@PathVariable Long id) {
    if (id == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Debe proporcionar un ID de usuario");
    }

    try {
        User user = userServices.findUserById(id);
        return ResponseEntity.ok(user);
    } catch (EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ningún usuario con este ID");
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Se produjo un error al procesar la solicitud");
    }
}

    
    @Operation(summary = "Obtiene un usuario por su ID y actualiza si información", description = "Actualiza datos del usuario por su ID")
    @PutMapping("update/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user){
        try {
            User updatedUser = userServices.updateUser(id, user); 
            return ResponseEntity.ok(updatedUser);
        }catch(EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        
    }

   
   @Operation(summary = "Obtiene un usuario por su ID y elimina toda su información", description = "Elimina datos del usuario por su ID")
   @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        try {
            userServices.userDelete(id);
            return ResponseEntity.ok("Usuario eliminado");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("se produjo un error al procesar la solicitud");
        }
        
    }
    
}

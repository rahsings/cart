package com.shopping.cart.controller.ext;

import com.shopping.cart.model.dto.ProductDTO;
import com.shopping.cart.model.dto.UserDTO;
import com.shopping.cart.service.ext.ProductService;
import com.shopping.cart.service.ext.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/ext/v1")
@RequiredArgsConstructor
public class ExtController {

    private final UserService userService;
    private final ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> listProducts() {
        List<ProductDTO> allProducts = productService.getAll();
        return ResponseEntity.ok(allProducts);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> listUsers() {
        List<UserDTO> userDTO = userService.getAll();
        return ResponseEntity.ok(userDTO);
    }
}

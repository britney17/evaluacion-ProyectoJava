package com.example.demo.proveedor;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
@RequiredArgsConstructor
public class ProveedorController {

    private final ProveedorRepository repo;

    // ===================== READ: listar todos =====================
    @GetMapping("/listar")
    public List<Proveedor> listar() {
        return repo.findAll();
    }

    // ===================== READ: listar solo activos =====================
    @GetMapping("/activos")
    public List<Proveedor> listarActivos() {
        return repo.findByActivoTrue();
    }

    // ===================== READ: obtener uno =====================
    @GetMapping("/obtener/{id}")
    public ResponseEntity<Proveedor> obtener(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ===================== CREATE: insertar =====================
    @PostMapping("/crear")
    public ResponseEntity<?> crear(@Valid @RequestBody Proveedor proveedor) {
        // // En este caso, validamos que no exista otro proveedor con el mismo nombre (opcional)
        // // Si no tienes ese método en el repo, puedes quitar este "if"

        //Mejora: realizar búsqueda de nombre no se repita y realizar comparación
        
        // proveedor.setId(null);
        // if (proveedor.getActivo() == null) {
        //     proveedor.setActivo(true);
        // }
        
        Proveedor guardado = repo.save(proveedor);
        
        return ResponseEntity
                .created(URI.create("/api/proveedores/obtener/" + guardado.getId()))
                .body(guardado);
    }

    // ===================== UPDATE: actualizar =====================
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Proveedor> actualizar(@PathVariable Long id, 
                                                @Valid @RequestBody Proveedor datos) {
        return repo.findById(id)
                .map(p -> {
                    p.setNombre(datos.getNombre());
                    p.setTelefono(datos.getTelefono());
                    p.setDireccion(datos.getDireccion());
                    if (datos.getActivo() != null) {
                        p.setActivo(datos.getActivo());
                    }
                    return ResponseEntity.ok(repo.save(p));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ===================== DELETE: eliminar =====================
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
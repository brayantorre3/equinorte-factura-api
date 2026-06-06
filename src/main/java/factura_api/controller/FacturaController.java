package factura_api.controller;

import factura_api.model.Factura;
import factura_api.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/facturas")
@CrossOrigin(origins = "*")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    // GET /api/facturas → trae todas las facturas
    @GetMapping
    public ResponseEntity<List<Factura>> obtenerTodas() {
        return ResponseEntity.ok(facturaService.obtenerTodas());
    }

    // GET /api/facturas/1 → trae una factura por ID
    @GetMapping("/{id}")
    public ResponseEntity<Factura> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(facturaService.obtenerPorId(id));
    }

    // PUT /api/facturas/1/recalcular → recalcula la factura
    @PutMapping("/{id}/recalcular")
    public ResponseEntity<?> recalcular(
            @PathVariable Long id,
            @RequestBody Map<String, Object> body) {
        try {
            Double nuevoSubtotal = Double.valueOf(body.get("nuevoSubtotal").toString());
            String tipoUsuario = body.get("tipoUsuario").toString();
            Factura resultado = facturaService.recalcular(id, nuevoSubtotal, tipoUsuario);
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
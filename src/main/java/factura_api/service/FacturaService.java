package factura_api.service;

import factura_api.model.DetalleFactura;
import factura_api.model.Factura;
import factura_api.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    
    public List<Factura> obtenerTodas() {
        return facturaRepository.findAll();
    }

    public Factura obtenerPorId(Long id) {
        return facturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada"));
    }

    
    public Factura recalcular(Long id, Double nuevoSubtotal, String tipoUsuario) {
        Factura factura = obtenerPorId(id);
        double subtotalActual = factura.getSubtotal();

    
        double diferencia = nuevoSubtotal - subtotalActual;
        if (tipoUsuario.equals("A") && diferencia > 20000) {
            throw new RuntimeException("Usuario Tipo A no puede incrementar más de $20.000");
        }
        if (tipoUsuario.equals("B") && diferencia > 50000) {
            throw new RuntimeException("Usuario Tipo B no puede incrementar más de $50.000");
        }
        
        double factor = nuevoSubtotal / subtotalActual;

      
        for (DetalleFactura detalle : factura.getDetalles()) {
            double nuevoValor = detalle.getSubtotalProducto() * factor;
            detalle.setSubtotalProducto(nuevoValor);
            detalle.setValorUnitario(nuevoValor / detalle.getCantidad());
        }

        factura.setSubtotal(nuevoSubtotal);
        factura.setIva(nuevoSubtotal * 0.19);
        factura.setTotal(nuevoSubtotal + factura.getIva());

        return facturaRepository.save(factura);
    }
}
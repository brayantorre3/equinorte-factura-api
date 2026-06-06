INSERT INTO facturas (numero_factura, cliente, subtotal, iva, total) VALUES
('FAC-001', 'Empresa ABC S.A.S', 80000, 15200, 95200);

INSERT INTO detalle_facturas (nombre_producto, valor_unitario, cantidad, subtotal_producto, factura_id) VALUES
('Laptop Dell', 40000, 1, 40000, 1),
('Mouse Logitech', 20000, 1, 20000, 1),
('Teclado Mecanico', 20000, 1, 20000, 1);
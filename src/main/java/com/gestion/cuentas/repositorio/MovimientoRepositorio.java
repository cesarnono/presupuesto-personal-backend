package com.gestion.cuentas.repositorio;

import com.gestion.cuentas.modelo.Movimiento;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovimientoRepositorio  extends MongoRepository<Movimiento, String> {
    Long countByIdcuenta(String idcuenta);
}

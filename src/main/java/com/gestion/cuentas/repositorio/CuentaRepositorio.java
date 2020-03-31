package com.gestion.cuentas.repositorio;

import com.gestion.cuentas.modelo.Cuenta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CuentaRepositorio extends MongoRepository<Cuenta,String> {

    List<Cuenta> findByIdpresupuesto(String idbalance);

}



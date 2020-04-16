package com.gestion.cuentas.repositorio;

import com.gestion.cuentas.modelo.PresupuestoPersonal;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PresupuestoPersonalRepositorio extends MongoRepository<PresupuestoPersonal,String> {
    List<PresupuestoPersonal> findByIdusuarioOrderByFechacreacionDesc(String idusuario);
}

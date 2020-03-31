package com.gestion.cuentas.servicio;

import com.gestion.cuentas.dto.CuentaDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface CuentaServicio {

    CuentaDto guardarCuenta(CuentaDto cuentaDto);

    CuentaDto actualizarCuenta(CuentaDto cuentaDto);

    boolean eliminarCuenta(CuentaDto cuentaDto);

}

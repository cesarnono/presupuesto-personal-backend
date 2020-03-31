package com.gestion.cuentas.controlador;

import com.gestion.cuentas.dto.CuentaDto;
import com.gestion.cuentas.servicio.CuentaServicio;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class CuentaControlador {

    private CuentaServicio cuentaServicio;

    public CuentaControlador(CuentaServicio cuentaServicio) {
        this.cuentaServicio = cuentaServicio;
    }

    @PostMapping
    public CuentaDto guardar(@RequestBody CuentaDto cuentaDto){
        return this.cuentaServicio.guardarCuenta(cuentaDto);
    }

    @PatchMapping
    public CuentaDto actualizar(@RequestBody CuentaDto cuentaDto){
        return this.cuentaServicio.actualizarCuenta(cuentaDto);
    }

    @DeleteMapping
    public boolean eliminar(@RequestBody CuentaDto cuentaDto){
        return this.cuentaServicio.eliminarCuenta(cuentaDto);
    }
}

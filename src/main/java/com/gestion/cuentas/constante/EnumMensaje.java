package com.gestion.cuentas.constante;

public enum EnumMensaje {
    INFORMACION_INVALIDA_GUARDAR_PRESUPUESTO_PERSONAL("Informacion invalida para guardar presupuesto personal."),
    INFORMACION_INVALIDA_GUARDAR_CUENTA("Informacion invalida para guardar cuenta."),
    INFORMACION_INVALIDA_ACTUALIZAR_CUENTA("Informacion invalida para actualizar cuenta."),
    INFORMACION_INVALIDA_OBTENER_PRESUPUESTOS("Informacion invalida para obtener presupuestos."),
    INFORMACION_INVALIDA_CONSULTAR_PRESUPUESTO("Informacion invalida para consultar presupuesto."),
    INFORMACION_INVALIDA_CREAR_TIPOCUENTA("Informacion invalida para crear tipo cuenta."),
    ERROR_GUARDAR_TIPOCUENTA_YA_EXISTE("Error al guardar debido a que el tipo de cuenta ya existe."),
    ERROR_CONSULTAR_IDCUENTA_NO_EXISTE("Error al consultar idcuenta no existe."),
    ERROR_ELIMINAR_IDCUENTA_INVALIDO("Error al eliminar debido a que el idcuenta es invalido."),
    ERROR_ELIMINAR_CUENTA_NO_EXISTE("Error al eliminar debido a que la cuenta no existe."),
    ERROR_CONSULTAR_CUENTAS_IDPRESUPUESTO_INVALIDO("Error al consultar cuentas, idpresupuesto invalido."),
    ERROR_CREAR_CUENTA_IDBALANCE_NO_EXISTE("Error al crear cuenta, idbalance no existe."),
    ERROR_CONSULTAR_PRESUPUESTO_ID_NO_EXISTE("Error al consultar presupuesto, idpresupuesto no existe."),
    ERROR_ACTUALIZAR_IDPRESUPUESTO_NO_EXISTE("Error al actualizar presupuesto, idpresupuesto no existe."),
    SIN_CUENTAS_ASOCIADAS_PARA_ACTUALIZAR_TOTALES("No existen cuenta asociadas para actualizar los totales del estado financiero."),
    NOMBRE_DE_USUARIO_O_PASSWORD_INCORRECTO ("Nombre de usuario o password incorrecto");
    private String mensaje;


    EnumMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return this.mensaje;
    }
}
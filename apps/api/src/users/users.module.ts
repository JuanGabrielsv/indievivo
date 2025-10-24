import { Module } from "@nestjs/common";
import { UsersService } from "./users.service";
import { UsersController } from "./users.controller";

@Module({
    controllers: [UsersController], //Registra el controlador que maneja las peticiones HTTP
    providers: [UsersService], // Registra el servicio que contiene la lógica de negocio
    exports: [UsersService], // Exporta el servicio para que otros módulos puedan usarlo (ej:AuthModule)
})
export class UsersModule { }
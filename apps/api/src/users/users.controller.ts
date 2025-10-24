import { Body, Controller, Delete, Get, Param, Post, Patch } from "@nestjs/common";
import { UsersService } from "./users.service";
import { CreateUserDto } from "./dto/create-user.dto";
import { UpdateUserDto } from "./dto/update-user.dto";

@Controller('users') // Define la ruta base: /users
export class UsersController {
    // Inyecta el servicio de usuarios
    constructor(private readonly usersService: UsersService) { }

    /**
     * POST /users
     * Crea un nuevo usuario
     */
    @Post()
    create(@Body() createUserDto: CreateUserDto) {
        return this.usersService.create(createUserDto);
    }

    /**
     * GET /users
     * Obtiene todos los usuarios
     */
    @Get()
    findAll() {
        return this.usersService.findAll();
    }

    /**
     * GET /users/:id
     * Obtiene un usuario específico por su ID
     * Ejemplo: GET /users/123e4567-e89b-12d3-a456-426614174000
     */
    @Get(':id')
    findOne(@Param('id') id: string) {
        return this.usersService.findOne(id);
    }

    /**
     * PATCH /users/:id
     * Actualiza un usuario existente
     * Ejemplo: PATCH /users/123e4567-e89b-12d3-a456-426614174000
     */
    @Patch(':id')
    update(@Param('id') id: string, @Body() updateUserDto: UpdateUserDto) {
        return this.usersService.update(id, updateUserDto);
    }

    /**
     * DELETE /users/:id
     * Elimina un usuario
     * Ejemplo: DELETE /users/123e4567-e89b-12d3-a456-426614174000
     */
    @Delete(':id')
    remove(@Param('id') id: string) {
        return this.usersService.remove(id);
    }
}
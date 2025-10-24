import { Injectable } from "@nestjs/common";
import { PrismaService } from "../prisma/prisma.service";
import { CreateUserDto } from "./dto/create-user.dto";
import { UpdateUserDto } from "./dto/update-user.dto";

@Injectable() // Marca esta clase como un servicio inyectable
export class UsersService {
    // Inyecta PrismaService para interactuar con la base de datos
    constructor(private prisma: PrismaService) { }

    /**
     * Crea un nuevo usuario en la base de datos
     * @param createUserDto - Datos del usuario a crear
     * @returns El usuario creado
     */
    async create(createUserDto: CreateUserDto) {
        return this.prisma.user.create({
            data: createUserDto,
        });
    }

    /**
     * Obtiene todos los usuarios con sus roles
     * @returns Array de usuarios con sus roles incluidos
     */
    async findAll() {
        return this.prisma.user.findMany({
            include: {
                roles: {
                    include: {
                        role: true, // Incluye la información completa del rol
                    },
                },
            },
        });
    }

    /**
     * Busca un usuario específico por su ID
     * @param id - UUID del usuario
     * @returns El usuario encontrado o null
     */
    async findOne(id: string) {
        return this.prisma.user.findUnique({
            where: { id },
            include: {
                roles: {
                    include: {
                        role: true,
                    },
                },
            },
        });
    }

    /**
     * Busca un usuario por su email
     * @param email - Email del usuario
     * @return El usuario encontrado o null
     */
    async findByEmail(email: string) {
        return this.prisma.user.findUnique({
            where: { email },
            include: {
                roles: {
                    include: {
                        role: true,
                    },
                },
            },
        });
    }

    /**
     * Actualiza los datos de un usuario
     * @param id - UUID del usuario a actualizar
     * @param updateUserDto - Datos a actualizar
     * @returns El usuario actualizado
     */
    async update(id: string, updateUserDto: UpdateUserDto) {
        return this.prisma.user.update({
            where: { id },
            data: updateUserDto,
        });
    }

    /**
     * Elimina un usuario de la base de datos
     * @param id - UUID del usuario a eliminar
     * @returns El usuario eliminado
     */
    async remove(id: string) {
        return this.prisma.user.delete({
            where: { id },
        });
    }
}
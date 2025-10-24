import { PartialType } from '@nestjs/mapped-types';
import { CreateUserDto } from "./create-user.dto";

/**
 * DTO para actualizar un usuario
 * Hereda todos los campos de CreateUserDto pero los hace opcionales
 * Así puedes actualizar sólo los campos que necesites
 */
export class UpdateUserDto extends PartialType(CreateUserDto) { }
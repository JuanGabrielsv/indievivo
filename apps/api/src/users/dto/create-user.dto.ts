import { IsEmail, IsNotEmpty, IsString, Matches, MinLength } from 'class-validator';
/**
 * DTO (Data Transfer Object) para crear un usuario
 * Define y valida los datos que se deben enviar para crear un usuario
 */
export class CreateUserDto {
    @IsEmail({}, { message: 'El email debe ser válido' }) // Valida que sea un email válido
    @IsNotEmpty({ message: 'El email es obligatorio' }) // No puede estar vacío
    email: string;

    @IsString({ message: 'La contraseña debe ser un texto' })
    @MinLength(10, { message: 'La contraseña debe tener al menos 10 caracteres' }) // Mínimo 10 caracteres
    @Matches(
        /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]/,
        {
            message: 'La contraseña debe contener al menos: una mayúscula, una minúscula, un número y un carácter especial (@$!%*?&)'
        }
    )
    @IsNotEmpty({ message: 'La contraseña es obligatoria' })
    password: string;

    @IsString({ message: 'El nombre debe ser un texto' })
    @IsNotEmpty({ message: 'El nombre es obligatorio' })
    name: string;

    @IsString({ message: 'El primer apellido debe ser un texto' })
    @IsNotEmpty({ message: 'El primer apellido es obligatorio' })
    firstSurname: string;

    @IsString({ message: 'El segundo apellido debe ser un texto' })
    @IsNotEmpty({ message: 'El segundo apellido es obligatorio' })
    secondSurname: string;
}
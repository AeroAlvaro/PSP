package com.agenda.agenda.repositorios;

import com.agenda.agenda.entidades.Rol;
import com.agenda.agenda.entidades.Usuario;
import com.agenda.agenda.seguridad.PasswordEncryptor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioRepository {

    public List<Usuario> getUsuarios() {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        usuarios.add(new Usuario("aitor", PasswordEncryptor.encrypt("1234"), Rol.ADMIN));
        usuarios.add(new Usuario("alicia", PasswordEncryptor.encrypt("1111"), Rol.USER));
        usuarios.add(new Usuario("invitado1", PasswordEncryptor.encrypt("0000"), Rol.INVITADO));

        return usuarios;
    }
}
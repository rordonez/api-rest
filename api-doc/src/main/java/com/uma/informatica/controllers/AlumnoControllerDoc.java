package com.uma.informatica.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uma.informatica.controllers.beans.DireccionRequestBody;
import com.uma.informatica.controllers.beans.SearchAlumnoRequestBody;
import com.uma.informatica.persistence.models.Alumno;
import com.uma.informatica.persistence.models.Pfc;
import com.uma.informatica.persistence.models.enums.TitulacionEnum;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * Created by rafaordonez on 02/03/14.
 */
@Controller
@RequestMapping(value = "/alumnos", produces = MediaType.APPLICATION_JSON_VALUE)
public class AlumnoControllerDoc implements AlumnoController {

    private List<Alumno> alumnos = new ArrayList<>();
    
    private final ResourceAssembler<Alumno, Resource<Alumno>> alumnoResourceAssembler;
    private final ResourceAssembler<Pfc, Resource<Pfc>> pfcResourceAssembler;
    
    
    @Inject
    public AlumnoControllerDoc(ResourceAssembler<Alumno, Resource<Alumno>> alumnoResourceAssembler, ResourceAssembler<Pfc, Resource<Pfc>> pfcResourceAssembler) {
        Alumno alumno = new Alumno("012345678A", "Pedro", "Moratilla Barrios", TitulacionEnum.SUPERIOR, "calle Pez", "Madrid", "España", "28080", "657465746", "ex@dm.com", new Date());
        alumno.setId(1L);
        alumno.setPfc(new Pfc("Pfc Prueba 1" , "Departamento Prueba 1"));
        alumnos.add(alumno);
        alumno = new Alumno("367473822S", "Luis", "Santos Barrios", TitulacionEnum.GESTION, "calle Ruis, 23", "Madrid", "España", "28080", "657342787", "ex2@dm.com", new Date());
        alumno.setId(2L);
        alumno.setPfc(new Pfc("Pfc Prueba 2" , "Departamento Prueba 2"));
        alumnos.add(alumno);
        alumno = new Alumno("367583094A", "Rafa", "Cruz Rios", TitulacionEnum.SISTEMAS, "calle Principal, 1", "Málaga", "España", "19080", "673265743", "ex3@dm.com", new Date());
        alumno.setId(3L);
        alumno.setPfc(new Pfc("Pfc Prueba 3" , "Departamento Prueba 3"));
        alumnos.add(alumno);
        
        this.alumnoResourceAssembler = alumnoResourceAssembler;
        this.pfcResourceAssembler = pfcResourceAssembler;
    }

    @Override
    @ApiOperation(value = "Devuelve la lista de alumnos")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Resources<Resource<Alumno>>> getAlumnos() {
    	return new ResponseEntity<> (Resources.wrap(alumnos), HttpStatus.OK);
    }


    @Override
    @ApiOperation(value = "Devuelve un alumno dado su identificador")
    @RequestMapping(value = "/{alumnoId}", method = RequestMethod.GET)
    public ResponseEntity<Resource<Alumno>> getAlumno(@PathVariable long alumnoId) {
        return new ResponseEntity<>(alumnoResourceAssembler.toResource(findAlumnoById(alumnoId)), HttpStatus.OK);
    }


    @Override
    @ApiOperation(value = "Crea un alumno con la información del alumno")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Resource<Alumno>> createAlumno(@RequestBody Alumno alumno) {
        alumno.setId(new Long(alumnos.size()));
        alumnos.add(alumno);
        return new ResponseEntity<>(alumnoResourceAssembler.toResource(alumno), HttpStatus.CREATED);
    }

    @Override
    @ApiOperation(value = "Borrar el alumno dado su identificador")
    @RequestMapping (method = RequestMethod.DELETE, value = "/{alumnoId}")
    public ResponseEntity<Resource<Alumno>> removeAlumno(@PathVariable long alumnoId) {
        Alumno alumno = findAlumnoById(alumnoId);
        alumnos.remove(alumno);
        return new ResponseEntity<>(alumnoResourceAssembler.toResource(alumno), HttpStatus.ACCEPTED);
    }

    @Override
    @ApiOperation(value = "Realiza una busqueda sobre alumnos")
    @RequestMapping (method = RequestMethod.POST)
    public ResponseEntity<Resources<Resource<Alumno>>> searchAlumnos(@NotNull @RequestBody SearchAlumnoRequestBody search) {
        List<Alumno> alumnosEncontrados = new ArrayList<>();
        if(search.getDni() != null) {
            alumnosEncontrados.add(findAlumnosByDni(search.getDni()));
        }
        else {
            if (search.getApellidos() != null) {
                alumnosEncontrados.addAll(findAlumnosByApellidos(search.getNombre(), search.getApellidos()));
            }
            else {
                alumnosEncontrados.addAll(findAlumnosByNombre(search.getNombre()));
            }
        }
        return new ResponseEntity<> (Resources.wrap(alumnosEncontrados), HttpStatus.OK);
    }


    @Override
    @ApiOperation(value = "Actualiza la dirección de un determinado alumno dado su identificador")
    @RequestMapping (method = RequestMethod.POST, value = "/{alumnoId}/direccion")
    public ResponseEntity<Resource<Alumno>> updateDireccion(@PathVariable long alumnoId, @NotNull @RequestBody DireccionRequestBody direccion) {
        Alumno alumno = findAlumnoById(alumnoId);
        alumno.setDomicilio(direccion.getDomicilio());
        alumno.setLocalidad(direccion.getLocalidad());
        alumno.setPais(direccion.getPais());
        alumno.setCodigoPostal(direccion.getCodigoPostal());
        return new ResponseEntity<>(alumnoResourceAssembler.toResource(alumno), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Resource<Alumno>> updateEmail(@PathVariable long alumnoId, @NotNull @RequestParam String email) {
        Alumno alumno = findAlumnoById(alumnoId);
        alumno.setEmail(email);
        return new ResponseEntity<>(alumnoResourceAssembler.toResource(alumno), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Resource<Alumno>> updateTelefono(@PathVariable long alumnoId, @RequestParam String telefono) {
        Alumno alumno = findAlumnoById(alumnoId);
        alumno.setTelefono(telefono);
        return new ResponseEntity<>(alumnoResourceAssembler.toResource(alumno), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Resource<Pfc>> getPfc(@PathVariable long alumnoId) {
        Alumno alumno = findAlumnoById(alumnoId);
        return new ResponseEntity<>(pfcResourceAssembler.toResource(alumno.getPfc()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Resource<Pfc>> addPfc(@PathVariable long alumnoId, @RequestBody Pfc pfc) {
        Alumno alumno = findAlumnoById(alumnoId);
        alumno.setPfc(pfc);
        return new ResponseEntity<>(pfcResourceAssembler.toResource(pfc), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Resource<Pfc>> deletePfc(@PathVariable long alumnoId) {
        Alumno alumno = findAlumnoById(alumnoId);
        Pfc pfc = alumno.getPfc();
        alumno.setPfc(null);
        return new ResponseEntity<>(pfcResourceAssembler.toResource(pfc), HttpStatus.ACCEPTED);
    }

    private Alumno findAlumnoById(long alumnoId) {
        Alumno alumnoEncontrado = null;
        for (int i = 0; alumnoEncontrado == null && i < alumnos.size(); i++) {
            if (alumnos.get(i).getId().equals(alumnoId)) {
                alumnoEncontrado = alumnos.get(i);
            }
            i++;
        }
        return alumnoEncontrado;
    }

    private List<Alumno> findAlumnosByNombre(String nombre) {
        List<Alumno> alumnosEncontrados = new ArrayList<>();
        for (Alumno alumno : alumnos) {
            if(alumno.getNombre().equals(nombre)) {
                alumnosEncontrados.add(alumno);
            }
        }
        return alumnosEncontrados;
    }

    private List<Alumno> findAlumnosByApellidos(String nombre, String apellidos) {
        List<Alumno> alumnosEncontrados = new ArrayList<>();
        for (Alumno alumno : alumnos) {
            if (nombre.equals(alumno.getNombre()) && apellidos.equals(alumno.getApellidos())) {
                alumnosEncontrados.add(alumno);
            }
        }
        return alumnosEncontrados;
    }

    private Alumno findAlumnosByDni(String dni) {
        Alumno alumnoEncontrado = null;
        for (Alumno alumno : alumnos) {
            if (alumno.getDni().equals(dni)) {
                alumnoEncontrado = alumno;
            }
        }
        return alumnoEncontrado;
    }
}

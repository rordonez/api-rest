package com.uma.informatica.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.uma.informatica.controllers.beans.SearchAlumnoRequestBody;
import com.uma.informatica.controllers.beans.UpdateAlumnoBody;
import com.uma.informatica.controllers.resources.AlumnoResourceAssembler;
import com.uma.informatica.controllers.resources.PfcResourceAssembler;
import com.uma.informatica.persistence.models.Alumno;
import com.uma.informatica.persistence.models.Pfc;
import com.uma.informatica.persistence.models.enums.TitulacionEnum;
import com.uma.informatica.resources.AlumnoResource;
import com.uma.informatica.resources.PfcResource;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * Created by rafaordonez on 02/03/14.
 */
@RestController
@ExposesResourceFor(Alumno.class)
@RequestMapping(value = "/alumnos", produces = MediaType.APPLICATION_JSON_VALUE)
public class AlumnoControllerDoc implements AlumnoController {

    private List<Alumno> alumnos = new ArrayList<>();
    
    private final AlumnoResourceAssembler alumnoResourceAssembler;
    private final PfcResourceAssembler pfcResourceAssembler;
    
    
    @Inject
    public AlumnoControllerDoc(AlumnoResourceAssembler alumnoResourceAssembler, PfcResourceAssembler pfcResourceAssembler) {
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
    public ResponseEntity<Resources<AlumnoResource>> getAlumnos() {
    	
    	Resources<AlumnoResource> alumnosResources = new Resources<AlumnoResource>(alumnoResourceAssembler.toResources(alumnos));
    	alumnosResources.add(linkTo(methodOn(AlumnoController.class).getAlumnos()).withSelfRel());
    	
        return new ResponseEntity<> (alumnosResources, HttpStatus.OK);        
    }
    
    
    @Override
    @ApiOperation(value = "Realiza una busqueda sobre alumnos")
    @RequestMapping (method = RequestMethod.POST)
    public ResponseEntity<Resources<AlumnoResource>> searchAlumnos(@Valid @RequestBody SearchAlumnoRequestBody search) {
        List<Alumno> alumnosEncontrados = new ArrayList<>();
        if(search.getDni() != null) {
            alumnosEncontrados.add(findAlumnosByDni(search.getDni()));
        }

        if (search.getApellidos() != null) {
            alumnosEncontrados.addAll(findAlumnosByApellidos(search.getNombre(), search.getApellidos()));
        }
        if (search.getNombre() != null) {
            alumnosEncontrados.addAll(findAlumnosByNombre(search.getNombre()));
        }
        Resources<AlumnoResource> alumnosResources = new Resources<AlumnoResource>(alumnoResourceAssembler.toResources(alumnosEncontrados));

        return new ResponseEntity<> (alumnosResources, HttpStatus.OK);
    }


    @Override
    @ApiOperation(value = "Crea un alumno con la información del alumno")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<AlumnoResource> createAlumno(@Valid @RequestBody Alumno alumno) {
        alumno.setId(new Long(alumnos.size()));
        alumnos.add(alumno);
        return new ResponseEntity<>(alumnoResourceAssembler.toResource(alumno), HttpStatus.CREATED);
    }
    
    
    @Override
    @ApiOperation(value = "Devuelve un alumno dado su identificador")
    @RequestMapping(value = "/{alumnoId}", method = RequestMethod.GET)
    public ResponseEntity<AlumnoResource> getAlumno(@PathVariable long alumnoId) {
        return new ResponseEntity<>(alumnoResourceAssembler.toResource(findAlumnoById(alumnoId)), HttpStatus.OK);
    }




    @Override
    @ApiOperation(value = "Borrar el alumno dado su identificador")
    @RequestMapping (method = RequestMethod.DELETE, value = "/{alumnoId}")
    public ResponseEntity<AlumnoResource> removeAlumno(@PathVariable long alumnoId) {
        Alumno alumno = findAlumnoById(alumnoId);
        alumnos.remove(alumno);
        return new ResponseEntity<>(alumnoResourceAssembler.toResource(alumno), HttpStatus.ACCEPTED);
    }




//    @Override
//    @ApiOperation(value = "Actualiza la dirección de un determinado alumno dado su identificador")
//    @RequestMapping (method = RequestMethod.POST, value = "/{alumnoId}/direccion")
//    public ResponseEntity<AlumnoResource> updateDireccion(@PathVariable long alumnoId, @NotNull @RequestBody DireccionRequestBody direccion) {
//        Alumno alumno = findAlumnoById(alumnoId);
//        alumno.setDomicilio(direccion.getDomicilio());
//        alumno.setLocalidad(direccion.getLocalidad());
//        alumno.setPais(direccion.getPais());
//        alumno.setCodigoPostal(direccion.getCodigoPostal());
//        return new ResponseEntity<>(alumnoResourceAssembler.toResource(alumno), HttpStatus.OK);
//    }
//
//    @Override
//    public ResponseEntity<AlumnoResource> updateEmail(@PathVariable long alumnoId, @NotNull @RequestParam String email) {
//        Alumno alumno = findAlumnoById(alumnoId);
//        alumno.setEmail(email);
//        return new ResponseEntity<>(alumnoResourceAssembler.toResource(alumno), HttpStatus.OK);
//    }
//
//    @Override
//    public ResponseEntity<AlumnoResource> updateTelefono(@PathVariable long alumnoId, @RequestParam String telefono) {
//        Alumno alumno = findAlumnoById(alumnoId);
//        alumno.setTelefono(telefono);
//        return new ResponseEntity<>(alumnoResourceAssembler.toResource(alumno), HttpStatus.OK);
//    }

    @Override
    public ResponseEntity<PfcResource> getPfc(@PathVariable long alumnoId) {
        Alumno alumno = findAlumnoById(alumnoId);
        return new ResponseEntity<>(pfcResourceAssembler.toResource(alumno.getPfc()), HttpStatus.OK);
    }

//    @Override
//    public ResponseEntity<PfcResource> addPfc(@PathVariable long alumnoId, @RequestBody Pfc pfc) {
//        Alumno alumno = findAlumnoById(alumnoId);
//        alumno.setPfc(pfc);
//        return new ResponseEntity<>(pfcResourceAssembler.toResource(pfc), HttpStatus.CREATED);
//    }


    @Override
    public ResponseEntity<AlumnoResource> updateAlumno(@PathVariable long alumnoId, @Valid @RequestBody UpdateAlumnoBody alumno) {
        return null;
    }

    @Override
    public ResponseEntity<PfcResource> addPfc(@PathVariable long alumnoId, @PathVariable long pfcId) {
        return null;
    }

    @Override
    public ResponseEntity<PfcResource> deletePfc(@PathVariable long alumnoId) {
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
            if(alumno.getNombre().contains(nombre)) {
                alumnosEncontrados.add(alumno);
            }
        }
        return alumnosEncontrados;
    }

    private List<Alumno> findAlumnosByApellidos(String nombre, String apellidos) {
        List<Alumno> alumnosEncontrados = new ArrayList<>();
        for (Alumno alumno : alumnos) {
            if (nombre.contains(alumno.getNombre()) && apellidos.contains(alumno.getApellidos())) {
                alumnosEncontrados.add(alumno);
            }
        }
        return alumnosEncontrados;
    }

    private Alumno findAlumnosByDni(String dni) {
        Alumno alumnoEncontrado = null;
        for (Alumno alumno : alumnos) {
            if (alumno.getDni().contains(dni)) {
                alumnoEncontrado = alumno;
            }
        }
        return alumnoEncontrado;
    }
}

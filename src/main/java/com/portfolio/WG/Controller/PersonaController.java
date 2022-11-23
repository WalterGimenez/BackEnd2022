
package com.portfolio.WG.Controller;

import com.portfolio.WG.Entity.Persona;
import com.portfolio.WG.Interface.IPersonaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PersonaController {
    @Autowired IPersonaService ipersonaService;
    
    @GetMapping("personas/lista")
    public List<Persona> getPersona(){
        return ipersonaService.getPersona();
    }
    
    @PostMapping("personas/crea")
    public String createPersona(@RequestBody Persona persona){
        ipersonaService.savePersona(persona);
        return "Persona creada exitosamente";
    }
    
    @DeleteMapping("personas/borrar/{id}")
    public String deletePersona(@PathVariable Long id){
        ipersonaService.deletePersona(id);
        return "Pesona borrada exitosamente";
    }
    
    @PutMapping("personas/editar/{id}")
    public Persona editPersona(@PathVariable Long id,
                            @RequestParam("name") String newName,
                            @RequestParam("lastName") String newLast,                          
                            @RequestParam("aboutMe") String newAM,
                            @RequestParam("aboutMe2") String newAM2,
                            @RequestParam("titulo1") String newT1,
                            @RequestParam("titulo2") String newT2){
        Persona personaNew = ipersonaService.findPersona(id);
        personaNew.setName(newName);
        personaNew.setLastName(newLast);
        personaNew.setAboutMe(newAM);
        personaNew.setAboutMe2(newAM2);
        personaNew.setTit1(newT1);
        personaNew.setTit2(newT2);
        ipersonaService.savePersona(personaNew);
        return personaNew;
    }
    
    @GetMapping("personas/informe")
    public Persona findPersona() //(Long id)
    {
        return ipersonaService.findPersona((long)1);   //(id);
    }
}
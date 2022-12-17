
package com.portfolio.WG.Controller;

import com.portfolio.WG.Dto.DtoHardSoft;
import com.portfolio.WG.Entity.Hardsoft;
import com.portfolio.WG.Security.Controller.Message;
import com.portfolio.WG.Service.ServiceHardSoft;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/hys")
public class HardSoftController {
    @Autowired ServiceHardSoft serviceHardSoft;
    
    @GetMapping("/list")
    public ResponseEntity<List<Hardsoft>> list(){
        List<Hardsoft> list = serviceHardSoft.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }
    @GetMapping("/detail/{id}")
    public ResponseEntity<Hardsoft> getById(@PathVariable("id")int id){
        if(!serviceHardSoft.existsById(id)){
            return new ResponseEntity(new Message("No existe el ID"), HttpStatus.BAD_REQUEST);
        }
        
        Hardsoft hardSoft = serviceHardSoft.getOne(id).get();
        return new ResponseEntity(hardSoft, HttpStatus.OK);
    }
    
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody DtoHardSoft dtoHardSoft){
        if(StringUtils.isBlank(dtoHardSoft.getName())){
            return new ResponseEntity(new Message("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        }
        if(serviceHardSoft.existsByName(dtoHardSoft.getName())){
            return new ResponseEntity(new Message("Ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        }
        
        Hardsoft hardSoft = new Hardsoft(
                dtoHardSoft.getName(), dtoHardSoft.getPerc()
            );
        serviceHardSoft.save(hardSoft);
        return new ResponseEntity(new Message("HardSoft creado"), HttpStatus.OK);
                
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody DtoHardSoft dtoHardSoft){
        if(!serviceHardSoft.existsById(id)){
            return new ResponseEntity(new Message("No existe el ID"), HttpStatus.NOT_FOUND);
        }
        if(serviceHardSoft.existsByName(dtoHardSoft.getName()) && serviceHardSoft.getByName(dtoHardSoft.getName()).get().getId() != id){
            return new ResponseEntity(new Message("Ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        }
        if(StringUtils.isBlank(dtoHardSoft.getName())){
            return new ResponseEntity(new Message("El campo no puede estar vacio"), HttpStatus.BAD_REQUEST);
        }
        
        Hardsoft hardSoft = serviceHardSoft.getOne(id).get();
        
        hardSoft.setName(dtoHardSoft.getName());
        hardSoft.setPerc(dtoHardSoft.getPerc());
        serviceHardSoft.save(hardSoft);
        
        return new ResponseEntity(new Message("HardSoft actualizado"), HttpStatus.OK);
    }
    
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id){
        if(!serviceHardSoft.existsById(id)){
            return new ResponseEntity(new Message("No existe el ID"), HttpStatus.NOT_FOUND);
        }
        serviceHardSoft.delete(id);
        return new ResponseEntity(new Message("HardSoft eliminado"), HttpStatus.OK);
    }
      
}

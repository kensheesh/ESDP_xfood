package kg.attractor.xfood.controller.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import kg.attractor.xfood.dto.CountryDto;
import kg.attractor.xfood.dto.LocationDto;
import kg.attractor.xfood.dto.okhttp.PizzeriasShowDodoIsDto;
import kg.attractor.xfood.dto.pizzeria.PizzeriaDto;
import kg.attractor.xfood.dto.user.UserDto;
import kg.attractor.xfood.dto.user.UserEditDto;
import kg.attractor.xfood.service.*;
import kg.attractor.xfood.service.impl.DtoBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("restAdminController")
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final AdminService adminService;
    private final UserService userService;
    private final DtoBuilder dtoBuilder;
    private final OkHttpService okHttpService;
    private final LocationService locationService;
    private final CountryService countryService;
    private final PizzeriaService pizzeriaService;

    @DeleteMapping("/user/{id}")
    public HttpStatus userDelete(@PathVariable Long id) {
        adminService.deleteUser(id);
        return HttpStatus.OK;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(dtoBuilder.buildUserDto(userService.findById(id)));
    }

    @PutMapping("/user")
    public HttpStatus editUser(@RequestBody UserEditDto userEditDto) {
        adminService.editUser(userEditDto);
        return HttpStatus.OK;
    }
    
    //-->
    @GetMapping("redis-pizzerias")
    public ResponseEntity<?> getPizzeriaFromForeignApi(@RequestParam @NotBlank String name) {
        List<PizzeriasShowDodoIsDto> p = okHttpService.getPizzeriasByMatch(name);
        return ResponseEntity.ok(p);
    }
    
    @PostMapping("load-pizzerias")
    public HttpStatus addPizzeriasByCountryCodes() {
        okHttpService.rewritePizzeriasToRedis();
        return HttpStatus.OK;
    }
    
    @GetMapping("/pizzerias")
    public ResponseEntity<?> getAllPizzerias() {
        return ResponseEntity.ok(pizzeriaService.getAllPizzerias());
    }
    
    @PostMapping("/pizzerias")
    public HttpStatus addPizzerias(@RequestBody @Valid PizzeriaDto dto) {
        pizzeriaService.add(dto);
        return HttpStatus.OK;
    }
    
    @PostMapping("/pizzerias/edit")
    public HttpStatus editPizzeria(@RequestBody @Valid PizzeriaDto dto) {
        pizzeriaService.edit(dto);
        return HttpStatus.OK;
    }
    
    //-->
    @GetMapping("/locations")
    public ResponseEntity<?> getLocations() {
        return ResponseEntity.ok(locationService.getAllLocations());
    }
    
    @PostMapping("/locations")
    public HttpStatus addLocation(@RequestBody @Valid LocationDto dto) {
        locationService.add(dto);
        return HttpStatus.OK;
    }
    
    @PostMapping("/locations/edit")
    public HttpStatus updateLocation(@RequestBody @Valid LocationDto dto) {
        locationService.edit(dto);
        return HttpStatus.OK;
    }
    //-->
    
    
    @GetMapping("/countries")
    public ResponseEntity<?> getCountries() {
        return ResponseEntity.ok(countryService.getCountries());
    }
    
    @PostMapping("/countries")
    public HttpStatus addCountry(@RequestBody @Valid CountryDto dto) {
        countryService.add(dto);
        return HttpStatus.OK;
    }
    
    @PostMapping("/countries/edit")
    public HttpStatus updateCountry(@RequestBody @Valid CountryDto dto) {
        countryService.edit(dto);
        return HttpStatus.OK;
    }
    
}

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
@PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
@RestController("restAdminController")
@RequestMapping("/api/admin")
@RequiredArgsConstructor
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

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(dtoBuilder.buildUserDto(userService.findById(id)));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
    @PutMapping("/user")
    public HttpStatus editUser(@RequestBody UserEditDto userEditDto) {
        adminService.editUser(userEditDto);
        return HttpStatus.OK;
    }

    //-->
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("redis-pizzerias")
    public ResponseEntity<?> getPizzeriaFromForeignApi(@RequestParam @NotBlank String name) {
        List<PizzeriasShowDodoIsDto> p = okHttpService.getPizzeriasByMatch(name);
        return ResponseEntity.ok(p);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("load-pizzerias")
    public HttpStatus addPizzeriasByCountryCodes() {
        okHttpService.rewritePizzeriasToRedis();
        return HttpStatus.OK;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/pizzerias")
    public ResponseEntity<?> getAllPizzerias() {
        return ResponseEntity.ok(pizzeriaService.getAllPizzerias());
    }
    
    @GetMapping("/pizzerias/{id}")
    public ResponseEntity<?> getPizzeria(@PathVariable @NotBlank String id) {
        return ResponseEntity.ok(pizzeriaService.getPizzeriaDtoById(Long.parseLong(id.trim())));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/pizzerias")
    public HttpStatus addPizzerias(@RequestBody @Valid PizzeriaDto dto) {
        pizzeriaService.add(dto);
        return HttpStatus.OK;
    }

    @PutMapping("/pizzerias/edit")
    public HttpStatus editPizzeria(@RequestBody @Valid PizzeriaDto dto) {
        pizzeriaService.edit(dto);
        return HttpStatus.OK;
    }

    //-->
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/locations")
    public ResponseEntity<?> getLocations() {
        return ResponseEntity.ok(locationService.getAllLocations());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/locations")
    public HttpStatus addLocation(@RequestBody @Valid LocationDto dto) {
        locationService.add(dto);
        return HttpStatus.OK;
    }

    @PreAuthorize("hasRole('ADMIN')")
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

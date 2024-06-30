package kg.attractor.xfood.stepdefs;

import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import kg.attractor.xfood.dto.pizzeria.PizzeriaDto;
import kg.attractor.xfood.service.PizzeriaService;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StepDefinitions {

    private PizzeriaDto pizzeriaDto;
    private PizzeriaService pizzeriaService;

    @Autowired
    public StepDefinitions(PizzeriaService pizzeriaService) {
        this.pizzeriaService = pizzeriaService;
    }

    @Когда("я запрашиваю пиццерию с id {int}")
    public void яЗапрашиваюПиццериюСId(int pizzeriaId) {
        var pizzeria = pizzeriaService.getPizzeriaDtoById(pizzeriaId);

        assertEquals(pizzeriaId, pizzeria.getId());
    }

    @Тогда("я получаю информацию по пиццерии")
    public void яПолучаюИнформациюПоПиццерии() {
        assertNotNull(pizzeriaDto);
    }
}

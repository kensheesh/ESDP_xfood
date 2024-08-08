//package kg.attractor.xfood.mockito.service;
//
//import kg.attractor.xfood.model.CriteriaPizzeria;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import static org.mockito.Mockito.*;
//
//public class CriteriaPizzeriaServiceTest {
////    @InjectMocks
////    private CriteriaPizzeriaServiceImpl criteriaPizzeriaService;
////    @Mock
////    private CriteriaPizzeriaRepository criteriaPizzeriaRepository;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testSave(){
//        CriteriaPizzeria criteriaPizzeria = new CriteriaPizzeria();
//        when(criteriaPizzeriaRepository.save(criteriaPizzeria)).thenReturn(criteriaPizzeria);
//        criteriaPizzeriaService.save(criteriaPizzeria);
//        verify(criteriaPizzeriaRepository, times(1)).save(criteriaPizzeria);
//    }
//}

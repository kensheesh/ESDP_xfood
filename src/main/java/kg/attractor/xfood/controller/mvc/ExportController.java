package kg.attractor.xfood.controller.mvc;

import jakarta.servlet.http.HttpServletResponse;
import kg.attractor.xfood.dto.checklist.CheckListAnalyticsDto;
import kg.attractor.xfood.service.CheckListService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/export")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SUPERVISOR','EXPERT','ADMIN')")
public class ExportController {

    private final CheckListService checkListService;
    
    @GetMapping("/excel")
    @PreAuthorize("hasAnyAuthority('admin:read','supervisor:read','expert:read')")
    public void exportAnalyticsToExcel(HttpServletResponse response,
                                       @RequestParam(name = "pizzeria", defaultValue = "default", required = false) String pizzeria,
                                       @RequestParam(name = "manager", defaultValue = "default", required = false) String manager,
                                       @RequestParam(name = "expert", defaultValue = "default", required = false) String expert,
                                       @RequestParam(name = "startDate", required = false) LocalDate startDate,
                                       @RequestParam(name = "endDate", required = false) LocalDate endDate
    ) throws IOException {
        List<CheckListAnalyticsDto> checklists = checkListService.getAnalytics(
                pizzeria, manager, expert, startDate, endDate
        );

        if (!checklists.isEmpty()) {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Analytics");

            Row headerRow = sheet.createRow(0);
            String[] columns = {"Пиццерия", "Менеджер", "Эксперт", "Дата", "Результат %"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            int rowNum = 1;
            for (CheckListAnalyticsDto checklist : checklists) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(checklist.getPizzeria().getName());
                row.createCell(1).setCellValue(checklist.getManager().getName() + " " + checklist.getManager().getSurname());
                row.createCell(2).setCellValue(checklist.getExpert().getName() + " " + checklist.getExpert().getSurname());
                row.createCell(3).setCellValue(checklist.getDate().toString());
                row.createCell(4).setCellValue(checklist.getResult());
            }

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=analytics.xlsx");

            workbook.write(response.getOutputStream());
            workbook.close();
        } else {
            throw new IllegalArgumentException("No checklists found");
        }
    }
}
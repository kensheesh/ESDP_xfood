package kg.attractor.xfood.dao;

import kg.attractor.xfood.enums.Status;
import kg.attractor.xfood.model.CheckList;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Component
@RequiredArgsConstructor
public class CheckListDao {

    private final JdbcTemplate jdbcTemplate;

    public void updateStatus(Status status, CheckList checkList) {
        String sql = "update check_lists set status = ? where id = ?";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setObject(1, status, java.sql.Types.OTHER);
            ps.setLong(2, checkList.getId());
            return ps;
        });
    }
}

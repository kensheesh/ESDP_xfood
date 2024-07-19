package kg.attractor.xfood.dao;

import kg.attractor.xfood.enums.Status;
import kg.attractor.xfood.model.CheckList;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Time;
import java.sql.Timestamp;

@Component
@RequiredArgsConstructor
public class CheckListDao {

    private final JdbcTemplate jdbcTemplate;

    public void updateStatusToDone(Status status, CheckList checkList) {
        String sql = "update check_lists set status = ?, duration = ?, end_time = ? where id = ?";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setObject(1, status, java.sql.Types.OTHER);
            ps.setTime(2, Time.valueOf(checkList.getDuration()));
            ps.setTimestamp(3, Timestamp.from(checkList.getEndTime()));
            ps.setLong(4, checkList.getId());
            return ps;
        });
    }

    public void updateStatusToInProgress(Status status, CheckList checkList){
        String sql = "update check_lists set status = ? where id = ?";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setObject(1, status, java.sql.Types.OTHER);
            ps.setLong(2, checkList.getId());
            return ps;
        });
    }
}

package mx.com.metalsa.soap.web.services.integrator.db.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@IdClass(TblControlId.class)
public class TblControl {

    @Id
    private String module;
    @Id
    private String tableName;
    @Column
    private String reportAbsolutePath;
    @Column
    private Integer tiempo;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getReportAbsolutePath() {
        return reportAbsolutePath;
    }

    public void setReportAbsolutePath(String reportAbsolutePath) {
        this.reportAbsolutePath = reportAbsolutePath;
    }

    public Integer getTiempo() {
        return tiempo;
    }

    public void setTiempo(Integer tiempo) {
        this.tiempo = tiempo;
    }
}

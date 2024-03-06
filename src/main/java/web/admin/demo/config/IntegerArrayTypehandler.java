package web.admin.demo.config;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;

public class IntegerArrayTypehandler extends BaseTypeHandler<int[]> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, int[] parameter, JdbcType jdbcType) throws SQLException {
        Integer[] array = new Integer[parameter.length];
        for (int j = 0; j < parameter.length; j++) {
            array[j] = parameter[j];
        }
        Array sqlArray = ps.getConnection().createArrayOf("INTEGER", array);
        ps.setArray(i, sqlArray);
    }

    @Override
    public int[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return toArray(rs.getArray(columnName));
    }

    @Override
    public int[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return toArray(rs.getArray(columnIndex));
    }

    @Override
    public int[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return toArray(cs.getArray(columnIndex));
    }

    private int[] toArray(Array array) throws SQLException {
        if (array == null)
            return null;
        return (int[]) array.getArray();
    }
}

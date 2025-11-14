package com.adoumadje.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ResultSetExtractor<T> {
    T extract(ResultSet res);

    List<T> extractList(ResultSet res) throws SQLException;
}

package com.eh.kotlin.ehktdbhelper

import com.eh.kotlin.ehktdbhelper.DBInfo
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

class ConnectionManager(dbInfo: DBInfo) {
    // JDBC driver name and database URL
    val JDBC_DRIVER = dbInfo.JDBC_DRIVER
    val DB_URL = dbInfo.DB_URL

    //  Database credentials
    val USER = dbInfo.USER
    val PASS = dbInfo.PASS

    fun query(sql: String, handleRS: (rs: ResultSet) -> Any): Any {
        Class.forName(JDBC_DRIVER)
        val myConn = connection()
        val stat = myConn.createStatement()
        val rs = stat.executeQuery(sql)
        val result = handleRS(rs)
        stat.close()
        myConn.close()
        return result
    }

    fun connection(): Connection {
        return DriverManager.getConnection(
            DB_URL,
            USER,
            PASS
        );
    }

    fun closeConnection(conn: Connection) {
        conn.close()
    }
}
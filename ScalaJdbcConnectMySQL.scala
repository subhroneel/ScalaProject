import java.sql.Connection
import java.sql.DriverManager

object ScalaJdbcConnectMySQL {
  def main(args: Array[String]): Unit = {
      val driver = "com.mysql.cj.jdbc.Driver"
      val url = "jdbc:mysql://127.0.0.1/greendb"
      val userName = "neel"
      val password = "P@ssw0rd"
      var connection:Connection = null
      try {
        connection = DriverManager.getConnection(url, userName, password)
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("select emp_code, emp_name from employee_master")
        while(resultSet.next())
          {
            val empCode = resultSet.getString("emp_code")
            val empName = resultSet.getString("emp_name")
            println("Employee Code = " + empCode + ", Employee Name = " + empName)
          }
        connection.close()
      }
      catch {
        case e: Throwable => e.printStackTrace()
      }
  }
}       

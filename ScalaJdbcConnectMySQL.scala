import java.sql.Connection
import java.sql.DriverManager
import java.io._

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
        val metaData = resultSet.getMetaData
        val columnCount = metaData.getColumnCount
        val fileObject = new File("/home/subhroneel/Documents/emp_mast.csv")
        val printWriter = new PrintWriter(fileObject)
        var colNames = ""
        for (i <- 1 to columnCount) {
          colNames = colNames.concat(metaData.getColumnName(i).toUpperCase())
          if(i<columnCount) {
            colNames = colNames.concat(",")
          }
        }
        printWriter.write(colNames + "\n")
        while(resultSet.next())
          {
            val empCode = resultSet.getString("emp_code")
            val empName = resultSet.getString("emp_name")
//            println("Employee Code = " + empCode + ", Employee Name = " + empName)
            printWriter.write(empCode + "," + empName + "\n")
          }
        printWriter.close()
        connection.close()
      }
      catch {
        case e: Throwable => e.printStackTrace()
      }
  }
}
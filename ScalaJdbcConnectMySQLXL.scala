import java.sql.{Connection, DriverManager}
import org.apache.poi.xssf.usermodel.{XSSFWorkbook, /*XSSFSheet, XSSFRow,*/ XSSFCell}
import java.io.FileOutputStream

object ScalaJdbcConnectMySQLXL {
  def main(args: Array[String]): Unit = {
      val url = "jdbc:mysql://127.0.0.1/greendb"
      val userName = "neel"
      val password = "P@ssw0rd"
      var connection:Connection = null
      val workbook = new XSSFWorkbook()
      val sheet = workbook.createSheet("emp_mast")
      try {
        val driver = "com.mysql.cj.jdbc.Driver"
        Class.forName(driver)
        connection = DriverManager.getConnection(url, userName, password)
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("select emp_code, emp_name, case when emp_grade='W' then 'Worker' else 'Staff' end emp_grade from employee_master")
        val metaData = resultSet.getMetaData
        val columnCount = metaData.getColumnCount
        val row = sheet.createRow(0)
        for (i <- 1 to columnCount) {
            val cell = row.createCell(i-1)
            cell.setCellValue(metaData.getColumnName(i).toUpperCase())
        }
        var cell:XSSFCell = null
        var r: Int = 1
        while(resultSet.next())
          {
            val row = sheet.createRow(r)
            cell = row.createCell(0)
            cell.setCellValue(resultSet.getString("emp_code"))
            cell = row.createCell(1)
            cell.setCellValue(resultSet.getString("emp_name"))
            cell = row.createCell(2)
            cell.setCellValue(resultSet.getString("emp_grade"))
            r+=1
          }
        val fileOut = new FileOutputStream("/home/subhroneel/Documents/greendb_new.xlsx")
        workbook.write(fileOut)
        fileOut.close()
        workbook.close()
        connection.close()
      }
      catch {
        case e: Throwable => e.printStackTrace()
      }
  }
}

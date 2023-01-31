import java.nio.file.{FileSystems, Files}

object Main {
  def main(args: Array[String]): Unit = {
    val dir = FileSystems.getDefault.getPath(args(0))
    val files = Files.list(dir)
      .toArray()
      .map(f => f.toString)
      .filter(p => p.endsWith(".csv"))
      .toList

    val processor = new SensorDataProcessor(files)
    processor.process

    println("Num of processed files: " + processor.getNumOfProcessedFiles)
    println("Num of processed measurements: " + processor.getNumOfProcessedMeasurements)
    println("Num of failed measurements: " + processor.getNumOfFailedMeasurements)


    println("                                   ")
    println("Sensors with highest avg humidity: ")
    println("                                   ")

    println("sensor-id,min,avg,max")
    processor.getSensorDataWithAvg.foreach(data => {
      if(data.areAllZero()) {
        println(data.sensorId + "," + "NaN" + "," + "NaN" + "," + "NaN")
      }
      else {
        println(data.sensorId + "," + data.min + "," + data.avg + "," + data.max)
      }
    })
  }
}
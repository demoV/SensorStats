import com.github.tototoshi.csv.CSVReader

import java.io.File

class SensorDataProcessor(files: List[String]) {
  private var numOfFailedMeasurements = 0
  private var numOfProcessedMeasurements = 0

  val transformer: SensorsDataTransformer = new SensorsDataTransformer

  def process: Unit = {
    files.foreach(filePath => {
      val reader = CSVReader.open(new File(filePath))
      reader.allWithHeaders().foreach(data => {
        val isAdded = transformer.add(data)
        if(!isAdded) {
          numOfFailedMeasurements += 1
        }
        numOfProcessedMeasurements += 1
      })
      reader.close()
    })
  }

  def getNumOfProcessedFiles : Int = {
    files.length
  }

  def getNumOfFailedMeasurements : Int = {
    numOfFailedMeasurements
  }

  def getNumOfProcessedMeasurements : Int = {
    numOfProcessedMeasurements
  }

  def getSensorDataWithAvg: List[SensorData] = {
    transformer.getSensorsDataWithAvg
  }
}

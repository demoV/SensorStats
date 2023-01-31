import scala.collection.mutable.HashMap

class SensorsDataTransformer {

  private val sensorsHumidity = new HashMap[String, List[Int]]

  def add(sensorData: Map[String, String]) : Boolean = {
    val sensorId = sensorData("sensor-id")
    val humidity = sensorData("humidity")


    if (!sensorsHumidity.contains(sensorId)) {
      sensorsHumidity.put(sensorId, List())
    }

    if (humidity == "NaN") {
      return false
    }
    val humidityList = sensorsHumidity(sensorId).::(humidity.toInt)
    sensorsHumidity.put(sensorId, humidityList)
    true
  }

  def numOfSensors : Int = {
    sensorsHumidity.keySet.size
  }

  def getSensorsDataWithAvg: List[SensorData] = {
    var sensorDataList: List[SensorData] = List()
    var sensorEmptyData: List[SensorData] = List()
    sensorsHumidity.foreach(sensorHumidity => {
      val humidities: List[Int] = sensorHumidity._2
      if (humidities.isEmpty) {
        val emptyData = SensorData(sensorHumidity._1, 0, 0, 0)
        sensorEmptyData = sensorEmptyData.::(emptyData)
      }
      else {
        val data: SensorData = SensorData(sensorHumidity._1, humidities.min, humidities.max, humidities.sum / humidities.length)
        sensorDataList = sensorDataList.::(data)
      }
    })
    sensorDataList.sortWith((current, next) => current.avg > next.avg)
    sensorDataList ::: sensorEmptyData
  }

}

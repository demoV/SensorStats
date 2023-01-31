case class SensorData(sensorId: String, min: Int, max: Int, avg: Int) {
  def areAllZero(): Boolean = {
    avg == 0 && min == 0 && max == 0
  }
}

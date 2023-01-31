import org.scalatest.funsuite.AnyFunSuite

class SensorsDataTransformerTest extends AnyFunSuite {

  test("add should add the sensor data and return true") {
    val sensorData = Map("sensor-id" -> "s1", "humidity" -> "80")
    val processor = new SensorsDataTransformer
    val isAdded = processor.add(sensorData)
    assert(isAdded)
  }

  test("add should not add the sensor data with humidity as NaN") {
    val sensorData = Map("sensor-id" -> "s1", "humidity" -> "NaN")
    val processor = new SensorsDataTransformer
    val isAdded = processor.add(sensorData)
    assert(!isAdded)
  }

  test("numOfSensors should give number of sensors processed") {
    val sensorData = Map("sensor-id" -> "s1", "humidity" -> "NaN")
    val sensorData1 = Map("sensor-id" -> "s2", "humidity" -> "80")
    val sensorData2 = Map("sensor-id" -> "s3", "humidity" -> "80")
    val processor = new SensorsDataTransformer
    processor.add(sensorData)
    processor.add(sensorData1)
    processor.add(sensorData2)
    assert(3 == processor.numOfSensors)
  }

  test("getSensorsDataWithAvg should give sensor data with min, max and avg") {
    val sensorData = Map("sensor-id" -> "s1", "humidity" -> "75")
    val sensorData1 = Map("sensor-id" -> "s1", "humidity" -> "75")
    val sensorData2 = Map("sensor-id" -> "s2", "humidity" -> "80")
    val sensorData3 = Map("sensor-id" -> "s2", "humidity" -> "90")

    val processor = new SensorsDataTransformer
    processor.add(sensorData)
    processor.add(sensorData1)
    processor.add(sensorData2)
    processor.add(sensorData3)

    val expectedSensorInfoS1 = SensorData("s1", 75, 75, 75)
    val expectedSensorInfoS2 = SensorData("s2", 80, 90, 85)

    val sensorsDataWithAvg = processor.getSensorsDataWithAvg
    assert(expectedSensorInfoS2 == sensorsDataWithAvg(0))
    assert(expectedSensorInfoS1 == sensorsDataWithAvg(1))
  }

  test("getSensorsDataWithAvg should give sensor data with 0 if NaN") {
    val sensorData = Map("sensor-id" -> "s1", "humidity" -> "NaN")
    val sensorData1 = Map("sensor-id" -> "s1", "humidity" -> "NaN")
    val sensorData2 = Map("sensor-id" -> "s2", "humidity" -> "80")
    val sensorData3 = Map("sensor-id" -> "s2", "humidity" -> "90")

    val processor = new SensorsDataTransformer
    processor.add(sensorData)
    processor.add(sensorData1)
    processor.add(sensorData2)
    processor.add(sensorData3)

    val expectedSensorInfoS1 = SensorData("s1", 0, 0, 0)
    val expectedSensorInfoS2 = SensorData("s2", 80, 90, 85)

    val sensorsDataWithAvg = processor.getSensorsDataWithAvg
    assert(expectedSensorInfoS2 == sensorsDataWithAvg(0))
    assert(expectedSensorInfoS1 == sensorsDataWithAvg(1))
  }
}

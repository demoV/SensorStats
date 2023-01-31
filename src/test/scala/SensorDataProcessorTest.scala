import org.scalatest.funsuite.AnyFunSuite

class SensorDataProcessorTest extends AnyFunSuite{

  test("process should read csv and get the measurements details") {
    val files = List("src/test/testData/sensorDataFirstTest.csv", "src/test/testData/sensorDataSecondTest.csv")
    val reader = new SensorDataProcessor(files)

    reader.process

    val s2Data = SensorData("s2", 78, 88, 82)
    val s1Data = SensorData("s1", 10, 98, 54)
    val s3Data = SensorData("s3", 0, 0, 0)

    val expectedSensorsData = List(s2Data, s1Data, s3Data)

    assert(reader.getNumOfProcessedFiles == 2)
    assert(reader.getNumOfProcessedMeasurements == 7)
    assert(reader.getNumOfFailedMeasurements == 2)
    assert(reader.getSensorDataWithAvg == expectedSensorsData)
  }
}

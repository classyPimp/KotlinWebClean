export class TimeUtils {

  static timestampStringToReadableDateString(timestampString: string, format: string = null): string {
    if (!timestampString) {
      return null
    }
    let date = new Date(timestampString)
    let day = date.getDate()
    let month = date.getMonth() + 1
    let year = date.getFullYear()
    return `${this.toTwoDigitString(day)}.${this.toTwoDigitString(month)}.${year}`
  }

  static stringToTimeStampString(valueTotransform: string, format: string = null) {
    if (!valueTotransform) {
      return
    }
    let parts = valueTotransform.split(".")
    let date = new Date()
    try {
      let year = parts[2]
      let month = parts[1]
      let day = parts[0]
      return `${date.getFullYear()}-${month}-${day} 00:00:00.000`
    } catch {
      alert("invalid date was put in")
    }
  }

  static toTwoDigitString(value: number): string {
    if (value < 10) {
      return `${0}${value}`
    } else {
      return `${value}`
    }
  }

}
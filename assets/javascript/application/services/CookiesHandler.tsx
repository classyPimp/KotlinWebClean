export class CookieHandler {

    static convertToObject(): {[id:string]: string}{
        let cookiesString = document.cookie
        let parts = cookiesString.split(";")
        let objectifiedCookies = {}
        if (parts[0].length < 3) {
            return objectifiedCookies
        }
        parts.forEach((part)=>{
            this.parsePart(part, objectifiedCookies)
        })
        return objectifiedCookies
    }

    static parsePart(part: string, objectifiedCookies: {[id:string]: string}){
        let pair = part.split("=")
        let key = pair[0]
        let value = pair[1]
        objectifiedCookies[key] = value
    }

}
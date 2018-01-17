

export class FileUploadUtils {


    static constructChunkedIdPath(id: number): string {
        let stringifiedId = `${id}`
        let idLength = stringifiedId.length
        let firstMissingChars = Array(10-idLength).join("0")       
        return this.chunkIdString(`${firstMissingChars}${stringifiedId}`, 3)
    }

    private static chunkIdString(str: string, n: number): string {
        var ret = [];
        var i;
        var len;
        for(i = 0, len = str.length; i < len; i += n) {
           ret.push(str.substr(i, n))
        }
        return ret.join("/")
    }

}
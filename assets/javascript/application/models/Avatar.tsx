import { User } from './User';
import { HasOne } from '../../modelLayer/annotations/HasOne';
import { BaseModel } from '../../modelLayer/BaseModel';
import { Property } from '../../modelLayer/annotations/Property';
import  { ModelRegistry } from '../../modelLayer/ModelRegistry' 

export class Avatar extends BaseModel {

    static className = "avatar"

    @Property
    id: number

    @Property
    fileName: string

    @HasOne("User")
    user: User

    cachedFilePath: string

    fileUrl(namespace: string): string {
        if (namespace) {
        
            if (this.cachedFilePath) {
                return this.cachedFilePath
            } else {
                this.cachedFilePath = `uploads/avatars/file/${this.constructChunkedIdPath(this.id)}/namespace/${this.fileName}`
                return this.cachedFilePath
            }
        } else {
            this.cachedFilePath = `uploads/avatars/file/${this.constructChunkedIdPath(this.id)}/original/${this.fileName}`
            return this.cachedFilePath
        }
    }    

    constructChunkedIdPath(id: number): string {
        let stringifiedId = `${id}`
        let idLength = stringifiedId.length
        let firstMissingChars = Array(10-idLength).join("0")       
        return this.chunkIdString(`${firstMissingChars}${stringifiedId}`, 3)
    }

    chunkIdString(str: string, n: number): string {
        var ret = [];
        var i;
        var len;
        for(i = 0, len = str.length; i < len; i += n) {
           ret.push(str.substr(i, n))
        }
        return ret.join("/")
    }

}

ModelRegistry.register("Avatar", Avatar)
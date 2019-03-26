import http from '@/util/http'

export default class FileCountService {
    constructor(){
        this.basePath = '/file-server/count';
    }
    getClassifiedStatistical(){
        return http.get(this.basePath + '/getClassifiedStatistical');
    }
}
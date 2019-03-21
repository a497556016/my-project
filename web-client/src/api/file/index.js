import BaseService from "../base";

export default class FileService extends BaseService {
    constructor(){
        super('/file-server');
    }

    upload(data) {
        return this.post('/upload', data);
    }
}
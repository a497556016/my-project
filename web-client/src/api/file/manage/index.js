import BaseService from "../../base";

export default class FileManageService extends BaseService{
    constructor(){
        super('/file-server/file-base-info')
    }
}
import BaseService from "../base";

export default class ExtendTableService extends BaseService{


    constructor(){
        super('/core/extend-table');
    }

    createTable(tableInfo){
        return this.post('/createTable', tableInfo);
    }
    getDatabaseTables(){
        return this.get('/getDatabaseTables');
    }
    saveFromDatabaseTable(tableInfo){
        return this.post('/saveFromTable', null, {
            params: tableInfo
        });
    }
    batchDelete(keys){
        return this.put('/batchDelete', keys);
    }
    createServerCode(id){
        return this.get('/createForm', {id: id});
    }
}
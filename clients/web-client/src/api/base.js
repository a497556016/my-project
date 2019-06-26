import http from '@/util/http'

export default class BaseService {


    constructor(
        basePath
    ){
        this.basePath = basePath;
    }


    selectPage(params){
        return this.get('/selectPage', params);
    }

    selectById(id) {
        return this.get('/selectById', {id: id});
    }

    save(data){
        return this.post('/save', data);
    }

    update(data) {
        return this.put('/update', data);
    }

    deleteById(id) {
        return this.delete('/delete', {id: id});
    }

    batchDelete(ids) {
        return this.put('/batchDelete', ids);
    }

    get(url, params, config = {}){
        config.params = params;
        return http.get(this.basePath + url, config);
    }

    post(url, data, config) {
        return http.post(this.basePath + url, data, config);
    }

    put(url, data, config) {
        return http.put(this.basePath + url, data, config);
    }

    delete(url, params, config = {}){
        config.params = params;
        return http.delete(this.basePath + url, config);
    }
}
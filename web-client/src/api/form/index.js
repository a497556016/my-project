import BaseService from "../base";

export default class CustomerFormService extends BaseService{
    constructor() {
        super('/form-server/');
    }

    save(form){
        return this.post('customerForm/save', form);
    }

    selectPage(params){
        return this.get('customerForm/selectPage', params);
    }

    selectById(id) {
        return this.get('customerForm/selectById', {id: id});
    }

    selectUserFormById(id) {
        return this.get('userForm/selectById', {id: id});
    }

    saveUserForm(form) {
        return this.post('userForm/save', form);
    }
}
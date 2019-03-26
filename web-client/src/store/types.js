
//用户账户
import accountTypes from './modules/account/types'

//用户管理
import * as userTypes from './modules/user/types'
import * as userEditTypes from './modules/user/edit/types'
import * as userAddTypes from './modules/user/add/types'

//界面布局
import * as layoutTypes from './modules/layout/types'

//文件系统
import fileTypes from './modules/file/types'
//文件系统首页
import * as fileMainTypes from './modules/file/main/types'


const _export = function (module, m) {

    const _export = {}

    for(let f in module){
        const fields = module[f];

        if(typeof fields === 'object') {
            const obj = {};
            for(let k in fields) {
                obj[k] = m + '/' + fields[k];
            }

            _export[f] = obj;
        }else {
            _export[f] = m + '/' + fields;
        }
    }

    console.log(_export);

    return _export;
};

export const account = _export(accountTypes, 'account');
export const user = _export(userTypes, 'user');
export const userEdit = _export(userEditTypes, 'userEdit');
export const userAdd = _export(userAddTypes, 'userAdd');
export const layout = _export(layoutTypes, 'layout');
export const file = _export(fileTypes, 'file');
export const fileMain = _export(fileMainTypes, 'fileMain');



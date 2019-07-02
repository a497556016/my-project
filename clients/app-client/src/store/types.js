export const GET_LOGIN_USER = "GET_LOGIN_USER";
export const SAVE_LOGIN_USER = "SAVE_LOGIN_USER";
export const LOGIN_BY_PHONE_CODE = "LOGIN_BY_PHONE_CODE";
export const LOGIN_BY_ACCOUNT = "LOGIN_BY_ACCOUNT";
export const LOGOUT_CURRENT_USER = "LOGOUT_CURRENT_USER";

//home
export const SET_TOP_IMAGES = "SET_TOP_IMAGES";
export const LOAD_TOP_IMAGES = "LOAD_TOP_IMAGES";
export const GET_COMMEND_POSTS = "GET_COMMEND_POSTS";

//menus
export const GET_MENU_ROW_COLS = "GET_MENU_ROW_COLS";


const moduleTypes = {
    'account': {
        GET_LOGIN_USER,
        SAVE_LOGIN_USER,
        LOGIN_BY_PHONE_CODE,
        LOGIN_BY_ACCOUNT,
        LOGOUT_CURRENT_USER
    },
    'home': {
        GET_MENU_ROW_COLS,
        SET_TOP_IMAGES,
        LOAD_TOP_IMAGES,
        GET_COMMEND_POSTS
    },
    'menus': {
        GET_MENU_ROW_COLS
    }
}

for (let module in moduleTypes) {
    const types = moduleTypes[module];

    for (let key in types) {
        const type = types[key];
        types[key] = module + '/' + type; //取带命名空间的types
    }

    types['value'] = module;//取模块名称
}

console.log('mapTypes', moduleTypes)

export const mapTypes = moduleTypes;
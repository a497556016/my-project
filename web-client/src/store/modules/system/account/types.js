export default {
    mutations: {
        LOGOUT: "LOGOUT",
        LOGIN: "LOGIN",
        SET_TOKEN: "SET_TOKEN",
    },
    getters: {
        GET_USER_INFO: "GET_USER_INFO",
        GET_AVATAR: "GET_AVATAR"
    },
    actions: {
        REFRESH_TOKEN: "REFRESH_TOKEN",
        REGISTER_USER: "REGISTER_USER"
    }
}
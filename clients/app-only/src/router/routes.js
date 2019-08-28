export default [
    {path: '/', redirect: '/chat'},
    {path: '/chat', component: () => import('@/views/Chat')},
    {path: '/search_user', component: () => import('@/views/SearchUser')},
    {path: '/login', component: () => import("@/views/Login")},
    {path: '/register', component: () => import("@/views/Register")}
]
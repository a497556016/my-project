
const routes = [
    {
        path: '/',
        redirect: { path: '/home' }
    },
    {
        path: '/login',
        component: () => import('@/views/Login.vue')
    },
    {
        path: '/home',
        component: () => import('@/views/Home')
    }
]

export default routes;
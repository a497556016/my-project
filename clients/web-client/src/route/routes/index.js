
const routes = [
    {
        path: '/',
        redirect: { path: '/home' }
    },
    {
        path: '/login',
        component: () => import('@/views/Login_v2.vue')
    },
    {
        path: '/home',
        component: () => import('@/views/Home')
    },
    {
        path: '/form/share/pc/:formId',
        component: () => import('@/views/form/share/PC'),
        props: true
    },
    {
        path: '/form/share/phone/:formId',
        component: () => import('@/views/form/share/Phone'),
        props: true
    }
]

export default routes;
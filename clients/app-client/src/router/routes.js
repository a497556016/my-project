export default [
    {path: '/', redirect: '/main'},
    {path: '/main', component: () => import('@/views/Main')}
]
export default [
    {path: '/', redirect: '/main/home'},
    {
        path: '/main',
        component: () => import('@/views/main'),
        children: [
            {path: '/main/home', component: () => import('@/views/main/home')},

            {path: '/main/user', component: () => import('@/views/main/user')},
            {path: '/main/user/edit', component: () => import('@/views/main/user/edit')},
        ]
    },
    {path: '/login', component: () => import('@/views/login/AccountLogin')},
    {path: '/login/phone', component: () => import('@/views/login/PhoneLogin')},
    {path: '/login/verifyCode', component: () => import('@/views/login/VerifyCodeInput')},
]
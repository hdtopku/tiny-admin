import {createRouter, createWebHistory} from "vue-router";
import {useMenuStore, useUserStore} from "@/store";

const routes = [{
    path: '/',
    component: () => import('@/layout/Index.vue')
}, {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login.vue')
},
    {
        path: '/',
        component: () => import('@/layout/Index.vue'),
        children: [
            {
                path: '403',
                component: () => import('@/views/403.vue'), // 注意看这里，替换成了 403 页面组件
            }
        ]
    },
    {
        // hide: true,
        path: '/:all(.*)*',
        component: () => import(`@/views/404.vue`),
    }
]
const router = createRouter({
    history: createWebHistory(),
    routes
})
const whiteList = ['/login']

let routeList, publicRouteList
router.beforeEach((to, from, next) => {
    // NProgress.start()
    const token = localStorage.getItem('token')
    if (whiteList.includes(to.path)) {
        if (to.path === '/login' && token?.length) {
            return next('/')
        }
        return next()
    }
    if (token?.length) {
        if (!routeList) {
            if (from.path !== '/login') {
                useUserStore().refreshUserInfo()
            }
            routeList = useUserStore().getRouteList()
            publicRouteList = useUserStore().userInfo.publicMenuList?.filter(item => item.type === 1)
            if (routeList?.length) {
                router.addRoute(
                    {
                        component: () => import('@/layout/Index.vue'),
                        path: "/",
                        name: "Layout",
                        children: routeList,
                    }
                )
            }
            return next(to)
        } else {
            const path = to.path?.split('/').filter(item => item.length).join('/').toLowerCase()
            if (to.matched.length === 1 && to.matched[0].path.endsWith('(.*)*') && publicRouteList?.find((item: any) => path === item.url?.split('/').join('/').toLowerCase())) {
                return next('/403')
            }
            return next()
        }
    } else {
        return next('/login')
    }
})
router.afterEach((to) => {
    useMenuStore().addTab(to)

    // NProgress.done()
    window.scrollTo(0, 0)
})

export default router
